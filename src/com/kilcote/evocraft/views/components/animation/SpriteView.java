package com.kilcote.evocraft.views.components.animation;

import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.utils.ResourceUtils;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteView extends ImageView {
    private final Rectangle2D[] cellClips;
    private int numCols;
    private int numRows;
    private int numFrames;

    public SpriteView(String filename, int numCols, int numRows, int numFrames, double shapeWidth, double shapeHeight) {
    	this.numCols = numCols;
    	this.numRows = numRows;
        this.numFrames = numFrames;
		Image image = ResourceUtils.getResourceImage(filename, shapeWidth * numCols * StandaloneSettings.roadWidth, shapeHeight * numRows * StandaloneSettings.roadHeight, true, true);

        double cellWidth  = image.getWidth() / this.numCols;
        double cellHeight = image.getHeight() / this.numRows;

        cellClips = new Rectangle2D[numRows * numCols];
        for (int rows = 0; rows < this.numRows; rows++) {
        	for (int cols = 0; cols < this.numCols; cols++) {
        		cellClips[rows * this.numCols + cols] = new Rectangle2D(Math.round((cols * image.getWidth()) / this.numCols), Math.round((rows * image.getHeight()) / this.numRows), cellWidth, cellHeight);
        	}
        }
        setImage(image);
        
    }

    public void draw(int rowIndex, int colIndex) {
    	setViewport(cellClips[(rowIndex * numFrames) + (colIndex % numFrames)]);
    }

}

