package com.kilcote.evocraft.views.game.unit;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.components.animation.SpriteView;
import com.kilcote.evocraft.views.game._base.GameObjUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class JavaBasicUnitUI extends GameObjUI<JavaBasicUnitModel>  { 
	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaBasicUnitUI(JavaBasicUnitModel model) {
		setModel(model);
		model.setUI(this);
	}

	///////////////////////////view///////////////////////////////

//	private Label text;
//	private Shape rectangle;
	private SpriteView unitModel;
	
	private double pixelPerTurnX, pixelPerTurnY;
	private double shiftX, shiftY;
	protected Label selection;
	
	private int step = 0; 
	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new JavaBasicUnitPane();
			parent.getChildren().add(shape);
			
//         rectangle
			RecalcGeometrySize();
//			this.rectangle = new Rectangle(20, 20);
//			this.rectangle.setFill(StandaloneSettings.TownFills.get(getModel().playerId - 1));
//			this.rectangle.setStroke(StandaloneSettings.neutralTownStroke);
//			SetElipseColor(rectangle, this.getModel().playerId);
//			shape.getChildren().add(rectangle);
			
//			label
//			text = new Label();
//			text.setTextFill(StandaloneSettings.TownStrokes.get(getModel().playerId - 1));
//			shape.getChildren().add(text);
			
//			animation
			Image image = ResourceUtils.getResourceImage("animation/unit1_walk.png");
			unitModel = new SpriteView(image, 4, 32);
			shape.getChildren().add(unitModel);
			
//			 shape
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth());
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight());
		}
//		text.setText(String.valueOf(this.getModel().warriorsCnt));
		pixelPerTurnX = this.shape.getWidth() / getModel().tickPerTurn;
		pixelPerTurnY = this.shape.getHeight() / getModel().tickPerTurn;
		
		SetShapeProperties();
		if (getModel().currPathIndex < getModel().path.size() - 1 && (getModel().path.get(getModel().currPathIndex).getKey() > getModel().path.get(getModel().currPathIndex + 1).getKey())) {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() - getModel().currTickOnCell * pixelPerTurnX + shiftX);
		} else if (getModel().currPathIndex < getModel().path.size() - 1 && (getModel().path.get(getModel().currPathIndex).getKey() < getModel().path.get(getModel().currPathIndex + 1).getKey())) {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() + getModel().currTickOnCell * pixelPerTurnX + shiftX);
		} else if (getModel().currPathIndex < getModel().path.size()) {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() + shiftX);
		}

		if (getModel().currPathIndex < getModel().path.size() - 1 && (getModel().path.get(getModel().currPathIndex).getValue() > getModel().path.get(getModel().currPathIndex + 1).getValue())) {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() - getModel().currTickOnCell * pixelPerTurnY + shiftY);
		} else if (getModel().currPathIndex < getModel().path.size() - 1 && (getModel().path.get(getModel().currPathIndex).getValue() < getModel().path.get(getModel().currPathIndex + 1).getValue())) {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() + getModel().currTickOnCell * pixelPerTurnY + shiftY);
		} else if (getModel().currPathIndex < getModel().path.size()) {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() + shiftY);
		}
	}
	
	public void SetShapeProperties() {
		step++;
		if (this.shape.getWidth() == 0 && this.shape.getHeight() == 0) {
//			rectangle.setVisible(false);
//			text.setVisible(false);
			unitModel.setVisible(false);
		} else {
//			rectangle.setVisible(true);
//			text.setVisible(true);
			unitModel.setVisible(true);
			
//			rectangle.setLayoutX(this.shape.getWidth() / 2 - (rectangle.getBoundsInLocal().getWidth() / 2));
//			rectangle.setLayoutY(this.shape.getHeight() / 2 - (rectangle.getBoundsInLocal().getHeight() / 2));
//			
//			text.setLayoutX(this.shape.getWidth() / 2 - (text.getWidth() / 2));
//			text.setLayoutY(this.shape.getHeight() / 2 - (text.getHeight() / 2));
			
			Integer direction = getModel().GetDirection();
			if (direction != null) {
				unitModel.draw(direction, step);
			}
			unitModel.setLayoutX(this.shape.getWidth() / 2 - (unitModel.getBoundsInLocal().getWidth() / 2));
			unitModel.setLayoutY(this.shape.getHeight() / 2 - (unitModel.getBoundsInLocal().getHeight() / 2));
		}
	}
	
	protected void RecalcGeometrySize() {
		pixelPerTurnX = StandaloneSettings.oneCellSizeX / getModel().tickPerTurn;
		pixelPerTurnY = StandaloneSettings.oneCellSizeY / getModel().tickPerTurn;
		shiftX = 0;
		shiftY = 0;
	}
}
