package com.kilcote.evocraft.engine._base;

import com.kilcote.evocraft.views.game._base.GameObjUI;

public abstract class GameObjModel<UI extends GameObjUI> {
	
	private UI ui = null;
	
	public void setUI(UI ui) {
		this.ui = ui;
	}
	
	public UI getUI() {
		return ui;
	}
}
