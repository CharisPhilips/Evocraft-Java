package com.kilcote.evocraft.engine.cell;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.unit.BasicUnit;
import com.kilcote.evocraft.views.game._base.GameCellUIObj;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameCell extends GameCellUIObj {
	//---------------------------------------------- Properties ----------------------------------------------
	public boolean isOpenLeft;
	public boolean isOpenTop;
	public boolean isOpenRight;
	public boolean isOpenBottom;

	public BasicCity city;
	public List<BasicUnit> units;

	//---------------------------------------------- Ctor ----------------------------------------------
	public GameCell() {
		this.isOpenBottom = false;
		this.isOpenLeft = false;
		this.isOpenRight = false;
		this.isOpenTop = false;
		units = new ArrayList<BasicUnit>();
		city = null;
	}

	public void InitializeDraw() {
		shape = new Pane();
		this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
		//		this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, Insets.EMPTY)));

		Rectangle rect;
		if((isOpenTop || isOpenBottom) && (isOpenLeft || isOpenRight)) {
			rect = FormRect();
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			shape.getChildren().add(rect);
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
		}
		if (isOpenTop) {
			rect = FormRect();
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(0);
			shape.getChildren().add(rect);
		}
		if (isOpenBottom) {
			rect = FormRect();
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(shape.getHeight() / 2);
			shape.getChildren().add(rect);
		}
		if (isOpenLeft) {
			rect = FormRect();
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			shape.getChildren().add(rect);
			rect.setX(0);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
		}
		if (isOpenRight) {
			rect = FormRect();
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			shape.getChildren().add(rect);
			rect.setX(shape.getWidth() / 2);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
		}
	}

	@Override
	public void InvalidateDraw() {
		Rectangle rect;
		int nChildIdx = 0;
		if((isOpenTop || isOpenBottom) && (isOpenLeft || isOpenRight)) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			nChildIdx++;
		}
		if (isOpenTop) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(shape.getHeight() / 2);
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(0);
			nChildIdx++;
		}
		if (isOpenBottom) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(Settings.roadWidth * shape.getWidth());
			rect.setHeight(shape.getHeight() / 2);
			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
			rect.setY(shape.getHeight() / 2);
			nChildIdx++;
		}
		if (isOpenLeft) {
			rect = (Rectangle)shape.getChildren().get(nChildIdx);
			rect.setWidth(shape.getWidth() / 2);
			rect.setHeight(Settings.roadHeight * shape.getHeight());
			rect.setX(0);
			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
			nChildIdx++;
		}
		if (isOpenRight) {
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
