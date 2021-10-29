package com.kilcote.evocraft.engine.map.mapGenerators.road;

import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.city.BasicCityPlacer;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;

public interface BasicMapGenerator {
	GameMap GenerateRandomMap(int SizeX, int SizeY, BasicCityPlacer sityPlacer, BasicCityId basicCityId);
}
