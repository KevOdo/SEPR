package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public class Powerup {

    private Random rand = new Random();
    private int powerType;
    private String[] powerfile = {"core/assets/powerupSpeed.png", "core/assets/powerupStrength.png", "core/assets/powerupHealth.png"};
    private TextureRegion[] powerText;
    private Texture tempText;
    private Animation<TextureRegion> powerAni;
    private static final int frameCols = 1, frameRows = 2;
    //powerup types in order = [speed, strength, health]


    public Powerup(){
    powerType = rand.nextInt(2) + 1;

    switch(powerType){
        case 0:
            tempText = new Texture(Gdx.files.internal(powerfile[0]));
            break;
        case 1:
            tempText = new Texture(Gdx.files.internal(powerfile[1]));
            break;
        case 2:
            tempText = new Texture(Gdx.files.internal(powerfile[2]));
            break;
        }

        animateSetUp(tempText);
    }
    private void animateSetUp(Texture tempText){

        TextureRegion[][] tmp = TextureRegion.split(tempText,
                tempText.getWidth() / frameCols,
                tempText.getHeight() / frameRows);

        powerText = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                powerText[index++] = tmp[i][j];
            }
        }
        powerAni = new Animation<TextureRegion>(0.25f, powerText);
    }

    public Integer getPowerType(){
        return powerType;
    }

    public Animation<TextureRegion> getPowerAni(){
        return powerAni;
    }
}


