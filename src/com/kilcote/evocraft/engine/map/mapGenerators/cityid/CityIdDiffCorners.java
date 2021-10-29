package com.kilcote.evocraft.engine.map.mapGenerators.cityid;

import java.util.Random;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.map.GameMap;

public class CityIdDiffCorners implements BasicCityId {

	@Override
	public void PlaceId(GameMap m, Random rnd) {
		int playerTowns = Settings.generator_CityId_TownsPerPlayer;
		while (playerTowns-- != 0) {
			int mini = m.sizeY, minj = m.sizeX;
			for (int i = 0; i < m.sizeY; ++i) {
				for (int j = 0; j < m.sizeX; ++j) {
					if (m.map[i][j].city != null && m.map[i][j].city.playerId == 0 && mini + minj > i + j) {
						mini = i;
						minj = j;
					}
				}
			}
			m.map[mini][minj].city.playerId = 1;
		}

		boolean end = false;
		int BotsCnt = Settings.generator_CityId_Bots;
		while (BotsCnt-- != 0) {
			int BotsTowns = Settings.generator_CityId_TownsPerBot;
			while (BotsTowns-- != 0) {
				int maxi = 0, maxj = 0;
				for (int i = m.sizeY - 1; i >= 0; --i) {
					for (int j = m.sizeX - 1; j >= 0; --j) {
						if (m.map[i][j].city != null && m.map[i][j].city.playerId == 0 && maxi + maxj < i + j) {
							maxi = i;
							maxj = j;
						}
					}
				}
				if (maxi == 0 && maxj == 0 && m.map[maxi][maxj].city == null) {
					end = true;
					break;
				}
				m.map[maxi][maxj].city.playerId = (int)(BotsCnt + 2);
			}
			if (end) {
				break;
			}
		}		
	}

}
