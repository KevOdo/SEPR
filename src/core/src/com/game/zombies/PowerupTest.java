package com.game.zombies;

import org.junit.Test;

import static org.junit.Assert.*;

public class PowerupTest extends ZombiesTesting{

    @Test
    public void getPowerType() {
        Powerup power = new Powerup();
        Integer powerType = power.getPowerType();
        assertNotNull(powerType);
    }

    @Test
    public void setFlagDeleteTrue() {
        Powerup power = new Powerup();
        power.setFlagDelete();
        assertEquals(true, power.getFlag());

    }

    @Test
    public void setFlagDeleteFalse() {
        Powerup power = new Powerup();
        assertEquals(false, power.getFlag());
    }

    @Test
    public void getFlag() {
        Powerup power = new Powerup();
        assertNotNull(power.getFlag());
    }
}