package com.kilcote.evocraft.views;

import java.util.ArrayList;
import java.util.BitSet;

import com.kilcote.evocraft.common.Global;
import com.kilcote.evocraft.common.StandaloneSettings;
import com.kilcote.evocraft.views.components.IconMenuItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

public class WindowFrame extends BorderPane {

	public static final int          EXIT_SUCCESS                    = 0;

	public static final String       WINDOW_BORDER_COLOR             = "#B5B5B5";
	public static final String       WINDOW_FILL_COLOR               = "#F4F4F4";
	public static final double       WINDOW_OPACITY_ON_DRAGGED       = 0.8;
	
	public static final double       WINDOW_BORDER_WIDTH             = 1.5;
	public static final double       WINDOW_CORNER_LENGTH            = 5.0;

	public static       boolean      IS_MAXIMIZED                    = false;
    public static       double       WINDOW_X                        = 0;
    public static       double       WINDOW_Y                        = 0;
    public static       double       WINDOW_PREVIOUS_WIDTH           = 0;
    public static       double       WINDOW_PREVIOUS_HEIGHT          = 0;

    /* Top */
    public        final Line         TopLeftEdge                     = new Line(0, 0, WINDOW_CORNER_LENGTH, 0);
    public        final Line         TopEdge                         = new Line(0, 0, StandaloneSettings.WINDOW_WIDTH - (WINDOW_CORNER_LENGTH * 3), 0);
    public        final Line         TopRightEdge                    = new Line(0, 0, WINDOW_CORNER_LENGTH, 0);

    private             double       _topEdgePrevY                   = 0;

    /* Bottom */
    public        final Line         BottomLeftEdge                  = new Line(0, 0, WINDOW_CORNER_LENGTH, 0);
    public        final Line         BottomEdge                      = new Line(0, 0, StandaloneSettings.WINDOW_WIDTH - (WINDOW_CORNER_LENGTH * 3), 0);
    public        final Line         BottomRightEdge                 = new Line(0, 0, WINDOW_CORNER_LENGTH, 0);

    private             double       _bottomEdgePrevY                = 0;

    /* Rear Left */
    public        final Line         RearTopLeftEdge                 = new Line(0, 0, 0, WINDOW_CORNER_LENGTH);
    public        final Line         RearLeftEdge                    = new Line(0, WINDOW_CORNER_LENGTH, 0, StandaloneSettings.WINDOW_HEIGHT - (WINDOW_CORNER_LENGTH * 3));
    public        final Line         RearBottomLeftEdge              = new Line(0, 0, 0, WINDOW_CORNER_LENGTH);

    private             double       _rearLeftEdgePrevX              = 0;

    /* Rear Right */
    public        final Line         RearTopRightEdge                = new Line(0, 0, 0, WINDOW_CORNER_LENGTH);
    public        final Line         RearRightEdge                   = new Line(0, WINDOW_CORNER_LENGTH, 0, StandaloneSettings.WINDOW_HEIGHT - (WINDOW_CORNER_LENGTH * 3));
    public        final Line         RearBottomRightEdge             = new Line(0, 0, 0, WINDOW_CORNER_LENGTH);

    private             double       _rearRightEdgePrevX             = 0;

    public static final IconMenuItem MAXIMIZE_MENU_ITEM              = new IconMenuItem("Maximize/Restore", "maximize_2.png");
    public static final IconMenuItem FULLSCREEN_MENU_ITEM            = new IconMenuItem("Full Screen",        "fullscreen_2.png");
    public static final IconMenuItem MINIMIZE_MENU_ITEM              = new IconMenuItem("Minimize",           "minimize_2.png");
    public static final IconMenuItem EXIT_MENU_ITEM                  = new IconMenuItem("Exit",               "logout.png",         "Alt + F4");

    
	public WindowFrame(Node center) {
		super(center);
		this._initializeData();
		this._initializeGUI();
		this._initializeEvents();
		this._initializeFinally();
	}

	private void _initializeData() {

	}

	private void _initializeGUI() {
		/* General */
		this.setBackground(new Background(new BackgroundFill(Color.web(WINDOW_FILL_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));

		/* Top */
		this.setTop(new HBox(this.TopLeftEdge, this.TopEdge, this.TopRightEdge));

		this.TopLeftEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
		this.TopLeftEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
		this.TopLeftEdge.setCursor(Cursor.NW_RESIZE);

		this.TopEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
		this.TopEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
		this.TopEdge.setCursor(Cursor.N_RESIZE);

		this.TopRightEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
		this.TopRightEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
		this.TopRightEdge.setCursor(Cursor.NE_RESIZE);
		
		  /* Bottom */
        this.setBottom(new HBox(this.BottomLeftEdge, this.BottomEdge, this.BottomRightEdge));
        
        this.BottomLeftEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.BottomLeftEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.BottomLeftEdge.setCursor(Cursor.SW_RESIZE);
        
        this.BottomEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.BottomEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.BottomEdge.setCursor(Cursor.S_RESIZE);
        
        this.BottomRightEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.BottomRightEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.BottomRightEdge.setCursor(Cursor.SE_RESIZE);
        
        /* Rear Left */
        this.setLeft(new VBox(this.RearTopLeftEdge, this.RearLeftEdge, this.RearBottomLeftEdge));
        
        this.RearTopLeftEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearTopLeftEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearTopLeftEdge.setCursor(Cursor.NW_RESIZE);
        
        this.RearLeftEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearLeftEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearLeftEdge.setCursor(Cursor.W_RESIZE);
        
        this.RearBottomLeftEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearBottomLeftEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearBottomLeftEdge.setCursor(Cursor.SW_RESIZE);
        
        /* Rear Right */
        this.setRight(new VBox(this.RearTopRightEdge, this.RearRightEdge, this.RearBottomRightEdge));
        
        this.RearTopRightEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearTopRightEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearTopRightEdge.setCursor(Cursor.NE_RESIZE);
        
        this.RearRightEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearRightEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearRightEdge.setCursor(Cursor.E_RESIZE);
        
        this.RearBottomRightEdge.setStrokeWidth(WINDOW_BORDER_WIDTH);
        this.RearBottomRightEdge.setStroke(Color.web(WINDOW_BORDER_COLOR));
        this.RearBottomRightEdge.setCursor(Cursor.SE_RESIZE);
	}

	private void _initializeEvents() {
		/* Window width and height update */
		this.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number width) {
				_updateEdgesOnResized(width.doubleValue(), getHeight());
			}
		});

		this.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number height) {
				_updateEdgesOnResized(getWidth(), height.doubleValue());
			}
		});

		/* Rear Right Edge */
		this.RearRightEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnRearRightEdgeMousePressed(event);
			}
		});

		this.RearRightEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnRearRightEdgeMouseDragged(event);
			}
		});

		/* Bottom Edge */
		this.BottomEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomEdgeMousePressed(event);
			}
		});

		this.BottomEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomEdgeMouseDragged(event);
			}
		});

		/* Rear Left Edge */
		this.RearLeftEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnRearLeftEdgeMousePressed(event);
			}
		});

		this.RearLeftEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnRearLeftEdgeMouseDragged(event);
			}
		});

		/* Top Edge */
		this.TopEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopEdgeMousePressed(event);
			}
		});

		this.TopEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopEdgeMouseDragged(event);
			}
		});

		/* Right Bottom Edge */
		this.RearBottomRightEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomRightEdgeMousePressed(event);
			}
		});

		this.RearBottomRightEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomRightEdgeMouseDragged(event);
			}
		});

		this.BottomRightEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomRightEdgeMousePressed(event);
			}
		});

		this.BottomRightEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomRightEdgeMouseDragged(event);
			}
		});

		/* Top Right Edge */
		this.TopRightEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopRightEdgeMousePressed(event);
			}
		});

		this.TopRightEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopRightEdgeMouseDragged(event);
			}
		});

		this.RearTopRightEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopRightEdgeMousePressed(event);
			}
		});

		this.RearTopRightEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopRightEdgeMouseDragged(event);
			}
		});

		/* Top Left Edge */
		this.TopLeftEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopLeftMousePressed(event);
			}
		});

		this.TopLeftEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopLeftMouseDragged(event);
			}
		});

		this.RearTopLeftEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopLeftMousePressed(event);
			}
		});

		this.RearTopLeftEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnTopLeftMouseDragged(event);
			}
		});

		/* Bottom Left Edge */
		this.BottomLeftEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomLeftMousePressed(event);
			}
		});

		this.BottomLeftEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomLeftMouseDragged(event);
			}
		});

		this.RearBottomLeftEdge.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomLeftMousePressed(event);
			}
		});

		this.RearBottomLeftEdge.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_setOnBottomLeftMouseDragged(event);
			}
		});

		/* Context Menu */
		MAXIMIZE_MENU_ITEM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MAXIMIZE_WINDOW();
			}
		});

		FULLSCREEN_MENU_ITEM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TOGGLE_FULLSCREEN();
			}
		});

		MINIMIZE_MENU_ITEM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MINIMIZE_WINDOW();
			}
		});

		EXIT_MENU_ITEM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				EXIT_PROGRAM();
			}
		});
	}

	private void _initializeFinally() {

	}

	public static void EXIT_PROGRAM() {
		System.exit(EXIT_SUCCESS);		
	}

	public void addKeyEventHandler() {
		Global.PRIMARY_STAGE.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		Global.PRIMARY_STAGE.getScene().addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	private BitSet keyboardBitSet = new BitSet();

	/**
	 * "Key Pressed" handler for all input events: register pressed key in the bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key down
			keyboardBitSet.set(event.getCode().ordinal(), true);
			onKeyUpdateEvent();
		}
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			onKeyUpdateEvent();
		}
	};

	/**
	 * Detect all keys and show them in the label
	 */
	private void onKeyUpdateEvent() {

		StringBuilder sb = new StringBuilder();
		int count = 0;
		for( KeyCode keyCode: KeyCode.values()) {
			if ( keyboardBitSet.get(keyCode.ordinal())) {
				if ( count > 0) {
					sb.append( " ");
				}
				sb.append(keyCode.toString());
				count++;
			}
		}
		System.out.println(sb.toString());


		ArrayList<KeyCode> keyEvent = new ArrayList<KeyCode>();
		for(KeyCode keyCode: KeyCode.values()) {
			if (keyboardBitSet.get(keyCode.ordinal())) {
				keyEvent.add(keyCode);
			}
		}
		//short key
		//		KeyCode selectAllEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.A};
		//		KeyCode copyEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.C};
		//		KeyCode cutEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.X};
		//		KeyCode pasteEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.V};
		//		KeyCode deleteEventKeyCodes[] = {KeyCode.DELETE};
		//
		//		KeyCode boldEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.B};
		//		KeyCode italicEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.I};
		//		KeyCode underlineEventKeyCodes[] = {KeyCode.CONTROL, KeyCode.U};
		//
		//		KeyCode alignmentTopLeftEventKeyCodes[] = {KeyCode.NUMPAD7};
		//		KeyCode alignmentTopCenterEventKeyCodes[] = {KeyCode.NUMPAD8};
		//		KeyCode alignmentTopRightEventKeyCodes[] = {KeyCode.NUMPAD9};
		//		KeyCode alignmentCenterRightEventKeyCodes[] = {KeyCode.NUMPAD6};
		//		KeyCode alignmentBottomRightEventKeyCodes[] = {KeyCode.NUMPAD3};
		//		KeyCode alignmentBottomCenterEventKeyCodes[] = {KeyCode.NUMPAD2};
		//		KeyCode alignmentBottomLeftEventKeyCodes[] = {KeyCode.NUMPAD1};
		//		KeyCode alignmentCenterLeftEventKeyCodes[] = {KeyCode.NUMPAD4};
		//		KeyCode alignmentCenterEventKeyCodes[] = {KeyCode.NUMPAD5};

		//		if (Global.isMatchKeyEvent(keyEvent, selectAllEventKeyCodes)) {
		//			//Ctrl + A
		//			Global.SelectAll();
		//		}
		//		if (Global.isMatchKeyEvent(keyEvent, copyEventKeyCodes)) {
		//			//Ctrl + C
		//			Global.CopyToClipBoards();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, cutEventKeyCodes)) {
		//			//Ctrl + X
		//			Global.CutToClipboard();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, pasteEventKeyCodes)) {
		//			//Ctrl + V
		//			Global.PasteFromClipboard(null);
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, deleteEventKeyCodes)) {
		//			//Delete
		//			Global.DeleteSelected();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, boldEventKeyCodes)) {
		//			//Ctrl + B
		//			Global.boldText();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, italicEventKeyCodes)) {
		//			//Ctrl + I
		//			Global.italicText();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, underlineEventKeyCodes)) {
		//			//Ctrl + U
		//			Global.underlineText();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentTopLeftEventKeyCodes)) {
		//			//NUMPAD7
		//			Global.alignTextTopLeft();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentTopCenterEventKeyCodes)) {
		//			//NUMPAD8
		//			Global.alignTextTopCenter();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentTopRightEventKeyCodes)) {
		//			//NUMPAD9
		//			Global.alignTextTopRight();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentCenterRightEventKeyCodes)) {
		//			//NUMPAD6
		//			Global.alignTextCenterRight();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentBottomRightEventKeyCodes)) {
		//			//NUMPAD3
		//			Global.alignTextBottomRight();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentBottomCenterEventKeyCodes)) {
		//			//NUMPAD2
		//			Global.alignTextBottomCenter();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentBottomLeftEventKeyCodes)) {
		//			//NUMPAD1
		//			Global.alignTextBottomLeft();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentCenterLeftEventKeyCodes)) {
		//			//NUMPAD4
		//			Global.alignTextCenterLeft();
		//		}
		//		else if (Global.isMatchKeyEvent(keyEvent, alignmentCenterEventKeyCodes)) {
		//			//NUMPAD5
		//			Global.alignTextCenter();
		//		}
	}

	private void _updateEdgesOnResized(double width, double height) {
		if (width > (WINDOW_CORNER_LENGTH * 2)) {
			this.TopEdge.setEndX(width - (WINDOW_CORNER_LENGTH * 3));
			//			this.BottomEdge.setEndX(width - (WINDOW_CORNER_LENGTH * 3));
		}

		if (height > (WINDOW_CORNER_LENGTH * 2)) {
			//			this.RearLeftEdge.setEndY(height - (WINDOW_CORNER_LENGTH * 3));
			//			this.RearRightEdge.setEndY(height - (WINDOW_CORNER_LENGTH * 3));
		}
	}

	public void SetWidth(double windowWidth) {
		if (windowWidth >= StandaloneSettings.MIN_WINDOW_WIDTH) {
			Global.PRIMARY_STAGE.setWidth(windowWidth);
		}
	}

	public void SetHeight(double windowHeight) {
		if (windowHeight >= StandaloneSettings.MIN_WINDOW_HEIGHT) {
			Global.PRIMARY_STAGE.setHeight(windowHeight);
		}
	}

	private void _setOnBottomRightEdgeMousePressed(MouseEvent event) {
		this._setOnBottomEdgeMousePressed(event);
		this._setOnRearRightEdgeMousePressed(event);
	}

	private void _setOnBottomRightEdgeMouseDragged(MouseEvent event) {
		this._setOnBottomEdgeMouseDragged(event);
		this._setOnRearRightEdgeMouseDragged(event);
	}

	private void _setOnTopEdgeMousePressed(MouseEvent event) {
		this._topEdgePrevY = event.getScreenY();
	}

	private void _setOnTopEdgeMouseDragged(MouseEvent event) {
		double diffY = event.getScreenY() - this._topEdgePrevY;

		double windowHeight = Global.PRIMARY_STAGE.getHeight() - diffY;

		if (windowHeight >= StandaloneSettings.MIN_WINDOW_HEIGHT) {
			Global.PRIMARY_STAGE.setY(Global.PRIMARY_STAGE.getY() + diffY);
			this.SetHeight(windowHeight);
		}

		this._topEdgePrevY = event.getScreenY();
	}

	private void _setOnBottomEdgeMousePressed(MouseEvent event) {
		this._bottomEdgePrevY = event.getScreenY();
	}

	private void _setOnBottomEdgeMouseDragged(MouseEvent event) {
		double windowHeight = Global.PRIMARY_STAGE.getHeight() + event.getScreenY() - this._bottomEdgePrevY;

		this.SetHeight(windowHeight);

		this._bottomEdgePrevY = event.getScreenY();
	}

	private void _setOnRearLeftEdgeMousePressed(MouseEvent event) {
		this._rearLeftEdgePrevX = event.getScreenX();
	}

	private void _setOnRearLeftEdgeMouseDragged(MouseEvent event) {
		double diffX       = event.getScreenX() - this._rearLeftEdgePrevX;
		double windowWidth = Global.PRIMARY_STAGE.getWidth() - diffX;

		if (windowWidth >= StandaloneSettings.MIN_WINDOW_WIDTH) {
			Global.PRIMARY_STAGE.setX(Global.PRIMARY_STAGE.getX() + diffX);
			this.SetWidth(windowWidth);
		}

		this._rearLeftEdgePrevX = event.getScreenX();
	}

	private void _setOnRearRightEdgeMousePressed(MouseEvent event) {
		this._rearRightEdgePrevX = event.getScreenX();
	}

	private void _setOnRearRightEdgeMouseDragged(MouseEvent event) {
		double windowWidth = Global.PRIMARY_STAGE.getWidth() + event.getScreenX() - this._rearRightEdgePrevX;

		this.SetWidth(windowWidth);

		this._rearRightEdgePrevX = event.getScreenX();
	}

	private void _setOnTopRightEdgeMousePressed(MouseEvent event) {
		this._setOnTopEdgeMousePressed(event);
		this._setOnRearRightEdgeMousePressed(event);
	}

	private void _setOnTopRightEdgeMouseDragged(MouseEvent event) {
		this._setOnTopEdgeMouseDragged(event);
		this._setOnRearRightEdgeMouseDragged(event);
	}

	private void _setOnTopLeftMousePressed(MouseEvent event) {
		this._setOnTopEdgeMousePressed(event);
		this._setOnRearLeftEdgeMousePressed(event);
	}

	private void _setOnTopLeftMouseDragged(MouseEvent event) {
		this._setOnTopEdgeMouseDragged(event);
		this._setOnRearLeftEdgeMouseDragged(event);
	}

	private void _setOnBottomLeftMousePressed(MouseEvent event) {
		this._setOnRearLeftEdgeMousePressed(event);
		this._setOnBottomEdgeMousePressed(event);
	}

	private void _setOnBottomLeftMouseDragged(MouseEvent event) {
		this._setOnRearLeftEdgeMouseDragged(event);
		this._setOnBottomEdgeMouseDragged(event);
	}

	public static void MAXIMIZE_WINDOW() {
		if (!IS_MAXIMIZED) {
			WINDOW_X               = Global.PRIMARY_STAGE.getX();
			WINDOW_Y               = Global.PRIMARY_STAGE.getY();
			WINDOW_PREVIOUS_WIDTH  = Global.PRIMARY_STAGE.getWidth();
			WINDOW_PREVIOUS_HEIGHT = Global.PRIMARY_STAGE.getHeight();

			Screen      screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			Global.PRIMARY_STAGE.setX(0);
			Global.PRIMARY_STAGE.setY(0);
			Global.PRIMARY_STAGE.setWidth(bounds.getMaxX());
			Global.PRIMARY_STAGE.setHeight(bounds.getMaxY());
		}
		else {
			Global.PRIMARY_STAGE.setX(WINDOW_X);
			Global.PRIMARY_STAGE.setY(WINDOW_Y);
			Global.PRIMARY_STAGE.setWidth(WINDOW_PREVIOUS_WIDTH);
			Global.PRIMARY_STAGE.setHeight(WINDOW_PREVIOUS_HEIGHT);
		}

		IS_MAXIMIZED = !IS_MAXIMIZED;
	}

	public static void MINIMIZE_WINDOW() {
		Global.PRIMARY_STAGE.setIconified(true);
	}

	public static void TOGGLE_FULLSCREEN() {
		Global.PRIMARY_STAGE.setFullScreen(!Global.PRIMARY_STAGE.isFullScreen());
	}

	public static void SHOW_CONTEXT_MENU(MouseEvent event) {
		double x = event.getScreenX();
		double y = event.getScreenY();
	}

}
