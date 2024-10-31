package co.btssstudio.btgf.logging;

import java.util.Calendar;

public class Logger {
	public String id;
	public Pattern pattern;
	public Logger(String pattern) {
		this(pattern, null);
	}
	public Logger(String pattern, String id) {
		this.id = id;
		this.pattern = new Pattern(pattern);
	}
	public Logger log(String message) {
		log(Level.INFO, message);
		return this;
	}
	public Logger log(Level lv, String message) {
		log(lv, "UNKNOWN", message);
		return this;
	}
	public Logger log(Level lv, String source, String message) {
		Calendar t = Calendar.getInstance();
		String ti = t.get(Calendar.YEAR) + "/" + t.get(Calendar.MONTH) + "/" + t.get(Calendar.DAY_OF_MONTH) + " " + t.get(Calendar.HOUR_OF_DAY) + ":" + t.get(Calendar.MINUTE) + ":" + t.get(Calendar.SECOND);
		log(ti, lv, source, message);
		return this;
	}
	public Logger log(String time, Level lv, String source, String message) {
		String str = pattern.process(lv.message, source, message, time);
		System.out.println(lv.withColor + str);
		return this;
	}
	public Logger logException(Exception e, String source) {
		Level lv = Level.EXCEPTION;
		String message = "An exception has been found! : " + e.getLocalizedMessage();
		log(lv, source, message);
		return this;
	}
}
