package com.deadlast.game;

import com.badlogic.gdx.Game;
import com.deadlast.assets.Resources;
import com.deadlast.screens.*;

/**
 * Main game class that orchestrates screen changes.
 * @author Xzytl
 *
 */
public class DeadLast extends Game {

	public Resources resources = new Resources();
	
	private LoadingScreen loadingScreen;
	private ScoreboardScreen scoreboardScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private HelpScreen helpScreen;
	private CharacterScreen charScreen;
	private EndScreen endScreen;
	
	/**
	 * The number of pixels that represents one metre in the game
	 */
	public static final int PPM = 50;
	/**
	 * The width of the viewport the game is displayed in.
	 */
	public static final int V_WIDTH = 800;
	/**
	 * The height of the viewport the game is displayed in.
	 */
	public static final int V_HEIGHT = 600;
	
	public static final int MENU = 0;
	public static final int SCOREBOARD = 1;
	public static final int HELP = 2;
	public static final int GAME = 3;
	public static final int CHARACTER = 4;
	public static final int END = 5;
	
	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	
	/**
	 * Changes the active screen that is being displayed to the user.
	 * @param screen	an integer representing the screen to change to
	 */
	public void changeScreen(int screen) {
		switch(screen) {
		case MENU:
			if (menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case SCOREBOARD:
			if (scoreboardScreen == null) scoreboardScreen = new ScoreboardScreen(this);
			this.setScreen(scoreboardScreen);
			break;
		case HELP:
			if (helpScreen == null) helpScreen = new HelpScreen(this);
			this.setScreen(helpScreen);
			break;
		case GAME:
			if (gameScreen == null) gameScreen = new GameScreen(this);
			this.setScreen(gameScreen);
			break;
		case CHARACTER:
			if (charScreen == null) charScreen = new CharacterScreen(this);
			this.setScreen(charScreen);
			break;
		case END:
			if (endScreen == null) endScreen = new EndScreen(this);
			this.setScreen(endScreen);
			break;
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (loadingScreen != null) {
			loadingScreen.dispose();
		}
		if (scoreboardScreen != null) {
			scoreboardScreen.dispose();
		}
		if (menuScreen != null) {
			menuScreen.dispose();
		}
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		if (helpScreen != null) {
			helpScreen.dispose();
		}
		if (endScreen != null) {
			endScreen.dispose();
		}
	}
	
	@Override
	public void pause() {
		super.pause();
	}

}
