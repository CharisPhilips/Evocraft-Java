package com.kilcote.evocraft.engine.bot._base;

import java.util.List;

import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;

public abstract class BasicBot {
	//---------------------------------------------- Fields ----------------------------------------------
	protected GameMap map;
	protected List<BasicCityModel> cities;
	protected List<BasicUnitModel> units;

	//---------------------------------------------- Properties ----------------------------------------------
	public int playerId;

	public abstract boolean TickReact();
	
	public int getPlayerId() { return playerId; }
	public void setPlayerId(int playerId) { this.playerId = playerId; }
}
