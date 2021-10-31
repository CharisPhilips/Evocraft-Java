package com.kilcote.evocraft.engine.unit;

import java.util.List;

import com.kilcote.evocraft.engine._base.GameObjModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.interfaces.Settingable;
import com.kilcote.evocraft.engine.interfaces.Tickable;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.setting._base.SettinsSetter;
import com.kilcote.evocraft.views.game.unit.BasicUnitUI;

import javafx.util.Pair;

public class BasicUnitModel extends GameObjModel<BasicUnitUI> implements Tickable, Settingable { 
	//---------------------------------------------- Fields ----------------------------------------------
	public List<Pair<Integer, Integer>> path;
	public int currPathIndex;
	public int currTickOnCell;

	public int warriorsCnt;
	public int tickPerTurn;
	public BasicCityModel destination;

	//---------------------------------------------- Properties ----------------------------------------------
	public int playerId;
	public GameMap gameMap;
	//---------------------------------------------- Constructor ----------------------------------------------
	public BasicUnitModel(int warriorsCnt, int PlayerId, List<Pair<Integer, Integer>> Path, BasicCityModel destination, GameMap gameMap) {
		this.warriorsCnt = warriorsCnt;
		this.playerId = PlayerId;
		this.path = Path;
		this.destination = destination;

		currTickOnCell = 1;
		currPathIndex = 0;
		this.gameMap = gameMap;
		this.gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.add(this);
		
		this.gameMap.getUIGeneartor().generateBasicUnit(this);
	}

	//---------------------------------------------- Methods ----------------------------------------------
	public boolean TickReact() {
		currTickOnCell++;
		if (currTickOnCell >= tickPerTurn) {
			currTickOnCell = 1;
			gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.remove(this);
			currPathIndex++;
			gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.add(this);

			if (
				(
					gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city != null &&
					gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city.playerId != this.playerId
				)
				|| (currPathIndex == path.size() - 1)
			) {
				gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.remove(this);
				gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city.GetUnits(this);
				return true;
			}
		}
		return false;
	}

	public int TicksLeftToDestination() {
		return (int)((path.size() - 1 - currPathIndex) * tickPerTurn - currTickOnCell);
	}

	public void GetSettings(SettinsSetter settinsSetter) throws Exception {
		settinsSetter.SetSettings(this);
	}
}
