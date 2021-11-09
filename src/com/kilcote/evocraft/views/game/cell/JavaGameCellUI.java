package com.kilcote.evocraft.views.game.cell;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.cell.JavaGameCellModel;
import com.kilcote.evocraft.views.game._base.GameObjCellUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaGameCellUI extends GameObjCellUI<JavaGameCellModel> {
	
	private boolean isDrawing = false;
	
	private Rectangle top;
	private Rectangle bottom;
	private Rectangle left;
	private Rectangle right;
	private Rectangle center;
	
	public JavaGameCellUI(JavaGameCellModel cell) {
		setModel(cell);
		cell.setUI(this);
	}

	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new JavaCellPane();
			if (StandaloneSettings.view_grid_show) {
				this.shape.setBackground(new Background(new BackgroundFill(StandaloneSettings.roadBackground, CornerRadii.EMPTY, new Insets(1, 1, 1, 1))));
			} else {
				this.shape.setBackground(new Background(new BackgroundFill(StandaloneSettings.roadBackground, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		} else if (shape.getWidth() != 0 || shape.getHeight() != 0) {
//			Rectangle rect;
			if (!isDrawing) {
				if ((getModel().isOpenTop || getModel().isOpenBottom) && (getModel().isOpenLeft || getModel().isOpenRight)) {
					center = FormRect();
					shape.getChildren().add(center);
				}
				if (getModel().isOpenTop) {
					top = FormRect();
					shape.getChildren().add(top);
				}
				if (getModel().isOpenBottom) {
					bottom = FormRect();
					shape.getChildren().add(bottom);
				}
				if (getModel().isOpenLeft) {
					left = FormRect();
					shape.getChildren().add(left);
				}
				if (getModel().isOpenRight) {
					right = FormRect();
					shape.getChildren().add(right);
				}
				isDrawing = true;
			}
			if ((getModel().isOpenTop || getModel().isOpenBottom) && (getModel().isOpenLeft || getModel().isOpenRight)) {
				center.setWidth(StandaloneSettings.roadWidth * shape.getWidth());
				center.setHeight(StandaloneSettings.roadHeight * shape.getHeight());
				center.setX(Math.round((shape.getWidth() - center.getWidth()) / 2));
				center.setY(Math.round((shape.getHeight() - center.getHeight()) / 2));
			}
			if (getModel().isOpenTop) {
				top.setWidth(StandaloneSettings.roadWidth * shape.getWidth());
				top.setHeight(Math.round(shape.getHeight() / 2));
				top.setX(Math.round((shape.getWidth() - top.getWidth()) / 2));
				top.setY(0);
			}
			if (getModel().isOpenBottom) {
				bottom.setWidth(StandaloneSettings.roadWidth * shape.getWidth());
				bottom.setHeight(Math.round(shape.getHeight() / 2));
				bottom.setX(Math.round((shape.getWidth() - bottom.getWidth()) / 2));
				bottom.setY(Math.round(shape.getHeight() / 2));
			}
			if (getModel().isOpenLeft) {
				left.setWidth(Math.round(shape.getWidth() / 2));
				left.setHeight(StandaloneSettings.roadHeight * shape.getHeight());
				left.setX(0);
				left.setY(Math.round(((shape.getHeight() - left.getHeight()) / 2)));
			}
			if (getModel().isOpenRight) {
				right.setWidth(Math.round(shape.getWidth() / 2));
				right.setHeight(StandaloneSettings.roadHeight * shape.getHeight());
				right.setX(shape.getWidth() / 2);
				right.setY(Math.round((shape.getHeight() - right.getHeight()) / 2));
			}
		}
	}

	private Rectangle FormRect() {
		Rectangle rect = new Rectangle(Math.round(shape.getWidth() / 2), Math.round(shape.getHeight() / 2));
		rect.setFill(Color.LIGHTGRAY);
		return rect;
	}
}
