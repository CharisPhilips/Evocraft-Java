package com.kilcote.evocraft.engine;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.views.WindowFrame;
import com.kilcote.evocraft.views.game.JavaClient;
import com.kilcote.evocraft.views.game._base.mapping.IFactoryUI;
import com.kilcote.evocraft.views.game._base.mapping.JavaUI;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class JavaGameEngine extends AnimationTimer {

	private GameEngine engine;
	private JavaGameMap gameMap;
	private IFactoryUI mUIGeneartor = new JavaUI();

	private GridPane mainGrid;
	private Long lastUpdate = null;
	private String winner = null;
	
	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaGameEngine(GridPane gamePage, int x, int y) {
		mainGrid = gamePage;
		this.engine = new GameEngine(x, y);
		FillIOWindow();
	}

	public IFactoryUI getUIGeneartor() {
		return mUIGeneartor;
	}
	
	//---------------------------------------------- Methods ----------------------------------------------
	
	@Override
	public void handle(long now) {
		if (lastUpdate == null || (TimeUnit.MILLISECONDS.convert((now - lastUpdate), TimeUnit.NANOSECONDS) > StandaloneSettings.milisecondsPerTick)) {
			Loop();
			lastUpdate = now;
		}
	}

	public void Play() {
		this.engine.Play();
		CreateGameMap();
		CreateGameBots();
		if (mUIGeneartor instanceof JavaUI) {
			InitializeDraw();
		}
		this.start();
	}
	
	public void Pause() {
		this.engine.Pause();
		this.stop();
	}

	public void Resume() {
		this.engine.Resume();
		this.start();
	}

	private void CreateGameMap() {
		this.engine.CreateGameMap();
		gameMap = new JavaGameMap(this.engine.getGameMap(), this);
		this.engine.setGameMap(gameMap);
		StandaloneSettings.oneCellSizeX = 10;
		StandaloneSettings.oneCellSizeY = 10;

		mUIGeneartor.generateGameMap(gameMap);
		gameMap.setJavaGameEngine(this);
	}
	
	private void CreateGameBots() {
		this.engine.CreateGameBots(gameMap);
	}
	
	private void InitializeDraw() {
		gameMap.getUI().setParent(mainGrid);
		gameMap.getUI().Initialize();
	}

	private void Loop() {
		if (mUIGeneartor instanceof JavaUI) {
			gameMap.getUI().InvalidateDraw();
		}
		this.engine.Loop();
		WinProcess();
	}

	private void FillIOWindow() {
		for (int i = 0; i < engine.getX(); i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(100.0 / engine.getX());
			mainGrid.getColumnConstraints().add(column);
		}
		for (int i = 0; i < engine.getY(); i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0 / engine.getY());
			mainGrid.getRowConstraints().add(row);
		}
	}
	
	private void WinProcess() {
		Integer id = engine.IsWin();
		if (id != null) {
			this.stop();
			JavaClient.clearSelectCities();
			mainGrid.getChildren().clear();
			if (id == 1) {
				winner = "You win!";
			} else {
				winner = "Bot win! Seems like you looser";
			}

			Platform.runLater(new Runnable() {
				@Override public void run() {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Info");
					alert.setHeaderText("Do you want to play again?");
					alert.setContentText("Winner: " + winner);
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						JavaGameEngine.this.Play();
					} else {
						WindowFrame.EXIT_PROGRAM();
					}
				}
			});
		}
	}

}
