package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.deadlast.game.DeadLast;

/**
 * The default screen for game views.
 * @author Xzytl
 *
 */
public abstract class DefaultScreen implements Screen {

	public final DeadLast game;
	
	/**
	 * Class constructor.
	 * Creates a screen with a reference to the {@link DeadLast} orchestrator class.
	 * @param game
	 */
	public DefaultScreen(DeadLast game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}
