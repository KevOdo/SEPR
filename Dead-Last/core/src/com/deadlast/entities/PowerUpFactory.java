package com.deadlast.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

public class PowerUpFactory {
	
	private static PowerUpFactory instance;
	
	private DeadLast game;
	
	private PowerUpFactory(DeadLast game) {
		this.game = game;
	}
	
	public static PowerUpFactory getInstance(DeadLast game) {
		if (instance == null) {
			instance = new PowerUpFactory(game);
		}
		return instance;
	}
	
	public PowerUp.Builder get(PowerUp.Type type) {
		PowerUp.Builder builder = new PowerUp.Builder()
				.setGame(game)
				.setScoreValue(10)
				.setBodyRadius(0.25f)
				.setType(type);
		switch(type) {
		case REGEN:
			builder.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/regen_powerup.png"))));
			break;
		case DOUBLE_POINTS:
			builder.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/doublepoints_powerup.png"))));
			break;
		case SPEED:
			builder.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/speed_powerup.png"))));
			break;
		case COIN:
			builder.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/coin_powerup.png"))));
			break;
		case STEALTH:
			break;
		case DOUBLE_DAMAGE:
			break;
		default:
			builder.setSprite(new Sprite(new Texture(Gdx.files.internal("entities/blank_powerup.png"))));
			break;
		}
		return builder;
				
	}

}
