package com.game.zombies;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ListenerClass implements ContactListener{

	final ZombieGame game = null;

	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureA().getBody().getUserData() == "playerBody" &&
		contact.getFixtureB().getBody().getUserData()=="doorBody"){
			GameScreen gs = new GameScreen(game, 1);
			gs.changeMap();
			System.out.println(contact.getFixtureA().getBody().getUserData());
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
