package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

public class EndScreen extends DefaultScreen {

	private Stage stage;

	private boolean won;
	
	public EndScreen(DeadLast game) {
		super(game);
		stage = new Stage(new ScreenViewport());
		won = GameManager.getInstance(game).getWinLevel() == 1 ? true : false;

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		table.center();
		table.pad(15);
		
		table.setDebug(true);
		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		String titleText;
		if (won) {
			titleText = "You won!";
		} else {
			titleText = "You lost.";
		}
		Label title = new Label(titleText, skin);
		table.add(title).align(Align.center).expandX().fillX().row();

		String blurbText;
		if (won) {
			blurbText = "You have successfully escaped with all of your vital functions intact!";
		} else {
			blurbText = "Due to the forced cessation of several of your vital functions, you have ceased to be alive.";
		}
		Label blurb = new Label(blurbText, skin);
		table.add(blurb).align(Align.center).row();


		table.add(new Label("Your end score: " + GameManager.getInstance(game).getScore(),skin)).align(Align.center).row();

		
		TextButton returnButton = new TextButton("Menu", skin);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.reset();
				game.changeScreen(DeadLast.MENU);
			}
		});
		
		table.add(returnButton);

		stage.addActor(table);

		GameManager.getInstance(game).clearLevel();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act();
		stage.draw();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
