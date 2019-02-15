package com.deadlast.entities;

import com.deadlast.game.DeadLast;

/**
 * Factory/utility class for building instances of {@link Enemy}.
 * @author Xzytl
 *
 */
public class EnemyFactory {
	
	private static EnemyFactory instance;
	
	private DeadLast game;
	
	private EnemyFactory(DeadLast game) {
		this.game = game;
	}
	
	public static EnemyFactory getInstance(DeadLast game) {
		if (instance == null) {
			instance = new EnemyFactory(game);
		}
		return instance;
	}
	
	public Enemy.Builder get(Enemy.Type type) {
		Enemy.Builder builder = new Enemy.Builder()
				.setGame(game);
		switch(type) {
		case BOMBER:
			builder.setHealthStat(10)
					.setSpeedStat(6)
					.setStrengthStat(10)
					.setDetectionStat(9)
					.setSprite(null)
					.setScoreValue(50)
					.setBodyRadius(0.75f);
			break;
		case FAST:
			builder.setHealthStat(4)
				.setSpeedStat(10)
				.setStrengthStat(5)
				.setDetectionStat(7)
				.setSprite(null)
				.setScoreValue(20)
				.setBodyRadius(0.4f);
			break;
		case HEAVY:
			builder.setHealthStat(10)
				.setSpeedStat(6)
				.setStrengthStat(10)
				.setDetectionStat(9)
				.setSprite(null)
				.setScoreValue(50)
				.setBodyRadius(0.75f);
			break;
		case HORDLING:
			builder.setHealthStat(10)
					.setSpeedStat(6)
					.setStrengthStat(10)
					.setDetectionStat(9)
					.setSprite(null)
					.setScoreValue(50)
					.setBodyRadius(0.75f);
			break;
		case JOCKEY:
			builder.setHealthStat(10)
					.setSpeedStat(6)
					.setStrengthStat(10)
					.setDetectionStat(9)
					.setSprite(null)
					.setScoreValue(50)
					.setBodyRadius(0.75f);
			break;
		case NORMAL:
			builder.setHealthStat(10)
					.setSpeedStat(6)
					.setStrengthStat(10)
					.setDetectionStat(9)
					.setSprite(null)
					.setScoreValue(50)
					.setBodyRadius(0.75f);
			break;
		case BOSS:
			builder.setHealthStat(50)
					.setSpeedStat(2)
					.setStrengthStat(4)
					.setDetectionStat(7)
					.setSprite(null)
					.setScoreValue(100)
					.setBodyRadius(0.4f);
			break;
		default:
			break;
		
		}
		return builder;
	}

}
