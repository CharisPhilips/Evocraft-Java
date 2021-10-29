//package com.kilcote.evocraft.views.game.map;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.kilcote.evocraft.common.Settings;
//import com.kilcote.evocraft.engine.bot._base.BasicBot;
//import com.kilcote.evocraft.engine.city.BasicCity;
//import com.kilcote.evocraft.engine.map.GameMap;
//import com.kilcote.evocraft.engine.unit.BasicUnit;
//import com.kilcote.evocraft.views.game.cell.GameCellUI;
//
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//
//public class GameMapUI {
////	//---------------------------------------------- Fields ----------------------------------------------
////	int cellSizeX;
////	int cellSizeY;
////	//---------------------------------------------- Properties ----------------------------------------------
////	public GameCellUI[][] map;
////	public List<BasicCity> cities;
////	public List<BasicUnit> units;
////	public List<BasicBot> bots;
//	
//	private GameMap model = null;;
//
//	public int sizeX;
//	public int sizeY;
//	
//	Pane canvas;
//	//---------------------------------------------- Ctor ----------------------------------------------
//	public GameMapUI(int SizeX, int SizeY) {
//		sizeX = SizeX; sizeY = SizeY;
//		map = new GameCellUI[sizeY][];
//		for (int i = 0; i < sizeY; ++i) {
//			map[i] = new GameCellUI[sizeX];
//			for (int j = 0; j < sizeX; ++j) {
//				map[i][j] = new GameCellUI();
//			}
//		}
//		cities = new ArrayList<BasicCity>();
//		units = new ArrayList<BasicUnit>();
//		bots = new ArrayList<BasicBot>();
//		
//		for (int i = 0; i < Settings.generator_CityId_Bots; i++) {
//			bots.add(null);
//		}
////		BasicCity.gameMap = this;
//	}
//
//	//---------------------------------------------- Methods ----------------------------------------------
//	public void Tick() {
//		for(BasicCity city : this.cities) {
//			city.TickReact();
//		}
//			
//		REPEAT_UNITS_TURN:
//		for (int nIndex = units.size() - 1; nIndex >= 0; nIndex--) {
//			BasicUnit unit = units.get(nIndex);
//			if (unit.TickReact()) {
//				continue REPEAT_UNITS_TURN;
//			}
//		}
//
//		for (int nIndex = bots.size() - 1; nIndex >= 0; nIndex--) {
//			for (BasicBot bot : this.bots) {
//				if(bot != null) {
//					bot.TickReact();
//				}
//			}
//		}
//	}
//
//	public void SendWarriors(List<BasicCity> from, BasicCity to) {
//		for (BasicCity i : from) {
//			if (i.currWarriors > 1) {
//				SendWarriors(i, to);
//			}
//		}
//	}
//
//	public void SendWarriors(BasicCity from, BasicCity to) {
//		if (from == to)
//			return;
//
//		BasicUnit unit = from.SendUnit(to);
//
//		if (unit== null) {
//			return;
//		}
//
//		unit.SetCanvas(canvas);
//
//		cellSizeX = (int)canvas.getWidth();
//		cellSizeY = (int)canvas.getHeight();
//		unit.InitializeDraw();
//
//		units.add(unit);
//	}
//
//	public void SetBot(int id, BasicBot type) {
//		bots.set(id, type);
//	}
//
//	public void SetCanvas(Pane canvas) {
//		this.canvas = canvas;
//	}
//
//	public void DrawStatic(GridPane grid) {
//		for (int i = 0; i < sizeY; ++i) {
//			for (int j = 0; j < sizeX; ++j) {
//				map[i][j].SetGrid(grid);
//				map[i][j].DrawOnGameCell(j, i);
//				if(map[i][j].city != null) {
//					map[i][j].city.SetGrid(grid);
//					map[i][j].city.DrawOnGameCell(j, i);
//				}
//			}
//		}
//	}
//
//	public void InvalidateDraw() {
//		for (int i = 0; i < sizeY; ++i) {
//			for (int j = 0; j < sizeX; ++j) {
//				map[i][j].InvalidateDraw();
//			}
//		}
//		for (BasicCity city : this.cities) {
//			city.InvalidateDraw();
//		}
//		for (BasicUnit unit : this.units) {
//			unit.InvalidateDraw();
//		}
//	}
//}
