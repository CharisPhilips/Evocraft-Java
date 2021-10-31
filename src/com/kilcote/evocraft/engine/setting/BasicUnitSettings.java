package com.kilcote.evocraft.engine.setting;

import com.kilcote.evocraft.engine.setting._base.UnitSettings;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;

public class BasicUnitSettings extends UnitSettings {

	@Override
	public void SetSettings(Object obj) throws Exception {
		BasicUnitModel unit = (BasicUnitModel)(obj);
		if (unit == null) {
			throw new Exception("Wrong unit in BasicUnitSettings.SetSettings");
		}
		unit.tickPerTurn = 10;
	}

	@Override
	protected void LoadSettingsFromFile() {
		
	}
}
