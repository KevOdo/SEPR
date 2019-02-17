package com.deadlast.test.com.deadlast.testing;

import com.deadlast.entities.Enemy;
import com.deadlast.game.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.Vector2;


import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    GameManager manager;
    Enemy boss;

    @BeforeEach
     public void setUp() {
        GameManager manager  = GameManager.getInstance(null);
    }

    @Test
    void addEnemy() {
        manager.addEnemy(Enemy.Type.BOSS2, new Vector2(5, 5));
    }

    @Test
    void getPlayer(){
        assertNotNull(manager.getPlayer());
    }

    @Test
    void checkBoss() {
        String CurrentLevel = manager.getLevelName();
        manager.addEnemy(Enemy.Type.BOSS2, new Vector2(5, 5));
        manager.update(1);
        manager.checkBoss();
        assertNotEquals(CurrentLevel, manager.getLevelName());
    }


    @Test
    void getMinigame() {
        manager.setMinigame();
        assertNotNull(manager.getMinigame());
    }
}