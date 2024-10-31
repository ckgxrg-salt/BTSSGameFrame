package co.btssstudio.btgf.texture;

import static org.lwjgl.opengl.GL40.*;

import java.io.File;
import java.nio.IntBuffer;

import co.btssstudio.btgf.util.BTGFUtil;

public class Texture3D {
	public int texID;
	public Texture3D(String path) {
		BTGFUtil.ImageData idata = BTGFUtil.readImageData(path);
		int[] data = idata.getData();
		texID = glGenTextures();
		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
		IntBuffer d = BTGFUtil.fillBufferWithInt(data);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, idata.getWidth(), idata.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, d);
		unbind();
	}
	public Texture3D(File f) {
		BTGFUtil.ImageData idata = BTGFUtil.readImageData(f);
		int[] data = idata.getData();
		texID = glGenTextures();
		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
		IntBuffer d = BTGFUtil.fillBufferWithInt(data);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, idata.getWidth(), idata.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, d);
		unbind();
	}
	public Texture3D bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
		return this;
	}
	public Texture3D unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
		return this;
	}
	public Texture3D genMipMap() {
		bind();
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_NEAREST);
		unbind();
		return this;
	}
}
