package com.kilcote.evocraft.engine.cell;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine.JavaGameEngine;
import com.kilcote.evocraft.engine._base.IGameObjModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.views.game.cell.JavaGameCellUI;

public class JavaGameCellModel extends GameCellModel implements IGameObjModel<JavaGameCellUI> {
	//---------------------------------------------- Properties ----------------------------------------------

	private JavaGameCellUI ui = null;

	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaGameCellModel(GameCellModel cell, JavaGameEngine gameEngine, JavaGameMap gameMap) {
		super();
		this.uuid = cell.getUuid();
		this.isOpenLeft = cell.isOpenLeft;
		this.isOpenTop = cell.isOpenTop;
		this.isOpenRight = cell.isOpenRight;
		this.isOpenBottom = cell.isOpenBottom;

		if (cell.city != null) {
			this.city = new JavaBasicCityModel(cell.city, gameMap);
			gameEngine.getUIGeneartor().generateBasicCity((JavaBasicCityModel) this.city);
		}
		
		List<BasicUnitModel> units = new ArrayList<BasicUnitModel>();
		for (BasicUnitModel unit: cell.units) {
			units.add(new JavaBasicUnitModel(unit));
		}
		this.units = units;
	}
	
	@Override
	public void setUI(JavaGameCellUI ui) {
		this.ui = ui;
	}
	
	@Override
	public JavaGameCellUI getUI() {
		return ui;
	}
}
