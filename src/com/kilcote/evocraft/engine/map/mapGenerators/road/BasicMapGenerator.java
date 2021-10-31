package com.kilcote.evocraft.engine.map.mapGenerators.road;

import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.city.BasicCityPlacer;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;
import com.kilcote.evocraft.views.game.mapping.IFactoryUI;

public interface BasicMapGenerator {
	GameMap GenerateRandomMap(int SizeX, int SizeY, BasicCityPlacer sityPlacer, BasicCityId basicCityId, IFactoryUI uiGeneartor);
}
