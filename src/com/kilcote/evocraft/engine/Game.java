package com.kilcote.evocraft.engine;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.bot.RushBot;
import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.city.BasicCityPlacer;
import com.kilcote.evocraft.engine.map.mapGenerators.city.SityPlacer14;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.CityIdDiffCorners;
import com.kilcote.evocraft.engine.map.mapGenerators.road.BasicMapGenerator;
import com.kilcote.evocraft.engine.map.mapGenerators.road.TunnelMapGenerator;
import com.kilcote.evocraft.views.WindowFrame;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Game extends AnimationTimer {
	public static long tick = 1;

	private GameMap gameMap;
	private GridPane mainGrid;
	private boolean isPlay, isNeedToExit;
	private int x, y;

	private Timeline timeline = null;
	private Long lastUpdate = null;

	public Game(GridPane gamePage, int X, int Y) {
		isPlay = true;
		isNeedToExit = false;
		mainGrid = gamePage;
		this.x = X;
		this.y = Y;
		FillIOWindow();

	}

	@Override
	public void handle(long now) {
		if (lastUpdate == null || (TimeUnit.MILLISECONDS.convert((now - lastUpdate), TimeUnit.NANOSECONDS) > Settings.milisecondsPerTick)) {
			Loop();
			lastUpdate = now;
		}
	}

	public void Play() {
		isPlay = true;
		Game.tick = 1;

		CreateGameMap();
		InitGameMap();
		this.start();
	}

	private void FillIOWindow() {
		for (int i = 0; i < x; i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(100.0 / x);
			mainGrid.getColumnConstraints().add(column);
		}
		for (int i = 0; i < y; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0 / y);
			mainGrid.getRowConstraints().add(row);
		}
	}

	private void CreateGameMap() {

		Settings.oneCellSizeX = 10;
		Settings.oneCellSizeY = 10;

		Settings.seed = (long)System.currentTimeMillis();

		gameMap = GenerateRandomMap(
				x, y,
				new TunnelMapGenerator(),
				new SityPlacer14(),
				new CityIdDiffCorners()
				);

		for (int i = 0; i < Settings.generator_CityId_Bots; ++i) {
			gameMap.SetBot(i, new RushBot(gameMap, gameMap.cities, gameMap.units, (byte)(i + 2)));
		}
	}

	private void InitGameMap() {
		gameMap.SetCanvas(mainGrid);
		gameMap.DrawStatic(mainGrid);
	}

	private void Loop() {
		this.tick++;
		gameMap.InvalidateDraw();
		gameMap.Tick();
		WinProcess();
	}

	private void WinProcess() {
		int id = 0;

		if (IsWin(id)) {
			this.stop();
			mainGrid.getChildren().clear();
			isPlay = false;

			String winner = "";
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
					alert.setContentText("Winner: winner");
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						Game.this.Play();
					} else {
						WindowFrame.EXIT_PROGRAM();
					}
				}
			});
		}
	}

	private boolean IsWin(int id) {
		for (BasicCity city : gameMap.cities) {
			if (city.playerId != 0) {
				id = city.playerId;
				break;
			}
		}
		for (BasicCity city : gameMap.cities) {
			if (city.playerId != 0 && city.playerId != id) {
				return false;
			}
		}
		return true;
	}

	//----------------------------------------------Static Methods ----------------------------------------------
	public static GameMap GenerateRandomMap(int SizeX, int SizeY, BasicMapGenerator mapGenerator, BasicCityPlacer sityPlacer, BasicCityId basicCityId) {
		return mapGenerator.GenerateRandomMap(SizeX, SizeY, sityPlacer, basicCityId);
	}
	
}
