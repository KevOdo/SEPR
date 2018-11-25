package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	final ZombieGame game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Sprite zom;
	private Texture zomIm;
	public BitmapFont font;

	/*
	 * Sets up GameScreen
	 * Sets camera, batch and font,
	 * Sets up Zombie Sprite
	 */
	public GameScreen(final ZombieGame game) {

		this.game = game;
		//create camera + load texture
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280,720);
		game.batch = new SpriteBatch();
		game.font = new BitmapFont();
		zomIm = new Texture("data/zomb.png");
		zom = new Sprite(zomIm);
		zom.setPosition(10, 10);
	}

	public void create() {

	}

	@Override
	public void render(float delta) {
		//Set screen colour
		Gdx.gl.glClearColor(0.5f,0.25f,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		Movement();

		game.batch.begin();
		game.font.draw(game.batch, "testing", 50,50);
		zom.draw(game.batch);
		game.batch.end();


	}

	//Checks input for zombie movement - will be moved to HumanoidClass
	public void Movement() {

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			zom.translateY(5f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			zom.translateY(-5f);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			zom.translateX(-5f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			zom.translateX(5f);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		zomIm.dispose();
		batch.dispose();
	}

}