package com.kilcote.evocraft.engine.setting;

import com.kilcote.evocraft.engine.setting._base.UnitSettings;
import com.kilcote.evocraft.engine.unit.BasicUnit;

public class BasicUnitSettings extends UnitSettings {

	@Override
	public void SetSettings(Object obj) throws Exception {
		BasicUnit unit = (BasicUnit)(obj);
		if (unit == null) {
			throw new Exception("Wrong unit in BasicUnitSettings.SetSettings");
		}
		unit.tickPerTurn = 10;
	}

	@Override
	protected void LoadSettingsFromFile() {
		
	}
}
