package com.deadlast.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deadlast.game.DeadLast;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dead Last";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new DeadLast(), config);
	}
}
