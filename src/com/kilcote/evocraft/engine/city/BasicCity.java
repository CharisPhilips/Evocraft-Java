package com.kilcote.evocraft.engine.city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.Game;
import com.kilcote.evocraft.engine.cell.GameCell;
import com.kilcote.evocraft.engine.interfaces.Settingable;
import com.kilcote.evocraft.engine.interfaces.Tickable;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.setting.BasicUnitSettings;
import com.kilcote.evocraft.engine.setting._base.SettinsSetter;
import com.kilcote.evocraft.engine.unit.BasicUnit;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.game.Client;
import com.kilcote.evocraft.views.game._base.GameCellUIObj;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class BasicCity extends GameCellUIObj implements Tickable, Settingable {

	public int playerId;
	public int ticksPerIncome;
	public int currWarriors, maxWarriors;
	public double sendPersent, atkPersent, defPersent;
	protected Map<BasicCity, List<Pair<Integer, Integer>>> pathToCities;
	public GameMap gameMap;
	
	public BasicCity(GameMap gameMap) {
		pathToCities = new HashMap<BasicCity, List<Pair<Integer, Integer>>>();
		this.gameMap = gameMap;
	}

	//---------------------------------------------- Methods ----------------------------------------------
	public boolean TickReact() {
		if (playerId != 0 && maxWarriors > currWarriors && Game.tick % ticksPerIncome == 0) {
			currWarriors++;
			return true;
		} else if (Settings.gameplay_RemoveOvercapedUnits && maxWarriors < currWarriors && Game.tick % ticksPerIncome == 0) {
			currWarriors--;
			return true;
		}
		return false;
	}

	public int GetAtkWarriors() {
		return (int)Math.round(currWarriors * sendPersent * atkPersent);
	}

	public int GetDefWarriors() {
		return (int)Math.round(currWarriors * defPersent);
	}

	public BasicUnit SendUnit(BasicCity to) {
		try {
			int sendWarriors = GetAtkWarriors();
			if(sendWarriors == 0) {
				return null;
			}

			currWarriors -= sendWarriors;
			if (currWarriors < 0) {
				currWarriors = 0;
			}

			GetShortestPath(to);
			BasicUnit unit = CreateLinkedUnit(sendWarriors, to);
			unit.GetSettings(new BasicUnitSettings());
			return unit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void GetUnits(BasicUnit unit) {
		if (playerId == unit.playerId) {
			this.currWarriors += unit.warriorsCnt;
			if (!Settings.gameplay_SaveWarriorsOverCap && currWarriors > maxWarriors)
				currWarriors = maxWarriors;
		}
		else {
			int defWarriors = currWarriors;
			unit.warriorsCnt = (int) Math.round(unit.warriorsCnt / this.defPersent);

			if (defWarriors > unit.warriorsCnt) {
				defWarriors -= (int)(unit.warriorsCnt);
				if (currWarriors > defWarriors) {
					currWarriors = defWarriors;
				}
			} else if (!Settings.gameplay_EqualsMeansCapture && defWarriors == unit.warriorsCnt) {
				currWarriors = 0;
			} else {
				currWarriors = (int)(unit.warriorsCnt - defWarriors);
				playerId = unit.playerId;
				this.shape.getChildren().clear();
				this.FillShape();
			}
		}

		gameMap.units.remove(unit);
	}

	public void GetSettings(SettinsSetter settinsSetter) throws Exception {
		settinsSetter.SetSettings(this);
	}

	public int GetShortestPath(BasicCity to) {
		pathToCities.remove(to);
		BuildPathWithoutEnemyCitiesPath(to);
		return pathToCities.get(to).size() - 1;
	}

	public boolean isShortestPath(BasicCity to) {
		pathToCities.remove(to);
		return BuildPathWithoutEnemyCitiesPath(to);
	}

	protected BasicUnit CreateLinkedUnit(int sendWarriors, BasicCity to){
		return new BasicUnit(sendWarriors, this.playerId, pathToCities.get(to), to, gameMap);
	}

	private boolean BuildPathWithoutEnemyCitiesPath(BasicCity to) {
		boolean rez;
		PathFinderCell[][] finder = new PathFinderCell[gameMap.map.length][];

		int fromX = 0, fromY = 0, toX = 0, toY = 0;

		for (int i = 0; i < finder.length; ++i) {
			finder[i] = new PathFinderCell[gameMap.map[0].length];
			for (int j = 0; j < finder[i].length; j++) {
				finder[i][j] = new PathFinderCell(gameMap.map[i][j]);
				if (gameMap.map[i][j].city == this) {
					fromX = j; fromY = i;
				} else if (gameMap.map[i][j].city == to) {
					toX = j; toY = i;
				}
			}
		}

		List<RecInfo> recList = new ArrayList<RecInfo>();
		RecInfo rec = new RecInfo();
		rec.x = fromX;
		rec.y = fromY;
		rec.value = 0; 
		recList.add(rec);
		while (recList.size() != 0) {
			Rec(recList, finder, recList.get(0), true);
			recList.remove(0);
		}

		List<Pair<Integer, Integer>> reversedPath = new ArrayList<Pair<Integer, Integer>>();

		UnRec(reversedPath, finder, toX, toY, finder[toY][toX].num);
		Collections.reverse(reversedPath);

		if (reversedPath.size() != 0) {
			pathToCities.put(to, reversedPath);
			rez = true;
		} else {
			BuildPath(to);
			rez = false;
		}

		return rez;
	}

	private void BuildPath(BasicCity to) {
		PathFinderCell[][] finder = new PathFinderCell[gameMap.map.length][];
		int fromX = 0, fromY = 0, toX = 0, toY = 0;

		for (int i = 0; i < finder.length; ++i) {
			finder[i] = new PathFinderCell[gameMap.map[0].length];
			for (int j = 0; j < finder[i].length; j++) {
				finder[i][j] = new PathFinderCell(gameMap.map[i][j]);
				if (gameMap.map[i][j].city == this) {
					fromX = j; fromY = i;
				} else if (gameMap.map[i][j].city == to) {
					toX = j; toY = i;
				}
			}
		}

		List<RecInfo> recList = new ArrayList<RecInfo>();
		RecInfo rec = new RecInfo();
		rec.x = fromX;
		rec.y = fromY;
		rec.value = 0; 
		recList.add(rec);

		while (recList.size() != 0) {
			Rec(recList, finder, recList.get(0), false);
			recList.remove(0);
		}

		List<Pair<Integer, Integer>> reversedPath = new ArrayList<Pair<Integer, Integer>>();

		UnRec(reversedPath, finder, toX, toY, finder[toY][toX].num);
		Collections.reverse(reversedPath);
		pathToCities.put(to, reversedPath);
	}

	public void Rec(List<RecInfo> recList, PathFinderCell[][] finder, RecInfo info, boolean isComparePlayerId) {

		int x = info.x;
		int y = info.y;
		if (finder[y][x].num != -1 && finder[y][x].num < info.value)
			return;

		if (isComparePlayerId) {
			if (gameMap.map[y][x].city != null && gameMap.map[y][x].city.playerId != this.playerId) {
				return;
			}
		}

		finder[y][x].num = info.value++;

		if (finder[y][x].isOpenBottom) {
			RecInfo rec = new RecInfo();
			rec.x = x;
			rec.y = y + 1;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenRight) {
			RecInfo rec = new RecInfo();
			rec.x = x + 1;
			rec.y = y;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenTop) {
			RecInfo rec = new RecInfo();
			rec.x = x;
			rec.y = y - 1;
			rec.value = info.value;
			recList.add(rec);
		}
		if (finder[y][x].isOpenLeft) {
			RecInfo rec = new RecInfo();
			rec.x = x - 1;
			rec.y = y;
			rec.value = info.value;
			recList.add(rec);
		}
	}

	boolean UnRec(List<Pair<Integer, Integer>> reversedPath, PathFinderCell[][] finder, int x, int y, int prevValue) {
		if (prevValue == finder[y][x].num && finder[y][x].num != -1) {
			boolean prev = false;
			reversedPath.add(new Pair<Integer, Integer>(x, y));
			if (finder[y][x].isOpenBottom) {
				prev = UnRec(reversedPath, finder, x, y + 1, prevValue - 1);
			}
			if (finder[y][x].isOpenTop && !prev) {
				prev = UnRec(reversedPath, finder, x, y - 1, prevValue - 1);
			}
			if (finder[y][x].isOpenLeft && !prev) {
				prev = UnRec(reversedPath, finder, x - 1, y, prevValue - 1);
			}
			if (finder[y][x].isOpenRight && !prev) {
				prev = UnRec(reversedPath, finder, x + 1, y, prevValue - 1);
			}
			return true;
		}
		return false;
	}

	///////////////////////////view///////////////////////////////

	protected Label label;
	protected Label text;
	protected Node cityModel;

	protected ImageView ivSelection;
	private boolean mSelect = false;

	@Override
	public void InitializeDraw() {
		shape = new Pane();
		FillShape();

		this.shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("click");
				if (event.getButton() == MouseButton.PRIMARY) {
					if(event.getClickCount() == 2) {
						System.out.println("Double clicked");
						if (BasicCity.this.playerId == 1) {
							gameMap.SendWarriors(Client.g_selectedCities, BasicCity.this);
							for(BasicCity x : Client.g_selectedCities) {
								switch (Settings.style_Num) {
								case 0:
									SetUiColor((Shape)cityModel, BasicCity.this.playerId);
									break;
								case 1:
									SetCityImgProperties();
									break;
								}
							}
							Client.clearSelectCities();
						}
					} else {
						if (BasicCity.this.playerId == 1) {
							if (!Client.g_selectedCities.contains(this)) {
								Client.selectCity(BasicCity.this);
							}
						} else if (BasicCity.this.playerId != 1) {
							gameMap.SendWarriors(Client.g_selectedCities, BasicCity.this);
							for(BasicCity x : Client.g_selectedCities) {
								switch (Settings.style_Num) {
								case 0:
									SetUiColor((Shape) BasicCity.this.cityModel, BasicCity.this.playerId);
									break;
								case 1:
									SetCityImgProperties();
									break;
								}
							}
							Client.clearSelectCities();
						}
					}
				} else {
					Client.deselectCity(BasicCity.this);
				}
			}
		});
	}
	
	@Override
	public void InvalidateDraw() {
		label.setText(String.valueOf(this.currWarriors) + '/' + String.valueOf(maxWarriors));
		label.relocate(this.shape.getWidth() * 1 / 8, this.shape.getHeight() * 1 / 8);
		if (this.cityModel != null && cityModel instanceof Circle) {
			((Circle)this.cityModel).setCenterX(this.shape.getWidth() / 2);
			((Circle)this.cityModel).setCenterY(this.shape.getHeight() / 2);
			((Circle)this.cityModel).setRadius(this.shape.getHeight() / 2);
		}
		if (this.cityModel != null && cityModel instanceof ImageView) {
			SetCityImgProperties();
		}
		(this.ivSelection).setLayoutX(this.shape.getWidth() / 2);
		(this.ivSelection).setLayoutX(this.shape.getHeight() / 2);
		SetSelectionImgProperties();
	}

	public static double CITY_IMAGE_WIDTH = 60;
	
	protected void FillShape() {
		//Elipse
		label = new Label();
		label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(label.getHeight() / 2), Insets.EMPTY)));
		label.setTextFill(Color.web("#ffffff"));
		switch (Settings.style_Num) {
		case 0:
			cityModel = new Circle(this.shape.getWidth() / 2 , this.shape.getHeight() / 2, 40);
			SetUiColor((Shape)cityModel, this.playerId);
			break;
		case 1:
			cityModel = ResourceUtils.getResourceImageView("cities/city_p0_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true);
			SetCityImgProperties();
			break;
		}
		ivSelection = ResourceUtils.getResourceImageView("war/our_selector.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true);
		SetSelectionImgProperties();
		shape.getChildren().add(cityModel);
		shape.getChildren().add(label);
		shape.getChildren().add(ivSelection);
	}

	public void SetCityImgProperties() {
		ImageView image = (ImageView)cityModel;
		if (shape.getWidth() == 0 || shape.getHeight()==0) {
			image.setVisible(false);
		} else {
			image.setVisible(true);
			if (playerId == 1) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p1_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
				image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
			} else if (playerId == 2) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p2_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
				image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
			} else if (playerId == 4) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p3_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
				image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
			} else if (playerId == 3) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p4_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
				image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
			} else {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p0_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
				image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
			}
		}
	}
	
	public void SetSelectionImgProperties() {
		if (shape.getWidth() == 0 || shape.getHeight()==0) {
			ivSelection.setVisible(false);
		} else {
			ivSelection.setVisible(true);
			if (this.playerId == 1) {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/our_selector.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				ivSelection.setLayoutX(this.shape.getWidth() / 2 - (ivSelection.getImage().getWidth() / 2));
				ivSelection.setLayoutY(this.shape.getHeight() / 2 - (ivSelection.getImage().getHeight() / 2));				
			} else {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/enemy_selector.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				ivSelection.setLayoutX(this.shape.getWidth() / 2 - (ivSelection.getImage().getWidth() / 2));
				ivSelection.setLayoutY(this.shape.getHeight() / 2 - (ivSelection.getImage().getHeight() / 2));
			}
			if (mSelect) {
				ivSelection.setOpacity(1);
			} else {
				ivSelection.setOpacity(0);
			}
		}
	}

	public void setSelection(boolean selection) {
		mSelect = selection;
	}

	class PathFinderCell {
		public boolean isOpenBottom = false;
		public boolean isOpenLeft = false;
		public boolean isOpenRight = false; 
		public boolean isOpenTop = false;
		public int num = -1;

		public PathFinderCell(GameCell cell) {
			this.isOpenBottom = cell.isOpenBottom;
			this.isOpenLeft = cell.isOpenLeft;
			this.isOpenRight = cell.isOpenRight;
			this.isOpenTop = cell.isOpenTop;
		}
	}

	class RecInfo {
		public int x, y, value;
	}

}
