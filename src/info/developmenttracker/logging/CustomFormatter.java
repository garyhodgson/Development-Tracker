package info.developmenttracker.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * from http://www.javalobby.org/java/forums/t18515.html
 * 
 */
public class CustomFormatter extends Formatter {

	private static final DateFormat format = new SimpleDateFormat("h:mm:ss");
	private static final String lineSep = System.getProperty("line.separator");

	public String format(LogRecord record) {
		String loggerName = record.getLoggerName();
		if (loggerName == null) {
			loggerName = "root";
		}
		return String.format("%s [%s] %s  | %s%s", format.format(new Date(record.getMillis())), record.getLevel(), record.getMessage(), loggerName, lineSep);
	}

}
