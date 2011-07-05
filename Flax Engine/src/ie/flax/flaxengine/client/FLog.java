package ie.flax.flaxengine.client;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.HtmlLogFormatter;
import com.google.gwt.logging.client.SystemLogHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class FLog {

	private static final Logger l = Logger.getLogger("FLog");
	private static ScrollPanel topPanel = new ScrollPanel();
	private static HTMLPanel logWidget = new HTMLPanel("");
	private static boolean inited = false;

	public static void debug(String string) {
		if (inited) {
			topPanel.scrollToBottom();
			l.log(new LogRecord(Level.FINE, string));
		}
	}

	public static void error(String string) {
		if (inited) {
			topPanel.scrollToBottom();
			l.log(new LogRecord(Level.SEVERE, string));
		}
	}

	public static ScrollPanel getWidget() {
		return topPanel;
	}

	public static void info(String string) {
		if (inited) {
			l.log(new LogRecord(Level.INFO, string));
		}
	}

	public static void init() {
		if (!inited) {
			l.setUseParentHandlers(false);

			l.addHandler(new ConsoleLogHandler());
			l.addHandler(new SystemLogHandler());
			HasWidgetsLogHandler hwlh = new HasWidgetsLogHandler(logWidget);

			hwlh.setFormatter(new FLogFormatter());

			l.addHandler(hwlh);
			
			topPanel.setHeight("140px");
			topPanel.add(logWidget);
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
			topPanel.scrollToBottom();
			l.log(new LogRecord(Level.WARNING, string));
		}
	}
}

class FLogFormatter extends HtmlLogFormatter {

	private static String newline = "__GWT_LOG_FORMATTER_BR__";

	public FLogFormatter() {
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
		StringBuilder s = new StringBuilder();
		s.append(date.toString());
		s.append(" ");
		s.append(newline);
		s.append(getName(event.getLevel().getName()));
		s.append(": ");
		return s.toString();
	}
}