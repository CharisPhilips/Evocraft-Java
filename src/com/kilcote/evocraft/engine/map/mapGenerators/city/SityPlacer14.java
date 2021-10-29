package com.kilcote.evocraft.engine.map.mapGenerators.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kilcote.evocraft.common.MyRandom;
import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.map.mapGenerators.cityid.BasicCityId;
import com.kilcote.evocraft.engine.setting.CitySettings;

import javafx.util.Pair;

public class SityPlacer14 implements BasicCityPlacer {

	//---------------------------------------------- Fields ----------------------------------------------
	List<BasicCity> cities = new ArrayList<BasicCity>(Settings.locateMemory_SizeForTowns);
	List<Pair<Integer, Integer>> bestCitiesPos = new ArrayList<Pair<Integer, Integer>>();

	//---------------------------------------------- Methods - main ----------------------------------------------
	@Override
	public void PlaceCities(GameMap m, BasicCityId chooserId) {

		FormCitiesList(m, Settings.rnd);

		int cnt = Settings.generator_SityPlacer14_Code_MaxSityPlaceRepeats;
		do {
			if (cnt-- == 0) {
				break;
			}

			FormBestPosition(m, Settings.rnd);
			MixCitiesAndPos(m, Settings.rnd);
			SpecialInsertWith1Road(m, Settings.rnd);
			InsertIntoMap(m);
		} while (cities.size() != 0);

		chooserId.PlaceId(m, Settings.rnd);

		m.cities = GetCitiesOnMap(m);
	}

	//-------------------------------------------- Methods - parts --------------------------------------------

	private void FormCitiesList(GameMap m, Random rnd) {
		int gameQuads = 0, citiesCnt;

		if (Settings.generator_SityPlacer14_QuadIsRoad) {
			for (int i = 0; i < m.sizeY; ++i) {
				for (int j = 0; j < m.sizeX; ++j) {
					if (m.map[i][j].isOpenBottom || m.map[i][j].isOpenTop || m.map[i][j].isOpenLeft || m.map[i][j].isOpenRight) {
						gameQuads++;
					}
				}
			}
			gameQuads /= Settings.generator_SityPlacer14_Quad_Size;
		}
		else {
			gameQuads = m.sizeX * m.sizeY / Settings.generator_SityPlacer14_Quad_Size;
		}
		if (gameQuads < Settings.generator_SityPlacer14_Quad_MinimimCnt) {
			gameQuads = Settings.generator_SityPlacer14_Quad_MinimimCnt;
		}

		//Neet to check
		citiesCnt = Settings.rnd.nextInt((Settings.generator_SityPlacer14_Quad_Cities_Min * gameQuads), (Settings.generator_SityPlacer14_Quad_Cities_Max * gameQuads));

		for (int i = 0; i < citiesCnt; ++i){
			BasicCity bs = new BasicCity(m);
			try {
				bs.GetSettings(new CitySettings());
			} catch (Exception e) {
				e.printStackTrace();
			}
			cities.add(bs);
		}
	}

	private void FormBestPosition(GameMap m, MyRandom rnd) {
		for (int i = 0; i < m.sizeY; ++i) {
			for (int j = 0; j < m.sizeX; ++j) {
				int s = (m.map[i][j].isOpenBottom ? 1 : 0) +
						(m.map[i][j].isOpenTop ? 1 : 0) +
						(m.map[i][j].isOpenLeft ? 1 : 0) +
						(m.map[i][j].isOpenRight ? 1 : 0);
				if (s == 1 && rnd.nextInt(0, 100) < Settings.generator_SityPlacer14_Chance_PosWith1Road) {
					bestCitiesPos.add(new Pair<Integer, Integer>(i, j));
				} else if (s == 2 && rnd.nextInt(0, 100) < Settings.generator_SityPlacer14_Chance_PosWith2Road) {
					bestCitiesPos.add(new Pair<Integer, Integer>(i, j));
				} else if (s == 3 && rnd.nextInt(0, 100) < Settings.generator_SityPlacer14_Chance_PosWith3Road) {
					bestCitiesPos.add(new Pair<Integer, Integer>(i, j));
				} else if (s == 4 && rnd.nextInt(0, 100) < Settings.generator_SityPlacer14_Chance_PosWith4Road) {
					bestCitiesPos.add(new Pair<Integer, Integer>(i, j));
				}
			}
		}
	}

	private void MixCitiesAndPos(GameMap m, MyRandom rnd) {
		for (int i = 0; i < bestCitiesPos.size() * (rnd.nextDouble() + 1); i++) {
			int pos1, pos2;
			Object tmp;
			if (bestCitiesPos.size() != 1 && bestCitiesPos.size() != 2) {
				pos1 = rnd.nextInt(0, bestCitiesPos.size());
				do {
					pos2 = rnd.nextInt(0, bestCitiesPos.size());
				}
				while (pos2 == pos1);
				tmp = bestCitiesPos.get(pos1);
				bestCitiesPos.set(pos1, bestCitiesPos.get(pos2));
				bestCitiesPos.set(pos2, (Pair<Integer, Integer>)tmp);
			}

			if (cities.size() != 1 && cities.size() != 2) {
				pos1 = rnd.nextInt(0, cities.size());
				do {
					pos2 = rnd.nextInt(0, cities.size());
				}
				while (pos2 == pos1);
				tmp = cities.get(pos1);
				cities.set(pos1, cities.get(pos2));
				cities.set(pos2, (BasicCity)tmp);
			}
		}
	}

	private void SpecialInsertWith1Road(GameMap m, Random rnd) {
		if (Settings.generator_SityPlacer14_FillAllWith1Road) {
			for (int k = 0; k < bestCitiesPos.size() && cities.size() != 0; ++k) {
				if (IsFreeAround(k, m)) {
					int i = bestCitiesPos.get(k).getKey(), j = bestCitiesPos.get(k).getValue();
					int s = (m.map[i][j].isOpenBottom ? 1 : 0) + (m.map[i][j].isOpenTop ? 1 : 0) +
							(m.map[i][j].isOpenLeft ? 1 : 0) + (m.map[i][j].isOpenRight ? 1 : 0);
					if (s == 1) {
						m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].city = cities.get(0);
						bestCitiesPos.remove(k);
						cities.remove(0);
						--k;
					}
				}
			}
		}

	}

	private void InsertIntoMap(GameMap m) {
		while (cities.size() != 0 && bestCitiesPos.size() != 0) {
			if (IsFreeAround(0, m)) {
				m.map[bestCitiesPos.get(0).getKey()][bestCitiesPos.get(0).getValue()].city = cities.get(0);
				bestCitiesPos.remove(0);
				cities.remove(0);
			}
			else
				bestCitiesPos.remove(0);
		}
	}

	//-------------------------------------- Methods - Support --------------------------------------------

	private boolean IsFreeAround(int k, GameMap m) {
		return (bestCitiesPos.get(k).getKey() == 0 || !m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenTop ||
				(bestCitiesPos.get(k).getKey() > 0 && m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenTop &&
					m.map[bestCitiesPos.get(k).getKey() - 1][bestCitiesPos.get(k).getValue()].city == null)) &&

				(bestCitiesPos.get(k).getKey() == m.map.length - 1 || !m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenBottom ||
				(bestCitiesPos.get(k).getKey() < m.map.length - 1 && m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenBottom &&
						m.map[bestCitiesPos.get(k).getKey() + 1][bestCitiesPos.get(k).getValue()].city == null)) &&

				(bestCitiesPos.get(k).getValue() == 0 || !m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenLeft ||
				(bestCitiesPos.get(k).getValue() > 0 && m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenLeft &&
						m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue() - 1].city == null)) &&

				(bestCitiesPos.get(k).getValue() == m.map[0].length - 1 || !m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenRight ||
				(bestCitiesPos.get(k).getValue() < m.map[0].length- 1 && m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue()].isOpenRight &&
						m.map[bestCitiesPos.get(k).getKey()][bestCitiesPos.get(k).getValue() + 1].city == null));
	}

	private int CntCities(GameMap m) {
		int rez = 0;
		for (int i = 0; i < m.sizeY; i++) {
			for (int j = 0; j < m.sizeX; j++) {
				if (m.map[i][j].city != null) {
					rez++;
				}
			}
		}
		return rez;
	}

	private List<BasicCity> GetCitiesOnMap(GameMap m) {
		List<BasicCity> list = new ArrayList<BasicCity>();
		for (int i = 0; i < m.sizeY; i++) {
			for (int j = 0; j < m.sizeX; ++j) {
				if (m.map[i][j].city != null) {
					list.add(m.map[i][j].city);
				}
			}
		}
		return list;
	}
}
