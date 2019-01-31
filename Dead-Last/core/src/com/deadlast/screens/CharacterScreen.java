package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deadlast.entities.PlayerType;
import com.deadlast.game.DeadLast;
import com.deadlast.game.GameManager;

/**
 * Screen responsible for presenting character selection choice and transferring to the game screen
 * @author Xzytl
 *
 */
public class CharacterScreen extends DefaultScreen {
	
	private Stage stage;

	public CharacterScreen(DeadLast game) {
		super(game);
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		table.center();
		table.pad(15);
		table.setHeight(Gdx.graphics.getHeight() - 30);

		table.setDebug(true);
		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		Label pageTitle = new Label("Character Selection", skin);
		table.add(pageTitle).align(Align.center).expandX().fillX().colspan(2);
		table.row();
		Label charTitle1 = new Label("Standard", skin);
		Label charTitle2 = new Label("Boxer", skin);
		table.add(charTitle1).align(Align.left).width(Value.percentWidth(.45F, table));
		table.add(charTitle2).align(Align.left).width(Value.percentWidth(.45F, table));
		table.row();
		Label char1Label = new Label("The student has normal stats for a character.", skin);
		char1Label.setWrap(true);
		table.add(char1Label).align(Align.left).width(Value.percentWidth(.45F, table));
		Label char2Label = new Label("The boxer is tough and strong, but cannot run as fast and is less stealthy.", skin);
		char2Label.setWrap(true);
		table.add(char2Label).align(Align.left).width(Value.percentWidth(.45F, table));
		table.row();
		
		TextButton char1Button = new TextButton("Select", skin);
		char1Button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				selectCharacter(PlayerType.STANDARD);
			}
		});
		
		table.add(char1Button);
		
		TextButton char2Button = new TextButton("Select", skin);
		char2Button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				selectCharacter(PlayerType.TANK);
			}
		});
		
		table.add(char2Button);
		
		stage.addActor(table);
	}
	
	private void selectCharacter(PlayerType type) {
		GameManager.getInstance(game).setPlayerType(type);
		GameManager.getInstance(game).setGameRunning(true);
		game.changeScreen(DeadLast.GAME);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act();
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
