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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Use this for logging. FLog outputs to a scrollable panel, which is accessed
 * by getWidget(). FLog also logs to the Webkit and Firebug, and IE9 consoles.
 * It formats things fairly nicely also. You need to call init() before using
 * this - if you don't call, FLog will fail silently, which is useful-ish for
 * production stuff. If you don't want end-users reading your logs, don't call
 * init(). Not calling init() will also probably make your code (a really tiny
 * bit) smaller, thanks to GWT's compiler.
 * 
 * @author carllange
 * 
 */
public class FLog {

	private static final Logger l = Logger.getLogger("FLog");
	private static HorizontalPanel topPanel = new HorizontalPanel();
	private static ScrollPanel scrollPanel = new ScrollPanel();
	private static HTMLPanel logWidget = new HTMLPanel("");
	private static boolean inited = false;
	/**
	 * Logs a debug message.
	 * 
	 * @param string
	 *            Log message
	 */
	public static void debug(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.FINE, string));
		}
	}

	/**
	 * Logs an error message.
	 * 
	 * @param string
	 *            Log message
	 */
	public static void error(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.SEVERE, string));
		}
	}

	/**
	 * You don't need to use this, but if you don't want to use the browser's
	 * console, it's a nice alternative. You can put this panel anywhere that
	 * can take panels.
	 * 
	 * @return {@link HorizontalPanel} The log panel.
	 */
	public static HorizontalPanel getWidget() {
		return topPanel;
	}
	
	/**
	 * Logs an info message.
	 * 
	 * @param string
	 *            Log message
	 */
	public static void info(String string) {
		if (inited) {
			l.log(new LogRecord(Level.INFO, string));
		}
	}

	/**
	 * Initialises the logger. Do not call this if you don't want to log things,
	 * as everything else should fail silently. If this is not called, it should
	 * also make your code size smaller (by a tiny bit), by not compiling other
	 * stuff in this class.
	 */
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

			inited = true;
		}
	}

	/**
	 * Logs a trace message.
	 * 
	 * @param string
	 *            Log message
	 */
	public static void trace(String string) {
		if (inited) {
			l.log(new LogRecord(Level.FINEST, string));
		}
	}

	/**
	 * Logs a warning message.
	 * 
	 * @param string
	 *            Log message
	 */
	public static void warn(String string) {
		if (inited) {
			scrollPanel.scrollToBottom();
			l.log(new LogRecord(Level.WARNING, string));
		}
	}
}

/**
 * The html formatter for FLog. This formats {@link LogRecord} objects into
 * pretty html. You shouldn't use or modify this if you don't know what you're
 * doing.
 * 
 * @author carllange
 * 
 */
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
		else if (logLevel >= Level.SEVERE.intValue())
			return "#F00"; // bright red
		else if (logLevel >= Level.WARNING.intValue())
			return "#E56717"; // orange
		else if (logLevel >= Level.INFO.intValue())
			return "#20b000"; // green
		else if (logLevel >= Level.CONFIG.intValue())
			return "#2B60DE"; // blue
		else if (logLevel >= Level.FINE.intValue())
			return "#F0F"; // purple
		else if (logLevel >= Level.FINER.intValue())
			return "#F0F"; // purple
		else if (logLevel >= Level.FINEST.intValue())
			return "#F0F"; // purple
		return "#000"; // black
	}

	private String getEscaped(String text) {
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
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
		String time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
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
		if ((n == "TRACE") || (n == "DEBUG") || (n == "INFO")) {
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
		return ((window.console != null) && (window.console.firebug == null)
				&& (window.console.log != null) && (typeof (window.console.log) == 'function'));
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

/**
 * The console formatter for FLog. This formats {@link LogRecord} objects so
 * that they look nice in the Webkit, Firebug, and possibly IE9 consoles. You
 * shouldn't use or modify this if you don't know what you're doing.
 * 
 * @author carllange
 * 
 */
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
		String time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		StringBuilder s = new StringBuilder();
		s.append(time);
		s.append(" ");
		s.append(newline);
		s.append(getName(event.getLevel().getName()));
		s.append(": ");
		return s.toString();
	}
}
