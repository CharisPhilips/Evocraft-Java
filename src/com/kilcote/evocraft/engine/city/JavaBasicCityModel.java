package com.kilcote.evocraft.engine.city;

import com.kilcote.evocraft.engine.JavaGameEngine;
import com.kilcote.evocraft.engine._base.IGameObjModel;
import com.kilcote.evocraft.engine.map.JavaGameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.engine.unit.JavaBasicUnitModel;
import com.kilcote.evocraft.views.game.city.JavaBasicCityUI;

public class JavaBasicCityModel extends BasicCityModel implements IGameObjModel<JavaBasicCityUI> {

	private JavaBasicCityUI ui = null;
	
	//---------------------------------------------- Constructor ----------------------------------------------
	
	public JavaBasicCityModel(BasicCityModel city, JavaGameMap gameMap) {
		super();
		this.uuid = city.getUuid();
		this.playerId = city.playerId;
		this.ticksPerIncome = city.ticksPerIncome;
		this.maxWarriors = city.maxWarriors;
		this.currWarriors = city.currWarriors; 
		this.sendPersent = city.sendPersent; 
		this.atkPersent = city.atkPersent;
		this.defPersent = city.defPersent;
		this.pathToCities = city.pathToCities;
		this.departedUnits = city.departedUnits;
		this.gameMap = gameMap;
	}

	//---------------------------------------------- Methods ----------------------------------------------

	@Override
	public void setUI(JavaBasicCityUI ui) {
		this.ui = ui;
	}
	
	@Override
	public JavaBasicCityUI getUI() {
		return ui;
	}
	
	@Override
	public BasicUnitModel getDepartedUnit() {
		BasicUnitModel unit = super.getDepartedUnit();
		if (unit == null) {
			return null;
		}
		JavaBasicUnitModel javaUnit = new JavaBasicUnitModel(unit);
		((JavaGameMap)gameMap).getJavaGameEngine().getUIGeneartor().generateBasicUnit(javaUnit);
		return javaUnit;
	}

}
