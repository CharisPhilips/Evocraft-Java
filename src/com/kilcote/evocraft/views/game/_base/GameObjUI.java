package com.kilcote.evocraft.views.game._base;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine._base.GameObjModel;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class GameObjUI<T extends GameObjModel> {
	
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
			elipse.setFill(Paint.valueOf(Settings.playerTownFill.toString()));
			elipse.setStroke(Paint.valueOf(Settings.playerTownStroke.toString()));
		} else if (playerId != 0) {
			elipse.setFill(Settings.TownFills.get(playerId - 2));
			elipse.setStroke(Settings.TownStrokes.get(playerId - 2));
		}
		elipse.setStrokeWidth(Settings.cityPassiveStrokeThickness);
	}

	public static void SetUiColor(Shape shape, int playerId) {
		if (playerId == 1) {
			shape.setFill(Paint.valueOf(Settings.playerTownFill.toString()));
		} else if (playerId != 0) {
			if (Settings.TownFills.size() <= playerId - 2) {
				shape.setFill(Paint.valueOf(Settings.TownFills.toString()));
			} else {
				shape.setFill(Paint.valueOf(Settings.TownFills.get(playerId - 2).toString()));
			}
		} else {
			shape.setFill(Paint.valueOf(Settings.neutralTownFill.toString()));
		}
	}
}
