package com.kilcote.evocraft.views.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MultilineTooltip extends Tooltip {
	public  static final double   DEFAULT_PADDING      = 2.5;
    public  static final int      DEFAULT_FONT_SIZE    = 14;

    public  static final String   PRIMARY_TEXT_COLOR   = "#ecf0f1";
    public  static final String   SECONDARY_TEXT_COLOR = "#bdc3c7";

    private              String[] _texts               = null;

    private        final VBox     _layout              = new VBox(DEFAULT_PADDING);

    public MultilineTooltip(String[] texts) {
        this._initializeData(texts);
        this._initializeGUI();
        this._initializeEvents();
        this._initializeFinally();
    }

    private void _initializeData(String[] texts) {
        this._texts = texts;
    }

    private void _initializeGUI() {
//        this.setShowDelay(Duration.millis(250));
        this._layout.setAlignment(Pos.CENTER);
        this.setGraphic(this._layout);
        
        if (this._texts != null) {
            for (int i = 0; i < this._texts.length; i++) {
                String text  = this._texts[i];
                Label  label = new Label(text);
                
                if (i == 0) {
                    label.setTextFill(Color.web(PRIMARY_TEXT_COLOR));
                    label.setFont(Font.font("System", FontWeight.NORMAL, DEFAULT_FONT_SIZE));
                }
                else {
                    label.setTextFill(Color.web(SECONDARY_TEXT_COLOR));
                    label.setFont(Font.font("System", FontWeight.BOLD, DEFAULT_FONT_SIZE - 2));
                }
                
                this._layout.getChildren().add(
                        label
                );
            }
        }
    }

    private void _initializeEvents() {
        
    }

    private void _initializeFinally() {
        
    }
}
