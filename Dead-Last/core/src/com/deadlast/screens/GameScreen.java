package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * The screen responsible for displaying the game world and relevant elements
 * @author Xzytl
 *
 */
public class GameScreen extends DefaultScreen {
	
	/**
	 * The camera that the world is shown through
	 */
	private OrthographicCamera camera;
	private ExtendViewport gamePort;
	/**
	 * The SpriteBatch which renders game sprites
	 */
	private SpriteBatch batch;
	
	private GameManager gameManager = GameManager.getInstance(this.game);

	public GameScreen(DeadLast game) {
		super(game);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / DeadLast.PPM, Gdx.graphics.getHeight() / DeadLast.PPM);
		gamePort = new ExtendViewport(DeadLast.V_WIDTH / DeadLast.PPM, DeadLast.V_HEIGHT / DeadLast.PPM, camera);
		
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		gameManager.setGameCamera(camera);
		gameManager.setSpriteBatch(batch);
		
		gameManager.setGameRunning(true);
		
		gameManager.loadLevel();
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(gameManager.getController());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameManager.update(delta);
		gameManager.render();
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		gameManager.dispose();
	}

}
