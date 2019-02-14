package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * 
 * @author Xzytl
 *
 */
public class MenuScreen extends DefaultScreen {

	private Stage stage;
	private Texture background;
	
	private SpriteBatch batch;

	public MenuScreen(DeadLast game) {
		super(game);
		// Create a new stage, and set it as the input processor
		stage = new Stage(new ScreenViewport());
		background = new Texture("ui/background.png");
		batch = new SpriteBatch();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.center();
		
		// TODO: Replace with an asset manager
		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		TextButton playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (GameManager.getInstance(game).isGameRunning()) {
					game.changeScreen(DeadLast.GAME);
				} else {
					game.changeScreen(DeadLast.CHARACTER);
				}
			}
		});
		
		TextButton scoreButton = new TextButton("Scoreboard", skin);
		scoreButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.changeScreen(DeadLast.SCOREBOARD);
			}
		});
		
		TextButton helpButton = new TextButton("How to Play", skin);
		helpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.changeScreen(DeadLast.HELP);
			}
		});

		TextButton miniButton = new TextButton("Minigame", skin);
		miniButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.getInstance(game).setMinigame();
				if (GameManager.getInstance(game).isGameRunning()) {
					game.changeScreen(DeadLast.GAME);
				} else {
					game.changeScreen(DeadLast.CHARACTER);
				}
			}
		});
		
		TextButton exitButton = new TextButton("Exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		mainTable.add(playButton).fillX().uniformX();
		mainTable.row().pad(10, 0, 10, 0);
		mainTable.add(scoreButton).fillX().uniformX();
		mainTable.row();
		mainTable.add(helpButton).fillX().uniformX();
		mainTable.row().pad(10, 0, 10, 0);
		mainTable.add(miniButton).fillX().uniformX();
		mainTable.row();
		mainTable.add(exitButton).fillX().uniformX();
		
		stage.addActor(mainTable);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
	public void dispose() {
		Gdx.app.debug("DeadLast", "dispose main menu");
		stage.dispose();
		background.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
