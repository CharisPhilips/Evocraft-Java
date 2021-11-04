package com.kilcote.evocraft.engine.city;

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
		this.type = city.type;
		this.level = city.level;
		this.currWarriors = city.currWarriors; 
		this.atkPercent = city.atkPercent;
		this.defPercent = city.defPercent;
		this.moveSteps = city.moveSteps;
		this.pathToCities = city.pathToCities;
		this.departedUnits = city.departedUnits;
		this.ticksPerIncome = city.ticksPerIncome;
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
