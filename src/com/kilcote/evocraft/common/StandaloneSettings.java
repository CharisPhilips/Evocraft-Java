package com.kilcote.evocraft.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class StandaloneSettings extends Settings {
	public static final String  VERSION                   = "0.1";
	public static final String  WINDOW_TITLE              = "Evocraft-Java";
	public static final String  APP_ICON                  = "app.png";
	public static final double  WINDOW_WIDTH              = 1300;
	public static final double  WINDOW_HEIGHT             = 700;
	public static final double  MIN_WINDOW_WIDTH          = 1200;
	public static final double  MIN_WINDOW_HEIGHT         = 400;

	
	public static int milisecondsPerTick = 100;
	
	public static int fieldSizeX = 15;
	public static int fieldSizeY = 8;
//	public static int fieldSizeX = 6;
//	public static int fieldSizeY = 3;
	
	static public double oneCellSizeX = 1;
	static public double oneCellSizeY = 1;
	
	public static double roadWidth = 0.2;
	public static double roadHeight = 0.2;
	
	public static double cityRatio = 0.7;
	
	//size
	public static double citySizeMult = 0.7;
	public static double unitSizeMult = 0.40;

	//view
	public static int style_Num = 1;//1
	public static boolean view_grid_show = true;
	
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
