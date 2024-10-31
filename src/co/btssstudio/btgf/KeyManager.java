package co.btssstudio.btgf;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallback;

import co.btssstudio.btgf.event.IEvent;

public class KeyManager extends GLFWKeyCallback{
	public static HashMap<Integer, KeyState> keys = new HashMap<Integer, KeyState>();
	public static class KeyState{
		int state;
		int modifier;
		public KeyState(int _state, int _modifier){
			state = _state;
			modifier = _modifier;
		}
		public int get() {
			return state;
		}
		public int getModifierKey() {
			return modifier;
		}
	}
	public static class KeyEvents {
		public static class Pressed implements IEvent {
			public int key;
			public Pressed(int key) {
				this.key = key;
			}
			public int key() {
				return key;
			}
			public IEvent set(int key) {
				this.key = key;
				return this;
			}
		}
		public static class Repeating implements IEvent {
			public int key;
			public Repeating(int key) {
				this.key = key;
			}
			public int key() {
				return key;
			}
			public IEvent set(int key) {
				this.key = key;
				return this;
			}
		}
		public static class Released implements IEvent {
			public int key;
			public Released(int key) {
				this.key = key;
			}
			public int key() {
				return key;
			}
			public IEvent set(int key) {
				this.key = key;
				return this;
			}
		}
		public static class Clicked implements IEvent {
			public int key;
			public Clicked(int key) {
				this.key = key;
			}
			public int key() {
				return key;
			}
			public IEvent set(int key) {
				this.key = key;
				return this;
			}
		}
	}
	@Override
	public void invoke(long arg0, int arg1, int arg2, int arg3, int arg4) {
		if(arg3 == GLFW_RELEASE) {
			BTGF.bus.post(new KeyEvents.Released(arg1));
			if(keys.getOrDefault(arg1, new KeyState(arg3, arg4)).get() != GLFW_RELEASE) {
				BTGF.bus.post(new KeyEvents.Clicked(arg1));
			}
		} else {
			if(arg3 == GLFW_PRESS) {
				BTGF.bus.post(new KeyEvents.Pressed(arg1));
			} else if(arg3 == GLFW_REPEAT) {
				BTGF.bus.post(new KeyEvents.Repeating(arg1));
			}
		}
		keys.put(arg1, new KeyState(arg3, arg4));
	}
	public static HashMap<?,?> getAllKeyStates(){
		return keys;
	}
	public static KeyState getKeyState(int key) {
		return keys.getOrDefault(key, new KeyState(GLFW_RELEASE, GLFW_KEY_UNKNOWN));
	}
	public static int getKey(int key) {
		return keys.getOrDefault(key, new KeyState(GLFW_RELEASE, GLFW_KEY_UNKNOWN)).get();
	}
	public static boolean isPressed(int key) {
		return (getKey(key) == GLFW_PRESS);
	}
	public static boolean isReleased(int key) {
		return (getKey(key) == GLFW_RELEASE);
	}
	public static boolean isRepeating(int key) {
		return (getKey(key) == GLFW_REPEAT);
	}
}
