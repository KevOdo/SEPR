package com.game.zombies;

import javax.swing.text.Position;

public abstract class Humanoid {

protected String name;
protected Integer currentHealth;
protected Integer maxHealth;
protected Integer damage;
protected Position pos;
protected Boolean alive;
protected Integer speed;

/*
 * Implement later
 * attack()
 * 
 */

/*
 * Constructor
 */
public Humanoid() {

}

public int getHealth() {
	return currentHealth;
}

public Position getPosition() {
	return pos;
}

public int getDistance() {
	return 0;
}

public int getDamage() {
	return damage;
}

}