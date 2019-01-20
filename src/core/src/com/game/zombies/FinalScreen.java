package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class FinalScreen implements Screen {


    private Stage stage;

    final ZombieGame game;
    OrthographicCamera camera;
    BitmapFont customFont;

    public FinalScreen(final ZombieGame game) { //Sets as current screen, sets camera
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
    }

    public void setFont(String fileLocation, int size) { //Allows use of TrueType Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fileLocation));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size; // font size
        customFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);	//Sets colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();					//Update camera coordinates, set coordinates of view to camera
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        setFont("data/ZombFont.ttf", 180);
        customFont.setColor(0f, 0f, 0f, 1f);
        customFont.draw(game.batch,"GAME OVER",340,600);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
