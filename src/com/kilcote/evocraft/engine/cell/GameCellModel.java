package com.kilcote.evocraft.engine.cell;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine._base.GameObjModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game.cell.GameCellUI;

public class GameCellModel extends GameObjModel<GameCellUI> {
	//---------------------------------------------- Properties ----------------------------------------------
	public boolean isOpenLeft;
	public boolean isOpenTop;
	public boolean isOpenRight;
	public boolean isOpenBottom;

	public BasicCityModel city;
	public List<BasicUnitModel> units;

	//---------------------------------------------- Ctor ----------------------------------------------
	public GameCellModel() {
		this.isOpenBottom = false;
		this.isOpenLeft = false;
		this.isOpenRight = false;
		this.isOpenTop = false;
		units = new ArrayList<BasicUnitModel>();
		city = null;
	}
}
