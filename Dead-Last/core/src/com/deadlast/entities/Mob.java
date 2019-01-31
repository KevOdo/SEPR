package com.deadlast.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.deadlast.game.DeadLast;

import box2dLight.ConeLight;

/**
 * Represents a moving, 'living' entity
 * @author Xzytl
 *
 */
public abstract class Mob extends Entity {

	/**
	 * Normal maximum health of this mob.
	 */
	private int healthStat;
	/**
	 * Normal maximum speed of this mob.
	 */
	private int speedStat;
	/**
	 * Normal maximum strength of this mob.
	 */
	private int strengthStat;
	
	/**
	 * Current health of this mob.
	 */
	private int currentHealth;
	/**
	 * Current speed of this mob.
	 */
	private int currentSpeed;
	/**
	 * Current strength of this mob.
	 */
	private int currentStrength;
	/**
	 * The time until the mob can use it's attack ability again
	 */
	protected float attackCooldown = 0;
	
	protected ConeLight coneLight;
	
	public Mob(
			DeadLast game, int scoreValue, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat
	) {
		super(game, scoreValue, sprite, bRadius, initialPos);
		this.healthStat = healthStat;
		this.speedStat = speedStat;
		this.strengthStat = strengthStat;
		resetStats();
	}
	
	/**
	 * Sets the health of the mob.
	 * @param currentHealth		the health to which the mob should be set
	 */
	public void setHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Set the speed of the mob.
	 * @param currentSpeed		the speed to which the mob should be set
	 */
	public void setSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
	/**
	 * Set the strength of the mob.
	 * @param currentStrength	the strength to which the mob should be set
	 */
	public void setStrength(int currentStrength) {
		this.currentStrength = currentStrength;
	}
	
	public int getHealth() {
		return currentHealth;
	}
	
	public int getMaxHealth() {
		return healthStat;
	}
	
	public int getSpeed() {
		return currentSpeed;
	}
	
	public int getMaxSpeed() {
		return speedStat;
	}
	
	public int getStrength() {
		return currentStrength;
	}
	
	public int getMaxStrength() {
		return strengthStat;
	}
	
	/**
	 * Resets all mob stats to their default max values.
	 */
	public void resetStats() {
		this.currentHealth = healthStat;
		this.currentSpeed = speedStat;
		this.currentStrength = strengthStat;
	}

	@Override
	public abstract void defineBody();
//		BodyDef bDef = new BodyDef();
//		bDef.type = BodyDef.BodyType.DynamicBody;
//		bDef.position.set(initialPos);
//		
//		CircleShape shape = new CircleShape();
//		shape.setRadius(this.bRadius);
//		
//		FixtureDef fDef = new FixtureDef();
//		fDef.shape = shape;
//		
//		b2body = world.createBody(bDef);
//		b2body.createFixture(fDef);
//		b2body.setUserData(this);
//
//		shape.dispose();
	
	@Override
	public void update(float delta) {
		if (this.currentHealth <= 0 ) {
			this.alive = false;
			return;
		}
	};
	
	@Override
	public void delete() {
		super.delete();
		coneLight.remove(true);
	}
	
	/**
	 * Convenience method for applying damage
	 * @param damage the amount of damage to apply to the mob
	 */
	public void applyDamage(int damage) {
		if (this.currentHealth - damage < 0) {
			this.currentHealth = 0;
		} else {
			this.currentHealth -= damage;
		}
	}

}
