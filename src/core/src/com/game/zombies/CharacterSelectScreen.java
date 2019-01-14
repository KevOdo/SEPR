package com.game.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Toolkit;
import java.awt.Dimension;

public class CharacterSelectScreen implements Screen{

    private Stage stage;

    final ZombieGame game;
    BitmapFont customFont;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    private  Texture[] characterText= new Texture[2];
    private String[] characterString = new String[2];
    private SpriteBatch batch = new SpriteBatch();

    public CharacterSelectScreen(final ZombieGame game){
        this.game = game;
        characterText[0] = new Texture((Gdx.files.internal("core/assets/strong_character/Strong_Character0.png")));
        characterString[0] = "The PE Student. \n Perk: +Strength \n Ready to punch some zombies!!";
    }

    public void setFont(String fileLocation, int size) { //Allows use of TrueType Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fileLocation));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size; // font size
        customFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {
        stage = new Stage();
        TextureAtlas atlas = new TextureAtlas("data/ui/button.pack");
        Skin skin = new Skin(atlas);

        TextButton.TextButtonStyle txtButtonStyle = new TextButton.TextButtonStyle();
        txtButtonStyle.up = skin.getDrawable("button.up");
        txtButtonStyle.down = skin.getDrawable("button.down");
        setFont("data/MenuFont.ttf", 25);
        txtButtonStyle.pressedOffsetY = -1;
        txtButtonStyle.pressedOffsetX = 1;
        txtButtonStyle.font = customFont;
        txtButtonStyle.fontColor = Color.BLACK;

        TextButton buttonDone = new TextButton("Done",txtButtonStyle);
        buttonDone.pad(20);
        buttonDone.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        Table table1 = new Table(skin);
        table1.setBounds( screenWidth - 800,screenHeight - 1020,20f,20f);

        TextButton buttonCharacterRight = new TextButton(">>",txtButtonStyle);
        buttonCharacterRight.pad(20);
        buttonCharacterRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        Table table2 = new Table(skin);
        table2.setBounds(screenWidth - 1400,screenHeight - 1000,20f,20f);

        TextButton buttonCharacterLeft = new TextButton("<<",txtButtonStyle);
        buttonCharacterLeft.pad(20);
        buttonCharacterLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        Table table3 = new Table(skin);
        table3.setBounds(screenWidth - 1800,screenHeight - 1000,20f,20f);

        TextButton buttonExit = new TextButton("BACK",txtButtonStyle);
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        Table table4 = new Table(skin);
        table4.setBounds(screenWidth - 800,screenHeight - 430,20f,20f);

        table1.add(buttonDone);
        table2.add(buttonCharacterRight);
        table3.add(buttonCharacterLeft);
        table4.add(buttonExit);
        stage.addActor(table1);
        stage.addActor(table2);
        stage.addActor(table3);
        stage.addActor(table4);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.8f, 0.6f, 0.2f);	//Sets colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        game.batch.begin();
        setFont("data/MenuFont.ttf", 20);
        customFont.setColor(0f, 0f, 0f, 1f);
        customFont.draw(game.batch,"Character Select",screenWidth - 1900,screenHeight - 400);
        customFont.draw(game.batch, characterString[0],screenWidth - 1400,screenHeight - 600);
        game.batch.end();
        Gdx.input.setInputProcessor(stage);

        batch.begin();
        batch.draw(characterText[0], screenWidth - 1780, screenHeight - 900, 400, 400);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}