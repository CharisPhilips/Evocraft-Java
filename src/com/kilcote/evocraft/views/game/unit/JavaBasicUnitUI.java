package com.kilcote.evocraft.views.game.unit;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.components.animation.SpriteView;
import com.kilcote.evocraft.views.game._base.GameObjUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;

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
			shiftX = 0;
			shiftY = 0;
		} else if (shape.getWidth() != 0 || shape.getHeight() != 0) {
			if (unitModel == null) {
				Image image = ResourceUtils.getResourceImage(String.format("animation/unit%d_walk.png", getModel().playerId), shape.getWidth() * 32 * StandaloneSettings.roadWidth, shape.getHeight() * 4 * StandaloneSettings.roadHeight, true, true);
				unitModel = new SpriteView(image, 4, 32);
				shape.getChildren().add(unitModel);
				
				
				
				shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth());
				shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight());
			}
			
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
	}
	
	public void SetShapeProperties() {
		step++;
		Integer direction = getModel().GetDirection();
		if (direction != null) {
			unitModel.draw(direction, step);
		}
		unitModel.setLayoutX(this.shape.getWidth() / 2 - (unitModel.getBoundsInLocal().getWidth() / 2));
		unitModel.setLayoutY(this.shape.getHeight() / 2 - (unitModel.getBoundsInLocal().getHeight() / 2));
	}
}
