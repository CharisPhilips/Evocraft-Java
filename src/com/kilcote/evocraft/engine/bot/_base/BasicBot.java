package com.kilcote.evocraft.engine.bot._base;

import java.util.List;

import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnit;

public abstract class BasicBot {
	//---------------------------------------------- Fields ----------------------------------------------
	protected GameMap map;
	protected List<BasicCity> cities;
	protected List<BasicUnit> units;

	//---------------------------------------------- Properties ----------------------------------------------
	public int playerId;

	public abstract boolean TickReact();
	
	public int getPlayerId() { return playerId; }
	public void setPlayerId(int playerId) { this.playerId = playerId; }
}
