package com.game.zombies;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameScreen extends ApplicationAdapter  implements Screen, InputProcessor {

	static ZombieGame game;
    private static TiledMap tiledMap;
    private OrthographicCamera camera;
    private SpriteBatch sb;
    private World world;
    private static TiledMapRenderer tiledMapRenderer;
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
	private float w = Gdx.graphics.getWidth();
	private float h = Gdx.graphics.getHeight();
    private static float playerPosX = 380;
    private static float playerPosY = 410;
    private float playerWidth = 10;
    private float playerHeight = 1;
    private float mapHeight = h - 320;
    private float mapWidth = w - playerWidth;
    private static float doorX;
    private static float doorY;
    private float doorWidth = 15;
    private float doorHeight = 1;
    private static String map;
    private static int mapSizeXlonger = 1600;
    private static int mapSizeXshorter = 1280;
    private static int mapSizeYhigher = 736;
    private static int mapSizeYlower = 736;
    private int mapPixelHeight;
    private int mapPixelWidth;

	public GameScreen(ZombieGame game, int charNum, String map, float doorX, float doorY) {
		this.game = game;
		this.map = map;
		Box2D.init();
        playerAnim = new Player(charNum);
        this.doorX = doorX;
        this.doorY = doorY;
	}

	public static String getMap(){
        return map;
    }
	
	public static void changeGame(int charNum, String map, float doorX, float doorY) {
        ListenerClass lc = new ListenerClass();
        game.setScreen(new GameScreen(game, charNum, map, doorX, doorY));
        changeMap(map);
		game.dispose();
	}

	public static void changeScreen(){
        game.setScreen(new FinalScreen(game));
    }
    
    public static void changeMap(String map) {
	    /*
	    Changes the map through the tiled api.
	    */
    	tiledMap = new TmxMapLoader().load(map);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public static void changeCamera(int xlong, int xshort, int yhigh, int ylow, float xpos, float ypos){
	    /*
	    Changes the player spawn location and camera clamping between the different locations.
	    */
        mapSizeXlonger = xlong;
        mapSizeXshorter = xshort;
        mapSizeYhigher = yhigh;
        mapSizeYlower = ylow;
        playerPosX = xpos;
        playerPosY = ypos;
    }

	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load(map);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		MapProperties prop = tiledMap.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
		System.out.println("Width = " + mapPixelWidth + " Height = " + mapPixelHeight);
		
		sb = new SpriteBatch();

        world = new World(gravity,doSleep);
        
        playerBody = BodyMaker.createBox(world, playerPosX, playerPosY, playerWidth, playerHeight, false, true);   
        doorBody = BodyMaker.createBox(world,doorX,doorY,doorWidth,doorHeight,true,true);
        
        int leftX = (int) ((mapSizeXlonger-1280/2)-(mapSizeXshorter/2) - playerWidth);
        int rightX = (int) (leftX + (mapSizeXlonger-1280/2) - 2*playerWidth);
        Edge = BodyMaker.createBox(world, leftX, 0, 0, mapPixelHeight, true, true);
        //Edge = BodyMaker.createBox(world, 0, 180, mapWidth, 0, true, true);
        //Edge = BodyMaker.createBox(world, 0, mapHeight, mapWidth, 0, true, true);
        Edge = BodyMaker.createBox(world, rightX, 0, 0, mapPixelHeight, true, true);
        
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

		camera.position.x = MathUtils.clamp(camera.position.x,mapSizeXshorter  / 2, mapSizeXlonger - 1280 /2);
		camera.position.y = MathUtils.clamp(camera.position.y, mapSizeYlower /2,  mapSizeYhigher - 720 /2);

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
        if(keycode == Input.Keys.SPACE) {
        	System.out.println("Player x= " + playerBody.getPosition().x);
        	System.out.println("Player y= " + playerBody.getPosition().y);
        	System.out.println("Edge x= " + Edge.getPosition().x);
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
