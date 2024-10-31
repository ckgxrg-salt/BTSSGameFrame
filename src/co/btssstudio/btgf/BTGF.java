package co.btssstudio.btgf;

import static org.lwjgl.glfw.GLFW.*;

//import com.google.gson.Gson;

import co.btssstudio.btgf.event.EventBus;
import co.btssstudio.btgf.logging.Logger;
import co.btssstudio.btgf.util.Window;

public class BTGF {
	public static Logger logger = new Logger("[%time%] [%source%] [%level%] %message%", "BTGF");
	public static boolean glfw = glfwInit();
	public static EventBus bus = new EventBus("BTGF");
	public static Window activeWindow = null;
	public static Class<?> mainClass = null;
	//public static Configure BTGFConfigure = new Configure();
	//public static Gson gson = new Gson();
	/**
	 * Sets the background color. Input 0~255(Alpha:0~100).
	 */
	public static void setBackgroundColor(float r, float g, float b, float alpha) {
		TickManager.setBackgroundColor(r, g, b, alpha);
	}
	/**
	 * Sets window.
	 * @param window The window you want to use.
	 */
	public static void setWindow(Window window) {
		activeWindow = window;
	}
	/**
	 * Make sure this method is called in your main(String args[]) method.
	 */
	public static void main(String args[]) {
		TickManager.init();
		while(!glfwWindowShouldClose(activeWindow.ID())) {
			TickManager._tick();
			TickManager.render();
		}
	}
}