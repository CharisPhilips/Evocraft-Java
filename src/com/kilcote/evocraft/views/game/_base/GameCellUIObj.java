package com.kilcote.evocraft.views.game._base;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class GameCellUIObj extends GameUIObj{

	protected GridPane grid;
	protected Pane shape;

	public GameCellUIObj() {
		super();
	}

	public void SetGrid(GridPane grid) {
		this.grid = grid;
	}

	//---------------------------------------------- Methods ----------------------------------------------
	
	public void DrawOnGameCell(int column, int row) {
		InitializeDraw();
		if (shape == null) {
			System.out.println("DrawOnGameCell" + column + row);
		}
		GridPane.setRowIndex(shape, row);
		GridPane.setColumnIndex(shape, column);
		grid.getChildren().remove(shape);
		grid.getChildren().add(shape);
	}
}
