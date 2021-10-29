package com.kilcote.evocraft.views.components.button;

import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.components.MultilineTooltip;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconToggleButton extends ToggleButton {
	public static final double DEFAULT_PADDING = 5;

	private             String   _text         = null;
	protected           String   _icon         = null;
	protected 			ImageView _iconImage1  = null;


	private             String[] _tooltipTexts = null;

	public IconToggleButton(String text, String icon, String[] tooltipTexts) {
		this._initializeData(text, icon, tooltipTexts);
		this._initializeGUI();
		this._initializeEvents();
		this._initializeFinally();
	}

	public IconToggleButton(String text, String icon) {
		this(text, icon, null);
	}

	private void _initializeData(String text, String icon, String[] tooltipTexts) {
		this._text         = text;
		this._icon         = icon;
		this._iconImage1   = ResourceUtils.getResourceImageView(this._icon);
		this._tooltipTexts = tooltipTexts;
	}

	private void _initializeGUI() {
		if (this._icon != null) {
			this.setGraphic(this._iconImage1);

			this.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING + 3, DEFAULT_PADDING, DEFAULT_PADDING + 3));
		}

		if (this._text != null) {
			this.setText(this._text);
		}

		if (this._tooltipTexts != null) {
			this.SetTooltip(this._tooltipTexts);
		}

		this.setCursor(Cursor.HAND);
	}

	private void _initializeEvents() {

	}

	private void _initializeFinally() {

	}

	public void SetTooltip(String[] texts) {
		MultilineTooltip tooltip = new MultilineTooltip(texts);
		this.setTooltip(tooltip);
	}

	public Image getIconImage() {
		return _iconImage1.getImage();
	}

}

