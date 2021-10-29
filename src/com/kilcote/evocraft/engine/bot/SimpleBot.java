package com.kilcote.evocraft.engine.bot;

import java.util.List;

import com.kilcote.evocraft.engine.Game;
import com.kilcote.evocraft.engine.bot._base.BasicBot;
import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnit;

public class SimpleBot extends BasicBot {

	int botStreakCnt = 0;
	boolean botStreak = false;

	public SimpleBot(GameMap map, List<BasicCity> cities, List<BasicUnit> Units, int botId) {
		this.map = map;
		this.cities = cities;
		this.units = Units;
		this.playerId = botId;
	}


	@Override
	public boolean TickReact() {
		if (Game.tick > 100 && Game.tick % 50 == 0) {
			if (Game.tick % 200 == 0)
				map.SendWarriors(this.cities.get(0), this.cities.get(2));

			if (this.cities.get(2).currWarriors >= 20) {
				botStreak = true;
				botStreakCnt = 0;
			}

			if (botStreak) {
				botStreakCnt++;
				map.SendWarriors(this.cities.get(2), this.cities.get(1));

				if (botStreakCnt == 3) {
					botStreak = false;
				}
			}

			return true;
		}
		return false;
	}


}
