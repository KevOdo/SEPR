package com.deadlast.entities;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;
import com.deadlast.stages.Hud;
import com.deadlast.world.BodyFactory;
import com.deadlast.world.FixtureType;
import com.deadlast.world.WorldContactListener;

import box2dLight.ConeLight;

/**
 * This class represents the player character.
 * @author Xzytl
 *
 */
public class Player extends Mob {
	
	private int stealthStat;
	
	/**
	 * Represents whether the zombies on the map are aware of the player by default.
	 * Currently unimplemented.
	 */
	@SuppressWarnings("unused")
	private boolean isHidden;

	private boolean attkCooldown = false;
	
	/**
	 * Whether the player is attempting to use their attack ability.
	 */
	private boolean isAttacking;
	
	/**
	 * Contains the power-ups currently active on the player.
	 * Float is the number of seconds remaining until the effect expires.
	 */
	private Map<PowerUp.Type, Float> activePowerUps;
	
	/**
	 * The time until the player can next be healed by a regen power-up.
	 */
	private float healCooldown = 1f;
	
	/**
	 * The time until the player can next use the attack ability.
	 */
	private float attackCooldown = 0f;
	
	/**
	 * Contains the enemies currently in range and in front of the player that will be
	 * damaged when the attack ability is used.
	 */
	private Set<Enemy> enemiesInRange;

	private Hud hud;
	
	/**
	 * Default constructor
	 * @param game			a reference to the DeadLast game instance
	 * @param sprite		the graphical sprite to use
	 * @param bRadius		the radius of the player's hitbox circle
	 * @param initialPos	the initial position to place the player
	 * @param healthStat	the player's normal health
	 * @param speedStat		the player's normal speed
	 * @param strengthStat	the player's normal strength
	 * @param stealthStat	the player's normal stealth level
	 */
	public Player(
			DeadLast game, Sprite sprite, float bRadius,
			Vector2 initialPos, int healthStat, int speedStat, int strengthStat, int stealthStat
	) {
		super(game, 0, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.stealthStat = stealthStat;
		this.isHidden = true;
		this.activePowerUps = new ConcurrentHashMap<>();
		this.enemiesInRange = new HashSet<>();
		hud = new Hud(game);
	}
	
	public int getStealthStat() {
		return this.stealthStat;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void isAttacking(boolean bool) {
		this.isAttacking = bool;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		Vector2 mousePos = GameManager.getInstance(this.game).getMouseWorldPos();
		double angle = Math.toDegrees(Math.atan2(mousePos.y - b2body.getPosition().y, mousePos.x - b2body.getPosition().x));
		this.setAngle(angle - 90);
		super.render(batch);
	}
	
	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(initialPos);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.filter.categoryBits = Entity.PLAYER;
		fDef.filter.maskBits = Entity.BOUNDARY | Entity.ENEMY | Entity.POWERUP | Entity.ENEMY_HEARING | Entity.ENEMY_VISION | Entity.END_ZONE;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef).setUserData(FixtureType.PLAYER);
		
		BodyFactory bFactory = BodyFactory.getInstance(world);
		bFactory.makeMeleeSensor(b2body, 7, 50, 1f);
		
		coneLight = new ConeLight(gameManager.getRayHandler(), 7, Color.BLUE, 2, b2body.getPosition().x, b2body.getPosition().y, b2body.getAngle() + 90, 35);
		coneLight.attachToBody(b2body, 0, 0, 90);
		
		b2body.setUserData(this);
		b2body.setSleepingAllowed(false);

		shape.dispose();
	}
	
	/**
	 * Called by {@link WorldContactListener} when the player makes contact with a power-up.
	 * @param powerUp the power-up the user obtained
	 */
	public void onPickup(PowerUp powerUp) {
		activePowerUps.put(powerUp.getType(), 15f);
	}
	
	/**
	 * Called by {@link WorldContactListener} when an enemy enters the player's effective melee zone.
	 * @param enemy
	 */
	public void onMeleeRangeEntered(Enemy enemy) {
		this.enemiesInRange.add(enemy);
	}
	
	/**
	 * Called by {@link WorldContactListener} when an enemy leaves the player's effective melee zone.
	 * @param enemy
	 */
	public void onMeleeRangeLeft(Enemy enemy) {
		this.enemiesInRange.remove(enemy);
	}
	
	/**
	 * Convenience method to check whether a player has a particular active power-up
	 * @param type
	 * @return
	 */
	public boolean isPowerUpActive(PowerUp.Type type) {
		return activePowerUps.containsKey(type);
	}
	
	public void onEndZoneReached() {
		gameManager.levelComplete();
	}
	
	@Override
	public void update(float delta) {
		if (isPowerUpActive(PowerUp.Type.REGEN)) {
			healCooldown -= delta;
			if (healCooldown <= 0 && this.getHealth() < this.getMaxHealth()) {
				this.setHealth(this.getHealth() + 1);
				healCooldown = 1f;
			} 
		}
		for(Map.Entry<PowerUp.Type, Float> entry : activePowerUps.entrySet()) {
			if (entry.getValue() - delta >= 0) {
				activePowerUps.put(entry.getKey(), entry.getValue() - delta);
			} else {
				activePowerUps.remove(entry.getKey());
			}
		}
		if(attkCooldown){
			if(attackCooldown - delta <= 0){
				this.hud.setCooldown(false);
				attkCooldown = false;
			} else {
				attackCooldown -= delta;
			}
		}
		if (isAttacking) {
			if (!attkCooldown) {
				enemiesInRange.forEach(e -> e.applyDamage(this.getStrength()));
				attackCooldown = 1f;
				this.hud.setCooldown(true);
				attkCooldown = true;
			}
		}
	}

	public boolean getCooldown(){return this.attkCooldown;}
	
	public static class Builder {
		
		private DeadLast game;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private int healthStat;
		private int speedStat;
		private int strengthStat;
		private int stealthStat;

		public Builder setGame(DeadLast game) {
			this.game = game;
			return this;
		}
		
		public Builder setSprite(Sprite sprite) {
			this.sprite = sprite;
			return this;
		}
		
		public Builder setBodyRadius(float bRadius) {
			this.bRadius = bRadius;
			return this;
		}
		
		public Builder setInitialPosition(Vector2 initialPos) {
			this.initialPos = initialPos;
			return this;
		}
		
		public Builder setHealthStat(int healthStat) {
			this.healthStat = healthStat;
			return this;
		}
		
		public Builder setSpeedStat(int speedStat) {
			this.speedStat = speedStat;
			return this;
		}
		
		public Builder setStrengthStat(int strengthStat) {
			this.strengthStat = strengthStat;
			return this;
		}
		
		public Builder setStealthStat(int stealthStat) {
			this.stealthStat = stealthStat;
			return this;
		}
		
		public Player build() {
			return new Player(
				game, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat, stealthStat
			);
		}

	}

}
