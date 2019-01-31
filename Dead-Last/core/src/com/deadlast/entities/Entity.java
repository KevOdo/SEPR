package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * Represents a dynamic object in the world.
 * @author Xzytl
 *
 */
public abstract class Entity {
	
	/**
	 * The instance of {@link DeadLast} this entity belongs to.
	 */
	protected DeadLast game;
	protected GameManager gameManager;
	/**
	 * The {@link World} this entity exists in.
	 */
	protected World world;
	/**
	 * The radius of this entity's body.
	 */
	protected float bRadius;
	/**
	 * The score modifier when this entity is killed or otherwise interacted with.
	 */
	protected int scoreValue;
	/**
	 * The {@link Body} that represents this entity in the {@link World}.
	 */
	protected Body b2body;
	/**
	 * The sprite that represents this entity in the world.
	 */
	protected Sprite sprite;
	/**
	 * The initial world position of this entity.
	 */
	protected Vector2 initialPos;
	/**
	 * Whether the entity is currently 'alive'
	 * For a mob, this is a literal description
	 * For pickups, it describes whether it has been destroyed by being picked up, etc
	 * This functions as a flag for the 'delete' method
	 */
	protected boolean alive = true;
	
	public static final int BOUNDARY = 0x0001;
	public static final int PLAYER = 0x0002;
	public static final int PLAYER_MELEE = 0x0004;
	public static final int ENEMY = 0x0008;
	public static final int ENEMY_VISION = 0x0010;
	public static final int ENEMY_HEARING = 0x0020;
	public static final int POWERUP = 0x0040;
	public static final int END_ZONE = 0x0080;
	
	
	/**
	 * Creates an entity with a score value and a specific sprite.
	 * @param game			the instance of the game the entity belongs to
	 * @param scoreValue	the score value given when this entity is interacted with
	 * @param sprite		the {@link Sprite} that represents this entity in the world
	 * @param bRadius		the radius of the circular body that represents this entity
	 * @param initialPos	the position the entity should spawn in the world
	 */
	public Entity(DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos) {
		this.game = game;
		gameManager = GameManager.getInstance(game);
		this.world = gameManager.getWorld();
		this.scoreValue = scoreValue;
		this.sprite = sprite;
		this.bRadius = bRadius;
		if (initialPos == null) {
			throw new IllegalArgumentException("Must provide an initialPosition argument.");
		}
		this.initialPos = initialPos;
		if (sprite != null) {
			sprite.setSize(bRadius * 2, bRadius * 2);
			sprite.setOrigin(bRadius, bRadius);
		}
	}
	
	/**
	 * Gets the body this entity represents.
	 * @return the {@link Body} this entity represents
	 */
	public Body getBody() {
		return b2body;
	}
	
	/**
	 * Convenience method to fetch entity position in the world
	 * @return the Vector2 representing the entity's world position
	 */
	public Vector2 getPos() {
		return this.b2body.getPosition();
	}
	
	/**
	 * Teleports the entity to the specified location. Be aware that it may cause issues with physics
	 * objects.
	 * @param x		The x coordinate of the destination
	 * @param y		The y coordinate of the destination
	 */
	public void moveTo(float x, float y) {
		b2body.setTransform(x, y, b2body.getAngle());
	}
	
	/**
	 * Rotates the entity by a specific number of degrees.
	 * @param angle		the number of degrees to rotate the body by
	 */
	public void setAngle(float angle) {
		b2body.setTransform(b2body.getPosition(), (float)Math.toRadians(angle));
	}
	
	public void setAngle(double angle) {
		setAngle((float)angle);
	}
	
	/**
	 * Removes the body from the world and destroys it.
	 * Do not call this method during world.step(), as it will cause errors.
	 */
	public void delete() {
		world.destroyBody(this.b2body);
		b2body.setUserData(null);
		b2body = null;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public int getScoreValue() {
		return scoreValue;
	}
	
	/**
	 * Defines this entity's body that exists in the world.
	 */
	public abstract void defineBody();
	
	/**
	 * Draws this entity's sprite in the correct place every render cycle.
	 * @param batch	the {@link SpriteBatch} used to draw the sprite
	 */
	public void render(SpriteBatch batch) {
		float posX = b2body.getPosition().x - bRadius;
		float posY = b2body.getPosition().y - bRadius;
		float rotation = (float) Math.toDegrees(b2body.getAngle());
		sprite.setPosition(posX, posY);
		sprite.setRotation(rotation);
		sprite.draw(batch);
	}
	
	public abstract void update(float delta);

}
