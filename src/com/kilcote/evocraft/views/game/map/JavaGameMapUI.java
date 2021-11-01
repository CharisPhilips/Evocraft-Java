package com.kilcote.evocraft.views.game.map;

import java.util.List;
import java.util.stream.Collectors;

import com.kilcote.evocraft.engine._base.IGameObjModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game._base.GameObjCellUI;
import com.kilcote.evocraft.views.game.cell.JavaGameCellUI;
import com.kilcote.evocraft.views.game.city.JavaBasicCityUI;
import com.kilcote.evocraft.views.game.unit.JavaBasicUnitPane;
import com.kilcote.evocraft.views.game.unit.JavaBasicUnitUI;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class JavaGameMapUI extends GameObjCellUI<JavaGameMap> {

	//---------------------------------------------- Methods ----------------------------------------------
	public JavaGameMapUI(JavaGameMap map) {
		this.setModel(map);
		map.setUI(this);
	}

	public void DrawStatic(GridPane grid) {
		setParent(grid);
	}
	
	public void Initialize() {
		for (int y = 0; y < getModel().getSizeY(); ++y) {
			for (int x = 0; x < getModel().getSizeX(); ++x) {
				((IGameObjModel<JavaGameCellUI>) getModel().getMapCell(y, x)).getUI().InitalizeCell(parent, x, y);
				if (getModel().getMapCell(y, x).city != null) {
					((JavaBasicCityModel) getModel().getMapCell(y, x).city).getUI().InitalizeCell(parent, x, y);
				}
			}
		}
	}

	@Override
	public void InvalidateDraw() {
		for (int y = 0; y < getModel().getSizeY(); y++) {
			for (int x = 0; x < getModel().getSizeX(); x++) {
				((IGameObjModel<JavaGameCellUI>) getModel().getMapCell(y, x)).getUI().InvalidateDraw();
				if (getModel().getMapCell(y, x).city != null) {
					((JavaBasicCityModel) getModel().getMapCell(y, x).city).getUI().InvalidateDraw();

				}
			}
		}
		
		List<Node> deletedUnits = this.parent.getChildren().stream().filter(
					data->data instanceof JavaBasicUnitPane && this.getModel().getUnits().stream().filter(
							model->((IGameObjModel<JavaBasicUnitUI>) model).getUI().getShape() == data
					).count() <= 0
				).collect(Collectors.toList());
		
		for (Node unit : deletedUnits) {
			parent.getChildren().remove(unit);
		}

		for (BasicUnitModel unit : this.getModel().getUnits()) {
			((IGameObjModel<JavaBasicUnitUI>) unit).getUI().setParent(parent);
			((IGameObjModel<JavaBasicUnitUI>) unit).getUI().InvalidateDraw();
		}
	}
}
