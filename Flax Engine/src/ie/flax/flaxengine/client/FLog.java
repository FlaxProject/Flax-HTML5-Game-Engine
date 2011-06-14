package ie.flax.flaxengine.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class FLog {
	private static String lastmessage = "";
	private static int msgcounter = 0;
	
	public static void debug(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.debug(msg);
		GWT.log(msg);
	}
	
	public static void trace(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.trace(msg);
		GWT.log(msg);
	}
	
	public static void info(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.info(msg);
		GWT.log(msg);
	}
	
	public static void error(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.error(msg);
		GWT.log(msg);
	}
	
	public static void fatal(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.fatal(msg);
		GWT.log(msg);
	}
	
	public static void warn(String msg) {
		//if (shouldPrintMessage(msg) == true) Log.warn(msg);
		GWT.log(msg);
	}
	
	/*
	 * This checks if msg has been printed to the log recently
	 * 
	 
	private static Boolean shouldPrintMessage(String msg) {
		if (msg != lastmessage) {
			lastmessage = msg;
			msgcounter = 0;
			return true;
		} else if (msg == lastmessage) {
			msgcounter++;
			if (msgcounter > 5) {
				return false;
			} 
			return true;
		}
		
		//if it hits here, something's wrong
		Log.warn("Problem with logger");
		return true;
	}
	
	public static Widget getWidget() {
		return Log.getLogger(DivLogger.class).getWidget();
	}
	*/
}