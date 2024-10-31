package co.btssstudio.btgf.event;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import co.btssstudio.btgf.BTGF;
import co.btssstudio.btgf.InternalProcessor;
import co.btssstudio.btgf.MainClass;
import co.btssstudio.btgf.TickManager;
import co.btssstudio.btgf.logging.Level;
import co.btssstudio.btgf.util.BTGFUtil;

public class EventBus {
	public static class Entry {
		Class<? extends IEvent> type;
		Stack<Method> listeners = new Stack<Method>();
		Entry(Class<? extends IEvent> type) {
			this.type = type;
		}
		Class<? extends IEvent> type() {
			return type;
		}
		Entry register(Method listener) {
			listeners.push(listener);
			return this;
		}
	}
	public static HashMap<String, EventBus> INSTANCES = new HashMap<String, EventBus>();
	public String id;
	public HashMap<Class<? extends IEvent>, Entry> entrys = new HashMap<Class<? extends IEvent>, Entry>();
	public EventBus(String id) {
		this.id = id;
		INSTANCES.put(id,this);
	}
	public String id() {
		return id;
	}
	public static void autoRegister() {
		LinkedList<File> classes_f = BTGFUtil.getAllFiles("src");
		LinkedList<Class<?>> clazz = new LinkedList<Class<?>>();
		try {
			for(File f : classes_f) {
				clazz.add(Class.forName(BTGFUtil.filePathToClassPath(f.getPath())));
			}
		} catch (ClassNotFoundException e) {
			BTGF.logger.logException(e, "EventBus");
		}
		for(Class<?> c : clazz) {
			Annotation[] as = c.getAnnotations();
			for(Annotation a : as) {
				if(a instanceof ListeningGroup) {
					for(Method m : c.getMethods()) {
						INSTANCES.get(((ListeningGroup) a).busID()).registerListener(m);
					}
				} else if(a instanceof MainClass) {
					BTGF.mainClass = c;
					BTGF.logger.log(Level.INFO, "EventBus", "Successfully found main class : " + c.toString());
					for(Method m : c.getMethods()) {
						Annotation[] ass = m.getAnnotations();
						for(Annotation asa : ass) {
							if(asa instanceof InternalProcessor) {
								switch(((InternalProcessor) asa).stage()) {
								case PREINIT:
									TickManager.preInit.add(m);
									break;
								case INIT:
									TickManager.init.add(m);
									break;
								case LATEINIT:
									TickManager.lateInit.add(m);
									break;
								case TICK:
									TickManager.tick.add(m);
									break;
								case RENDER:
									TickManager.render.add(m);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	public EventBus registerEvent(IEvent type) {
		return registerEvent(type.getClass());
	}
	public EventBus registerEvent(Class<? extends IEvent> type) {
		Entry entry = new Entry(type);
		if(!entrys.containsKey(type)) entrys.put(type, entry);
		return this;
	}
	public void registerListener(Method listener) {
		if(listener != null) {
			boolean flag = false;
			Annotation[] as = listener.getAnnotations();
			for(Annotation a : as) {
				if(a instanceof Listener && listener.getParameterCount() == 1) {
					flag = true;
					break;
				}
			}
			if(flag) {
				Class<?>[] list = listener.getParameterTypes();
				Entry e = entrys.get(list[0]);
				e.listeners.add(listener);
			}
		}
	}
	public EventBus registerListener(Object listeningGroup) {
		if(listeningGroup != null) {
			Class<?> clazz = listeningGroup.getClass();
			Method[] methods = clazz.getMethods();
			for(Method m : methods) {
				registerListener(m);
			}
		}
		return this;
	}
	public void post(IEvent event) {
		try {
			Entry e = entrys.get(event.getClass());
			if(e != null && !e.listeners.empty()) {
				Stack<Method> ms = (Stack<Method>) e.listeners.clone();
				if(event instanceof ICancelableEvent) {
					ICancelableEvent cevent = (ICancelableEvent) event;
					while(!ms.empty()) {
						Method m = ms.pop();
						m.invoke(null, cevent);
						if(cevent.isCanceled()) break;
					}
				} else {
					while(!ms.empty()) {
						Method m = ms.pop();
						m.invoke(null, event);
					}
				}
			}
		} catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
			BTGF.logger.logException(e, "EventBus");
		}
	}
}
