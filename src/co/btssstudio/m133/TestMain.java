package co.btssstudio.m133;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import co.btssstudio.btgf.*;
import co.btssstudio.btgf.texture.Texture2D;
import co.btssstudio.btgf.util.Window;

@MainClass
public class TestMain {
	@InternalProcessor(stage = StageType.PREINIT)
	public static void preInit() {
		BTGF.setBackgroundColor(100,100,100,100);
		BTGF.setWindow(new Window.Builder().hint(GLFW_RESIZABLE, GLFW_TRUE).hint(GLFW_CONTEXT_VERSION_MAJOR, 4).hint(GLFW_CONTEXT_VERSION_MINOR, 0)
				.width(800).height(600).title("BTGFTest").middlePos().swapInterval(1).build());
	}
	public static GameObject object;
	public static GameObject createAnObject() {
		float[] vert = {
				300f, 200f, 0f, //Left-Bottom-Front
				500f, 200f, 0f, //Right-Bottom-Front
				300f, 400f, 0f, //Left-Top-Front
				500f, 400f, 0f, //Right-Top-Front
				300f, 200f, -200f, //Left-Bottom-Behind
				500f, 200f, -200f, //Right-Bottom-Behind
				300f, 400f, -200f, //Left-Top-Behind
				500f, 400f, -200f //Right-Top-Behind
			};
			float[] texCoord = {
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f
			};
			byte indices[] = {
				2, 0, 1,
				1, 3, 2,
				6, 4, 5,
				5, 7, 6,
				6, 2, 3,
				3, 7, 6,
				4, 0, 1,
				1, 5, 4,
				6, 4, 0,
				0, 2, 6,
				7, 5, 1,
				1, 3, 7,
			};
			Model model = new Model(vert, texCoord, indices);
			Shader shader = new Shader("shader/general.vert", "shader/general.frag");
			Texture2D tex = new Texture2D("tex/test.png");
			GameObject object = new GameObject(model, tex, shader, null);
			object.setMat4("view", new Matrix4f());
			//object.projectionParamOrthographic(800f, 600f, 0.1f, 1000f);
			return object;
	}
}
