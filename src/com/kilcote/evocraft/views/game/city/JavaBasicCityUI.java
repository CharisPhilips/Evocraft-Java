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
	
	@Override
	public void InvalidateDraw() {
		if (shape == null) {
			shape = new JavaBasicCityPane();
			//Elipse
			label = new Label();
			label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(label.getHeight() / 2), Insets.EMPTY)));
			label.setTextFill(Color.web("#ffffff"));
			switch (StandaloneSettings.style_Num) {
			case 0:
				cityModel = new Circle(this.shape.getWidth() / 2 , this.shape.getHeight() / 2, 40);
				SetUiColor((Shape)cityModel, this.getModel().playerId);
				break;
			case 1:
				cityModel = ResourceUtils.getResourceImageView("cities/city_p0_s4_l5.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true);
				SetCityImgProperties();
				break;
			}
			ivSelection = ResourceUtils.getResourceImageView("war/our_selector.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true);
			shape.getChildren().add(cityModel);
			shape.getChildren().add(label);
			shape.getChildren().add(ivSelection);
			
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
		}
		label.setText(String.valueOf((int)Math.ceil(getModel().currWarriors)) + '/' + String.valueOf(getModel().maxWarriors));
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
	
	public void SetCityImgProperties() {
		ImageView image = (ImageView)cityModel;
		if (shape.getWidth() == 0 || shape.getHeight()==0) {
			image.setVisible(false);
		} else {
			image.setVisible(true);
			if (getModel().playerId == 1) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p1_s4_l5.png", shape.getWidth(), shape.getHeight(), true, true));
			} else if (getModel().playerId == 2) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p2_s4_l5.png", shape.getWidth(), shape.getHeight(), true, true));
			} else if (getModel().playerId == 3) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p3_s4_l5.png", shape.getWidth(), shape.getHeight(), true, true));
			} else if (getModel().playerId == 4) {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p4_s4_l5.png", shape.getWidth(), shape.getHeight(), true, true));
			} else {
				image.setImage(ResourceUtils.getResourceImage("cities/city_p0_s4_l5.png", shape.getWidth(), shape.getHeight(), true, true));
			}
			image.setLayoutX(this.shape.getWidth() / 2 - (image.getImage().getWidth() / 2));
			image.setLayoutY(this.shape.getHeight() / 2 - (image.getImage().getHeight() / 2));
		}
	}
	
	public void SetSelectionImgProperties() {
		if (shape.getWidth() == 0 || shape.getHeight()==0) {
			ivSelection.setVisible(false);
		} else {
			ivSelection.setVisible(true);
			if (this.getModel().playerId == 1) {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/our_selector.png", shape.getWidth(), shape.getHeight(), true, true));
				ivSelection.setLayoutX(this.shape.getWidth() / 2 - (ivSelection.getImage().getWidth() / 2));
				ivSelection.setLayoutY(this.shape.getHeight() / 2 - (ivSelection.getImage().getHeight() / 2));				
			} else {
				ivSelection.setImage(ResourceUtils.getResourceImage("war/enemy_selector.png", CITY_IMAGE_WIDTH, CITY_IMAGE_WIDTH, true, true));
				ivSelection.setLayoutX(this.shape.getWidth() / 2 - (ivSelection.getImage().getWidth()));
				ivSelection.setLayoutY(this.shape.getHeight() / 2 - (ivSelection.getImage().getHeight()));
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
