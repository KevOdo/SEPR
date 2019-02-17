package com.deadlast.test.com.deadlast.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;
import com.deadlast.entities.Player;
import com.deadlast.entities.PowerUp;

public class PowerUpTest {

	Player player;
	
	@BeforeEach
	public void init() {
		player  = new Player(null, null, 0.4f, new Vector2(5,5), 50, 4, 50, 50);
	}
	
	@Test
	public void regenTest() {
		player.setHealth(1);
		player.onPickup(new PowerUp(null, 10, null, 0.25f, new Vector2(0,0), PowerUp.Type.REGEN));
		player.update(1f);
		assertEquals(player.getHealth(), 2);
		player.update(1f);
		assertEquals(player.getHealth(), 3);
	}
	
	@Test
	public void regenNotExceedMaxHealth() {
		player.setHealth(player.getMaxHealth());
		player.onPickup(new PowerUp(null, 10, null, 0.25f, new Vector2(0,0), PowerUp.Type.REGEN));
		player.update(1f);
		assertEquals(player.getHealth(), player.getMaxHealth());
	}

}
