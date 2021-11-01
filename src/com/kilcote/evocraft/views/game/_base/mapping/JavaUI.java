package com.kilcote.evocraft.views.game._base.mapping;

import com.kilcote.evocraft.engine.cell.JavaGameCellModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.views.game.cell.JavaGameCellUI;
import com.kilcote.evocraft.views.game.city.JavaBasicCityUI;
import com.kilcote.evocraft.views.game.map.JavaGameMapUI;
import com.kilcote.evocraft.views.game.unit.JavaBasicUnitUI;

public class JavaUI implements IFactoryUI {
	public void generateGameMap(JavaGameMap gameMap) {
		new JavaGameMapUI(gameMap);
		for (int y = 0; y < gameMap.getSizeY(); y++) {
			for (int x = 0; x < gameMap.getSizeX(); x++) {
				this.generateGameCell((JavaGameCellModel) gameMap.getMapCell(y, x));
			}
		}
//		for (int i = 0; i < gameMap.getCitySize(); i++) {
//			this.generateBasicCity((JavaBasicCityModel) gameMap.getCity(i));
//		}
	}

	@Override
	public void generateGameCell(JavaGameCellModel gameCellModel) {
		new JavaGameCellUI(gameCellModel);
	}
	
	@Override
	public void generateBasicCity(JavaBasicCityModel basicCityModel) {
		new JavaBasicCityUI(basicCityModel);
	}

	@Override
	public void generateBasicUnit(JavaBasicUnitModel basicUnitModel) {
		new JavaBasicUnitUI(basicUnitModel);
	}
}
