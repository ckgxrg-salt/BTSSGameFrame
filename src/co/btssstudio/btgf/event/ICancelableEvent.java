package co.btssstudio.btgf.event;

public interface ICancelableEvent extends IEvent{
	void cancel();
	boolean isCanceled();
}
