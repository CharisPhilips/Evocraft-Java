package com.kilcote.evocraft.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;

public class Settings {
	public static final String  VERSION                   = "0.1";
	public static final String  WINDOW_TITLE              = "Evocraft-Java";
	public static final String  APP_ICON                  = "app.png";
	public static final double  WINDOW_WIDTH              = 1300;
	public static final double  WINDOW_HEIGHT             = 700;
	public static final double  MIN_WINDOW_WIDTH          = 1200;
	public static final double  MIN_WINDOW_HEIGHT         = 400;

	/* Default Settings */
	public static MyRandom rnd = new MyRandom();

	public static final boolean IS_PAGE_PORTRAIT          = true;

	public static final byte locateMemory_SizeForTowns = 12;
	public static final byte locateMemory_SizeForUnits = 12;

	//view
	public static int style_Num = 1;//1
	public static boolean view_grid_show = false;

	public static long seed = System.currentTimeMillis();

	public static int fieldSizeX = 10;
	public static int fieldSizeY = 10;

	public static int milisecondsPerTick = 1;

	public static boolean gameplay_SaveWarriorsOverCap = true;
	public static boolean gameplay_EqualsMeansCapture = true;
	public static boolean gameplay_RemoveOvercapedUnits = true;

	public static int generator_TunenelMapGenerator_SkipChance = 10;
	public static int generator_TunenelMapGenerator_IgnoreSkipChanceForFirstNTitles = 10;
	public static boolean generator_TunenelMapGenerator_CrossOnStart = true;

	public static boolean generator_SityPlacer14_QuadIsRoad = false;
	public static int generator_SityPlacer14_Quad_Size = 25;
	public static int generator_SityPlacer14_Quad_MinimimCnt = 2;
	public static int generator_SityPlacer14_Quad_Cities_Min = 2;
	public static int generator_SityPlacer14_Quad_Cities_Max = 4;
	public static boolean generator_SityPlacer14_FillAllWith1Road = true;
	public static int generator_SityPlacer14_Chance_PosWith1Road = 100;
	public static int generator_SityPlacer14_Chance_PosWith2Road = 10;
	public static int generator_SityPlacer14_Chance_PosWith3Road = 25;
	public static int generator_SityPlacer14_Chance_PosWith4Road = 100;
	public static int generator_SityPlacer14_Code_MaxSityPlaceRepeats = 3;

	public static byte generator_CityId_Bots = 3;
	public static byte generator_CityId_TownsPerPlayer = 1;
	public static byte generator_CityId_TownsPerBot = 1;

	public static int basicUnit_ticks_MoveWarrior = 10;
	public static int bot_rushBot_Tick_IgnoreFirstN = 200;
	public static int bot_rushBot_Tick_React = (int)(basicUnit_ticks_MoveWarrior / 2);
	public static boolean bot_rushBot_IsProtectCities = true;
	public static int bot_rushBot_Protect_MinimumUnitsLeft = 2;
	public static boolean bot_rushBot_IsDropOvercapacityUnits = true;
	public static int bot_rushBot_Overcapacity_NearValue = 1;
	public static boolean bot_rushBot_IsMoveUnitsToWeakCities = true;
	public static int bot_rushBot_Rush_Cnt = 4;
	public static byte bot_rushBot_Rush_MinimumMore = 1;

	public static List<Pair<Integer, Integer>> bot_rushBot_RushWaves_Chance = new ArrayList<Pair<Integer, Integer>>(Arrays.asList(
			new Pair<Integer, Integer>(1, 25),
			new Pair<Integer, Integer>(2, 25),
			new Pair<Integer, Integer>(3, 25),
			new Pair<Integer, Integer>(4, 25)
			));
	//size
	static public double oneCellSizeX = 1;
	static public double oneCellSizeY = 1;

	public static double sitySizeMult = 0.7;
	public static double unitSizeMult = 0.40;

	public static double roadWidth = 0.2;
	public static double roadHeight = 0.3;

	public static double cityPassiveStrokeThickness = 1;

	//color
	public static Color roadBackground = Color.valueOf("#ffffff33");
	public static Paint roadStroke = Paint.valueOf(Color.BLACK.toString());
	public static double roadStrokeThickness = 0.3;

	public static Color neutralTownFill = Color.WHEAT;
	public static Paint neutralTownStroke = Paint.valueOf(Color.WHITE.toString());

	public static Color playerTownFill = Color.RED;
	public static Paint playerTownStroke = Paint.valueOf(Color.BLACK.toString());

	public static Paint citySelectedStroke = Paint.valueOf(Color.YELLOW.toString());
	public static double citySelectedStrokeThickness = 2;

	public static List<Color> TownFills= new ArrayList<Color>(
			Arrays.asList(
					Color.BLUE,
					Color.GREEN,
					Color.YELLOW,
					Color.ORANGE,
					Color.PINK,
					Color.PURPLE,
					Color.GRAY,
					Color.TEAL,
					Color.LIGHTBLUE,
					Color.DARKGREEN,
					Color.BROWN,
					Color.MAROON,
					Color.NAVY,
					Color.TURQUOISE,
					Color.VIOLET,
					Color.WHEAT,
					Color.PEACHPUFF,
					Color.MINTCREAM,
					Color.LAVENDER,
					Color.DARKGRAY,
					Color.SNOW,
					Color.DARKGREEN,
					Color.PERU
					)
			);

	public static List<Color> TownStrokes= new ArrayList<Color>(
			Arrays.asList(
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK,
					Color.BLACK
					)
			);
}
