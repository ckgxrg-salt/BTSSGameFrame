package co.btssstudio.btgf;

import co.btssstudio.btgf.event.IEvent;

public class BTGFEvents {
	public static final Class<? extends IEvent> PREINIT = PreInitEvent.class;
	public static final Class<? extends IEvent> INIT = InitEvent.class;
	public static final Class<? extends IEvent> LATEINIT = LateInitEvent.class;
	public static final Class<? extends IEvent> TICK = TickEvent.class;
	public static final Class<? extends IEvent> RENDER = RenderEvent.class;
	public static class PreInitEvent implements IEvent {}
	public static class InitEvent implements IEvent {}
	public static class LateInitEvent implements IEvent {}
	public static class TickEvent implements IEvent {}
	public static class RenderEvent implements IEvent {}
}
