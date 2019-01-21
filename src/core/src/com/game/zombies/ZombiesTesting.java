package com.game.zombies;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import org.junit.*;
import org.mockito.Mockito;

/*
Base class for tests that require libGDX. (Normal functions tested without extending from this)
This will spawn a libGDX application in headless mode and run tests that require libGDX.
 */

public class ZombiesTesting {
    private static Application application;

    // Start libGDX in headless mode
    @BeforeClass


    public static void init() {
        application = new HeadlessApplication(new ApplicationListener() {
            // From libGDX
            @Override
            public void create() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void render() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterClass
    public static void tidy() {
        application.exit();
        application = null;
    }
}