package com.kilcote.evocraft.views;

import com.kilcote.evocraft.common.Global;
import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.engine.Game;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.components.MultilineTooltip;
import com.kilcote.evocraft.views.components.ToolBarSpacer;
import com.kilcote.evocraft.views.components.ToolbarGroup;
import com.kilcote.evocraft.views.components.button.IconToggleButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EvoCraftPane extends BorderPane {

	/* Window Title*/
	private final Label _windowTitleLabel = new Label(Settings.WINDOW_TITLE);

	/* Window Icon*/
	private final ImageView _appIcon = ResourceUtils.getResourceImageView(Settings.APP_ICON, 22, 22, true, true);

	//Window icons
	private final IconToggleButton _exitIconButton = new IconToggleButton(null, "exit.png", new String[]{"Exit"});
	private final IconToggleButton _maximizeIconButton = new IconToggleButton(null, "maximize.png", new String[]{"Maximize / Restore"});
	private final IconToggleButton _minimizeIconButton = new IconToggleButton(null, "minimize.png", new String[]{"Minimize"});
	private final Label _gameTitleLabel = new Label();

	/* Top */
	public final ToolBar TopToolBar = new ToolBar();

	/* Center */
    public final GridPane GameLayout = new GridPane();

	/* Position */
	private double _prevWindowX = 0;
    private double _prevWindowY = 0;
    
	/* Top Right Main ToolBar Buttons */
	public static final String FEATURE_FIND_HOVER_OUT_ICON = "find-feature.png";
	public static final String FEATURE_FIND_HOVER_IN_ICON = "find-feature-hover-in.png";
	public static final String FEATURE_FIND_COLOR = "#006DF0";

	public final IconToggleButton PlayButton = new IconToggleButton("Play", "menu/" + "play-button.png", new String[]{"View as Presentation", "F5"});
	public final IconToggleButton PauseButton = new IconToggleButton("Pause", "menu/" + "pause-button.png", new String[]{"View as Presentation", "F5"});
	public final IconToggleButton FullScreenButton = new IconToggleButton("Full Screen", "menu/" + "fullscreen-symbol.png", new String[]{"Full Screen", "Ctrl + Shift + F"});

//	public final GamePage gamePage = new GamePage(this.MiddleLayout);
//	public final GridPane gamePage = new GridPane();

	private Game game = null; 
			
	public EvoCraftPane() {
		this._initializeData();
		this._initializeGUI();
		this._initializeEvents();
		this._initializeFinally();
	}

	private void _initializeData() {
		
	}

	private void _initializeGUI() {

		this._windowTitleLabel.setTextFill(Color.web("#34495e"));
		this._windowTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		this._windowTitleLabel.setTooltip(new MultilineTooltip(new String[]{"Version " + Settings.VERSION}));
		this._exitIconButton.setStyle("-fx-background-radius: " + ToolbarGroup.CORNER_RADIUS + "; -fx-padding: 4;");

		this._maximizeIconButton.setStyle("-fx-background-radius: " + ToolbarGroup.CORNER_RADIUS + "; -fx-padding: 4;");
		this._minimizeIconButton.setStyle("-fx-background-radius: " + ToolbarGroup.CORNER_RADIUS + "; -fx-padding: 4;");

		this._gameTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		/* Top */
		this.setTop(this.TopToolBar);

		this.PlayButton.setPadding(new Insets(3, 6, 3, 6));
		this.PauseButton.setPadding(new Insets(3, 6, 3, 6));
		this.FullScreenButton.setPadding(new Insets(3, 6, 3, 6));

		this.TopToolBar.getItems().addAll(
				this._appIcon,
				this._windowTitleLabel,
				new Separator(),
				new ToolBarSpacer(),
				this._gameTitleLabel,
				new ToolBarSpacer(),
				this.PlayButton,
				this.FullScreenButton,
				new Separator(),
				this._minimizeIconButton,
				this._maximizeIconButton,
				this._exitIconButton
				);

		 /* Center */
        this.setCenter(this.GameLayout);
        this.GameLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private void _initializeEvents() {

	    this.TopToolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _onWindowPressed(event);
            }
        });

        this.TopToolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _onWindowDragged(event);
            }
        });

        this.TopToolBar.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _onWindowReleased();
            }
        });

        /* Exit */
        this._exitIconButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WindowFrame.EXIT_PROGRAM();
            }
        });

        /* Maximize */
        this._maximizeIconButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WindowFrame.MAXIMIZE_WINDOW();
            }
        });

        /* Minimize */
        this._minimizeIconButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WindowFrame.MINIMIZE_WINDOW();
            }
        });

        /* Full Screen */
        this.FullScreenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WindowFrame.TOGGLE_FULLSCREEN();
            }
        });
        
        this.PlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (game == null) {
            		game = new Game(GameLayout, Settings.fieldSizeX, Settings.fieldSizeY);
            		game.Play();
            	} else {
            		game.Resume();
            	}
    			
    			int index = EvoCraftPane.this.TopToolBar.getItems().indexOf(PlayButton);
    			EvoCraftPane.this.TopToolBar.getItems().remove(index);
    			EvoCraftPane.this.TopToolBar.getItems().add(index, PauseButton);
            }
        });
        
        this.PauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
    			game.Pause();
    			int index = EvoCraftPane.this.TopToolBar.getItems().indexOf(PauseButton);
    			EvoCraftPane.this.TopToolBar.getItems().remove(index);
    			EvoCraftPane.this.TopToolBar.getItems().add(index, PlayButton);
            }
        });
	}

	private void _initializeFinally() {
	}

	public void ApplyDefaultSettings() {
    }
	
	private void _onWindowPressed(MouseEvent event) {
        Global.PRIMARY_STAGE.getScene().setFill(Color.TRANSPARENT);
        this._prevWindowX = event.getScreenX();
        this._prevWindowY = event.getScreenY();
    }

    private void _onWindowDragged(MouseEvent event) {
    	Global.PRIMARY_STAGE.setX(Global.PRIMARY_STAGE.getX() + event.getScreenX() - this._prevWindowX);
    	Global.PRIMARY_STAGE.setY(Global.PRIMARY_STAGE.getY() + event.getScreenY() - this._prevWindowY);

        this._prevWindowX = event.getScreenX();
        this._prevWindowY = event.getScreenY();

        this.setOpacity(WindowFrame.WINDOW_OPACITY_ON_DRAGGED);

        if (Global.WINDOW_FRAME != null) {
            Global.WINDOW_FRAME.setOpacity(WindowFrame.WINDOW_OPACITY_ON_DRAGGED);
        }

        this.setCursor(Cursor.MOVE);
    }

    private void _onWindowReleased() {
        this.setOpacity(1);

        if (Global.WINDOW_FRAME != null) {
            Global.WINDOW_FRAME.setOpacity(1);
        }

        this.setCursor(Cursor.DEFAULT);
    }
}
