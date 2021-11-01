package com.kilcote.evocraft.views.game._base.mapping;

import com.kilcote.evocraft.engine.cell.JavaGameCellModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;

public interface IFactoryUI {
	public void generateGameMap(JavaGameMap gameMap);
	public void generateGameCell(JavaGameCellModel gameCellModel);
	public void generateBasicCity(JavaBasicCityModel basicCityModel);
	public void generateBasicUnit(JavaBasicUnitModel basicUnitModel);
}
