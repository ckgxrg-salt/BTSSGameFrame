package co.btssstudio.btgf.util;

public class Position {
	public int x;
	public int y;
	public int z;
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public int z() {
		return z;
	}
}
