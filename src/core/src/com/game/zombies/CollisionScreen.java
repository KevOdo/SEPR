package com.game.zombies;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class CollisionScreen extends ApplicationAdapter implements Screen {

	ZombieGame game;
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	boolean complete = false;
	SpriteBatch sb;
	Texture texture;
	Sprite player;
	private FitViewport viewport;
	TiledMapRenderer tiledMapRenderer;

	public CollisionScreen(ZombieGame game) {
		this.game = game;

	}

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();



		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("data/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


		sb = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("data/player.png"));
		player = new Sprite(texture);
		player.setPosition(Gdx.graphics.getWidth()/2 - 32, Gdx.graphics.getHeight()/2 - 32);

	}

	@Override
	public void render (float delta) {
		if(complete == false){
			create();
			complete = true;
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();

		player.draw(sb);

		sb.end();
		camera.position.set(player.getX(), player.getY(), 0);
		camera.update();
		Movement();
		camera.position.x = MathUtils.clamp(camera.position.x, 1280 / 2, 1600 - 1280 /2);
		camera.position.y = MathUtils.clamp(camera.position.y, 720 /2, 1024 - 720 /2);

	}


	public void Movement() {
			if (Gdx.input.isKeyPressed(Input.Keys.UP) && (player.getY() < 1024 -64)) {
				player.translateY(2);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (player.getY() > 0)) {
				player.translateY(-2);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.getX() > 0)) {
				player.translateX(-2);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.getX() < 1600-64)) {
				player.translateX(2);
			}
		}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}
}