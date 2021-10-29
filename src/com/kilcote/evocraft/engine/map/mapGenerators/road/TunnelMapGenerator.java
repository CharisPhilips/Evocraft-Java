package com.kilcote.evocraft.engine.map.mapGenerators.road;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.city.BasicCityPlacer;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;

import javafx.util.Pair;

public class TunnelMapGenerator implements BasicMapGenerator {

	@Override
	public GameMap GenerateRandomMap(int sizeX, int sizeY, BasicCityPlacer sityPlacer, BasicCityId basicCityId) {
		
		GameMap m = new GameMap(sizeX, sizeY);
		LaburintCell[][] map = new LaburintCell[sizeY][];
		
		for (int i = 0; i < sizeY; i++) {
			map[i] = new LaburintCell[sizeX];
			for (int j = 0; j < sizeX; j++) {
				map[i][j] = new LaburintCell();
			}
		}

		int digNum = 0;
		List<Pair<Integer, Integer>> digPos;

		if (Settings.generator_TunenelMapGenerator_CrossOnStart) {
			digPos = new ArrayList<Pair<Integer, Integer>>(Arrays.asList(new Pair<Integer, Integer>(Settings.rnd.nextInt(1, sizeX - 1), Settings.rnd.nextInt(1, sizeY - 1))));
		} else {
			digPos = new ArrayList<Pair<Integer, Integer>>(Arrays.asList(new Pair<Integer, Integer>(Settings.rnd.nextInt(0, sizeX ), Settings.rnd.nextInt(0, sizeY))));
		}

		while (digPos.size() != 0) {
			digNum = Dig(digNum, map, digPos, sizeX, sizeY, digPos.get(0).getKey(), digPos.get(0).getValue());
			digPos.remove(0);
		}

		for (int i = 0; i < sizeY; ++i) { 
			for (int j = 0; j < sizeX; ++j) {
				m.map[i][j].isOpenLeft = map[i][j].isOpenLeft;
				m.map[i][j].isOpenRight = map[i][j].isOpenRight;
				m.map[i][j].isOpenTop = map[i][j].isOpenTop;
				m.map[i][j].isOpenBottom = map[i][j].isOpenBottom;
			}
		}

		sityPlacer.PlaceCities(m, basicCityId);
		return m;
	}
	
	private boolean CellsLeftUnvisited(LaburintCell[][] map, int sizeX, int sizeY) {
		for (int i = 0; i < sizeY; ++i) {
			for (int j = 0; j < sizeX; ++j) {
				if(!map[i][j].isVisited) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int Dig(int digNum, LaburintCell[][] map, List<Pair<Integer, Integer>> digPos, int sizeX, int sizeY, int x, int y) {
		digNum++;

		if (Settings.rnd.nextInt(0, 100) < Settings.generator_TunenelMapGenerator_SkipChance && digNum > Settings.generator_TunenelMapGenerator_IgnoreSkipChanceForFirstNTitles) {
			return digNum;
		}
		map[y][x].isVisited = true;
		List<Pair<Integer, Integer>> jumpPos = new ArrayList<Pair<Integer, Integer>>();
		if (x != sizeX - 1 && !map[y][x + 1].isVisited)
			jumpPos.add(new Pair<Integer, Integer>(x + 1, y));
		if (x != 0 && !map[y][x - 1].isVisited)
			jumpPos.add(new Pair<Integer, Integer>(x - 1, y));
		if (y != sizeY - 1 && !map[y + 1][x].isVisited)
			jumpPos.add(new Pair<Integer, Integer>(x, y + 1));
		if (y != 0 && !map[y - 1][x].isVisited)
			jumpPos.add(new Pair<Integer, Integer>(x, y - 1));

		int jumpCnt = (int)(jumpPos.size() != 0 ? (Settings.rnd.nextInt(1, jumpPos.size())) : 0);

		if (Settings.generator_TunenelMapGenerator_CrossOnStart && digNum == 1) {
			jumpCnt = 4;
		}

		while (jumpCnt-- > 0) {
			Pair<Integer, Integer> curr = jumpPos.get(Settings.rnd.nextInt(0, jumpPos.size()));
			jumpPos.remove(curr);

			if (curr.getKey() == x + 1) {
				map[y][x].isOpenRight = map[y][x + 1].isOpenLeft = true;
			}
			if (curr.getKey() == x - 1) {
				map[y][x].isOpenLeft = map[y][x - 1].isOpenRight = true;
			}
			if (curr.getValue() == y - 1) {
				map[y][x].isOpenTop = map[y - 1][x].isOpenBottom = true;
			}
			if (curr.getValue() == y + 1) {
				map[y][x].isOpenBottom = map[y + 1][x].isOpenTop = true;
			}
			digPos.add(curr);
		}
		return digNum;
	}
	
	class LaburintCell {
		public boolean isOpenLeft;
		public boolean isOpenTop;
		public boolean isOpenRight;
		public boolean isOpenBottom;
		public boolean isVisited;
	}

}
