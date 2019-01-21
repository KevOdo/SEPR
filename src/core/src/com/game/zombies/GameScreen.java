package com.game.zombies;

import java.util.Random;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    private static World world;
    private static TiledMapRenderer tiledMapRenderer;
    private static Player playerAnim;
    private static Powerup powerUp;
    private Box2DDebugRenderer debugRenderer;
    private float stateTime;
    private Body playerBody;
    private Body Edge;
    private Body doorBody;
    private static Body powerUpBody;
    private Body tableBody;
    private int leftX;
    private int rightX;
    private int topY;
    private int botY;
    private static String map;
    private int mapPixelHeight;
    private int mapPixelWidth;
    private static float doorX;
    private static float doorY;
    private float pUpPosX;
    private float pUpPosY;
    private TiledMapTileLayer collisionLayer;
    private static int player;

	final float PIXELS_TO_METERS = 100f;
    private Vector2 gravity = new Vector2(0,0);
	private boolean complete = false;
    private boolean doSleep = true;
	private float w = Gdx.graphics.getWidth();
	private float h = Gdx.graphics.getHeight();
    private static float playerPosX = 380;
    private static float playerPosY = 310;
    private float playerWidth = 20;
    private float playerHeight = 20;
    private float pUpWidth = 10;
    private float pUpHeight = 10;
    private float doorWidth = 15;
    private float doorHeight = 1;
    private static int mapSizeXlonger = 1600;
    private static int mapSizeXshorter = 1280;
    private static int mapSizeYhigher = 736;
    private static int mapSizeYlower = 736;

	public GameScreen(ZombieGame game, int charNum, String map, float doorX, float doorY) {
		/*
		 Initialises the GameScreen class
		 */
		this.game = game;
		this.map = map;
		Box2D.init();
        playerAnim = new Player(charNum);
        this.player = charNum;
        powerUp = new Powerup();
        this.doorX = doorX;
        this.doorY = doorY;
	}
	
	public static int getPlayer() {
		return player;
	}

	public static String getMap(){
        return map;
    }
	
	public static void changeGame(int charNum, String map, float doorX, float doorY) {
		/*
		 Changes the Level by setting a new Screen and changing the map
		 */
        ListenerClass lc = new ListenerClass();
        game.setScreen(new GameScreen(game, charNum, map, doorX, doorY));
        changeMap(map);
	}

	public static void changeScreen(){
		/*
		 * Changes the Screen to the winning Screen
		 */
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
		/*
		 The main method that generates the bodies in the world
		 */

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		
		tiledMap = new TmxMapLoader().load(map);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		sb = new SpriteBatch();

        world = new World(gravity,doSleep);
        collisionLayer = (TiledMapTileLayer)tiledMap.getLayers().get("overlay");
        
        if(map.equals("data/map_accom.tmx")) {
        	//the accomodation map has a different edge layout so edge coordiinates have to be more hardcoded
        	topY = (int) (440 - playerHeight);
        	botY = (int) (190 - playerHeight);
        	leftX = (int) (320 - playerWidth);
        	rightX = (int) (1280 - playerWidth);
        }
        else {
        	//sets edge coordinates based on map size
            topY = (int) (mapSizeYhigher - mapSizeYlower/2 - playerHeight); 
            rightX = (int) (mapSizeXlonger - (mapSizeXshorter/2) - playerWidth);
            leftX = (int) (mapSizeXshorter - 640 - playerWidth);
            botY = (int) (mapSizeYlower - 360 - playerHeight);
        }
        
        mapPixelHeight = topY - botY;
        mapPixelWidth = rightX - leftX;
        //creates edges in the map so the player doesn't go out of bounds
        Edge = BodyMaker.createBox(world, leftX, botY, 0, mapPixelHeight, true, true);//Left Edge
        Edge = BodyMaker.createBox(world, leftX, topY, mapPixelWidth, 0, true, true);//Top Edge 
        Edge = BodyMaker.createBox(world, leftX, botY, mapPixelWidth, 0, true, true);//Bottom Edge
        Edge = BodyMaker.createBox(world, rightX, botY, 0, mapPixelHeight, true, true);//Right Edge
        
        boolean done = false;
        while(!done){
        	//Creates a powerup in a random location, making sure it isn't on a table or collideable object
        	Random random = new Random();
            pUpPosX = leftX + random.nextInt(rightX-leftX);
            pUpPosY = botY + random.nextInt(topY-botY);
            int x = (int) pUpPosX/ (int) collisionLayer.getTileWidth();
            int y = (int) pUpPosY / (int) collisionLayer.getTileHeight();
    		TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
    		if(cell == null) {
    			done = true;
    		}
        };
        
        //Creates specific bodies in the world (player, door and powerup)
        playerBody = BodyMaker.createBox(world, playerPosX, playerPosY, playerWidth, playerHeight, false, true);   
        doorBody = BodyMaker.createBox(world,doorX,doorY,doorWidth,doorHeight,true,true);
        powerUpBody = BodyMaker.createBox(world, pUpPosX, pUpPosY, pUpWidth, pUpHeight, true, true);
        
        createTables();
        
        //setting the user data enables the contact listener class to call on it to check collisions.
        playerBody.setUserData("playerBody");
        doorBody.setUserData("doorBody");
        powerUpBody.setUserData("powerUpBody");        
        
        ListenerClass lc = new ListenerClass();
        world.setContactListener(lc);

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        stateTime = 0f;
	}
	
	public static void getPowerUp() {
		/*
		 When called on applies an effect on the player based on which powerup they pick up
		 */
		int power = powerUp.getPowerType();
		switch(power) {
		case 0:
			System.out.println("You got Speed");
			playerAnim.setSpeed(3);
			break;
		case 1:
			System.out.println("You got Strength");
			playerAnim.setDamage(50);
			break;
		case 2:
			System.out.println("You got Health");
			playerAnim.setHealth(50);
			break;
		}
		powerUp.setFlagDelete();		
	}
	
	public static void destroyBodies() {
		/*
		 Used to remove the powerup body when collected. Has to be called after the world step to avoid runtime errors
		 */
		if(powerUp.getFlag()) {
			world.destroyBody(powerUpBody);
			powerUpBody.setUserData(null);
			powerUpBody = null;
			powerUp.setFlagDelete();
		}
	}

	@Override
	public void render (float delta) {
		/*
		 This method is used to actually display sprites, animations and the map on the current GameScreen
		 */
		if(!complete){
			create();
			complete = true;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
        world.step(1f/60f, 6, 2);
        destroyBodies();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();

		//The game creates the playerBody first, then the player animation is drawn on top of that. The same goes for the power up
		stateTime += Gdx.graphics.getDeltaTime();
        sb.draw(playerAnim.getWalkAnimation().getKeyFrame(stateTime, true), playerBody.getPosition().x, playerBody.getPosition().y);
        if(powerUpBody != null) {
            sb.draw(powerUp.getPowerAni().getKeyFrame(stateTime, true), powerUpBody.getPosition().x, powerUpBody.getPosition().y);
        }
        
		sb.end();

		//setting the camera to follow the player and to be zoomed in
        camera.zoom = 0.5f;
		camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);

		camera.update();

		//The camera clamp ensures that when the camera is zoomed in it will still follow the player around, without going past the edge boundaries
		camera.position.x = MathUtils.clamp(camera.position.x,mapSizeXshorter  / 2, mapSizeXlonger - 1280 /2);
		camera.position.y = MathUtils.clamp(camera.position.y, mapSizeYlower /2,  mapSizeYhigher - 720 /2);


	}
	
	public void createTables() {
		/*
		 This method checks through the tiled Map for the locations of table tiles, then creates bodies in those locations
		 so that the player can't walk through/over them
		 */
		int x = (int) mapPixelWidth / (int) collisionLayer.getTileWidth();
        int y = (int) mapPixelHeight / (int) collisionLayer.getTileHeight();
        for(int i = 0; i < x; i++) {
        	for(int j = 0;j < y; j++) {
        		TiledMapTileLayer.Cell cell = collisionLayer.getCell(i, j);
                if (cell != null) {
                    if(cell.getTile().getProperties().containsKey("blocked")){
                        tableBody = BodyMaker.createBox(world, i*collisionLayer.getTileWidth(), j*collisionLayer.getTileHeight(), collisionLayer.getTileWidth() - 20, collisionLayer.getTileWidth() - 20, true, true);
                    }
                }
        	}
        }
        
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

    @Override
    public boolean keyDown(int keycode) {
    	/*
    	 Movement is achieved using physics forces, applied to the center of the body to avoid rotation.
    	 */
          if (keycode == Input.Keys.UP) {
            playerBody.applyForceToCenter(0f,playerAnim.getSpeed(),true);
          }
          if (keycode == Input.Keys.DOWN) {
            playerBody.applyForceToCenter(0f,-playerAnim.getSpeed(),true);
          }
          if (keycode == Input.Keys.LEFT) {
            playerBody.applyForceToCenter(-playerAnim.getSpeed(),0,true);
          }
          if (keycode == Input.Keys.RIGHT) {
            playerBody.applyForceToCenter(playerAnim.getSpeed(),0f,true);
          }
        return true;
	}

    @Override
    public boolean keyUp(int keycode) {
    	/*
    	 This method stops the player's movement when a key stops being pressed. It obtains the current forces acting in
    	 a certain direction on the body, and applies an impulse equal to the opposite of the current velocity in the axis
    	 */
	    if (keycode == Input.Keys.UP) {
	        playerBody.applyLinearImpulse(new Vector2(0f, -playerBody.getLinearVelocity().y), playerBody.getPosition(), true);
	    }
	    if (keycode == Input.Keys.DOWN) {
	        playerBody.applyLinearImpulse(new Vector2(0f, -playerBody.getLinearVelocity().y), playerBody.getPosition(), true);
	    }
	    if (keycode == Input.Keys.LEFT) {
	        playerBody.applyLinearImpulse(new Vector2(-playerBody.getLinearVelocity().x, 0f), playerBody.getPosition(), true);
	    }
	    if (keycode == Input.Keys.RIGHT) {
            playerBody.applyLinearImpulse(new Vector2(-playerBody.getLinearVelocity().x, 0f), playerBody.getPosition(), true);
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
