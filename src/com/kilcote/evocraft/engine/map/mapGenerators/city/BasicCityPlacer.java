package com.kilcote.evocraft.engine.map.mapGenerators.city;

import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;

public interface BasicCityPlacer {
	public void PlaceCities(GameMap m, BasicCityId chooserId);
}
