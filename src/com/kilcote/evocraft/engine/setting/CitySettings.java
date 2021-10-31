package com.kilcote.evocraft.engine.setting;

import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.setting._base.SettinsSetter;

public class CitySettings extends SettinsSetter{
	
	@Override
	public void SetSettings(Object obj) throws Exception {
		BasicCityModel city = (BasicCityModel)(obj);
		if (city == null) {
			throw new Exception("Wrong city in BasicCitySettings.SetSettings");
		}
			
		city.maxWarriors = 50;
		city.currWarriors = 10;
		city.sendPersent = 0.5f;
		city.atkPersent = 1.0f;
		city.defPersent = 2.0f;
		city.ticksPerIncome = 50;
	}

	@Override
	protected void LoadSettingsFromFile() {
		
	}
}
