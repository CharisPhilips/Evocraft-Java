package com.kilcote.evocraft.views.game._base;

import com.kilcote.evocraft.engine._base.GameObjModel;

import javafx.scene.layout.GridPane;

public abstract class GameObjCellUI<T extends GameObjModel> extends GameObjUI<T> {

	//---------------------------------------------- Methods ----------------------------------------------
	public void InitalizeCell(GridPane parent, int column, int row) {
		setParent(parent);
		InvalidateDraw();
		if (shape == null) {
			System.out.println("DrawOnGameCell" + column + row);
		}
		GridPane.setRowIndex(shape, row);
		GridPane.setColumnIndex(shape, column);
		parent.getChildren().remove(shape);
		parent.getChildren().add(shape);
	}
}
