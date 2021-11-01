package com.kilcote.evocraft.engine._base;

import com.kilcote.evocraft.views.game._base.GameObjUI;

public interface IGameObjModel<UI extends GameObjUI> {
	public void setUI(UI ui);
	public UI getUI();
}
