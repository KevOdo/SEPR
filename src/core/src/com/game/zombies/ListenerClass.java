package com.game.zombies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ListenerClass implements ContactListener{
	private String nextMap = "data/map_compsci.tmx";
    private int xlong = 1920;
    private int doorX = 1350;
    private int doorY = 60;
    private boolean hasWon = false;


	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData() == "playerBody" &&
		contact.getFixtureB().getBody().getUserData()=="doorBody"){
			String map = GameScreen.getMap();
			if(map.equals("data/map_compsci.tmx")) {
				nextMap = "data/map_derwent.tmx";
                xlong = 1280;
                doorX = 800;
                doorY = 50;
			}
			if(map.equals("data/map_derwent.tmx")){
			    GameScreen.changeScreen();
            }else {
                GameScreen.changeCamera(xlong, 640, 912, 368, 64f, 625f);
                GameScreen.changeGame(0, nextMap, doorX, doorY);
            }
		}
		
	}

	public boolean getHasWon(){
	    return hasWon;
    }

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}



}
