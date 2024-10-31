package co.btssstudio.btgf;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import co.btssstudio.btgf.util.BTGFUtil;

import static org.lwjgl.opengl.GL40.*;

public class Model {
	public float[] vertices, texCoords;
	public byte[] indices;
	
	public int VAO, VBO, TBO, IBO;
	
	public boolean hasIndices;
	
	public Model(float[] vertices, float[] texCoords) {
		hasIndices = false;
		this.vertices = vertices;
		this.texCoords = texCoords;
		FloatBuffer vBuffer = BTGFUtil.fillBufferWithFloat(this.vertices);
		FloatBuffer tBuffer = BTGFUtil.fillBufferWithFloat(this.texCoords);
		
		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
		
		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		TBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, TBO);
		glBufferData(GL_ARRAY_BUFFER, tBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	public Model(float[] vertices, float[] texCoords, byte[] indices) {
		hasIndices = true;
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.indices = indices;
		FloatBuffer vBuffer = BTGFUtil.fillBufferWithFloat(this.vertices);
		FloatBuffer tBuffer = BTGFUtil.fillBufferWithFloat(this.texCoords);
		ByteBuffer iBuffer = BTGFUtil.fillBufferWithByte(this.indices);
		
		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
		
		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		TBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, TBO);
		glBufferData(GL_ARRAY_BUFFER, tBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		IBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, iBuffer, GL_STATIC_DRAW);
		
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	public Model draw() {
		bind();
		if(hasIndices) 
			glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_BYTE, 0);
		else 
			glDrawArrays(GL_TRIANGLES, 0, vertices.length);
		unbind();
		return this;
	}
	public Model bind() {
		glBindVertexArray(VAO);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		if(hasIndices) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
		return this;
	}
	public Model unbind() {
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		if(hasIndices) glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		return this;
	}
}
