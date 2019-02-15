package com.deadlast.entities;

public enum PlayerType {
	
	TANK(0.5f, 100, 3, 8, 20),
	STEALTH(0.3f, 40, 6, 4, 90),
	SPECIALIST(0.3f, 40, 5, 4, 40),
	STANDARD(0.4f, 50, 4, 5, 50);
	
	float bodyRadius;
	int health;
	int speed;
	int strength;
	int stealth;
	
	PlayerType(float bodyRadius, int health, int speed, int strength, int stealth) {
		this.bodyRadius = bodyRadius;
		this.health = health;
		this.speed = speed;
		this.strength = strength;
		this.stealth = stealth;
	}
	
	public float getBodyRadius() {
		return bodyRadius;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getStealth() {
		return stealth;
	}

}
