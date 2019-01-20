package com.game.zombies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ListenerClass implements ContactListener{
	private String nextMap = "data/map_compsci.tmx";

	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData() == "playerBody" &&
		contact.getFixtureB().getBody().getUserData()=="doorBody"){
			if(!(nextMap.equals("data/map_compsci.tmx"))){
				nextMap = "data/map_derwent.tmx";
			}
            GameScreen.changeCamera(1920,640,912,368,64f,625f);
			GameScreen.changeGame(0, nextMap, 50, 50);
		}
		
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
