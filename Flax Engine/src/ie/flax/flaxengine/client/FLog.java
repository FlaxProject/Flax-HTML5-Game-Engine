package ie.flax.flaxengine.client;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.HtmlLogFormatter;
import com.google.gwt.logging.client.SystemLogHandler;
import com.google.gwt.logging.client.TextLogFormatter;
import com.google.gwt.logging.impl.FormatterImpl;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class FLog {

	private static final Logger l = Logger.getLogger("FLog");
	private static HorizontalPanel topPanel = new HorizontalPanel();
	private static ScrollPanel scrollPanel = new ScrollPanel();
	private static HTMLPanel logWidget = new HTMLPanel("");
	private static boolean inited = false;
	private static HTMLPanel fpsPanel = new HTMLPanel("");
	private static HTML fpsHtml = new HTML("");
	
	public static void debug(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.FINE, string));
		}
	}

	public static void error(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.SEVERE, string));
		}
	}

	public static HorizontalPanel getWidget() {
		return topPanel;
	}
	
	public static HTMLPanel getFpsPanel(){
		return fpsPanel;
	}

	public static void info(String string) {
		if (inited) {
			l.log(new LogRecord(Level.INFO, string));
		}
	}

	public static void init() {
		if (!inited) {
			l.setUseParentHandlers(false);

			l.addHandler(new SystemLogHandler());
			
			FLogConsoleLogHandler clh = new FLogConsoleLogHandler();
			
			clh.setFormatter(new FLogConsoleFormatter());
			
			HasWidgetsLogHandler hwlh = new HasWidgetsLogHandler(logWidget);

			hwlh.setFormatter(new FLogHtmlFormatter());

			l.addHandler(clh);
			l.addHandler(hwlh);
			
			scrollPanel.setHeight("140px");
			scrollPanel.add(logWidget);
			topPanel.add(scrollPanel);
			
			topPanel.add(fpsPanel);
			topPanel.setCellWidth(fpsPanel, "50px");
			
			inited = true;
		}
	}

	public static void trace(String string) {
		if (inited) {
			l.log(new LogRecord(Level.FINEST, string));
		}
	}

	public static void warn(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.WARNING, string));
		}
	}

	/*
	 * This is really only here because I couldn't think of another place.
	 * TODO: remove fps stuff from Flog.
	 */
	public static void updateFps(int frameCount) {
		fpsPanel.remove(fpsHtml);
		fpsHtml = new HTML("FPS: " + frameCount);
		fpsPanel.add(fpsHtml);
	}
}

class FLogHtmlFormatter extends HtmlLogFormatter {

	private static String newline = "__GWT_LOG_FORMATTER_BR__";

	public FLogHtmlFormatter() {
		super(false);
	}

	@Override
	public String format(LogRecord event) {
		StringBuilder html = new StringBuilder(getHtmlPrefix(event));
		html.append(getHtmlPrefix(event));
		html.append(getRecordInfo(event, " "));
		html.append(getEscaped(event.getMessage()));

		html.append(getHtmlSuffix(event));
		return html.toString();
	}

	private String getColor(int logLevel) {
		if (logLevel == Level.OFF.intValue())
			return "#000"; // black
		if (logLevel >= Level.SEVERE.intValue())
			return "#F00"; // bright red
		if (logLevel >= Level.WARNING.intValue())
			return "#E56717"; // dark orange
		if (logLevel >= Level.INFO.intValue())
			return "#20b000"; // green
		if (logLevel >= Level.CONFIG.intValue())
			return "#2B60DE"; // blue
		if (logLevel >= Level.FINE.intValue())
			return "#F0F"; // purple
		if (logLevel >= Level.FINER.intValue())
			return "#F0F"; // purple
		if (logLevel >= Level.FINEST.intValue())
			return "#F0F"; // purple
		return "#000"; // black
	}

	private String getEscaped(String text) {
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		// but allow the line breaks that we put in ourselves
		text = text.replaceAll(newline, "<br>");
		return text;
	}

	private String getName(String name) {
		String r = "";
		if (name.equalsIgnoreCase("finest")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("finer")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("fine")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("config")) {
			r = "DEBUG";
		} else if (name.equalsIgnoreCase("info")) {
			r = "INFO";
		} else if (name.equalsIgnoreCase("warning")) {
			r = "WARN";
		} else if (name.equalsIgnoreCase("severe")) {
			r = "ERROR";
		}
		return r;
	}

	@Override
	protected String getHtmlPrefix(LogRecord event) {
		StringBuilder prefix = new StringBuilder();
		prefix.append("<span style='color:");
		prefix.append(getColor(event.getLevel().intValue()));
		prefix.append("'>");
		prefix.append("<code>");
		return prefix.toString();
	}

	@Override
	protected String getRecordInfo(LogRecord event, String newline) {
		Date date = new Date(event.getMillis());
		String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		StringBuilder s = new StringBuilder();
		s.append(time);
		s.append(" ");
		s.append(newline);
		s.append(getName(event.getLevel().getName()));
		s.append(": ");
		return s.toString();
	}
}

class FLogConsoleLogHandler extends Handler {

	  public FLogConsoleLogHandler() {
	    setFormatter(new TextLogFormatter(true));
	    setLevel(Level.ALL);
	  }
	  
	  @Override
	  public void close() {
	    // No action needed
	  }

	  @Override
	  public void flush() {
	    // No action needed
	  }

	  @Override
	  public void publish(LogRecord record) {
	    if (!isSupported() || !isLoggable(record)) {
	      return;
	    }
	    String msg = getFormatter().format(record);
	    
	    String n = getName(record.getLevel().getName());
	    if ((n == "TRACE") || (n == "DEBUG") || (n == "INFO")){
	    	nlDebug(msg);
	    } else if (n == "WARN") {
	    	nlWarn(msg);
	    } else if (n == "ERROR") {
	    	nlErr(msg);
	    } else {
	    	log(msg);
	    }
	  }

	  private String getName(String name) {
			String r = "";
			if (name.equalsIgnoreCase("finest")) {
				r = "TRACE";
			} else if (name.equalsIgnoreCase("finer")) {
				r = "TRACE";
			} else if (name.equalsIgnoreCase("fine")) {
				r = "TRACE";
			} else if (name.equalsIgnoreCase("config")) {
				r = "DEBUG";
			} else if (name.equalsIgnoreCase("info")) {
				r = "INFO";
			} else if (name.equalsIgnoreCase("warning")) {
				r = "WARN";
			} else if (name.equalsIgnoreCase("severe")) {
				r = "ERROR";
			}
			return r;
		}
	  
	  private native boolean isSupported() /*-{
	    return ((window.console != null) &&
	            (window.console.firebug == null) && 
	            (window.console.log != null) &&
	            (typeof(window.console.log) == 'function'));
	  }-*/;

	  private native void log(String message) /*-{
	    window.console.log(message);
	  }-*/;
	  private native void nlDebug(String message) /*-{
	    window.console.debug(message);
	  }-*/;
	  private native void nlWarn(String message) /*-{
	    window.console.warn(message);
	  }-*/;
	  private native void nlErr(String message) /*-{
	    window.console.error(message);
	  }-*/;

	}


class FLogConsoleFormatter extends FormatterImpl {
	

	@Override
	public String format(LogRecord event) {
		StringBuilder html = new StringBuilder();
		html.append(getRecordInfo(event, " "));
		html.append(event.getMessage());
		return html.toString();
	}

	private String getName(String name) {
		String r = "";
		if (name.equalsIgnoreCase("finest")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("finer")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("fine")) {
			r = "TRACE";
		} else if (name.equalsIgnoreCase("config")) {
			r = "DEBUG";
		} else if (name.equalsIgnoreCase("info")) {
			r = "INFO";
		} else if (name.equalsIgnoreCase("warning")) {
			r = "WARN";
		} else if (name.equalsIgnoreCase("severe")) {
			r = "ERROR";
		}
		return r;
	}
	
	@Override
	protected String getRecordInfo(LogRecord event, String newline) {
		Date date = new Date(event.getMillis());
		String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		StringBuilder s = new StringBuilder();
		s.append(time);
		s.append(" ");
		s.append(newline);
		s.append(getName(event.getLevel().getName()));
		s.append(": ");
		return s.toString();
	}
}


