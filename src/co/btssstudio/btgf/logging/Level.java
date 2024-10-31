package co.btssstudio.btgf.logging;

public class Level {
	
	public static final Level INFO = new Level("INFO");
	public static final Level WARN = new Level("WARN");
	public static final Level EXCEPTION = new Level("EXCEPTION");
	public static final Level ERROR = new Level("ERROR");
	
	public String message;
	public String withColor;
	public Level() {
		this("LOG");
	}
	public Level(String message) {
		this(message, "");
	}
	/**
	 * Construct a logging level.
	 * @param message The tip of the level.
	 * @param withColor The color(like "\033[31m") of the log.
	 */
	public Level(String message, String withColor) {
		this.message = message;
		this.withColor = withColor;
	}
}
