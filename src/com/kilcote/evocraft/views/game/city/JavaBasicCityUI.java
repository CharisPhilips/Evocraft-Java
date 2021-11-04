 package com.kilcote.evocraft.views.game.city;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.engine.cell.JavaGameCellModel;
import com.kilcote.evocraft.engine.city.BasicCityModel;
import com.kilcote.evocraft.engine.city.JavaBasicCityModel;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.game.JavaClient;
import com.kilcote.evocraft.views.game._base.GameObjCellUI;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class JavaBasicCityUI extends GameObjCellUI<JavaBasicCityModel> {

	public JavaBasicCityUI(JavaBasicCityModel model) {
		setModel(model);
		model.setUI(this);
	}

	private Label label;
	private Label text;
	private Node cityModel;

	private ImageView ivSelection;
	private boolean mSelect = false;
	
	private final static String[] city_types = { "city", "castle", "forge", "stable" };
	
	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new JavaBasicCityPane();
			
			this.shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (getModel().gameMap.getGameEngine().isPlaying()) {
						
						if (event.getButton() == MouseButton.PRIMARY) {
							if (event.getClickCount() == 2) {
								if (JavaBasicCityUI.this.getModel().playerId == 1) {
									getModel().gameMap.SendWarriors(JavaClient.g_selectedCities, JavaBasicCityUI.this.getModel());
									for(BasicCityModel x : JavaClient.g_selectedCities) {
										switch (StandaloneSettings.style_Num) {
										case 0:
											SetUiColor((Shape)cityModel, JavaBasicCityUI.this.getModel().playerId);
											break;
										case 1:
											SetCityImgProperties();
											break;
										}
									}
									JavaClient.clearSelectCities();
								}
							} else {
								if (JavaBasicCityUI.this.getModel().playerId == 1) {
									if (!JavaClient.g_selectedCities.contains(this)) {
										JavaClient.selectCity(JavaBasicCityUI.this.getModel());
									}
								} else if (JavaBasicCityUI.this.getModel().playerId != 1) {
									getModel().gameMap.SendWarriors(JavaClient.g_selectedCities, JavaBasicCityUI.this.getModel());
									for(BasicCityModel x : JavaClient.g_selectedCities) {
										switch (StandaloneSettings.style_Num) {
										case 0:
											SetUiColor((Shape) JavaBasicCityUI.this.cityModel, JavaBasicCityUI.this.getModel().playerId);
											break;
										case 1:
											SetCityImgProperties();
											break;
										}
									}
									JavaClient.clearSelectCities();
								}
							}
						} else {
							JavaClient.deselectCity(JavaBasicCityUI.this.getModel());
						}
					}
				}
			});
		} else if (shape.getWidth() != 0 || shape.getHeight() != 0) {
			//Label
			SetLabelProperties();
			
			//Shape
			switch (StandaloneSettings.style_Num) {
			case 0:
				if (cityModel == null) {
					cityModel = new Circle(this.shape.getWidth() / 2 , this.shape.getHeight() / 2, 40);
					SetUiColor((Shape)cityModel, this.getModel().playerId);
					((Circle)this.cityModel).setCenterX(this.shape.getWidth() / 2);
					((Circle)this.cityModel).setCenterY(this.shape.getHeight() / 2);
					((Circle)this.cityModel).setRadius(this.shape.getHeight() / 2);
					shape.getChildren().add(cityModel);
				}
				break;
			case 1:
				SetCityImgProperties();
				break;
			}
			//Selection
			SetSelectionImgProperties();
		
			if (this.cityModel != null && cityModel instanceof ImageView) {
				SetCityImgProperties();
			}
		}
	}
	
	public void SetLabelProperties() {
		if (label == null) {
			label = new Label();
			label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(label.getHeight() / 2), Insets.EMPTY)));
			label.setTextFill(Color.web("#ffffff"));
			shape.getChildren().add(label);
			label.relocate(this.shape.getWidth() * 1 / 8, this.shape.getHeight() * 1 / 8);
		}
		label.setText(String.valueOf((int)Math.ceil(getModel().currWarriors)) + '/' + String.valueOf(getModel().getMaxWarriors()));
	}

	public void SetCityImgProperties() {
		switch (getModel().playerId) {
		case 1: case 2: case 3: case 4:
			if (cityModel == null) {
				cityModel = ResourceUtils.getResourceImageView(String.format("cities/%s_p%d_s4_l%d.png", city_types[getModel().type], getModel().playerId, getModel().level), shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true);
				shape.getChildren().add(cityModel);
			} else {
				((ImageView)cityModel).setImage(ResourceUtils.getResourceImage(String.format("cities/%s_p%d_s4_l%d.png", city_types[getModel().type], getModel().playerId, getModel().level), shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true));
			}
			break;
		default:
			if (cityModel == null) {
				cityModel = ResourceUtils.getResourceImageView(String.format("cities/%s_p0_s4_l%d.png", city_types[getModel().type], getModel().level), shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true);
				shape.getChildren().add(cityModel);
			} else {
				((ImageView)cityModel).setImage(ResourceUtils.getResourceImage(String.format("cities/%s_p0_s4_l%d.png", city_types[getModel().type], getModel().level), shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true));
			}
		}
		((ImageView)cityModel).setLayoutX(this.shape.getWidth() / 2 - (((ImageView)cityModel).getImage().getWidth() / 2));
		((ImageView)cityModel).setLayoutY(this.shape.getHeight() / 2 - (((ImageView)cityModel).getImage().getHeight() / 2));
	}
	
	public void SetSelectionImgProperties() {
		if (this.getModel().playerId == 1) {
			if (ivSelection == null) {
				ivSelection = ResourceUtils.getResourceImageView("war/our_selector.png", shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true);
				shape.getChildren().add(ivSelection);
			} else {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/our_selector.png", shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true));
			}
		} else {
			if (ivSelection == null) {
				ivSelection = ResourceUtils.getResourceImageView("war/enemy_selector.png", shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true);
				shape.getChildren().add(ivSelection);
			} else {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/enemy_selector.png", shape.getWidth() * StandaloneSettings.cityRatio, shape.getHeight() * StandaloneSettings.cityRatio, true, true));
			}
		}
		this.ivSelection.setLayoutX(this.shape.getWidth() / 2 - ((ImageView)ivSelection).getImage().getWidth() / 2);
		this.ivSelection.setLayoutY(this.shape.getHeight() / 2 - ((ImageView)ivSelection).getImage().getHeight() / 2);
		if (mSelect) {
			ivSelection.setOpacity(1);
		} else {
			ivSelection.setOpacity(0);
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

		public PathFinderCell(JavaGameCellModel cell) {
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
