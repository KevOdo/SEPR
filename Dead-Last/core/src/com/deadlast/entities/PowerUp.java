package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deadlast.game.DeadLast;
import com.deadlast.world.FixtureType;

public class PowerUp extends Entity {
	
	private Type type;
	
	public PowerUp(DeadLast game, int scoreValue, Sprite sprite, float bRadius, Vector2 initialPos, Type type) {
		super(game, scoreValue, sprite, bRadius, initialPos);
		this.type = type;
	}

	public enum Type {
		STEALTH,
		DOUBLE_DAMAGE,
		DOUBLE_POINTS,
		REGEN,
		SPEED,
		COIN
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public void defineBody() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.StaticBody;
		bDef.position.set(initialPos);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(this.bRadius);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.filter.categoryBits = Entity.POWERUP;
		fDef.filter.maskBits = Entity.PLAYER;
		
		b2body = world.createBody(bDef);
		b2body.createFixture(fDef).setUserData(FixtureType.POWERUP);
		b2body.setUserData(this);

		shape.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Builder {
		
		private DeadLast game;
		private int scoreValue;
		private Sprite sprite;
		private float bRadius;
		private Vector2 initialPos;
		private Type type;
		
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
		
		public Builder setType(Type type) {
			this.type = type;
			return this;
		}
		
		/**
		 * Converts builder object into instance of PowerUP
		 * @return an instance of PowerUp with the provided parameters
		 * @throws IllegalArgumentException if mandatory parameters are not set
		 */
		public PowerUp build() {
			if (game == null) {
				throw new IllegalArgumentException("Invalid 'game' parameter");
			}
			if (type == null) {
				throw new IllegalArgumentException("Invalid 'type' parameter");
			}
			if (sprite == null) {
				sprite = new Sprite(new Texture(Gdx.files.internal("entities/blank_powerup.png")));
			}
			if (bRadius == 0) {
				bRadius = 0.25f;
			}
			if (initialPos == null) {
				throw new IllegalArgumentException("Invalid 'initialPos' parameter");
			}
			return new PowerUp(game, scoreValue, sprite, bRadius, initialPos, type);
		}
		
	}

}
