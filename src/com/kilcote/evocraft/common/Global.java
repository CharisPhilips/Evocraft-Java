package com.kilcote.evocraft.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.kilcote.evocraft.EvoApp;
import com.kilcote.evocraft.utils.ResourceUtils;
import com.kilcote.evocraft.views.EvoCraftPane;
import com.kilcote.evocraft.views.WindowFrame;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Global {
	
	public static final boolean IS_LOG = false;
	public final static boolean IS_DEBUG = true;
	public static Stage             PRIMARY_STAGE = null;
	public static WindowFrame       WINDOW_FRAME  = null;
	public static EvoCraftPane   PROGRAM       = null;
	
	public static void LOAD_AT_STARTUP(Stage primaryStage) {
		// Check if the same process is exists
		if (OsValidator.IS_WINDOWS()) {
			try {
				final String      COMMAND        = System.getenv("windir") + "/system32/tasklist.exe";
				Process           process        = Runtime.getRuntime().exec(COMMAND);
				String            line           = null;
				BufferedReader    input          = new BufferedReader(new InputStreamReader(process.getInputStream()));
				ArrayList<String> pidInfo        = new ArrayList<>();
				int               totalProcesses = 0;

				while ((line = input.readLine()) != null) {
					pidInfo.add(line); 
				}

				input.close();

				String firmDrawProcessName = StandaloneSettings.WINDOW_TITLE.toLowerCase();

				for (String processName : pidInfo) {
					if (processName.toLowerCase().contains(firmDrawProcessName)) {
						++totalProcesses;
					}

					if (totalProcesses > 1) {
						Alert alert = new Alert(Alert.AlertType.WARNING, "Program is already running", ButtonType.CLOSE);
						alert.showAndWait();
						WindowFrame.EXIT_PROGRAM();
					}
				}
			}
			catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		Global.PRIMARY_STAGE = primaryStage;
		Global.PRIMARY_STAGE.initStyle(StageStyle.TRANSPARENT);
		Global.PRIMARY_STAGE.getIcons().add(ResourceUtils.getResourceImage(StandaloneSettings.APP_ICON));
	}

	public static void LOAD_AT_END(WindowFrame frame, EvoCraftPane program) {
		WINDOW_FRAME = frame;
		PROGRAM      = program;
		frame.addKeyEventHandler();

		Global.PRIMARY_STAGE.getScene().getStylesheets().add(EvoApp.class.getResource("Resource/" + "stylesheets/style.css").toExternalForm());
		PROGRAM.ApplyDefaultSettings();
	}
}
