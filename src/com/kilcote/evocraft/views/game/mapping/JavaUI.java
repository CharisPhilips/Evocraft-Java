package com.kilcote.evocraft.views.game.mapping;

import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game.cell.GameCellUI;
import com.kilcote.evocraft.views.game.city.BasicCityUI;
import com.kilcote.evocraft.views.game.map.GameMapUI;
import com.kilcote.evocraft.views.game.unit.BasicUnitUI;

public class JavaUI implements IFactoryUI {
	public void generateGameMap(GameMap gameMap) {
		new GameMapUI(gameMap);
	}

	@Override
	public void generateGameCell(GameCellModel gameCellModel) {
		new GameCellUI(gameCellModel);
	}

	@Override
	public void generateBasicUnit(BasicUnitModel basicUnitModel) {
		new BasicUnitUI(basicUnitModel);
	}
	
	@Override
	public void generateBasicCity(BasicCityModel basicCityModel) {
		new BasicCityUI(basicCityModel);
	}
}
