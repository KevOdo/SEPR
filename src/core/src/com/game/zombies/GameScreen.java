package com.game.zombies;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameScreen extends ApplicationAdapter  implements Screen, InputProcessor {

	ZombieGame game;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private SpriteBatch sb;
    private World world;
    private TiledMapRenderer tiledMapRenderer;
    private Player playerAnim;
    private Box2DDebugRenderer debugRenderer;
    private float stateTime;
    private Body playerBody;
    private Body Edge;
    private Body doorBody;

	final float PIXELS_TO_METERS = 100f;
    private Vector2 gravity = new Vector2(0,0);
	private boolean complete = false;
    private boolean doSleep = true;
    private int playerPosX = Gdx.graphics.getWidth() /2;
    private int playerPosY = Gdx.graphics.getHeight() /2;
    private float playerWidth = 1;
    private float playerHeight = 1;
    private float mapHeight = 600;
    private float mapWidth = 1000;



	public GameScreen(ZombieGame game, int charNum) {
		this.game = game;
		Box2D.init();
        playerAnim = new Player(charNum);
	}

    public void changeMap(){
        tiledMap = new TmxMapLoader().load("data/map_compsci.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

	@Override
	public void create () {

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("data/map_accom.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


		sb = new SpriteBatch();

        world = new World(gravity,doSleep);
        
        playerBody = BodyMaker.createBox(world, playerPosX, playerPosY, playerWidth, playerHeight, false, true);      
        Edge = BodyMaker.createBox(world, 0, 0, 0, mapHeight, true, true);
        Edge = BodyMaker.createBox(world, 0, 0, mapWidth, 0, true, true);
        Edge = BodyMaker.createBox(world, 0, mapHeight, mapWidth, 0, true, true);
        Edge = BodyMaker.createBox(world, mapWidth, 0, 0, mapHeight, true, true);
        doorBody = BodyMaker.createBox(world,100,100,50,50,true,true);
        
        playerBody.setUserData("playerBody");
        doorBody.setUserData("doorBody");
        
        ListenerClass lc = new ListenerClass();
        world.setContactListener(lc);

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        stateTime = 0f;
	}

	@Override
	public void render (float delta) {
		if(!complete){
			create();
			complete = true;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
        world.step(1f/60f, 6, 2);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();

		stateTime += Gdx.graphics.getDeltaTime();
        sb.draw(playerAnim.getWalkAnimation().getKeyFrame(stateTime, true), playerBody.getPosition().x, playerBody.getPosition().y);

		sb.end();

        camera.zoom = 0.5f;
		camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);

		camera.update();

		camera.position.x = MathUtils.clamp(camera.position.x, 1280 / 2, 1600 - 1280 /2);
		camera.position.y = MathUtils.clamp(camera.position.y, 720 /2, 736 - 720 /2);

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            playerBody.applyForceToCenter(0f,5000f,true);
        }
        if (keycode == Input.Keys.DOWN) {
            playerBody.applyForceToCenter(0f,-5000f,true);
        }
        if (keycode == Input.Keys.LEFT) {
            playerBody.applyForceToCenter(-5000f,0,true);
        }
        if (keycode == Input.Keys.RIGHT) {
            playerBody.applyForceToCenter(5000f,0f,true);
        }
        return true;
	}

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP){
            playerBody.applyLinearImpulse(new Vector2(0f,-playerBody.getLinearVelocity().y),playerBody.getPosition(),true);
        }
        if (keycode == Input.Keys.DOWN){
            playerBody.applyLinearImpulse(new Vector2(0f, -playerBody.getLinearVelocity().y),playerBody.getPosition(),true);
        }
        if (keycode == Input.Keys.LEFT){
            playerBody.applyLinearImpulse(new Vector2(-playerBody.getLinearVelocity().x, 0f),playerBody.getPosition(),true);
        }
        if (keycode == Input.Keys.RIGHT){
            playerBody.applyLinearImpulse(new Vector2(-playerBody.getLinearVelocity().x, 0f),playerBody.getPosition(),true);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
