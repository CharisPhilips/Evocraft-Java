package com.kilcote.evocraft.views.game.mapping;

import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;

public interface IFactoryUI {
	public void generateGameMap(GameMap gameMap);
	public void generateGameCell(GameCellModel gameCellModel);
	public void generateBasicCity(BasicCityModel basicCityModel);
	public void generateBasicUnit(BasicUnitModel basicUnitModel);
}
