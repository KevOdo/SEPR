package com.deadlast.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deadlast.game.DeadLast;

/**
 * 
 * @author Xzytl
 *
 */
public class HelpScreen extends DefaultScreen {
	
	private Stage stage;
	
	private Label pageTitle;
	private Label pageContents;
	private ScrollPane contentsScroll;
	private TextButton menuButton;
	
	private String[] lines;

	public HelpScreen(DeadLast game) {
		super(game);
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		lines = new String[] {
				"DeadLast is a stealth based zombie game. "
				+ "Your aim is, surprisingly, to avoid being torn apart or otherwise having vital limbs or organs removed.",
				"\n",
				"Movement controls:",
				"WASD or arrow keys to move",
				"Mouse to look around and aim",
				"Space to attack",
				"\n",
				"Enemies have vision cones - if you enter it, they will see you.",
				"Zombies can also hear you if you come too close to them",
				"More text here."
		};
		
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line + "\n");
		}
		
		Table table = new Table();
		table.setFillParent(true);
		table.center();
		table.setHeight(Gdx.graphics.getHeight() - 10);
		
		table.setDebug(true);
		
		// TODO: Replace with an asset manager
		Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		table.setSkin(skin);
		table.pad(15);
		
		pageTitle = new Label("How to Play", skin);
		pageContents = new Label(sb.toString(), skin);
		pageContents.setWrap(true);
		contentsScroll = new ScrollPane(pageContents);

		table.add(pageTitle).align(Align.left).expandX().height(30);
		table.row();
		table.add(new ScrollPane(contentsScroll)).align(Align.left).expand().fillX().top();
		table.row();
		
		menuButton = new TextButton("Menu", skin);
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.changeScreen(DeadLast.MENU);
			}
		});
		
		table.add(menuButton).height(30);
		
		stage.addActor(table);
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
	public void dispose() {
		stage.dispose();
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

}
