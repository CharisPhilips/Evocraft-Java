package com.kilcote.evocraft.views.game.unit;

import java.util.List;
import java.util.stream.Collectors;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.components.animation.SpriteView;
import com.kilcote.evocraft.views.game._base.GameObjUI;
import com.kilcote.evocraft.views.game.city.JavaBasicCityPane;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class JavaBasicUnitUI extends GameObjUI<JavaBasicUnitModel>  { 
	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaBasicUnitUI(JavaBasicUnitModel model) {
		setModel(model);
		model.setUI(this);
	}

	///////////////////////////view///////////////////////////////

	private SpriteView unitModel;
	protected Label selection;
	
	private int step = 0; 
	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new JavaBasicUnitPane();
			
			List<Node> cities = parent.getChildren().stream().filter(data->data instanceof JavaBasicCityPane).collect(Collectors.toList());
			
			int minIndex = (int) Double.POSITIVE_INFINITY;
			for (Node city : cities) {
				int nIndex = parent.getChildren().indexOf(city);
				if (nIndex != -1) {
					if (minIndex > nIndex) {
						minIndex = nIndex;
					}
				}
			}
			parent.getChildren().add(minIndex, shape);
		} else if (shape.getWidth() != 0 || shape.getHeight() != 0) {
			if (unitModel == null) {
				Image image = ResourceUtils.getResourceImage(String.format("animation/unit%d_walk.png", getModel().playerId), shape.getWidth() * 32 * StandaloneSettings.roadWidth, shape.getHeight() * 4 * StandaloneSettings.roadHeight, true, true);
				unitModel = new SpriteView(image, 4, 32);
				shape.getChildren().add(unitModel);
				
				shape.setTranslateX(getModel().path.get(getModel().currPathIndex).getKey() * this.shape.getWidth());
				shape.setTranslateY(getModel().path.get(getModel().currPathIndex).getValue() * this.shape.getHeight());
			}
			
			SetShapeProperties();
			
		}
	}
	
	public void SetShapeProperties() {
		Integer direction = getModel().GetDirection();
		if (direction != null) {
			unitModel.draw(direction, step);
			unitModel.setLayoutX(this.shape.getWidth() / 2 - (unitModel.getBoundsInLocal().getWidth() / 2));
			unitModel.setLayoutY(this.shape.getHeight() / 2 - (unitModel.getBoundsInLocal().getHeight() / 2));
			
			switch (direction) {
			case JavaBasicUnitModel.RIGHT_PATH:
				shape.setTranslateX((this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey()) + ((getModel().currTickOnCell) * this.shape.getWidth()) / (getModel().tickPerTurn - 1));
				shape.setTranslateY(this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue());
				break;
			case JavaBasicUnitModel.TOP_PATH:
				shape.setTranslateY((this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue()) - (getModel().currTickOnCell * this.shape.getHeight()) / (getModel().tickPerTurn - 1));
				shape.setTranslateX(this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey());

				break;
			case JavaBasicUnitModel.LEFT_PATH:
				shape.setTranslateX((this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey()) - ((getModel().currTickOnCell) * this.shape.getWidth()) / (getModel().tickPerTurn - 1));
				shape.setTranslateY(this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue());
				break;
			case JavaBasicUnitModel.BOTTOM_PATH:
				shape.setTranslateY((this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue()) + (getModel().currTickOnCell * this.shape.getHeight()) / (getModel().tickPerTurn - 1));
				shape.setTranslateX(this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey());

				break;
			}
		}
		step++;
	}
	
}
