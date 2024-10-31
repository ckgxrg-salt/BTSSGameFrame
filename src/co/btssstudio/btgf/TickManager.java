package co.btssstudio.btgf;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import co.btssstudio.btgf.event.EventBus;
import co.btssstudio.btgf.logging.Level;

public class TickManager {
	
	static float[] bgcolors = new float[4];
	static double tickRate = 60.0d;
	static long nano;
	static long millis;
	static long now;
	static int ticks = 0;
	static long nanoDifference;
	static double nanoPerTick = 1000000000.0d / tickRate;
	
	public static LinkedList<Method> preInit = new LinkedList<Method>();
	public static LinkedList<Method> init = new LinkedList<Method>();
	public static LinkedList<Method> lateInit = new LinkedList<Method>();
	public static LinkedList<Method> tick = new LinkedList<Method>(); 
	public static LinkedList<Method> render = new LinkedList<Method>(); 
	public static int tps;
	/**
	 * Sets the background color. Input 0~255(Alpha:0~100).
	 */
	public static void setBackgroundColor(float r, float g, float b, float alpha) {
		bgcolors[0] = r/255;
		bgcolors[1] = g/255;
		bgcolors[2] = b/255;
		bgcolors[3] = alpha/100;
	}
	/**
	 * Sets the expected "tick per second" for BTGF. NOTICE:If your interval isn't 0 in Windows.Builder, this value may not be correct.
	 */
	public static void tickRate(double value) {
		tickRate = value;
		nanoPerTick = 1000000000.0d / tickRate;
	}
	/*public static void readConfigure() {
		try {
			File f = new File("BTGF.cfg");
			if(!f.exists() || f.isDirectory()) createConfigure(f);
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			StringBuilder content = new StringBuilder();
			while(isr.ready()) {
				char c = ((char) isr.read());
				if(c != '\t' && c !='\n')
					content.append(c);
			}
			isr.close();
			fis.close();
			BTGF.BTGFConfigure = BTGF.gson.fromJson(content.toString(), Configure.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}*/
	/*private static void createConfigure(File f) throws IOException, RuntimeException {
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		osw.write(BTGFUtil.decorateJSON(BTGF.gson.toJson(new Configure())));
		osw.close();
		fos.close();
		throw new RuntimeException("Configure isn't exist!");
	}*/
	/*public static void loadConfigure() {
		try {
			Class<?> mainClass = Class.forName(BTGF.BTGFConfigure.mainClassName);
			Method m = mainClass.getMethod("preInit");
			m.invoke(null);
			BTGF.bus.post(BTGFEvents.EVENTREGISTER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}*/
	public static void preInit() {
		BTGF.logger.log(Level.INFO, "TickManager", "Starting Loading Phase :: PREINIT");
		for(Method m : preInit) {
			try {
				m.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				BTGF.logger.logException(e, "TickManager");
			}
		}
		if(!BTGF.glfw) {
			throw new IllegalStateException("GLFW won't stop.");
		}
		glViewport(0, 0, BTGF.activeWindow.width(), BTGF.activeWindow.height());
		glfwSetKeyCallback(BTGF.activeWindow.ID(), new KeyManager());
		BTGF.activeWindow.show();
		BTGF.bus.post(new BTGFEvents.PreInitEvent());
		BTGF.logger.log(Level.INFO, "TickManager", "Finished Loading Phase :: PREINIT");
	}
	public static void init() {
		//readConfigure();
		registerEvents();
		//loadConfigure();
		preInit();
		//Init
		BTGF.logger.log(Level.INFO, "TickManager", "Starting Loading Phase :: INIT");
		for(Method m : init) {
			try {
				m.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				BTGF.logger.logException(e, "TickManager");
			}
		}
		BTGF.bus.post(new BTGFEvents.InitEvent());
		BTGF.logger.log(Level.INFO, "TickManager", "Finished Loading Phase :: INIT");
		//Init
		lateInit();
		nano = System.nanoTime();
		millis = System.currentTimeMillis();
	}
	public static void lateInit() {
		BTGF.logger.log(Level.INFO, "TickManager", "Starting Loading Phase :: LATEINIT");
		for(Method m : lateInit) {
			try {
				m.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				BTGF.logger.logException(e, "TickManager");
			}
		}
		BTGF.bus.post(new BTGFEvents.LateInitEvent());
		BTGF.logger.log(Level.INFO, "TickManager", "Finished Loading Phase :: LATEINIT");
	}
	public static void registerEvents() {
		BTGF.logger.log(Level.INFO, "TickManager", "Starting Loading Phase :: REGISTEREVENTS");
		BTGF.bus.registerEvent(BTGFEvents.PREINIT);
		BTGF.bus.registerEvent(BTGFEvents.INIT);
		BTGF.bus.registerEvent(BTGFEvents.LATEINIT);
		BTGF.bus.registerEvent(BTGFEvents.TICK);
		BTGF.bus.registerEvent(BTGFEvents.RENDER);
		EventBus.autoRegister();
		BTGF.logger.log(Level.INFO, "TickManager", "Finished Loading Phase :: REGISTEREVENTS");
	}
	public static void _tick() {
		now = System.nanoTime();
		nanoDifference = now - nano;
		if(nanoDifference >= nanoPerTick) {
			nanoDifference -= nanoPerTick;
			ticks++;
			tick();
		}
		if(System.currentTimeMillis() - millis >= 1000) {
			millis = System.currentTimeMillis();
			tps = ticks;
			ticks = 0;
		}
	}
	public static void tick() {
		for(Method m : tick) {
			try {
				m.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				BTGF.logger.logException(e, "TickManager");
			}
		}
		glfwPollEvents();
		BTGF.bus.post(new BTGFEvents.TickEvent());
	}
	public static void render() {
		for(Method m : render) {
			try {
				m.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				BTGF.logger.logException(e, "TickManager");
			}
		}
		glClearColor(bgcolors[0], bgcolors[1], bgcolors[2], bgcolors[3]);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		BTGF.bus.post(new BTGFEvents.RenderEvent());
		BTGF.activeWindow.swapBuffers();
	}
}
