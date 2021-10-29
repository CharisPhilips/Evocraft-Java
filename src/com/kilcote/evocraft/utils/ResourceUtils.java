package com.kilcote.evocraft.utils;

import java.io.InputStream;

import com.kilcote.evocraft.EvoApp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceUtils {

	//get Image 
	public static Image getResourceImage(String strPath) {
		return new Image(EvoApp.class.getResourceAsStream("Resource/image/" + strPath));
	}
	
	public static Image getResourceImage(String strPath, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
		return new Image(EvoApp.class.getResourceAsStream("Resource/image/" + strPath), requestedWidth, requestedHeight, preserveRatio, smooth);
	}
	
	public static Image getFileImage(String strPath) {
		return new Image("file:" + strPath);
	}
	
	public static Image getFileImage(String strPath, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
		return new Image("file:" + strPath, requestedWidth, requestedHeight, preserveRatio, smooth);
	}
	//get ImageView
	public static ImageView getResourceImageView(String strPath) {
		return new ImageView(getResourceImage(strPath));
	}
	
	public static ImageView getResourceImageView(String strPath, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
		return new ImageView(getResourceImage(strPath, requestedWidth, requestedHeight, preserveRatio, smooth));
	}
	
	public static ImageView getFileImageView(String strPath) {
		return new ImageView(new Image("file:" + strPath));
	}
	
	public static ImageView getFileImageView(String strPath, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
		return new ImageView(new Image("file:" + strPath, requestedWidth, requestedHeight, preserveRatio, smooth));
	}
	
	public static ImageView getStreamImageView(InputStream is) {
		return new ImageView(new Image(is));
	}
	
	public static ImageView getStreamImageView(InputStream is, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
		return new ImageView(new Image(is, requestedWidth, requestedHeight, preserveRatio, smooth));
	}
}
