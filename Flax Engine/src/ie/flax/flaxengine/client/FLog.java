package ie.flax.flaxengine.client;

import com.allen_sauer.gwt.log.client.Log;

public class FLog {
	private static String lastmessage = "";
	private static int msgcounter = 0;
	
	static void debug(String msg) {
		if (shouldPrintMessage(msg) == true) Log.debug(msg);
	}
	
	static void trace(String msg) {
		if (shouldPrintMessage(msg) == true) Log.trace(msg);
	}
	
	static void info(String msg) {
		if (shouldPrintMessage(msg) == true) Log.info(msg);
	}
	
	static void error(String msg) {
		if (shouldPrintMessage(msg) == true) Log.error(msg);
	}
	
	static void fatal(String msg) {
		if (shouldPrintMessage(msg) == true) Log.fatal(msg);
	}
	
	static void warn(String msg) {
		if (shouldPrintMessage(msg) == true) Log.warn(msg);
	}
	
	/*
	 * This checks if msg has been printed to the log recently
	 * 
	 */
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
}