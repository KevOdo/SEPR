package com.game.zombies;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu implements Screen { //The Main Menu Screen
	
	final ZombieGame game;
	OrthographicCamera camera;

	public MainMenu(final ZombieGame game) { //Sets as current screen, sets camera
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}
	
	@Override
	public void render(float delta) { 
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);	//Sets colour
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();					//Update camera coordinates, set coordinates of view to camera
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();					//Beings draw stage
		game.font.draw(game.batch, "Zombie Boi ", 100, 150);
		game.font.draw(game.batch, "Click anywhere to begin", 100, 100);
		game.batch.end();					//Ends draw stage

		if (Gdx.input.isTouched()) {		//If user clicks anywhere, goes to GameScreen Screen, dispose this screen
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
