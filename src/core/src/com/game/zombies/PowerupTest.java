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
    public void getPowerAni() {
    }

    @Test
    public void setFlagDelete() {
    }

    @Test
    public void getFlag() {
    }
}