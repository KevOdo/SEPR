package com.game.zombies;

import javax.swing.text.Position;

public abstract class Humanoid {

protected String name;
protected Integer currentHealth = 0;
protected Integer maxHealth;
protected Integer currentDamage = 0;
protected Position pos;
protected Boolean alive;
protected float currentSpeed = 5000f;

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
	return currentDamage;
}

public void setHealth(int health) {
	currentHealth += health;
}

public void setDamage(int damage) {
	currentDamage += damage;
}

public void setSpeed(int speed) {
	currentSpeed *= speed;
}

public float getSpeed() {
	return currentSpeed;
}
}