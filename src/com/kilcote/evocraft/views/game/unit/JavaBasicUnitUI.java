package com.kilcote.evocraft.views.game.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.views.components.animation.SpriteView;
import com.kilcote.evocraft.views.game._base.GameObjUI;
import com.kilcote.evocraft.views.game.city.JavaBasicCityPane;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class JavaBasicUnitUI extends GameObjUI<JavaBasicUnitModel>  { 
	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaBasicUnitUI(JavaBasicUnitModel model) {
		setModel(model);
		model.setUI(this);
	}

	///////////////////////////view///////////////////////////////

	private SpriteView unitModel;
	protected Label selection;

	private static List<AnmiationResource> animation_data = new ArrayList<AnmiationResource>(
		Arrays.asList(
			new AnmiationResource("walk", 32, 4),
			new AnmiationResource("strong", 32, 4),
			new AnmiationResource("walk_flag", 32, 5),
			new AnmiationResource("strong_flag", 32, 5)
		)
	);

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
				
				unitModel = new SpriteView(
						String.format("animation/unit%d_%s.png", getModel().playerId, animation_data.get(getModel().type).keyword),
						animation_data.get(getModel().type).cols,
						animation_data.get(getModel().type).rows,
						32,
						shape.getWidth(),
						shape.getHeight()
						);
				shape.getChildren().add(unitModel);
				shape.setTranslateX(this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey());
				shape.setTranslateY(this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue());
			}

			SetShapeProperties();

		}
	}

	public void SetShapeProperties() {
		Integer direction = getModel().GetDirection();
		if (direction != null) {
			unitModel.draw(direction, (getModel().currTickOnCell / getModel().moveSteps));
			unitModel.setLayoutX(this.shape.getWidth() / 2 - (unitModel.getBoundsInLocal().getWidth() / 2));
			unitModel.setLayoutY(this.shape.getHeight() / 2 - (unitModel.getBoundsInLocal().getHeight() / 2));

			switch (direction) {
			case JavaBasicUnitModel.RIGHT_PATH:
				shape.setTranslateX((this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey()) + ((getModel().currTickOnCell) * this.shape.getWidth()) / (StandaloneSettings.stepTicks_PerCell));
				shape.setTranslateY(this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue());
				break;
			case JavaBasicUnitModel.TOP_PATH:
				shape.setTranslateX(this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey());
				shape.setTranslateY((this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue()) - (getModel().currTickOnCell * this.shape.getHeight()) / (StandaloneSettings.stepTicks_PerCell));

				break;
			case JavaBasicUnitModel.LEFT_PATH:
				shape.setTranslateX((this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey()) - ((getModel().currTickOnCell) * this.shape.getWidth()) / (StandaloneSettings.stepTicks_PerCell));
				shape.setTranslateY(this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue());
				break;
			case JavaBasicUnitModel.BOTTOM_PATH:
				shape.setTranslateX(this.shape.getWidth() * getModel().path.get(getModel().currPathIndex).getKey());
				shape.setTranslateY((this.shape.getHeight() * getModel().path.get(getModel().currPathIndex).getValue()) + (getModel().currTickOnCell * this.shape.getHeight()) / (StandaloneSettings.stepTicks_PerCell));

				break;
			}
		}
	}

}
class AnmiationResource {
	public String keyword = "";
	public AnmiationResource(String type, int cols, int rows) {
		super();
		this.keyword = type;
		this.cols = cols;
		this.rows = rows;
	}
	
	public int cols = 0;
	public int rows = 0;		
}	
