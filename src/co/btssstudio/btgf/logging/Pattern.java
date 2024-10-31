package co.btssstudio.btgf.logging;

public class Pattern {
	protected String content;
	protected Pattern(String content) {
		this.content = content;
	}
	protected String process(String level, String source, String message, String time) {
		return content.replaceAll("%level%", level).replaceAll("%source%", source).replaceAll("%message%", message).replaceAll("%time%", time);
	}
}
