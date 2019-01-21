package com.game.zombies;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameScreenTest extends ZombiesTesting {


    @Test
    public void getPowerUp() {

        Powerup speedPowerUp = new Powerup();
        Powerup healthPowerUp = new Powerup();
        Powerup damagePowerUp = new Powerup();

        Player somePlayer = new Player(0);
        getPowerUp();

        if (!((somePlayer.currentSpeed == 3) && (somePlayer.currentDamage == 50) && (somePlayer.currentHealth == 50))){
            fail();
        }
    }

    @Test
    public void destroyBodies() {
        Player somePlayer = new Player(0);
        destroyBodies();
        assertNull(somePlayer);
    }
}