//package com.kilcote.evocraft.views.game.cell;
//
//import java.util.ArrayList;
//
//import com.kilcote.evocraft.common.Settings;
//import com.kilcote.evocraft.engine.cell.GameCell;
//import com.kilcote.evocraft.engine.unit.BasicUnit;
//import com.kilcote.evocraft.views.game._base.GameCellUIObj;
//
//import javafx.geometry.Insets;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//public class GameCellUI extends GameCellUIObj {
//	//---------------------------------------------- Properties ----------------------------------------------
//	//	public boolean isOpenLeft;
//	//	public boolean isOpenTop;
//	//	public boolean isOpenRight;
//	//	public boolean isOpenBottom;
//
//	//	public BasicCity city;
//	//	public List<BasicUnit> units;
//	private GameCell model = null;
//
//	//---------------------------------------------- Ctor ----------------------------------------------
//	public GameCellUI() {
//		model = new GameCell();
//	}
//
//	@Override
//	public void InitializeDraw() {
//		shape = new Pane();
//		FillShape();
//	}
//
//	private void FillShape() {
//		invalidate();
//	}
//
//	private void invalidate() {
//		this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
//		//		this.shape.setBackground(new Background(new BackgroundFill(Settings.roadBackground, CornerRadii.EMPTY, Insets.EMPTY)));
//
//		Rectangle rect;
//		if((model.isOpenTop || model.isOpenBottom) && (model.isOpenLeft || model.isOpenRight)) {
//			rect = FormRect();
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			shape.getChildren().add(rect);
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//		}
//		if (model.isOpenTop) {
//			rect = FormRect();
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY(0);
//			shape.getChildren().add(rect);
//		}
//		if (model.isOpenBottom) {
//			rect = FormRect();
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY(shape.getHeight() / 2);
//			shape.getChildren().add(rect);
//		}
//		if (model.isOpenLeft) {
//			rect = FormRect();
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			shape.getChildren().add(rect);
//			rect.setX(0);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//		}
//		if (model.isOpenRight) {
//			rect = FormRect();
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			shape.getChildren().add(rect);
//			rect.setX(shape.getWidth() / 2);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//		}
//	}
//
//	@Override
//	public void InvalidateDraw() {
//		Rectangle rect;
//		int nChildIdx = 0;
//		if((model.isOpenTop || model.isOpenBottom) && (model.isOpenLeft || model.isOpenRight)) {
//			rect = (Rectangle)shape.getChildren().get(nChildIdx);
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//			nChildIdx++;
//		}
//		if (model.isOpenTop) {
//			rect = (Rectangle)shape.getChildren().get(nChildIdx);
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setHeight(shape.getHeight() / 2);
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY(0);
//			nChildIdx++;
//		}
//		if (model.isOpenBottom) {
//			rect = (Rectangle)shape.getChildren().get(nChildIdx);
//			rect.setWidth(Settings.roadWidth * shape.getWidth());
//			rect.setHeight(shape.getHeight() / 2);
//			rect.setX((shape.getWidth() - rect.getWidth()) / 2);
//			rect.setY(shape.getHeight() / 2);
//			nChildIdx++;
//		}
//		if (model.isOpenLeft) {
//			rect = (Rectangle)shape.getChildren().get(nChildIdx);
//			rect.setWidth(shape.getWidth() / 2);
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			rect.setX(0);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//			nChildIdx++;
//		}
//		if (model.isOpenRight) {
//			rect = (Rectangle)shape.getChildren().get(nChildIdx);
//			rect.setWidth(shape.getWidth() / 2);
//			rect.setHeight(Settings.roadHeight * shape.getHeight());
//			rect.setX(shape.getWidth() / 2);
//			rect.setY((shape.getHeight() - rect.getHeight()) / 2);
//			nChildIdx++;
//		}
//	}
//
//	private Rectangle FormRect() {
//		Rectangle rect = new Rectangle(shape.getWidth() / 2, shape.getHeight() / 2);
//		rect.setFill(Color.LIGHTGRAY);
//		return rect;
//	}
//}
