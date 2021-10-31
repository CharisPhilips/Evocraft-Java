package com.kilcote.evocraft.engine.map;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine._base.GameObjModel;
import com.kilcote.evocraft.engine.bot._base.BasicBot;
import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game.map.GameMapUI;
import com.kilcote.evocraft.views.game.mapping.IFactoryUI;

public class GameMap extends GameObjModel<GameMapUI> {
	
	//---------------------------------------------- Fields ----------------------------------------------
	int cellSizeX;
	int cellSizeY;
	//---------------------------------------------- Properties ----------------------------------------------
	public List<BasicCityModel> cities;
	public List<BasicUnitModel> units;
	public List<BasicBot> bots;

	public int sizeX;
	public int sizeY;
	
	public GameCellModel[][] map;
	
	private IFactoryUI mUIGeneartor = null;

	//---------------------------------------------- Constructor----------------------------------------------
	public GameMap(int SizeX, int SizeY, IFactoryUI uiGeneartor) {
		this.mUIGeneartor = uiGeneartor;
		sizeX = SizeX; sizeY = SizeY;
		map = new GameCellModel[sizeY][];
		for (int i = 0; i < sizeY; ++i) {
			map[i] = new GameCellModel[sizeX];
			for (int j = 0; j < sizeX; ++j) {
				map[i][j] = new GameCellModel();
				this.mUIGeneartor.generateGameCell(map[i][j]);
			}
		}
		cities = new ArrayList<BasicCityModel>();
		units = new ArrayList<BasicUnitModel>();
		bots = new ArrayList<BasicBot>();
		
		for (int i = 0; i < Settings.generator_CityId_Bots; i++) {
			bots.add(null);
		}
	}

	//---------------------------------------------- Methods ----------------------------------------------
	
	public IFactoryUI getUIGeneartor() { return mUIGeneartor; }
	
	public void Tick() {
		for(BasicCityModel city : this.cities) {
			city.TickReact();
		}
			
		REPEAT_UNITS_TURN:
		for (int nIndex = units.size() - 1; nIndex >= 0; nIndex--) {
			BasicUnitModel unit = units.get(nIndex);
			if (unit.TickReact()) {
				continue REPEAT_UNITS_TURN;
			}
		}

		for (int nIndex = bots.size() - 1; nIndex >= 0; nIndex--) {
			for (BasicBot bot : this.bots) {
				if (bot != null) {
					bot.TickReact();
				}
			}
		}
	}

	public void SendWarriors(List<BasicCityModel> from, BasicCityModel to) {
		for (BasicCityModel i : from) {
			if (i.currWarriors > 1) {
				SendWarriors(i, to);
			}
		}
	}

	public void SendWarriors(BasicCityModel from, BasicCityModel to) {
		if (from == to) {
			return;
		}

		BasicUnitModel unit = from.SendUnit(to);
		if (unit== null) {
			return;
		}
		units.add(unit);
	}

	public void SetBot(int id, BasicBot type) {
		bots.set(id, type);
	}

}
