package com.game.zombies.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.zombies.ZombieGame;



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Depressed Discipline - Zombie Game";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new ZombieGame(), config);
	}
}
