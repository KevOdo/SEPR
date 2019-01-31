package com.deadlast.screens;

import com.deadlast.game.DeadLast;

/**
 * 
 * @author Xzytl
 *
 */
public class LoadingScreen extends DefaultScreen {

	public LoadingScreen(DeadLast game) {
		super(game);
		game.resources.loadImages();
		game.resources.manager.finishLoading();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		game.changeScreen(DeadLast.MENU);
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
