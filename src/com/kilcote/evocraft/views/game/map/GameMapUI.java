package com.kilcote.evocraft.views.game.map;

import java.util.List;
import java.util.stream.Collectors;

import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game._base.GameObjCellUI;
import com.kilcote.evocraft.views.game.unit.BasicUnitPane;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GameMapUI extends GameObjCellUI<GameMap> {

	//---------------------------------------------- Methods ----------------------------------------------
	public GameMapUI(GameMap map) {
		this.setModel(map);
		map.setUI(this);
	}

	public void DrawStatic(GridPane grid) {
		setParent(grid);
	}
	
	public void Initialize() {
		for (int i = 0; i < getModel().sizeY; ++i) {
			for (int j = 0; j < getModel().sizeX; ++j) {
				getModel().map[i][j].getUI().InitalizeCell(parent, j, i);
				if (getModel().map[i][j].city != null) {
					getModel().map[i][j].city.getUI().InitalizeCell(parent, j, i);
				}
			}
		}
	}

	@Override
	public void InvalidateDraw() {
		for (int i = 0; i < getModel().sizeY; i++) {
			for (int j = 0; j < getModel().sizeX; j++) {
				getModel().map[i][j].getUI().InvalidateDraw();
			}
		}
		for (BasicCityModel city : this.getModel().cities) {
			city.getUI().InvalidateDraw();
		}
		
		List<Node> deletedUnits = this.parent.getChildren().stream().filter(
					data->data instanceof BasicUnitPane && this.getModel().units.stream().filter(
							model->model.getUI().getShape() == data
					).count() <= 0
				).collect(Collectors.toList());
		
		for (Node unit : deletedUnits) {
			parent.getChildren().remove(unit);
		}

		for (BasicUnitModel unit : this.getModel().units) {
			unit.getUI().setParent(parent);
			unit.getUI().InvalidateDraw();
		}
	}
}
