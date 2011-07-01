package ie.flax.flaxengine.client;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.HtmlLogFormatter;
import com.google.gwt.logging.client.SystemLogHandler;
import com.google.gwt.user.client.ui.HTMLPanel;

public class FLog {

    private static final Logger l = Logger.getLogger("FLog");
    private static HTMLPanel logWidget = new HTMLPanel("");
    private static boolean inited = false;

    public static void debug(String string) {
        if (inited) {
            l.log(new LogRecord(Level.FINE, string));
        }
    }

    public static void error(String string) {
        if (inited) {
            l.log(new LogRecord(Level.SEVERE, string));
        }
    }

    public static HTMLPanel getWidget() {
        return logWidget;
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

            hwlh.setFormatter(new FLogFormatter(true));

            l.addHandler(hwlh);
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
            l.log(new LogRecord(Level.WARNING, string));
        }
    }
}

class FLogFormatter extends HtmlLogFormatter {

    public FLogFormatter(boolean showStackTraces) {
        super(showStackTraces);
    }
}