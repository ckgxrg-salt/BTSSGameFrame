package co.btssstudio.btgf;

import static org.lwjgl.opengl.GL40.*;

import java.io.File;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import co.btssstudio.btgf.util.BTGFUtil;

public class Shader {
	private int vertexShader;
	private int fragmentShader;
	private int program;
	private boolean shaderEnabled;
	public static Matrix4f projection = new Matrix4f();
	public Shader(String vertPath, String fragPath) {
		this(vertPath, fragPath, false);
	}
	public Shader(String vertCode, String fragCode, boolean codeDirectly) {
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		if(!codeDirectly) {
			glShaderSource(vertexShader, BTGFUtil.pathToString(vertCode));
			glShaderSource(fragmentShader, BTGFUtil.pathToString(fragCode));
		} else {
			glShaderSource(vertexShader, vertCode);
			glShaderSource(fragmentShader, fragCode);
		}
		glCompileShader(vertexShader);
		glCompileShader(fragmentShader);
		shaderEnabled = true;
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glLinkProgram(program);
		glValidateProgram(program);
	}
	public Shader(File vert, File frag) {
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertexShader, BTGFUtil.fileToString(vert));
		glShaderSource(fragmentShader, BTGFUtil.fileToString(frag));
		glCompileShader(vertexShader);
		glCompileShader(fragmentShader);
		shaderEnabled = true;
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glLinkProgram(program);
		glValidateProgram(program);
	}
	public Shader use() {
		glUseProgram(program);
		return this;
	}
	public Shader disable() {
		glUseProgram(0);
		return this;
	}
	public Shader releaseShaderMemory() {
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		shaderEnabled = false;
		return this;
	}
	@Override
	public void finalize() {
		releaseShaderMemory();
		glDeleteProgram(program);
	}
	public int getProgram() {
		return program;
	}
	public int getVertex() {
		if(!shaderEnabled) return -1;
		return vertexShader;
	}
	public int getFragment() {
		if(!shaderEnabled) return -1;
		return fragmentShader;
	}
	public int uniformLoc(String name) {
		return glGetUniformLocation(program, name);
	}
	public Shader uniformInt1(String name, int value){
		glUniform1i(uniformLoc(name), value);
		return this;
	}
	public Shader uniformMat4(String name, Matrix4f value){
		FloatBuffer fb = BTGFUtil.matrixToFloatBuffer(value);
	    glUniformMatrix4fv(uniformLoc(name), false, fb);
		return this;
	}
	public Shader setAndUseProjection(Matrix4f newProjection) {
		this.projection = newProjection;
		this.use();
		this.uniformMat4("projection", projection);
		this.disable();
		return this;
	}
}
