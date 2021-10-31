package com.kilcote.evocraft.views.game.unit;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game._base.GameObjUI;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BasicUnitUI extends GameObjUI<BasicUnitModel>  { 
	//---------------------------------------------- Constructor ----------------------------------------------
	public BasicUnitUI(BasicUnitModel model) {
		setModel(model);
		model.setUI(this);
	}

	///////////////////////////view///////////////////////////////

	private Label text;
	private Shape rectangle;
	private double pixelPerTurnX, pixelPerTurnY;
	private double shiftX, shiftY;
	
	protected Label selection;
	
	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new BasicUnitPane();
			parent.getChildren().add(shape);
			
			RecalcGeometrySize();
			this.rectangle = new Rectangle(20, 20);
			
			this.rectangle.setFill(Settings.TownFills.get(getModel().playerId - 1));
			this.rectangle.setStroke(Settings.neutralTownStroke);
			
			SetElipseColor(rectangle, this.getModel().playerId);
			shape.getChildren().add(rectangle);

			text = new Label();
			text.setTextFill(Settings.TownStrokes.get(getModel().playerId - 1));
			shape.getChildren().add(text);
			
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth());
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight());
		}
		text.setText(String.valueOf(this.getModel().warriorsCnt));
		pixelPerTurnX = this.shape.getWidth() / getModel().tickPerTurn;
		pixelPerTurnY = this.shape.getHeight() / getModel().tickPerTurn;
		
		SetShapeProperties();
		if (getModel().currPathIndex < getModel().path.size() && (getModel().path.get(getModel().currPathIndex).getKey() > getModel().path.get(getModel().currPathIndex + 1).getKey())) {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() - getModel().currTickOnCell * pixelPerTurnX + shiftX);
		} else if (getModel().currPathIndex < getModel().path.size() && (getModel().path.get(getModel().currPathIndex).getKey() < getModel().path.get(getModel().currPathIndex + 1).getKey())) {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() + getModel().currTickOnCell * pixelPerTurnX + shiftX);
		} else {
			shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth() + shiftX);
		}

		if (getModel().currPathIndex < getModel().path.size() && (getModel().path.get(getModel().currPathIndex).getValue() > getModel().path.get(getModel().currPathIndex + 1).getValue())) {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() - getModel().currTickOnCell * pixelPerTurnY + shiftY);
		} else if (getModel().currPathIndex < getModel().path.size() && (getModel().path.get(getModel().currPathIndex).getValue() < getModel().path.get(getModel().currPathIndex + 1).getValue())) {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() + getModel().currTickOnCell * pixelPerTurnY + shiftY);
		} else {
			shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight() + shiftY);
		}
	}
	
	public void SetShapeProperties() {
		if (this.shape.getWidth() == 0 && this.shape.getHeight() == 0) {
			rectangle.setVisible(false);
			text.setVisible(false);
		} else {
			rectangle.setVisible(true);
			text.setVisible(true);
			rectangle.setLayoutX(this.shape.getWidth() / 2 - (rectangle.getBoundsInLocal().getWidth() / 2));
			rectangle.setLayoutY(this.shape.getHeight() / 2 - (rectangle.getBoundsInLocal().getHeight() / 2));
			text.setLayoutX(this.shape.getWidth() / 2 - (text.getWidth() / 2));
			text.setLayoutY(this.shape.getHeight() / 2 - (text.getHeight() / 2));
		}
	}
	
	protected void RecalcGeometrySize() {
		pixelPerTurnX = Settings.oneCellSizeX / getModel().tickPerTurn;
		pixelPerTurnY = Settings.oneCellSizeY / getModel().tickPerTurn;
		shiftX = 0;
		shiftY = 0;
	}
}
