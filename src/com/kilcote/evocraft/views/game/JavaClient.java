package com.kilcote.evocraft.views.game;

import java.util.ArrayList;
import java.util.List;

import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;

public class JavaClient {
	public static List<BasicCityModel> g_selectedCities = new ArrayList<BasicCityModel>();
	
	public static void clearSelectCities() {
		for (int nIndex = g_selectedCities.size() - 1; nIndex >= 0; nIndex--) {
			JavaBasicCityModel city = (JavaBasicCityModel) g_selectedCities.get(nIndex);
			deselectCity(city);
		}
	}

	public static void selectCity(JavaBasicCityModel city) {
		if (g_selectedCities.indexOf(city) == -1) {
			g_selectedCities.add(city);	
			city.getUI().setSelection(true);
		}
	}

	public static void deselectCity(JavaBasicCityModel city) {
		if (g_selectedCities.indexOf(city) != -1) {
			city.getUI().setSelection(false);
			g_selectedCities.remove(city);	
		}
	}
}
