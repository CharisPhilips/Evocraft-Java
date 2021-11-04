package com.kilcote.evocraft.engine.unit;

import com.kilcote.evocraft.engine._base.IGameObjModel;
import com.kilcote.evocraft.engine.interfaces.Settingable;
import com.kilcote.evocraft.engine.interfaces.Tickable;
import com.kilcote.evocraft.views.game.unit.JavaBasicUnitUI;

public class JavaBasicUnitModel extends BasicUnitModel implements IGameObjModel<JavaBasicUnitUI>, Tickable, Settingable { 

	private JavaBasicUnitUI ui = null;
	
	//---------------------------------------------- Constructor ----------------------------------------------
	public JavaBasicUnitModel(BasicUnitModel unit) {
		super();
		this.uuid = unit.getUuid();
		this.path = unit.path;
		this.currPathIndex = unit.currPathIndex;
		this.currTickOnCell = unit.currTickOnCell;

		this.type = unit.type;
		this.warriorsCnt = unit.warriorsCnt;
		this.atkPercent = unit.atkPercent;
		this.moveSteps = unit.moveSteps;
		this.destination = unit.destination;

		//---------------------------------------------- Properties ----------------------------------------------
		this.playerId = unit.playerId;
		this.gameMap = unit.gameMap;
		
		this.gameMap.getMapCell(path.get(currPathIndex).getValue(), path.get(currPathIndex).getKey()).units.remove(unit);
		this.gameMap.getMapCell(path.get(currPathIndex).getValue(), path.get(currPathIndex).getKey()).units.add(this);
	}
	
	//---------------------------------------------- Methods ----------------------------------------------

	@Override
	public void setUI(JavaBasicUnitUI ui) {
		this.ui = ui;
	}
	
	@Override
	public JavaBasicUnitUI getUI() {
		return ui;
	}
}
