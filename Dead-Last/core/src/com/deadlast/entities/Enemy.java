package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.world.BodyFactory;
import com.deadlast.world.FixtureType;
import com.deadlast.world.WorldContactListener;

import box2dLight.ConeLight;

/**
 * A hostile mob that will attempt to damage the player.
 * @author Xzytl
 *
 */
public class Enemy extends Mob {
	
	/**
	 * Types of enemy, used with {@link BodyFactory}.
	 * @author Xzytl
	 *
	 */
	public enum Type {
		HEAVY,
		FAST,
		BOMBER,
		HORDLING,
		JOCKEY,
		NORMAL
	}
	
	/**
	 * Determines how good the enemy is at detecting the player
	 */
	private int detectionStat;
	
	/**
	 * Whether the enemy is alerted the the player and knows where he/she is
	 */
	private boolean knowsPlayerLocation = false;
	/**
	 * Whether the player is close enough to the player to attack
	 */
	private boolean inMeleeRange = false;

	public Enemy(DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos,
			int healthStat, int speedStat, int strengthStat, int detectionStat) {
		super(game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat, strengthStat);
		this.detectionStat = detectionStat;
	}
	
	public int getDetectionStat() {
		return this.detectionStat;
	}
	
	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(initialPos);
		
		// The physical body of the enemy
		FixtureDef fBodyDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		fBodyDef.shape = shape;
		fBodyDef.filter.categoryBits = Entity.ENEMY;
		fBodyDef.filter.maskBits = Entity.BOUNDARY | Entity.PLAYER | Entity.PLAYER_MELEE;
		
		// Create body and add fixtures
		b2body = world.createBody(bDef);
		b2body.createFixture(fBodyDef).setUserData(FixtureType.ENEMY);
		
		BodyFactory bFactory = BodyFactory.getInstance(world);
		bFactory.makeConeSensor(b2body, 7, 70, 5f);
		bFactory.makeHearingSensor(b2body, this.bRadius + ((0.1f * (float)this.detectionStat)) + 0.5f);
		
		coneLight = new ConeLight(gameManager.getRayHandler(), 7, Color.RED, 6, b2body.getPosition().x, b2body.getPosition().y, b2body.getAngle() + 90, 35);
		coneLight.attachToBody(b2body, 0, 0, 90);
		
		b2body.setUserData(this);

		shape.dispose();
		
		b2body.setLinearDamping(5.0f);
	}
	
	/**
	 * Called by {@link WorldContactListener} when the player enters a sensor
	 * @param body
	 */
	public void beginDetection(Body body) {
		this.knowsPlayerLocation = true;
	}
	
	/**
	 * Called by {@link WorldContactListener} when the player exits sensor range
	 * @param body
	 */
	public void endDetection(Body body) {
		this.knowsPlayerLocation = false;
	}
	
	/**
	 * Called by {@link WorldContactListener} when the player is in contact
	 * @param body
	 */
	public void beginContact(Body body) {
		this.inMeleeRange = true;
		b2body.setSleepingAllowed(false);
	}
	
	/**
	 * Called by {@link WorldContactListener} when the player leaves contact
	 * @param body
	 */
	public void endContact(Body body) {
		this.inMeleeRange = false;
		b2body.setSleepingAllowed(true);
	}

	public void followPlayer(){
		Vector2 playerLoc = gameManager.getPlayer().getPos();
		this.b2body.setLinearVelocity((playerLoc.x - b2body.getPosition().x), (playerLoc.y - b2body.getPosition().y));
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (this.getHealth() <= 0) {
			this.setAlive(false);
			return;
		}
		if (knowsPlayerLocation) {
			Vector2 playerLoc = gameManager.getPlayer().getPos();
			double angle = Math.toDegrees(Math.atan2(playerLoc.y - b2body.getPosition().y, playerLoc.x - b2body.getPosition().x)) - 90;
			this.setAngle(angle);
			followPlayer();
		}
		if (inMeleeRange) {
			if (attackCooldown == 0) {
				Player player = gameManager.getPlayer();
				player.applyDamage(this.getStrength());
				attackCooldown = (float) ((-0.5 * (this.getSpeed())) + 3);
			}
		}
		if (attackCooldown - delta <= 0) {
			attackCooldown = 0;
		} else {
			attackCooldown -= delta;
		}
	}
	
	/**
	 * Utility for building Enemy instances.
	 * @author Xzytl
	 *
	 */
	public static class Builder {

		private DeadLast game;
		private int scoreValue;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private int healthStat;
		private int speedStat;
		private int strengthStat;
		private int detectionStat;
		
		public Builder setGame(DeadLast game) {
			this.game = game;
			return this;
		}
		
		public Builder setScoreValue(int scoreValue) {
			this.scoreValue = scoreValue;
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
		
		public Builder setDetectionStat(int detectionStat) {
			this.detectionStat = detectionStat;
			return this;
		}
		
		/**
		 * Converts builder object into instance of Enemy
		 * @return an instance of Enemy with the provided parameters
		 * @throws IllegalArgumentException if required parameters are not provided
		 */
		public Enemy build() {
			// Ensure variables are not undefined
			// Note that primitive's are initialised as zero by default
			if (game == null) {
				throw new IllegalArgumentException("Invalid 'game' parameter");
			}
			if (sprite == null) {
				sprite = new Sprite(new Texture(Gdx.files.internal("entities/zombie.png")));
			}
			if (bRadius == 0) {
				bRadius = 0.5f;
			}
			if (initialPos == null) {
				throw new IllegalArgumentException("Invalid 'initialPos' parameter");
			}
			return new Enemy(
					game, scoreValue, sprite, bRadius, initialPos, healthStat, speedStat,
					strengthStat, detectionStat
			);
		}
	}

}
