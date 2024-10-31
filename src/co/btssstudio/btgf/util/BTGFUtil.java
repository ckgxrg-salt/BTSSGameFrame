package co.btssstudio.btgf.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;

import co.btssstudio.btgf.BTGF;

public class BTGFUtil {
	public static String pathToString(String path) {
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			String s;
			while((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			BTGF.logger.logException(e, "Util");
		} catch (IOException e) {
			BTGF.logger.logException(e, "Util");
		}
		return "";
	}
	public static String fileToString(File f) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			String s;
			while((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			BTGF.logger.logException(e, "Util");
		} catch (IOException e) {
			BTGF.logger.logException(e, "Util");
		}
		return "";
	}
	public static class ImageData {
		public int[] imageData;
		public int width;
		public int height;
		public ImageData(int[] imageData, int w, int h) {
			this.imageData = imageData;
			width = w;
			height = h;
		}
		public int[] getData() {
			return imageData;
		}
		public int getWidth() {
			return width;
		}
		public int getHeight() {
			return height;
		}
	}
	public static ImageData readImageData(String path) {
		try {
			BufferedImage src = ImageIO.read(new FileInputStream(path));
			int width = src.getWidth();
			int height = src.getHeight();
			int[] pixels = new int[width * height];
			src.getRGB(0, 0, width, height, pixels, 0, width);
			int[] imageData = new int[width * height];
			for(int i = 0 ; i < imageData.length ; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				imageData[i] = a << 24 | b << 16 | g << 8 | r;
			}
			return new ImageData(imageData, width, height);
		} catch (IOException e) {
			BTGF.logger.logException(e, "Util");
		}
		return null;
	}
	public static ImageData readImageData(File f) {
		try {
			BufferedImage src = ImageIO.read(new FileInputStream(f));
			int width = src.getWidth();
			int height = src.getHeight();
			int[] pixels = new int[width * height];
			src.getRGB(0, 0, width, height, pixels, 0, width);
			int[] imageData = new int[width * height];
			for(int i = 0 ; i < imageData.length ; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				imageData[i] = a << 24 | b << 16 | g << 8 | r;
			}
			return new ImageData(imageData, width, height);
		} catch (IOException e) {
			BTGF.logger.logException(e, "Util");
		}
		return null;
	}
	public static ByteBuffer fillBufferWithByte(byte[] array) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		buffer.put(array).flip();
		return buffer;
	}
	public static FloatBuffer fillBufferWithFloat(float[] array) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(array).flip();
		return buffer;
	}
	public static IntBuffer fillBufferWithInt(int[] array) {
		IntBuffer buffer = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(array).flip();
		return buffer;
	}
	public static FloatBuffer matrixToFloatBuffer(Matrix4f mat) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(0, mat.m00());
		buffer.put(1, mat.m01());
		buffer.put(2, mat.m02());
		buffer.put(3, mat.m03());
		buffer.put(4, mat.m10());
		buffer.put(5, mat.m11());
		buffer.put(6, mat.m12());
		buffer.put(7, mat.m13());
		buffer.put(8, mat.m20());
		buffer.put(9, mat.m21());
		buffer.put(10, mat.m22());
		buffer.put(11, mat.m23());
		buffer.put(12, mat.m30());
		buffer.put(13, mat.m31());
		buffer.put(14, mat.m32());
		buffer.put(15, mat.m33());
		return buffer;
	}
	public static String decorateJSON(String json){
		StringBuilder content = new StringBuilder();
		StringBuilder prefix = new StringBuilder();
		char[] array = json.toCharArray();
		for(char c : array) {
			switch(c) {
			case '{' :
				prefix.append("\t");
				content.append(c+"\n"+prefix.toString());
				break;
			case ':' :
				prefix.append("\t");
				content.append(c+"\n"+prefix.toString());
				break;
			case ',' :
				prefix.delete(prefix.length() - 1, prefix.length());
				content.append(c+"\n"+prefix.toString());
				break;
			case '}' :
				prefix.delete(prefix.length() - 1, prefix.length());
				content.append("\n"+c);
				break;
			default :
				content.append(c);
			}
		}
		return content.toString();
	}
	public static LinkedList<File> getAllFiles(String path) {
		File now = new File(path);
		LinkedList<File> result = new LinkedList<File>();
		LinkedList<File> dirs = new LinkedList<File>();
		do {
			if(now.isFile()) {
				result.add(now);
				break;
			}
			File[] fs = now.listFiles();
			for(File f : fs) {
				if(f.isDirectory()) {
					dirs.add(f);
				} else {
					result.add(f);
				}
			}
			if(dirs.isEmpty()) break;
			now = dirs.getFirst();
			dirs.removeFirst();
		} while(now.isDirectory());
		return result;
	}
	public static String filePathToClassPath(String path) {
		path = path.split("\\.")[0];
		path = path.split("src\\\\")[1];
		char[] ch = path.toCharArray();
		StringBuilder result = new StringBuilder();
		for(char c : ch) {
			if(c == '\\') {
				c = '.';
			}
			result.append(c);
		}
		return result.toString();
	}
}
