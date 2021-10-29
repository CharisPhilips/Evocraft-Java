package com.kilcote.evocraft.views.components;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kil Cote
 */
public class ToolbarGroup extends VBox {
    public static final double  SPACING          = 5;
    public static final double  CORNER_RADIUS    = 18;

    private       final HBox    _nodePane        = new HBox();

    private             String  _title           = null;
    private             boolean _isRoundedCorner = false;

    public ToolbarGroup(String title) {
        this(title, false);
    }

    public ToolbarGroup(String title, boolean isRoundedCorner) {
        this._initializeData(title, isRoundedCorner);
        this._initializeGUI();
        this._initializeEvents();
        this._initializeFinally();
    }

    private void _initializeData(String title, boolean isRoundedCorner) {
        this._title           = title;
        this._isRoundedCorner = isRoundedCorner;
    }

    private void _initializeGUI() {
        this.setSpacing(SPACING);
        this.setAlignment(Pos.CENTER);
        
        Label label = new Label(this._title);
        
        this.getChildren().addAll(
                this._nodePane,
                label
        );
        
        this._nodePane.setAlignment(Pos.CENTER);
    }

    private void _initializeEvents() {
        this._nodePane.getChildren().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Object> c) {
                if ((_nodePane.getChildren().size() > 1) && (_isRoundedCorner)) {
                    for (int i = 0; i < _nodePane.getChildren().size(); i++) {
                        Control node        = (Control) _nodePane.getChildren().get(i);
                        double  topLeft     = 0;
                        double  topRight    = 0;
                        double  bottomRight = 0;
                        double  bottomLeft  = 0;

                        if (i == 0) {
                            topLeft    = CORNER_RADIUS;
                            bottomLeft = CORNER_RADIUS;
                        }
                        else if (i == _nodePane.getChildren().size() - 1) {
                            topRight    = CORNER_RADIUS;
                            bottomRight = CORNER_RADIUS;
                        }

                        node.setStyle("-fx-background-radius: " + topLeft + " " + topRight + " " + bottomRight + " " + bottomLeft + ";");
                    }
                }
            }
        });
    }

    private void _initializeFinally() {
    }

    public void AddAll(Node... nodes) {
        this._nodePane.getChildren().addAll(nodes);
    }

    public void Add(Node node) {
        this._nodePane.getChildren().add(node);
    }
}
