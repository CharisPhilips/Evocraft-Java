package com.kilcote.evocraft.engine.map;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine.JavaGameEngine;
import com.kilcote.evocraft.engine._base.IGameObjModel;
import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.engine.cell.JavaGameCellModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.views.game.map.JavaGameMapUI;

public class JavaGameMap extends GameMap implements IGameObjModel<JavaGameMapUI> {
	
	private JavaGameMapUI ui = null;
	private JavaGameEngine javaGameEngine = null;

	//---------------------------------------------- Constructor----------------------------------------------
	public JavaGameMap(GameMap gameMap, JavaGameEngine gameEngine) {
		super();
		
		this.sizeX = gameMap.sizeX;
		this.sizeY = gameMap.sizeY;
		
		GameCellModel[][] map = new JavaGameCellModel[sizeY][];
		for (int y = 0; y < sizeY; y++) {
			map[y] = new JavaGameCellModel[sizeX];
			for (int x = 0; x < sizeX; x++) {
				map[y][x] = new JavaGameCellModel(gameMap.map[y][x], gameEngine, this);
			}
		}
		this.map = map;
		
		List<BasicUnitModel> units = new ArrayList<BasicUnitModel>();
		for (BasicUnitModel unit: gameMap.units) {
			units.add(new JavaBasicUnitModel(unit));
		}
		this.gameEngine = gameMap.gameEngine;
		this.units = units;
		this.bots = gameMap.bots;
	}
	//---------------------------------------------- Methods ----------------------------------------------
	
	@Override
	public void setUI(JavaGameMapUI ui) {
		this.ui = ui;
	}
	
	@Override
	public JavaGameMapUI getUI() {
		return ui;
	}
	
	public JavaGameEngine getJavaGameEngine() {
		return javaGameEngine;
	}
	
	public void setJavaGameEngine(JavaGameEngine javaGameEngine) {
		this.javaGameEngine = javaGameEngine;
	}
	
	public void SendWarriors(BasicCityModel from, BasicCityModel to) {
		if (from.getUuid().equals(to.getUuid())) {
			return;
		}
		BasicUnitModel unit = from.SendUnit(to);
		if (unit == null) {
			return;
		}
		JavaBasicUnitModel javaUnit = new JavaBasicUnitModel(unit);
		javaGameEngine.getUIGeneartor().generateBasicUnit(javaUnit);
		units.add(javaUnit);
	}
}
