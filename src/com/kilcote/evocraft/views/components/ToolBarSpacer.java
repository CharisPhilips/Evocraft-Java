package com.kilcote.evocraft.views.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ToolBarSpacer extends HBox {
    public ToolBarSpacer() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
