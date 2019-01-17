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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.graphics.g2d.Animation;

public class GameScreen extends ApplicationAdapter  implements Screen, InputProcessor {

	ZombieGame game;
	TiledMap tiledMap;
	OrthographicCamera camera;
	boolean complete = false;
	SpriteBatch sb;
	Texture texture;
	Sprite player;

	TiledMapRenderer tiledMapRenderer;

    private Body playerBody;
	Box2DDebugRenderer debugRenderer;
	final float PIXELS_TO_METERS = 100f;
    Vector2 gravity = new Vector2(0,0);
    boolean doSleep = true;
    World world;

    Player playerV;
    private float stateTime;


	public GameScreen(ZombieGame game, int charNum) {
		this.game = game;
		Box2D.init();
        playerV = new Player(charNum);
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
		texture = new Texture(Gdx.files.internal("core/assets/strong_character/Strong_Character0.png"));
		player = new Sprite(texture);

        world = new World(gravity,doSleep);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight() /2);

        playerBody = world.createBody(bodyDef);

        BodyDef objectBodyDef = new BodyDef();
        objectBodyDef.position.set(new Vector2(0, 0));

        float widthInMeters = 1600 - 64/ PIXELS_TO_METERS;
        float heightInMeters = 1024 - 64 / PIXELS_TO_METERS;
        Vector2 lowerLeftCorner = new Vector2(0,0);
        Vector2 lowerRightCorner  = new Vector2(widthInMeters,0);
        Vector2 upperLeftCorner  = new Vector2(0,heightInMeters);
        Vector2 upperRightCorner  = new Vector2(widthInMeters,heightInMeters);

        BodyDef screenBorderDef = new BodyDef();
        screenBorderDef.position.set(0,0);
        Body screenBorderBody = world.createBody(objectBodyDef);
        screenBorderBody.setType(BodyType.StaticBody);
        EdgeShape screenBorderShape = new EdgeShape();

        screenBorderShape.set(lowerLeftCorner,lowerRightCorner);
        screenBorderBody.createFixture(screenBorderShape,0);
        screenBorderShape.set(lowerRightCorner,upperRightCorner);
        screenBorderBody.createFixture(screenBorderShape,0);
        screenBorderShape.set(upperRightCorner,upperLeftCorner);
        screenBorderBody.createFixture(screenBorderShape,0);
        screenBorderShape.set(upperLeftCorner,lowerLeftCorner);
        screenBorderBody.createFixture(screenBorderShape,0);

        ListenerClass lc = new ListenerClass();
        world.setContactListener(lc);

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        stateTime = 0f;
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
        world.step(1f/60f, 6, 2);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();

        player.setPosition(playerBody.getPosition().x, playerBody.getPosition().y);
		player.draw(sb);

		stateTime += Gdx.graphics.getDeltaTime();
        sb.draw(playerV.getWalkAnimation().getKeyFrame(stateTime, true), 0, 0);

		sb.end();
		camera.position.set(player.getX(), player.getY(), 0);
		camera.update();

		camera.position.x = MathUtils.clamp(camera.position.x, 1280 / 2, 1600 - 1280 /2);
		camera.position.y = MathUtils.clamp(camera.position.y, 720 /2, 1024 - 720 /2);

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

        player.setPosition((playerBody.getPosition().x * PIXELS_TO_METERS) - player.getWidth()/2 ,
                (playerBody.getPosition().y * PIXELS_TO_METERS) -player.getHeight()/2 );
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
        player.setPosition((playerBody.getPosition().x * PIXELS_TO_METERS) - player.getWidth()/2 ,
                (playerBody.getPosition().y * PIXELS_TO_METERS) -player.getHeight()/2 );
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
