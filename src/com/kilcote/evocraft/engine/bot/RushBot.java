package com.kilcote.evocraft.engine.bot;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.Game;
import com.kilcote.evocraft.engine.bot._base.BasicBot;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.unit.BasicUnitModel;

import javafx.util.Pair;

public class RushBot extends BasicBot {

	//---------------------------------------------- Fields ----------------------------------------------
	//with bot cities
	List<BasicCityModel> botCities = new ArrayList<BasicCityModel>();
	List<BasicCityModel> overcapedBotCities = new ArrayList<BasicCityModel>();

	List<BasicCityModel> botCitiesUnderAttack = new ArrayList<BasicCityModel>();
	List<List<BasicUnitModel>> botCitiesUnderAttackUnits = new ArrayList<List<BasicUnitModel>>();

	//With enemy cities
	List<BasicCityModel> rushingCities = new ArrayList<BasicCityModel>();
	List<BasicCityModel> canAttackDirectly = new ArrayList<BasicCityModel>();


	boolean isRushing = false;
	int rushWaveRemains;
	BasicCityModel rushCity;
	int tickReact;

	//---------------------------------------------- Properties ----------------------------------------------

	//---------------------------------------------- Ctor ----------------------------------------------
	public RushBot(GameMap Map, List<BasicCityModel> cities, List<BasicUnitModel> Units, int botId ) {
		map = Map;
		this.cities = cities;
		this.units = Units;
		this.playerId = botId;
		this.tickReact = Settings.bot_rushBot_Tick_React;
	}

	//---------------------------------------------- Methods - Main ----------------------------------------------

	@Override
	public boolean TickReact() {
		if (Game.tick > Settings.bot_rushBot_Tick_IgnoreFirstN &&
				Game.tick % tickReact == 0) {

			RecalcBotCities();
			RecalcRushingCities();
			RecalcCanAttackDirectly();

			if (!isRushing)
				CalculateWhoNeedToBeRushed();
			if (isRushing)
				RushFromAllCities();

			RecalcOvercapedBotCities();
			RecalcBotCitiesUnderAttack();

			if (Settings.bot_rushBot_IsProtectCities)
				ProtectCities();

			if (Settings.bot_rushBot_IsDropOvercapacityUnits)
				DropOvercapacityUnits();

			if (Settings.bot_rushBot_IsMoveUnitsToWeakCities)
				MoveUnitsToWeakSity();

			return true;
		}

		return false;
	}

	//---------------------------------------------- Methods - fillData ----------------------------------------------

	private void RecalcBotCities() {
		this.botCities.clear();
		for (BasicCityModel city : this.cities) {
			if (city.playerId == playerId)
				this.botCities.add(city);
		}
	}

	private void RecalcRushingCities() {
		rushingCities.clear();
		for (BasicUnitModel unit : units) {
			if (unit.playerId == playerId && !rushingCities.contains(unit.destination)) {
				rushingCities.add(unit.destination);
			}
		}
	}

	private void RecalcCanAttackDirectly() {
		canAttackDirectly.clear();
		for (BasicCityModel city : this.cities) {
			if (!this.botCities.contains(city)) {
				boolean directly = false;
				for (BasicCityModel bc : this.botCities) {
					boolean tmp = city.isShortestPath(city);
					if (tmp) {
						directly = true;
						break;
					}
				}
				if (directly) {
					canAttackDirectly.add(city);
				}
			}
		}
	}

	private void RecalcOvercapedBotCities() {
		this.overcapedBotCities.clear();
		for (BasicCityModel bc : this.botCities) {
			int currUnits = (int) (bc.currWarriors + Settings.bot_rushBot_Overcapacity_NearValue);
			for (BasicUnitModel unit : this.units) {
				if (unit.playerId == this.playerId && unit.destination == bc && unit.TicksLeftToDestination() <= this.tickReact) {
					currUnits += unit.warriorsCnt;
				}
			}
			if (currUnits >= bc.maxWarriors) {
				overcapedBotCities.add(bc);
			}
		}
	}

	private void RecalcBotCitiesUnderAttack() {
		botCitiesUnderAttack.clear();
		botCitiesUnderAttackUnits.clear();
		for (BasicCityModel bc : this.botCities) {
			boolean isUnderAttack = false;
			for (BasicUnitModel unit : this.units) {
				if (unit.playerId != this.playerId && unit.destination == bc) {
					isUnderAttack = true;
					break;
				}
			}

			if (isUnderAttack) {
				botCitiesUnderAttackUnits.add(new ArrayList<BasicUnitModel>());
				botCitiesUnderAttack.add(bc);
				for (BasicUnitModel unit : this.units) {
					if (unit.destination == bc) {
						botCitiesUnderAttackUnits.get(botCitiesUnderAttackUnits.size() - 1).add(unit);
					}
				}
			}
		}
	}

	//---------------------------------------------- Methods - behavior ----------------------------------------------
	private void CalculateWhoNeedToBeRushed() {
		List<List<BasicCityModel>> potentialRushes = new ArrayList<List<BasicCityModel>>();
		for (int i = 1; i <= Settings.bot_rushBot_Rush_Cnt; i++) {
			potentialRushes.add(new ArrayList<BasicCityModel>());

			for (BasicCityModel city : canAttackDirectly) {
				int potentialArmy = CalcPotentialArmy(i, city.defPersent);
				if ( !rushingCities.contains(city) && (GetEnemyArmy(city)) < potentialArmy) {
					boolean isInPrev = false;
					for (List<BasicCityModel> rush : potentialRushes) {
						if (rush.contains(city)) {
							isInPrev = true;
							break;
						}
					}
					if (!isInPrev) {
						potentialRushes.get(i - 1).add(city);
					}
				}
			}
		}

		List<Pair<Integer, Integer>> rushChance = new ArrayList<Pair<Integer, Integer>>(potentialRushes.size());

		int tmp = 0;
		while (tmp != potentialRushes.size()) {
			if (potentialRushes.get(tmp).size() != 0) {
				rushChance.add(new Pair<Integer, Integer>(Settings.bot_rushBot_RushWaves_Chance.get(tmp).getKey(), Settings.bot_rushBot_RushWaves_Chance.get(tmp).getValue()));
			}
			tmp++;
		}

		//System.Windows.MessageBox.Show(rushChance.Count.ToString());

		if (rushChance.size() != 0) {
			int potentialRushPos = 0;

			int sumPersent = 0;
			for (Pair<Integer, Integer> i : rushChance)
			sumPersent += i.getValue();
			if (sumPersent != 100) {
				for (int i = 0; i < rushChance.size(); i++) {
					rushChance.set(i, new Pair<Integer, Integer>(rushChance.get(i).getKey(), (int)Math.round((double)(rushChance.get(i).getValue()) / sumPersent * 100)));
				}
			}

			int randPersent = (int)Settings.rnd.nextInt(0, 100);
			for (int i = 0; i < rushChance.size(); ++i) {
				if (randPersent <= rushChance.get(i).getValue()) {
					potentialRushPos = rushChance.get(i).getKey() - 1;
					break;
				}
				else {
					randPersent -= rushChance.get(i).getValue();
				}
			}

			List<BasicCityModel> potentialRush = potentialRushes.get(potentialRushPos);
			rushCity = potentialRush.get(Settings.rnd.nextInt(0, potentialRush.size()));
			isRushing = true;
			rushWaveRemains = (byte)(potentialRushPos + 1);
		}

	}

	private void RushFromAllCities() {
		if (rushCity.playerId == this.playerId)
			isRushing = false;

		if (rushWaveRemains-- == 0) {
			isRushing = false;
		}
		else {
			for (BasicCityModel sity : this.botCities) {
				map.SendWarriors(sity, rushCity);
			}
		}
	}

	void ProtectCities() {
//		for (int i = 0; i < this.botCitiesUnderAttack.size(); ++i) {
//			BasicCity sity = this.botCitiesUnderAttack.get(i);
//			List<BasicUnit> units = this.botCitiesUnderAttackUnits.get(i);
//
//			int attackersCnt = 0;
//			for(BasicUnit unit : units) {
//				if (unit.playerId != this.playerId)
//					attackersCnt += unit.warriorsCnt;
//				else
//					attackersCnt -= unit.warriorsCnt;
//			}
//
//			if (attackersCnt >= Settings.bot_rushBot_Protect_MinimumUnitsLeft) {
//
//			}
//		}
	}

	private void DropOvercapacityUnits() {
		for (BasicCityModel i : this.overcapedBotCities) {
			map.SendWarriors(i, this.cities.get(Settings.rnd.nextInt(0, this.cities.size())));
		}
	}

	private void MoveUnitsToWeakSity() {

	}

	//---------------------------------------------- Methods - Support  ----------------------------------------------
	private int CalcPotentialArmy(int rushesCntBase, double defPersent) {
		int potentialArmy = 0;
		for (BasicCityModel city : this.botCities) {
			int currSend = 0, rushesCnt = rushesCntBase;
			while (rushesCnt-- != 0) {
				currSend += (int)((city.currWarriors - currSend) * city.sendPersent);
			}
			potentialArmy += (int)Math.round(currSend / defPersent);
		}
		//System.Windows.MessageBox.Show(botCities[0].playerId.ToString() + "  " + potentialArmy.ToString());
		return potentialArmy;
	}

	private int GetAvgDistance(BasicCityModel sity) {
		double avg = 0;
		for (BasicCityModel bc : this.botCities) {
			avg += bc.GetShortestPath(sity);
		}
		avg /= this.botCities.size();
		return (int)Math.round(avg);
	}

	private double GetEnemyArmy(BasicCityModel cityTo) {
		if (cityTo.playerId == 0)
			return cityTo.currWarriors + Settings.bot_rushBot_Rush_MinimumMore;
		else
			return cityTo.currWarriors + Settings.bot_rushBot_Rush_MinimumMore
					+ ((Settings.basicUnit_ticks_MoveWarrior * GetAvgDistance(cityTo)) / cityTo.ticksPerIncome);
	}
}
