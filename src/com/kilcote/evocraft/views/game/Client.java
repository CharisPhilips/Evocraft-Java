package com.kilcote.evocraft.views.game;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine.city.BasicCity;

public class Client {
	public static List<BasicCity> g_selectedCities = new ArrayList<BasicCity>();
	
	public static void clearSelectCities() {
		for (int nIndex = g_selectedCities.size() - 1; nIndex >= 0; nIndex--) {
			BasicCity city = g_selectedCities.get(nIndex);
			deselectCity(city);
		}
	}

	public static void selectCity(BasicCity city) {
		if (g_selectedCities.indexOf(city) == -1) {
			g_selectedCities.add(city);	
			city.setSelection(true);
		}
	}

	public static void deselectCity(BasicCity city) {
		if (g_selectedCities.indexOf(city) != -1) {
			city.setSelection(false);
			g_selectedCities.remove(city);	
		}
	}
}
