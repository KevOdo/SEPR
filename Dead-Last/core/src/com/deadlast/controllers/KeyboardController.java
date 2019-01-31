package com.deadlast.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 * Interprets input events from a keyboard and mouse/touch screen.
 * @author Xzytl
 *
 */
public class KeyboardController implements InputProcessor {
	
	/** 
	 * Boolean representing whether or not the respective key is held down
	 */
	public boolean left, right, up, down;
	/**
	 * Boolean representing whether or not the respective mouse button is held down
	 */
	public boolean isMouse1Down, isMouse2Down, isMouse3Down;
	/**
	 * Boolean representing whether or not the mouse is being dragged (moved while button is held)
	 */
	public boolean isDragged;
	/**
	 * Boolean representing whether or not the left shift key is held down
	 */
	public boolean isShiftDown;
	/**
	 * Boolean representing whether or not space is held down
	 */
	public boolean isSpaceDown;
	/**
	 * {@link Vector2} representing the mouse pointer's current location on the screen
	 */
	public Vector2 mouseLocation = new Vector2();

	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		switch (keycode)
		{
		case Keys.LEFT:
		case Keys.A:
			left = true;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
		case Keys.D:
			right = true;
			keyProcessed = true;
			break;
		case Keys.UP:
		case Keys.W:
			up = true;
			keyProcessed = true;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = true;
			keyProcessed = true;
			break;
		case Keys.SHIFT_LEFT:
			isShiftDown = true;
			keyProcessed = true;
			break;
		case Keys.SPACE:
			isSpaceDown = true;
			keyProcessed = true;
			break;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch (keycode)
		{
		case Keys.LEFT:
		case Keys.A:
			left = false;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
		case Keys.D:
			right = false;
			keyProcessed = true;
			break;
		case Keys.UP:
		case Keys.W:
			up = false;
			keyProcessed = true;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = false;
			keyProcessed = true;
			break;
		case Keys.SHIFT_LEFT:
			isShiftDown = false;
			keyProcessed = true;
			break;
		case Keys.SPACE:
			isSpaceDown = false;
			keyProcessed = true;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == 0) {
			isMouse1Down = true;
		}
		if(button == 1) {
			isMouse2Down = true;
		}
		if(button == 2) {
			isMouse3Down = true;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDragged = false;
		if(button == 0) {
			isMouse1Down = false;
		}
		if(button == 1) {
			isMouse2Down = false;
		}
		if(button == 2) {
			isMouse3Down = false;
		}
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragged = true;
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseLocation.x = screenX;
		mouseLocation.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
