package com.kilcote.evocraft;

import com.kilcote.evocraft.common.Global;
import com.kilcote.evocraft.common.Settings;
import com.kilcote.evocraft.log.logger;
import com.kilcote.evocraft.views.EvoCraftPane;
import com.kilcote.evocraft.views.WindowFrame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EvoApp extends Application {
	 @Override
		public void stop() throws Exception {
			super.stop();
		}

		@Override
	    public void start(Stage primaryStage) {
	    	try {
//	    		Platform.setImplicitExit(false);
	    		Global.LOAD_AT_STARTUP(primaryStage);
	    		logger.info(this.getClass(), "start", "Start stage");
	    		
	    		EvoCraftPane program = new EvoCraftPane();
	    		logger.info(this.getClass(), "start", "Start FirmDrawProgram");

	    		WindowFrame     window  = new WindowFrame(program);
	    		logger.info(this.getClass(), "start", "Start WindowFrame");
	    		
	    		Scene           scene   = new Scene(window, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
	    		logger.info(this.getClass(), "start", "Start Scene");
	    		
	    		primaryStage.setTitle(Settings.WINDOW_TITLE);
	    		logger.info(this.getClass(), "start", "primaryStage.setTitle");
	    		
	    		primaryStage.setScene(scene);
	    		logger.info(this.getClass(), "start", "primaryStage.setScene");

	    		primaryStage.show();
	    		logger.info(this.getClass(), "start", "Show primaryStage");
	    		
	    		Global.LOAD_AT_END(window, program);
	    		logger.info(this.getClass(), "start", "End stage");
	    	}
	    	catch(Throwable e) {
	    		logger.error(e);
	    		logger.close();
	    	}
	    }

	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
	        launch(args);
	    }
}
