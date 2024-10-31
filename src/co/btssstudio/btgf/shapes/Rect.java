package co.btssstudio.btgf.shapes;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import co.btssstudio.btgf.GameObject;
import co.btssstudio.btgf.Model;
import co.btssstudio.btgf.Shader;
import co.btssstudio.btgf.texture.Texture2D;
import co.btssstudio.btgf.util.Position;

public class Rect extends GameObject {
	public static class Builder {
		Position pos;
		int width;
		int height;
		Texture2D tex;
		Shader shader;
		public Builder basePoint(Position pos) {
			this.pos = pos;
			return this;
		}
		public Builder width(int width) {
			this.width = width;
			return this;
		}
		public Builder height(int height) {
			this.height = height;
			return this;
		}
		public Builder texture(Texture2D tex) {
			this.tex = tex;
			return this;
		}
		public Builder shader(Shader shader) {
			this.shader = shader;
			return this;
		}
		public Rect build() {
			float[] vertices = {
					pos.x, pos.y, pos.z,                 //Left-Bottom
					pos.x + width, pos.y, pos.z,    //Right-Bottom
					pos.x, pos.y + height, pos.z,   //Left-Top
					pos.x + width, pos.y + height //Right-Top
			};
			float[] texCoords = {
					0f, 1f,
					1f, 1f,
					0f, 0f,
					1f, 0f
			};
			byte[] indices = {
					2, 0, 1,
					1, 3, 2
			};
			Rect result = new Rect(pos, width, height, new Model(vertices, texCoords, indices), tex, shader);
			result.setMat4("view", new Matrix4f());
			return result;
		}
	}
	public int width;
	public int height;
	public HashMap<Vector3f, Vector3f> positions = new HashMap<Vector3f, Vector3f>();
	private Rect(Position basePos, int width, int height, Model model, Texture2D tex, Shader shader) {
		super(model, tex, shader, basePos);
		this.width = width;
		this.height = height;
	}
}
