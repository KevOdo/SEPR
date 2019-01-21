package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainMenu implements Screen { //The Main Menu Screen

	private Stage stage;

	final ZombieGame game;
	OrthographicCamera camera;
	BitmapFont customFont;

	public MainMenu(final ZombieGame game) { //Sets as current screen, sets camera
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
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);	//Sets colour
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();					//Update camera coordinates, set coordinates of view to camera
		game.batch.setProjectionMatrix(camera.combined);
		stage.act(delta);
		stage.draw();
											//Draws the title of the game
		game.batch.begin();
		setFont("data/ZombFont.ttf", 180);
		customFont.setColor(0f, 0f, 0f, 1f);
		customFont.draw(game.batch,"Zombie Escape",195,685);
		game.batch.end();
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void show() {
		/*
		 Method to create the various menu buttons in the world and determine what action to take  based on the button clicked.
		 */
		stage = new Stage();
		TextureAtlas atlas = new TextureAtlas("data/ui/button.pack");
		Skin skin = new Skin(atlas);
		Table table = new Table(skin);

		table.setBounds(595,340,40f,40f);
		TextButton.TextButtonStyle txtButtonStyle = new TextButton.TextButtonStyle();
		txtButtonStyle.up = skin.getDrawable("button.up");
		txtButtonStyle.down = skin.getDrawable("button.down");
		setFont("data/MenuFont.ttf", 45);
		txtButtonStyle.pressedOffsetY = -1;
		txtButtonStyle.pressedOffsetX = 1;
		txtButtonStyle.font = customFont;
		txtButtonStyle.fontColor = Color.BLACK;

		TextButton buttonNewGame = new TextButton("NEW GAME",txtButtonStyle);
		buttonNewGame.pad(20);

		buttonNewGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new CharacterSelectScreen(game));
				dispose();
			}
		});

		Table table2 = new Table(skin);
		table2.setBounds(595,240,40f,40f);

		TextButton buttonLoadGame = new TextButton("LOAD GAME",txtButtonStyle);
		buttonLoadGame.pad(20);

		buttonLoadGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Load Game");
			}
		});

		Table table3 = new Table(skin);
		table3.setBounds(595,140,40f,40f);

		TextButton buttonExit = new TextButton("EXIT",txtButtonStyle);
		buttonExit.pad(20);

		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		Table table4 = new Table(skin);
		table4.setBounds(595,40,40f,40f);
		
		TextButton buttonCollision = new TextButton("COLLISION TEST",txtButtonStyle);
		buttonCollision.pad(20);

		//sets up the table for the buttons and adds it to the stage
		table.add(buttonNewGame);
		table2.add(buttonLoadGame);
		table3.add(buttonExit);
		stage.addActor(table);
		stage.addActor(table2);
		stage.addActor(table3);

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
