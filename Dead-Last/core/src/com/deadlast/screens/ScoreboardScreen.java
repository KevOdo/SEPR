package com.deadlast.screens;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deadlast.assets.Scoreboard;
import com.deadlast.game.DeadLast;
import com.deadlast.util.Util;

/**
 * 
 * @author Xzytl
 *
 */
public class ScoreboardScreen extends DefaultScreen {
	
	protected Stage stage;

	public ScoreboardScreen(DeadLast game) {
		super(game);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {
		// TODO: Replace with an asset manager
		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.changeScreen(DeadLast.MENU);
			}
		});
		
		Table scoreTable = new Table();
		scoreTable.setFillParent(true);
		scoreTable.top();
		scoreTable.setSkin(skin);
		scoreTable.defaults().padLeft(5).padRight(5);
		//scoreTable.setDebug(true);
		
		Label nameLabel = new Label("Name", skin);
		nameLabel.setAlignment(Align.center);
		Label scoreLabel = new Label("Score", skin);
		scoreLabel.setAlignment(Align.center);
		Label dateLabel = new Label("Date", skin);
		dateLabel.setAlignment(Align.center);
		
		scoreTable.add(nameLabel).minWidth(100);
		scoreTable.add(scoreLabel).minWidth(45);
		scoreTable.add(dateLabel).minWidth(200);
		scoreTable.row();
		
		try {
			Scoreboard scores = Util.parseScoreFile();
			scores.getSortedEntries().forEach(entry -> {
				scoreTable.add(entry.getName());
				scoreTable.add(entry.getScoreString());
				scoreTable.add(entry.getDate());
				scoreTable.row();
			});
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			scoreTable.add(new Label("Unable to load scores from file", skin)).colspan(3);
		}
		
		stage.addActor(scoreTable);
		stage.addActor(backButton);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
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
		stage.dispose();
	}

}
