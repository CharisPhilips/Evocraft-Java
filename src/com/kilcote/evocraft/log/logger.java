package com.kilcote.evocraft.log;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.kilcote.evocraft.common.Global;

public class logger {
	
	public static Logger logger = null;
	public static FileHandler fh = null;
	static {
		if(Global.IS_LOG) {
			logger = Logger.getLogger("Evocraft"); 
			try {
				fh = new FileHandler(System.getProperty("user.dir") + "/" + "log.txt");
				logger.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}  
		}
	}
	
	public static void info(String strLog) {
		log(strLog, Level.FINE);
	}
	
	private static String[] FILTER_METHOD = null;
//	private static String[] FILTER_METHOD = {
//			"setOnMouseExited"
//	};
	
	private static Class<?>[] FILTER_CLASS = null;
//	private static Class<?>[] FILTER_CLASS = {
//			ConnectorPointAdd.class
//	};
	
	public static void info(Class<?> clazz, String strMethod, String strLog) {
		if(FILTER_CLASS!=null) {
			boolean isSearchFilter = false;
			for(int i = 0; i < FILTER_CLASS.length; i++) {
				if(clazz.getName().indexOf(FILTER_CLASS[i].getName())!=-1) {
					isSearchFilter = true;
					break;
				}
			}
			if(!isSearchFilter ) {
				return;
			}
		}
		if(FILTER_METHOD!=null) {
			boolean isSearchFilter = false;
			for(int i = 0; i < FILTER_METHOD.length; i++) {
				if(strMethod.indexOf(FILTER_METHOD[i])!=-1) {
					isSearchFilter = true;
					break;
				}
			}
			if(!isSearchFilter ) {
				return;
			}
		}
		if(strLog.trim().length() <= 0) {
			log(String.format("\t\t[%s() \r\n \t\t{%s class (%s)}]\r\n", strMethod, clazz.getSimpleName(), clazz.getName()), Level.FINE);
			//log(String.format("\t\t[%s() method invoked at {%s class}]\r\n", strMethod, clazz.getName()), Level.FINE);
		}
		else {
			log(String.format("%s \r\n\t\t[%s() \r\n \t\t{%s class (%s)}]\r\n", strLog, strMethod, clazz.getSimpleName(), clazz.getName()), Level.FINE);
			//log(String.format("%s \r\n\t\t[%s() method invoked at {%s class}]\r\n", strLog, strMethod, clazz.getName()), Level.FINE);
		}
	}
	
	public static void error(Throwable e) {
		log(e, Level.SEVERE);
	}
	
	private static void log(Object message, Level level) {
		
		StringBuffer buffer = new StringBuffer("");
		try {
			if(message!=null) {
				
				if(message instanceof Throwable && ((Throwable)message).getStackTrace().length > 0) {
					
					int nCount = ((Throwable)message).getStackTrace().length;
					if(((Throwable)message).getCause()!=null) {
						buffer.append(String.format("Error:%s, Cause:%s \r\n.", ((Throwable)message).getMessage(), ((Throwable)message).getCause().getMessage()));
					}
					else {
						buffer.append(String.format("Error:%s \r\n.", ((Throwable)message).getMessage()));
					}

					for(int i = 0; i < nCount; i++) {
						buffer.append(String.format("File:%s, class:%s, method:%s, line: %d, \r\n"
								, ((Throwable)message).getStackTrace()[i].getFileName()
								, ((Throwable)message).getStackTrace()[i].getClassName()
								, ((Throwable)message).getStackTrace()[i].getMethodName()
								, ((Throwable)message).getStackTrace()[i].getLineNumber()));
					}
					if(Global.IS_LOG) {
						logger.log(level, buffer.toString());
					}
					if(Global.IS_DEBUG) {
						((Throwable)message).printStackTrace();
					}
				}
				else if(message instanceof String) {
					if(Global.IS_LOG) {
						logger.log(level, (String) message);
					}
					if(Global.IS_DEBUG) {
						System.out.println((String) message);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		if(Global.IS_LOG) {
			fh.close();
		}
	}
}
