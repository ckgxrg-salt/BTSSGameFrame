package co.btssstudio.m133;

import co.btssstudio.btgf.BTGFEvents.*;
import co.btssstudio.btgf.Shader;
import co.btssstudio.btgf.event.Listener;
import co.btssstudio.btgf.event.ListeningGroup;
import co.btssstudio.btgf.shapes.Rect;
import co.btssstudio.btgf.texture.Texture2D;
import co.btssstudio.btgf.util.Position;

import static org.lwjgl.opengl.GL40.*;

import org.joml.Matrix4f;

@ListeningGroup(busID = "BTGF")
public class Events {
	@Listener
	public static void InitListener(InitEvent event) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		Shader sha = new Shader("shader/general.vert", "shader/general.frag");
		sha.setAndUseProjection(new Matrix4f().ortho(0f, 800, 0f, 600, 0.1f, 100f));
		TestMain.object = new Rect.Builder().basePoint(new Position(330, 330, -20)).width(300).height(300).texture(new Texture2D("tex/test.png")).shader(sha).build();
		TestMain.object = TestMain.createAnObject();
	}
	@Listener
	public static void TickListener(TickEvent event) {
		
	}
	@Listener
	public static void RenderListener(RenderEvent event) {
		TestMain.object.draw();
	}
}
