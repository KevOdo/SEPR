package com.deadlast.test.com.deadlast.testing;

import com.deadlast.entities.PowerUpFactory;
import com.deadlast.game.DeadLast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpFactoryTest {

    @Test
    void getInstance() {
        assertNotNull(PowerUpFactory.getInstance(null));
    }
}