package com.deadlast.sensors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.entities.Entity;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;
import com.deadlast.world.FixtureType;

import box2dLight.PointLight;

public class LevelEndZone {
	
	@SuppressWarnings("unused")
	private DeadLast game;
	private World world;
	private Body b2body;
	
	private GameManager gameManager;
	
	private float x;
	private float y;
	private float height;
	private float width;

	public LevelEndZone(DeadLast game, float x, float y, float height, float width) {
		this.game = game;
		gameManager = GameManager.getInstance(game);
		this.world = gameManager.getWorld();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.StaticBody;
		bDef.position.set(new Vector2(x + width/2,y + height/2)); 
		
		FixtureDef fBodyDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(2f);
		fBodyDef.shape = shape;
		fBodyDef.filter.categoryBits = Entity.END_ZONE;
		fBodyDef.filter.maskBits = Entity.PLAYER;
		fBodyDef.isSensor = true;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fBodyDef).setUserData(FixtureType.END_ZONE);
		
		PointLight pointLight = new PointLight(gameManager.getRayHandler(), 10, Color.GREEN, 2f, x + width/2, y + height/2);
		pointLight.attachToBody(b2body);
		
		b2body.setUserData(this);
		shape.dispose();
	}

	public void delete() {
		world.destroyBody(this.b2body);
		b2body.setUserData(null);
		b2body = null;
	}


}
