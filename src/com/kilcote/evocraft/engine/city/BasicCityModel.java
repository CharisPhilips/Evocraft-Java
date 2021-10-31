package com.kilcote.evocraft.engine.city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.Game;
import com.kilcote.evocraft.engine._base.GameObjModel;
import com.kilcote.evocraft.engine.cell.GameCellModel;
import com.kilcote.evocraft.engine.interfaces.Settingable;
import com.kilcote.evocraft.engine.interfaces.Tickable;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.setting.BasicUnitSettings;
import com.kilcote.evocraft.engine.setting._base.SettinsSetter;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;
import com.kilcote.evocraft.views.game.city.BasicCityUI;

import javafx.util.Pair;

public class BasicCityModel extends GameObjModel<BasicCityUI> implements Tickable, Settingable {

	public int playerId;
	public int ticksPerIncome;
	public int maxWarriors;
	public double currWarriors; 
	public double sendPersent, atkPersent, defPersent;
	protected Map<BasicCityModel, List<Pair<Integer, Integer>>> pathToCities;
	public GameMap gameMap;
	
	public BasicCityModel(GameMap gameMap) {
		pathToCities = new HashMap<BasicCityModel, List<Pair<Integer, Integer>>>();
		this.gameMap = gameMap;
		this.gameMap.getUIGeneartor().generateBasicCity(this);
	}

	//---------------------------------------------- Methods ----------------------------------------------
	public boolean TickReact() {
		if (playerId != 0 && maxWarriors > currWarriors && Game.tick % ticksPerIncome == 0) {
			if ((currWarriors - (int)currWarriors) > 0) {
				currWarriors = ((int)currWarriors) + 1;
			} else {
				currWarriors++;
			}
			return true;
		} else if (Settings.gameplay_RemoveOvercapedUnits && maxWarriors < currWarriors && Game.tick % ticksPerIncome == 0) {
			currWarriors--;
			return true;
		}
		return false;
	}

	public int GetAtkWarriors() {
		return (int)Math.round(currWarriors * sendPersent * atkPersent);
	}

	public int GetDefWarriors() {
		return (int)Math.round(currWarriors * defPersent);
	}

	public BasicUnitModel SendUnit(BasicCityModel to) {
		try {
			int sendWarriors = GetAtkWarriors();
			if (sendWarriors == 0) {
				return null;
			}

			currWarriors -= sendWarriors;
			if (currWarriors < 0) {
				currWarriors = 0;
			}

			GetShortestPath(to);
			BasicUnitModel unit = CreateLinkedUnit(sendWarriors, to);
			unit.GetSettings(new BasicUnitSettings());
			return unit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void GetUnits(BasicUnitModel unit) {
		if (playerId == unit.playerId) {
			this.currWarriors += unit.warriorsCnt;
			if (!Settings.gameplay_SaveWarriorsOverCap && currWarriors > maxWarriors) {
				currWarriors = maxWarriors;
			}
		} else {
			double warriorsCnt = (double)(unit.warriorsCnt) / this.defPersent;
			if (this.currWarriors > warriorsCnt) {
				this.currWarriors -= warriorsCnt;
			} else if (!Settings.gameplay_EqualsMeansCapture && this.currWarriors == unit.warriorsCnt) {
				currWarriors = 0;
			} else {
				currWarriors = (int)(unit.warriorsCnt - this.currWarriors);
				playerId = unit.playerId;
			}
		}

		gameMap.units.remove(unit);
	}

	public void GetSettings(SettinsSetter settinsSetter) throws Exception {
		settinsSetter.SetSettings(this);
	}

	public int GetShortestPath(BasicCityModel to) {
		pathToCities.remove(to);
		BuildPathWithoutEnemyCitiesPath(to);
		return pathToCities.get(to).size() - 1;
	}

	public boolean isShortestPath(BasicCityModel to) {
		pathToCities.remove(to);
		return BuildPathWithoutEnemyCitiesPath(to);
	}

	protected BasicUnitModel CreateLinkedUnit(int sendWarriors, BasicCityModel to) {
		return new BasicUnitModel(sendWarriors, this.playerId, pathToCities.get(to), to, gameMap);
	}

	private boolean BuildPathWithoutEnemyCitiesPath(BasicCityModel to) {
		boolean rez;
		PathFinderCell[][] finder = new PathFinderCell[gameMap.map.length][];

		int fromX = 0, fromY = 0, toX = 0, toY = 0;

		for (int i = 0; i < finder.length; ++i) {
			finder[i] = new PathFinderCell[gameMap.map[0].length];
			for (int j = 0; j < finder[i].length; j++) {
				finder[i][j] = new PathFinderCell(gameMap.map[i][j]);
				if (gameMap.map[i][j].city == this) {
					fromX = j; fromY = i;
				} else if (gameMap.map[i][j].city == to) {
					toX = j; toY = i;
				}
			}
		}

		List<RecInfo> recList = new ArrayList<RecInfo>();
		RecInfo rec = new RecInfo();
		rec.x = fromX;
		rec.y = fromY;
		rec.value = 0; 
		recList.add(rec);
		while (recList.size() != 0) {
			Rec(recList, finder, recList.get(0), true);
			recList.remove(0);
		}

		List<Pair<Integer, Integer>> reversedPath = new ArrayList<Pair<Integer, Integer>>();

		UnRec(reversedPath, finder, toX, toY, finder[toY][toX].num);
		Collections.reverse(reversedPath);

		if (reversedPath.size() != 0) {
			pathToCities.put(to, reversedPath);
			rez = true;
		} else {
			BuildPath(to);
			rez = false;
		}

		return rez;
	}

	private void BuildPath(BasicCityModel to) {
		PathFinderCell[][] finder = new PathFinderCell[gameMap.map.length][];
		int fromX = 0, fromY = 0, toX = 0, toY = 0;

		for (int i = 0; i < finder.length; ++i) {
			finder[i] = new PathFinderCell[gameMap.map[0].length];
			for (int j = 0; j < finder[i].length; j++) {
				finder[i][j] = new PathFinderCell(gameMap.map[i][j]);
				if (gameMap.map[i][j].city == this) {
					fromX = j; fromY = i;
				} else if (gameMap.map[i][j].city == to) {
					toX = j; toY = i;
				}
			}
		}

		List<RecInfo> recList = new ArrayList<RecInfo>();
		RecInfo rec = new RecInfo();
		rec.x = fromX;
		rec.y = fromY;
		rec.value = 0; 
		recList.add(rec);

		while (recList.size() != 0) {
			Rec(recList, finder, recList.get(0), false);
			recList.remove(0);
		}

		List<Pair<Integer, Integer>> reversedPath = new ArrayList<Pair<Integer, Integer>>();

		UnRec(reversedPath, finder, toX, toY, finder[toY][toX].num);
		Collections.reverse(reversedPath);
		pathToCities.put(to, reversedPath);
	}

	public void Rec(List<RecInfo> recList, PathFinderCell[][] finder, RecInfo info, boolean isComparePlayerId) {

		int x = info.x;
		int y = info.y;
		if (finder[y][x].num != -1 && finder[y][x].num < info.value)
			return;

		if (isComparePlayerId) {
			if (gameMap.map[y][x].city != null && gameMap.map[y][x].city.playerId != this.playerId) {
				return;
			}
		}

		finder[y][x].num = info.value++;

		if (finder[y][x].isOpenBottom) {
			RecInfo rec = new RecInfo();
			rec.x = x;
			rec.y = y + 1;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenRight) {
			RecInfo rec = new RecInfo();
			rec.x = x + 1;
			rec.y = y;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenTop) {
			RecInfo rec = new RecInfo();
			rec.x = x;
			rec.y = y - 1;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenLeft) {
			RecInfo rec = new RecInfo();
			rec.x = x - 1;
			rec.y = y;
			rec.value = info.value;
			recList.add(rec);
		}
	}

	boolean UnRec(List<Pair<Integer, Integer>> reversedPath, PathFinderCell[][] finder, int x, int y, int prevValue) {
		if (prevValue == finder[y][x].num && finder[y][x].num != -1) {
			boolean prev = false;
			reversedPath.add(new Pair<Integer, Integer>(x, y));
			if (finder[y][x].isOpenBottom) {
				prev = UnRec(reversedPath, finder, x, y + 1, prevValue - 1);
			}
			if (finder[y][x].isOpenTop && !prev) {
				prev = UnRec(reversedPath, finder, x, y - 1, prevValue - 1);
			}
			if (finder[y][x].isOpenLeft && !prev) {
				prev = UnRec(reversedPath, finder, x - 1, y, prevValue - 1);
			}
			if (finder[y][x].isOpenRight && !prev) {
				prev = UnRec(reversedPath, finder, x + 1, y, prevValue - 1);
			}
			return true;
		}
		return false;
	}

	class PathFinderCell {
		public boolean isOpenBottom = false;
		public boolean isOpenLeft = false;
		public boolean isOpenRight = false; 
		public boolean isOpenTop = false;
		public int num = -1;

		public PathFinderCell(GameCellModel cell) {
			this.isOpenBottom = cell.isOpenBottom;
			this.isOpenLeft = cell.isOpenLeft;
			this.isOpenRight = cell.isOpenRight;
			this.isOpenTop = cell.isOpenTop;
		}
	}

	class RecInfo {
		public int x, y, value;
	}

}
