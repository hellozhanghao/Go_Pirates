package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 3/4/16.
 */
public class LoginScreen implements Screen {

    private float gameWidth;
    private float gameHeight;
    private float BUTTON_WIDTH;
    private float BUTTON_HEIGHT;

    private SpriteBatch batch;
    private Texture background;
    private Sprite sprite;

    private TextButton quickGameButton;

    private Stage stage;



    private PirateGame game;

    public LoginScreen(PirateGame game, float gameWidth, float gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        BUTTON_WIDTH = 120;
        BUTTON_HEIGHT = 40;

        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));


    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(background);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        quickGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });



        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act();
        stage.draw();

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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
