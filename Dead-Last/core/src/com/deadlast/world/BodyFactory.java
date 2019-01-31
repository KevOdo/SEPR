package com.deadlast.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.entities.Entity;

/**
 * Factory singleton for easy creation of {@link FixtureDef} and {@link Body} objects.
 * @author Xzytl
 *
 */
public class BodyFactory {
	
	private static BodyFactory instance;
	private World world;
	
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory getInstance(World world) {
		if (instance == null) {
			instance = new BodyFactory(world);
		}
		return instance;
	}
	
	public static FixtureDef makeFixture(int material, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		
		switch(material){
		case 0:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.3f;
			fixtureDef.restitution = 0.1f;
			break;
		case 1:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case 2:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 1f;
			break;
		case 3:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		
		return fixtureDef;
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType) {
		return makeCirclePolyBody(posx, posy, radius, material, bodyType, false);
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
		
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius / 2);
		boxBody.createFixture(makeFixture(material, circleShape));
		circleShape.dispose();
		
		return boxBody;
	}
	
	public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType, boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
		
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(width / 2, height / 2);
		boxBody.createFixture(makeFixture(material, polyShape));
		polyShape.dispose();
		
		return boxBody;
	}
	
	public Body makePolyShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		Body boxBody = world.createBody(boxBodyDef);
		
		PolygonShape polyShape = new PolygonShape();
		polyShape.set(vertices);
		boxBody.createFixture(makeFixture(material, polyShape));
		
		return boxBody;
	}
	
	/**
	 * Creates a cone-shaped {@link FixtureDef} sensor to be used as a melee range indicator
	 * @param body		the {@link Body} to add the fixture to
	 * @param points	the integer number of points to use in the arc (more is smoother);
	 * 					must be greater than 1 and less than 8
	 * @param angle		the f.o.v. angle
	 * @param radius	the radius of the sector
	 * @throws IllegalArgumentException	if points parameter is not 1 < x < 8
	 */
	public void makeMeleeSensor(Body body, int points, float angle, float radius) {
		if (points < 2 || points > 7) {
			throw new IllegalArgumentException("Points must be between 2 and 7 (inclusive)!");
		}
		FixtureDef fDef = new FixtureDef();
		PolygonShape polyShape = new PolygonShape();
		Vector2[] vertices = new Vector2[points + 1];
		
		vertices[0] = new Vector2(0,0);
		float startAngle;
		float subAngle = angle / (float)points;
		if (points % 2 == 0) {
			startAngle = (subAngle / 2) + (((points - 2) / 2) * subAngle) + 90;
		} else {
			startAngle = subAngle * ((points - 1) / 2) + 90;
		}
		for (int i = 0; i < points; i++) {
			double radAngle = Math.toRadians(startAngle);
			vertices[i+1] = new Vector2(radius * (float)Math.cos(radAngle), radius * (float)Math.sin(radAngle));
			startAngle -= subAngle;
		}
		polyShape.set(vertices);
		fDef.shape = polyShape;
		fDef.isSensor = true;
		fDef.filter.categoryBits = Entity.PLAYER_MELEE;
		fDef.filter.maskBits = Entity.ENEMY;
		body.createFixture(fDef).setUserData(FixtureType.MELEE_SENSOR);;
		polyShape.dispose();
	}
	
	/**
	 * Creates a cone-shaped {@link FixtureDef} sensor to be used as a field-of-view
	 * @param body		the {@link Body} to add the fixture to
	 * @param points	the integer number of points to use in the arc (more is smoother);
	 * 					must be greater than 1 and less than 8
	 * @param angle		the f.o.v. angle
	 * @param radius	the radius of the sector
	 * @throws IllegalArgumentException	if points parameter is not 1 < x < 8
	 */
	public void makeConeSensor(Body body, int points, float angle, float radius) throws IllegalArgumentException {
		if (points < 2 || points > 7) {
			throw new IllegalArgumentException("Points must be between 2 and 7 (inclusive)!");
		}
		FixtureDef fDef = new FixtureDef();
		PolygonShape polyShape = new PolygonShape();
		Vector2[] vertices = new Vector2[points + 1];
		
		vertices[0] = new Vector2(0,0);
		float startAngle;
		float subAngle = angle / (float)points;
		if (points % 2 == 0) {
			startAngle = (subAngle / 2) + (((points - 2) / 2) * subAngle) + 90;
		} else {
			startAngle = subAngle * ((points - 1) / 2) + 90;
		}
		for (int i = 0; i < points; i++) {
			double radAngle = Math.toRadians(startAngle);
			vertices[i+1] = new Vector2(radius * (float)Math.cos(radAngle), radius * (float)Math.sin(radAngle));
			startAngle -= subAngle;
		}
		polyShape.set(vertices);
		fDef.shape = polyShape;
		fDef.isSensor = true;
		fDef.filter.categoryBits = Entity.ENEMY_VISION;
		fDef.filter.maskBits = Entity.PLAYER;
		body.createFixture(fDef).setUserData(FixtureType.VISUAL_SENSOR);;
		polyShape.dispose();
	}
	
	/**
	 * Creates a circular {@link FixtureDef} sensor to be used as a hearing radius/general detection area
	 * @param body		the {@link Body} to add the fixture to
	 * @param radius	the radius of the circle
	 */
	public void makeHearingSensor(Body body, float radius) {
		FixtureDef fDef = new FixtureDef();
		CircleShape detectionShape = new CircleShape();
		detectionShape.setRadius(radius);
		fDef.shape = detectionShape;
		fDef.isSensor = true;
		fDef.filter.categoryBits = Entity.ENEMY_HEARING;
		fDef.filter.maskBits = Entity.PLAYER;
		body.createFixture(fDef).setUserData(FixtureType.HEARING_SENSOR);
		detectionShape.dispose();
	}

}
