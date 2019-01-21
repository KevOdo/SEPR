package com.game.zombies;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyMaker {
	/*
	 BodyMaker takes parameters and uses them to create bodies in whichever world it's called from.
	 */
	
	public static Body createBox(final World world, float x, float y, float w, float h, boolean isStatic, boolean size) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x,y);
		
		if(isStatic) {
			bodyDef.type = BodyDef.BodyType.StaticBody;
		}
		else {
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		if(size) {
			shape.setAsBox(w, h);
		}
		fixtureDef.shape = shape;
		
		return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
	}

}