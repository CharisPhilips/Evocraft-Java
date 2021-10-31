package com.kilcote.evocraft.views.game.cell;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.views.game._base.GameObjCellUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCellUI extends GameObjCellUI<GameCellModel> {
	
	public GameCellUI(GameCellModel cell) {
		setModel(cell);
		cell.setUI(this);
	}

	@Override
	public void InvalidateDraw() {
		if (shape == null) 
		{
			shape = new CellPane();
			if (Settings.view_grid_show) {
				this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
			} else {
				this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, Insets.EMPTY)));
			}
			
			Rectangle rect;
			if ((getModel().isOpenTop || getModel().isOpenBottom) && (getModel().isOpenLeft || getModel().isOpenRight)) {
				rect = FormRect();
				rect.setWidth(Settings.roadWidth * shape.getWidth());
				rect.setHeight(Settings.roadHeight * shape.getHeight());
				shape.getChildren().add(rect);
				rect.setX((shape.getWidth() - rect.getWidth()) / 2);
				rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			}
			if (getModel().isOpenTop) {
				rect = FormRect();
				rect.setWidth(Settings.roadWidth * shape.getWidth());
				rect.setX((shape.getWidth() - rect.getWidth()) / 2);
				rect.setY(0);
				shape.getChildren().add(rect);
			}
			if (getModel().isOpenBottom) {
				rect = FormRect();
				rect.setWidth(Settings.roadWidth * shape.getWidth());
				rect.setX((shape.getWidth() - rect.getWidth()) / 2);
				rect.setY(shape.getHeight() / 2);
				shape.getChildren().add(rect);
			}
			if (getModel().isOpenLeft) {
				rect = FormRect();
				rect.setHeight(Settings.roadHeight * shape.getHeight());
				shape.getChildren().add(rect);
				rect.setX(0);
				rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			}
			if (getModel().isOpenRight) {
				rect = FormRect();
				rect.setHeight(Settings.roadHeight * shape.getHeight());
				shape.getChildren().add(rect);
				rect.setX(shape.getWidth() / 2);
				rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			}
		}
		Rectangle rect;
		int nChildIdx = 0;
		if ((getModel().isOpenTop || getModel().isOpenBottom) && (getModel().isOpenLeft || getModel().isOpenRight)) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			nChildIdx++;
		}
		if (getModel().isOpenTop) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(shape.getHeight() / 2);
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(0);
			nChildIdx++;
		}
		if (getModel().isOpenBottom) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(shape.getHeight() / 2);
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(shape.getHeight() / 2);
			nChildIdx++;
		}
		if (getModel().isOpenLeft) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(shape.getWidth() / 2);
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			rect.setX(0);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			nChildIdx++;
		}
		if (getModel().isOpenRight) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(shape.getWidth() / 2);
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			rect.setX(shape.getWidth() / 2);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			nChildIdx++;
		}
	}

	private Rectangle FormRect() {
		Rectangle rect = new Rectangle(shape.getWidth() / 2, shape.getHeight() / 2);
		rect.setFill(Color.LIGHTGRAY);
		return rect;
	}
}
