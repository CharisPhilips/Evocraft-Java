package com.kilcote.evocraft.views.game._base;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine._base.IGameObjModel;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class GameObjUI<T extends IGameObjModel> {
	
	private T model = null;
	protected GridPane parent;
	protected Pane shape = null;
	
	public T getModel() { return model; }
	public void setModel(T model) { this.model = model; }
	
	public Pane getShape() { return shape; } 
	
	public void setParent(GridPane grid) { this.parent = grid; }
	public GridPane getParent() { return this.parent; }
	
	public abstract void InvalidateDraw();
	//----------------------------------------------Static Methods ----------------------------------------------
	public static void SetElipseColor(Shape elipse, int playerId) {
		if (playerId == 1) {
			elipse.setFill(Paint.valueOf(StandaloneSettings.playerTownFill.toString()));
			elipse.setStroke(Paint.valueOf(StandaloneSettings.playerTownStroke.toString()));
		} else if (playerId != 0) {
			elipse.setFill(StandaloneSettings.TownFills.get(playerId - 2));
			elipse.setStroke(StandaloneSettings.TownStrokes.get(playerId - 2));
		}
		elipse.setStrokeWidth(StandaloneSettings.cityPassiveStrokeThickness);
	}

	public static void SetUiColor(Shape shape, int playerId) {
		if (playerId == 1) {
			shape.setFill(Paint.valueOf(StandaloneSettings.playerTownFill.toString()));
		} else if (playerId != 0) {
			if (StandaloneSettings.TownFills.size() <= playerId - 2) {
				shape.setFill(Paint.valueOf(StandaloneSettings.TownFills.toString()));
			} else {
				shape.setFill(Paint.valueOf(StandaloneSettings.TownFills.get(playerId - 2).toString()));
			}
		} else {
			shape.setFill(Paint.valueOf(StandaloneSettings.neutralTownFill.toString()));
		}
	}
}
