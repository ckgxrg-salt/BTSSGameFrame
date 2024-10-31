package co.btssstudio.btgf.event;

public interface IListener {
	/**
	 * When @param event posted, this method will be executed.
	 * @param event The event waits to post.
	 * @return If @param recieved a cancelable event, return false to cancel it.
	 */
	void invoke(IEvent event);
}
