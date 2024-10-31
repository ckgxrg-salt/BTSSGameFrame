package co.btssstudio.btgf;

import org.joml.Matrix4f;

import co.btssstudio.btgf.texture.Texture2D;
import co.btssstudio.btgf.util.Position;

public class GameObject {
	public Position pos;
	public Texture2D tex;
	public Shader shader;
	public Model model;
	public Matrix4f transform = new Matrix4f();
	public GameObject(Model model, Texture2D texture, Shader shader, Position pos) {
		this.model = model;
		this.tex = texture;
		this.shader = shader;
		this.pos = pos;
		applyTransform();
	}
	/*public static void projectionParamOrthographic(float right, float top, float near, float far) {
		BTGF.projection.ortho(0f, right, 0f, top, near, far);
		setMat4("projection", BTGF.projection);
	}
	public static void projectionParamPerspective(float fov, float near, float far) {
		.perspective(Math.toRadians(fov), (BTGF.activeWindow.width() / BTGF.activeWindow.height()), near, far);
		//projection.perspectiveRect(40, 30, near, far);
	}*/
	public GameObject draw() {
		shader.use();
		shader.uniformMat4("transform", transform);
		tex.bind();
		model.draw();
		tex.unbind();
		shader.disable();
		return this;
	}
	/**
	 * Remember, there must a Uniform mat4 in your shader.
	 */
	public GameObject setMat4(String name, Matrix4f matrix) {
		shader.use();
		shader.uniformMat4(name, matrix);
		shader.disable();
		return this;
	}
	public GameObject applyTransform() {
		setMat4("transform", transform);
		return this;
	}
	public GameObject translate(float x, float y, float z) {
		transform.translate(x, y, z);
		return this;
	}
	public GameObject scale(float x, float y, float z) {
		transform.scale(x, y, z);
		return this;
	}
	public GameObject scale(float xyz) {
		transform.scale(xyz);
		return this;
	}
	public GameObject rotate(float angle, float x, float y, float z) {
		transform.rotate(angle, x, y, z);
		return this;
	}
	/**
	 * If you need advanced operations, call this method and it will return this object's transform matrix.
	 * @return The transform matrix of current game object.
	 */
	public Matrix4f transformMatrix() {
		return transform;
	}
	/**
	 * Usually, changed transform matrix provided by {@link #transformMatrix()} will automatic apply new value.
	 * However, if it doesn't work, call this method set the value manually.
	 */
	public GameObject transformMatrix(Matrix4f transform) {
		this.transform = transform;
		return this;
	}
}
