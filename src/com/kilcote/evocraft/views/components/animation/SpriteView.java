package com.kilcote.evocraft.views.components.animation;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteView extends ImageView {
    private final Rectangle2D[][] cellClips;
    private int numCols;
    private int numRows;

    public SpriteView(Image image, int numRows, int numCols) {
    	this.numRows = numRows;
        this.numCols = numCols;

        double cellWidth  = image.getWidth() / this.numCols;
        double cellHeight = image.getHeight() / this.numRows;

        cellClips = new Rectangle2D[numRows][numCols];
        for (int i = 0; i < this.numRows; i++) {
        	cellClips[i] = new Rectangle2D[this.numCols];
        	for (int j = 0; j < this.numCols; j++) {
        		cellClips[i][j] = new Rectangle2D(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
        	}
        }
        setImage(image);
        
    }

    public void draw(int rowIndex, int colIndex) {
    	setViewport(cellClips[rowIndex][colIndex % this.numCols]);
    }

}

