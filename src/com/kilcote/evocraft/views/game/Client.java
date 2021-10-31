package com.kilcote.evocraft.views.game;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine.city.BasicCityModel;

public class Client {
	public static List<BasicCityModel> g_selectedCities = new ArrayList<BasicCityModel>();
	
	public static void clearSelectCities() {
		for (int nIndex = g_selectedCities.size() - 1; nIndex >= 0; nIndex--) {
			BasicCityModel city = g_selectedCities.get(nIndex);
			deselectCity(city);
		}
	}

	public static void selectCity(BasicCityModel city) {
		if (g_selectedCities.indexOf(city) == -1) {
			g_selectedCities.add(city);	
			city.getUI().setSelection(true);
		}
	}

	public static void deselectCity(BasicCityModel city) {
		if (g_selectedCities.indexOf(city) != -1) {
			city.getUI().setSelection(false);
			g_selectedCities.remove(city);	
		}
	}
}
