package com.deadlast.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.deadlast.entities.Enemy;
import com.deadlast.entities.PowerUp;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;
import com.deadlast.sensors.LevelEndZone;

/**
 * Represents a level in the game
 * Holds spawn locations from the player, zombies, and power-ups
 * @author ljd546
 * @author Xzytl
 */
public class Level implements Disposable {
	
	private DeadLast game;
	private Vector2 playerSpawn;
	private List<SpawnPoint<Enemy.Type>> enemySpawns;
	private List<SpawnPoint<PowerUp.Type>> powerUpSpawns;
	
	private GameManager gameManager;
	
	private String levelName;
	private TiledMap tiledMap;
	private int[] backgroundLayers = {0,1,2,3};
	private int[] foregroundLayers = {};
	
	private LevelEndZone endZone;
	
	public Level(DeadLast game, String levelName) {
		this.game = game;
		this.levelName = levelName;
		this.gameManager = GameManager.getInstance(game);
		enemySpawns = new ArrayList<>();
		powerUpSpawns = new ArrayList<>();
		parseMap();
	}
	
	public void parseMap() {
		tiledMap = new TmxMapLoader().load("maps/" + levelName + ".tmx");
		MapLayer objLayer = tiledMap.getLayers().get("Objects");
		objLayer.setVisible(false);
		MapObjects objects = objLayer.getObjects();
		RectangleMapObject playerSpawnObj = (RectangleMapObject) objects.get("SpawnPoint");
		RectangleMapObject levelExitObj = (RectangleMapObject) objects.get("ExitPoint");
		
		MapLayer entityLayer = tiledMap.getLayers().get("Entities");
		entityLayer.setVisible(false);
		MapObjects entities = entityLayer.getObjects();
		
		entities.forEach(e -> {
			MapProperties props = e.getProperties();
			String entityClass = props.get("entityClass", String.class);
			String entityType = props.get("entityType", String.class);
			float x = props.get("x", Float.class) / 32;
			float y = props.get("y", Float.class) / 32;
			
			if (entityClass.equals("Enemy")) {
				switch(entityType) {
				case "FAST":
					enemySpawns.add(new SpawnPoint<Enemy.Type>(Enemy.Type.FAST, new Vector2(x,y)));
					break;
				default:
					break;
				}
			} else if (entityClass.equals("PowerUp")) {
				switch(entityType) {
				case "DOUBLE_POINTS":
					powerUpSpawns.add(new SpawnPoint<PowerUp.Type>(PowerUp.Type.DOUBLE_POINTS, new Vector2(x,y)));
					break;
				case "SPEED":
					powerUpSpawns.add(new SpawnPoint<PowerUp.Type>(PowerUp.Type.SPEED, new Vector2(x,y)));
					break;
				case "REGEN":
					powerUpSpawns.add(new SpawnPoint<PowerUp.Type>(PowerUp.Type.REGEN, new Vector2(x,y)));
					break;
                case "COIN":
                    powerUpSpawns.add(new SpawnPoint<PowerUp.Type>(PowerUp.Type.COIN, new Vector2(x,y)));
				default:
					break;
				}
			} else {
				return;
			}
		});
		float playerSpawnX = playerSpawnObj.getRectangle().x;
		float playerSpawnY = playerSpawnObj.getRectangle().y;
		playerSpawn = new Vector2(playerSpawnX / 32, playerSpawnY / 32);
		
		float endZoneX = levelExitObj.getRectangle().x;
		float endZoneY = levelExitObj.getRectangle().y;
		float endZoneHeight = levelExitObj.getRectangle().height;
		float endZoneWidth = levelExitObj.getRectangle().width;
		endZone = new LevelEndZone(game, endZoneX / 32, endZoneY / 32, endZoneHeight / 32, endZoneWidth / 32);
		endZone.defineBody();
		
		MapBodyBuilder.buildBodies(tiledMap, gameManager.getWorld(), "Walls");
        MapBodyBuilder.buildBodies(tiledMap, gameManager.getWorld(), "Furniture");
	}
	
	public TiledMap load() {
		enemySpawns.forEach(
			es -> gameManager.addEnemy(es.getType(), es.getSpawnLocation())
		);
		powerUpSpawns.forEach(
			ps -> gameManager.addPowerUp(ps.getType(), ps.getSpawnLocation())
		);
		gameManager.setPlayerSpawn(playerSpawn);
		return tiledMap;
	}
	
	public int[] getBackgroundLayers() {
		return backgroundLayers;
	}
	
	public int[] getForegroundLayers() {
		return foregroundLayers;
	}
	
	static class SpawnPoint<K> {
		final K type;
		final Vector2 spawnLoc;
		
		public SpawnPoint(K type, Vector2 spawnLoc) {
			this.type = type;
			this.spawnLoc = spawnLoc;
		}
		
		public K getType() {
			return type;
		}
		
		public Vector2 getSpawnLocation() {
			return spawnLoc;
		}
	}

	@Override
	public void dispose() {
		tiledMap.dispose();
		endZone.delete();
	}
	
	
}
