package com.kilcote.evocraft.views.game._base;

import com.kilcote.evocraft.common.Settings;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class GameUIObj {
	public abstract void InitializeDraw();
	public abstract void InvalidateDraw();
	
	//----------------------------------------------Static Methods ----------------------------------------------
	public static void SetElipseColor(Shape elipse, int playerId) {
		if (playerId == 1) {
			elipse.setFill(Paint.valueOf(Settings.playerTownFill.toString()));
			elipse.setStroke(Paint.valueOf(Settings.playerTownStroke.toString()));
		} else if (playerId != 0) {
			if (Settings.TownFills.size() <= playerId - 2) {
				elipse.setFill(Settings.TownFills.get(Settings.TownFills.size() - 1));
			} else {
				elipse.setFill(Settings.TownFills.get(playerId - 2));
			}

			if (Settings.TownStrokes.size() <= playerId - 2) {
				elipse.setStroke(Settings.TownStrokes.get(Settings.TownStrokes.size() - 1));
			} else {
				elipse.setStroke(Settings.TownStrokes.get(playerId - 2));
			}
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
