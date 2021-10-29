package com.kilcote.evocraft.views.components;

import com.kilcote.evocraft.utils.ResourceUtils;

import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Kil Cote
 */
public class IconMenuItem extends MenuItem {
    private String _text           = null;
    private String _icon           = null;
    private String _keyCombination = null;

    public IconMenuItem(String text, String icon) {
        this(text, icon, null);
    }

    public IconMenuItem(String text, String icon, String keyCombination) {
        this._initializeData(text, icon, keyCombination);
        this._initializeGUI();
        this._initializeEvents();
        this._initializeFinally();
    }

    private void _initializeData(String text, String icon, String keyCombination) {
        this._text           = text;
        this._icon           = icon;
        this._keyCombination = keyCombination;
    }

    private void _initializeGUI() {
        if (this._icon != null) {
            ImageView iconImage = ResourceUtils.getResourceImageView("menu/" + this._icon);
            this.setGraphic(iconImage);
        }
        
        this.setText(this._text);
        
        if (this._keyCombination != null) {
            this.setAccelerator(KeyCombination.keyCombination(this._keyCombination));
        }
    }

    private void _initializeEvents() {
        
    }

    private void _initializeFinally() {
        
    }
}
