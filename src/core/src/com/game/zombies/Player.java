package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Humanoid  {
	protected Sprite figure;
	private TextureRegion[] playerWalk;
	private Integer[] ability = new Integer[5];
	private Animation<TextureRegion> walkAnimation;
	private static final int frameCols = 1;
	private int frameRows = 2;
	//ability split up as [speed, strength, health, stealth, ??)

	private Texture playerText;
	
	/*
	 * Constructor
	 */
	public Player(int charType) {

		switch(charType){
			case 0:
				frameRows = 4;
					playerText = new Texture(Gdx.files.internal("core/assets/characterStrength.png"));
				break;
			case 1:
					playerText = new Texture(Gdx.files.internal("core/assets/characterSpeed.png"));
				break;
		}
		walkSetUp(playerText);

	}

	private void walkSetUp(Texture playerText) {

		TextureRegion[][] tmp = TextureRegion.split(playerText,
				playerText.getWidth() / frameCols,
				playerText.getHeight() / frameRows);

		playerWalk = new TextureRegion[frameCols * frameRows];
		int index = 0;
		for (int i = 0; i < frameRows; i++) {
			for (int j = 0; j < frameCols; j++) {
				playerWalk[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation<TextureRegion>(0.25f, playerWalk);
	}


	public Animation<TextureRegion> getWalkAnimation(){
		return walkAnimation;
	}
	/*
	 * Key based movement
	 * Speed dependent 
	 */
	private void move() {
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			figure.translateY(speed);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			figure.translateY(speed);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			figure.translateX(speed);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			figure.translateX(speed);
		}
	}
	
	/*
	 * Ability type found by attribute ability
	 * Player attributes changed dependent on this
	 */
	private void useAbility() {
		
	}
}
