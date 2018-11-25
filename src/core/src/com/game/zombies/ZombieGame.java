package com.game.zombies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ZombieGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	boolean fullscreen = false;

	public void create() { //Runs when game is opened
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenu(this)); //Opens MainMenu Screen (New class)
	}

	public void render() {
		super.render();
		changeFullscreen();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void changeFullscreen() {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			if(fullscreen) {
				Gdx.graphics.setWindowedMode(1280, 720);
				fullscreen = false;
			}
			else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				fullscreen = true;
			}
		}
	}
}

