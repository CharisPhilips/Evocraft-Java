package com.kilcote.evocraft.engine.unit;

import java.util.List;

import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.city.BasicCity;
import com.kilcote.evocraft.engine.interfaces.Settingable;
import com.kilcote.evocraft.engine.interfaces.Tickable;
import com.kilcote.evocraft.engine.map.GameMap;
import com.kilcote.evocraft.engine.setting._base.SettinsSetter;
import com.kilcote.evocraft.views.game._base.GameCellUIObj;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class BasicUnit extends GameCellUIObj implements Tickable, Settingable { 
	//---------------------------------------------- Fields ----------------------------------------------
	protected List<Pair<Integer, Integer>> path;
	protected int currPathIndex;
	protected int currTickOnCell;

	public int warriorsCnt;
	public int tickPerTurn;
	public BasicCity destination;

	//---------------------------------------------- Properties ----------------------------------------------
	public int playerId;
	public GameMap gameMap;
	//---------------------------------------------- Ctor ----------------------------------------------
	public BasicUnit(int warriorsCnt, int PlayerId, List<Pair<Integer, Integer>> Path, BasicCity destination, GameMap gameMap) {
		this.warriorsCnt = warriorsCnt;
		this.playerId = PlayerId;
		this.path = Path;
		this.destination = destination;

		currTickOnCell = 1;
		currPathIndex = 0;
		this.gameMap = gameMap;
		this.gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.add(this);
	}

	//---------------------------------------------- Methods ----------------------------------------------
	public boolean TickReact() {
		currTickOnCell++;
		if(currTickOnCell >= tickPerTurn) {
			currTickOnCell = 1;
			gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.remove(this);
			currPathIndex++;
			gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.add(this);

			if( (gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city != null &&
					gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city.playerId != this.playerId) ||
					(currPathIndex == path.size() - 1)
					) {
				gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].units.remove(this);
				gameMap.map[path.get(currPathIndex).getValue()][path.get(currPathIndex).getKey()].city.GetUnits(this);
				canvas.getChildren().remove(shape);
				return true;
			}
		}
		return false;
	}

	public int TicksLeftToDestination() {
		return (int)((path.size() - 1 - currPathIndex) * tickPerTurn - currTickOnCell);
	}

	public void GetSettings(SettinsSetter settinsSetter) throws Exception {
		settinsSetter.SetSettings(this);
	}

	///////////////////////////view///////////////////////////////

	protected Label text;
	protected Shape rectangle;
	Pane canvas;
	double pixelPerTurnX, pixelPerTurnY;
	double shiftX, shiftY;
	
	protected Label selection;
	
	public void SetCanvas(Pane c) {
		canvas = c;
	}
	
	@Override
	public void InitializeDraw() {
		shape = new Pane();
		FillShape();
		canvas.getChildren().add(shape);
	}

	@Override
	public void InvalidateDraw() {
		text.setText(String.valueOf(this.warriorsCnt));

		pixelPerTurnX = this.shape.getWidth() / tickPerTurn;
		pixelPerTurnY = this.shape.getHeight() / tickPerTurn;
		
		SetShapeProperties();
		if(path.get(currPathIndex).getKey() > path.get(currPathIndex + 1).getKey()) {
			shape.setTranslateX(path.get(currPathIndex).getKey() * this.shape.getWidth() - currTickOnCell * pixelPerTurnX + shiftX);
		} else if (path.get(currPathIndex).getKey() < path.get(currPathIndex + 1).getKey()) {
			shape.setTranslateX(path.get(currPathIndex).getKey() * this.shape.getWidth() + currTickOnCell * pixelPerTurnX + shiftX);
		} else {
			shape.setTranslateX(path.get(currPathIndex).getKey() * this.shape.getWidth() + shiftX);
		}

		if (path.get(currPathIndex).getValue() > path.get(currPathIndex + 1).getValue()) {
			shape.setTranslateY(path.get(currPathIndex).getValue() * this.shape.getHeight() - currTickOnCell * pixelPerTurnY + shiftY);
		} else if (path.get(currPathIndex).getValue() < path.get(currPathIndex + 1).getValue()) {
			shape.setTranslateY(path.get(currPathIndex).getValue() * this.shape.getHeight() + currTickOnCell * pixelPerTurnY + shiftY);
		} else {
			shape.setTranslateY(path.get(currPathIndex).getValue() * this.shape.getHeight() + shiftY);
		}
	}
	
	protected void FillShape() {
		RecalcGeometrySize();
		this.rectangle = new Rectangle(20, 20);
		
		this.rectangle.setFill(Settings.TownFills.get(playerId - 1));
		this.rectangle.setStroke(Settings.neutralTownStroke);
		
		SetElipseColor(rectangle, this.playerId);
		shape.getChildren().add(rectangle);

		text = new Label();
		shape.getChildren().add(text);
		InvalidateDraw();
		
		shape.setTranslateX(path.get(currPathIndex).getKey() * this.shape.getWidth());
		shape.setTranslateY(path.get(currPathIndex).getValue() * this.shape.getHeight());
	}

	public void SetShapeProperties() {
		if (this.shape.getWidth() == 0 && this.shape.getHeight() == 0) {
			rectangle.setVisible(false);
			text.setVisible(false);
		} else {
			rectangle.setVisible(true);
			text.setVisible(true);
			rectangle.setLayoutX(this.shape.getWidth() / 2 - (rectangle.getBoundsInLocal().getWidth() / 2));
			rectangle.setLayoutY(this.shape.getHeight() / 2 - (rectangle.getBoundsInLocal().getHeight() / 2));
			text.setLayoutX(this.shape.getWidth() / 2 - (text.getWidth() / 2));
			text.setLayoutY(this.shape.getHeight() / 2 - (text.getHeight() / 2));
		}
	}
	
	protected void RecalcGeometrySize() {
		pixelPerTurnX = Settings.oneCellSizeX / tickPerTurn;
		pixelPerTurnY = Settings.oneCellSizeY / tickPerTurn;
		shiftX = 0;
		shiftY = 0;
	}
}
