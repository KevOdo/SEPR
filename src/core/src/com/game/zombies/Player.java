package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Humanoid  {
	protected Sprite figure;
	private Texture figureText;
	private String ability;	
	
	/*
	 * Constructor
	 */
	public Player() {
	
		figure = new Sprite(figureText);
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
