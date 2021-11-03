package com.kilcote.evocraft.views.components.animation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SplitImagePane extends StackPane {
	
    public SplitImagePane(Image objectImage, Image explosionImage, int numCells, int numRows) {
        ImageView objectView = new ImageView(objectImage);
        SpriteView animationView = new SpriteView(explosionImage, numCells, numRows);
        setMinSize(
                Math.max(
                        objectImage.getWidth(),
                        animationView.getViewport().getWidth()
                ),
                Math.max(
                        objectImage.getHeight(),
                        animationView.getViewport().getHeight()
                )
        );

        objectView.setPickOnBounds(false);
//        objectView.setOnMouseClicked(event -> {
//            getChildren().setAll(animationView);
//            animationView.explode(complete -> getChildren().setAll(objectView));
//        });

//        DropShadow drop = new DropShadow(10, Color.GOLD);
//        drop.setInput(new Glow());
//        objectView.setOnMouseEntered(event -> objectView.setEffect(drop));
//        objectView.setOnMouseExited(event -> objectView.setEffect(null));

//        getChildren().setAll(objectView);
    }
}