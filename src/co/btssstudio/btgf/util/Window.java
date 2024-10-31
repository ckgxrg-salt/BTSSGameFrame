package co.btssstudio.btgf.util;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import co.btssstudio.btgf.BTGF;

public class Window {
	private long ID;
	private int width;
	private int height;
	private String title = "BTGF";
	private int posX = 0;
	private int posY = 0;
	private int interval = 0;
	protected Window(long ID, int width, int height, String title, int posX, int posY, int interval) {
		this.ID = ID;
		this.width = width;
		this.height = height;
		this.title = title;
		this.posX = posX;
		this.posY = posY;
		this.interval = interval;
		BTGF.activeWindow = this;
	}
	public Window show() {
		glfwShowWindow(ID);
		return this;
	}
	public long ID() {
		return ID;
	}
	public int width() {
		return width;
	}
	public int height() {
		return height;
	}
	public Window swapBuffers() {
		glfwSwapBuffers(ID);
		return this;
	}
	public int getInterval() {
		return interval;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public String getTitle() {
		return title;
	}
	public static class Builder {
		private int width;
		private int height;
		private String title;
		private int posX;
		private int posY;
		private int interval;
		public Window.Builder hint(int hint, int value) {
			if(!BTGF.glfw)
				//BTGF.logger.error("Please initalize GLFW!");
			glfwWindowHint(hint, value);
			return this;
		}
		public Window.Builder width(int value) {
			width = value;
			return this;
		}
		public Window.Builder height(int value) {
			height = value;
			return this;
		}
		public Window.Builder title(String value) {
			title = value;
			return this;
		}
		public Window.Builder pos(int x, int y) {
			posX = x;
			posY = y;
			return this;
		}
		public Window.Builder middlePos() {
			GLFWVidMode vmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			posX = (vmode.width() - width) / 2;
			posY = (vmode.height() - height) / 2;
			return this;
		}
		public Window.Builder swapInterval(int value) {
			interval = value;
			return this;
		}
		public Window build() {
			if(!BTGF.glfw) {
				//BTGF.logger.error("Please initalize GLFW!");
				return null;
			}
			long ID = glfwCreateWindow(width, height, title, NULL, NULL);
			if(ID == NULL) {
				//BTGF.logger.error("Unable to create window!");
				return null;
			}
			glfwSetWindowPos(ID, posX, posY);
			glfwMakeContextCurrent(ID);
			GL.createCapabilities();
			glfwSwapInterval(interval);
			return new Window(ID, width, height, title, posX, posY, interval);
		}
	}
}
