package com.game.zombies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CollisionScreen extends ApplicationAdapter implements Screen, InputProcessor {

	final ZombieGame game;
	private OrthographicCamera camera;
	public BitmapFont font;
	private Body playerBody;
	private Body objectBody;
	private Texture texture;
	private Sprite playerSprite;
	private Sprite objectSprite;
	private SpriteBatch sb;
	Vector2 gravity = new Vector2(0,0);
	boolean doSleep = true;
	World world;
	Box2DDebugRenderer debugRenderer;
	final float PIXELS_TO_METERS = 100f;

	/**
	 * Sets up GameScreen
	 * Sets camera, batch and font,
	 * Sets up Player Sprite
	 */
	public CollisionScreen(final ZombieGame game) {

		this.game = game;
		//create camera + load texture
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280,720);
		
		game.batch = new SpriteBatch();
		game.font = new BitmapFont();
		
		setup();
		
		Box2D.init();
	}
	
	public void setup() {
		
		texture = new Texture(Gdx.files.internal("data/player.png"));
		playerSprite = new Sprite(texture);
		objectSprite = new Sprite(texture);
		sb = new SpriteBatch();
		world = new World(gravity,doSleep);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100, 300);
		
		BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        bodyDef2.position.set(400,300);

        objectBody = world.createBody(bodyDef2);
		playerBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerSprite.getWidth()/2, playerSprite.getHeight()/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		Fixture fixture = playerBody.createFixture(fixtureDef);
		
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = shape;
		Fixture fixture2 = objectBody.createFixture(fixtureDef2);
		
		shape.dispose();
		
		BodyDef objectBodyDef = new BodyDef();  
		objectBodyDef.position.set(new Vector2(0, 10));  

		Body groundBody = world.createBody(objectBodyDef);  

		PolygonShape groundBox = new PolygonShape();  
		groundBox.setAsBox(camera.viewportWidth, 10.0f);
		groundBody.createFixture(groundBox, 0.0f); 
		groundBox.dispose();
    
		ListenerClass lc = new ListenerClass();
		world.setContactListener(lc);
		
		Gdx.input.setInputProcessor(this);
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render(float delta) {
		camera.update();
		world.step(1f/60f, 6, 2);
		
		//Set screen colour
		Gdx.gl.glClearColor(0.5f,0.25f,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(camera.combined);

		sb.begin();
		playerSprite.setPosition(playerBody.getPosition().x, playerBody.getPosition().y);
		objectSprite.setPosition(objectBody.getPosition().x, objectBody.getPosition().y);
		playerSprite.draw(sb);
		objectSprite.draw(sb);
		sb.end();

		Movement();
		//boolean overlaps = player.overlaps(object);


	}
	
	public void Movement() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && (playerSprite.getY() < 1024 -64)) {
			playerBody.applyForceToCenter(0f,100f,true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (playerSprite.getY() > 0)) {
			playerBody.applyForceToCenter(0f,-100f,true);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (playerSprite.getX() > 0)) {
			playerBody.applyForceToCenter(-100f, 0f, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (playerSprite.getX() < 1600-64)) {
			playerBody.applyForceToCenter(100f,0f, true);
		}
		playerSprite.setPosition((playerBody.getPosition().x * PIXELS_TO_METERS) - playerSprite.
                getWidth()/2 , 
     (playerBody.getPosition().y * PIXELS_TO_METERS) -playerSprite.getHeight()/2 )
      ;
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}