package com.kilcote.evocraft.engine.setting._base;

public abstract class SettinsSetter {
	abstract public void SetSettings(Object obj) throws Exception;
	abstract protected void LoadSettingsFromFile();
}
