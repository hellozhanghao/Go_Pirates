package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 16/4/16.
 */
public class LoadingScreen implements Screen {
    Viewport viewport;
    OrthographicCamera cam;

    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Texture background;


    private Stage stage;
    private PirateGame game;
    private boolean screenChanged;
    private int numberOfTap;

    public LoadingScreen(PirateGame game) {

        this.game = game;
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920f, 1080f);
        stage = new Stage(viewport, batch);
        screenChanged = false;
        numberOfTap = 0;
    }

    @Override
    public void show() {
        background = new Texture("img/LoadingScreen.png");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(background);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        stage.act();
        stage.draw();

        if (game.sessionInfo.mState.equals("Play") & !screenChanged) {
            screenChanged = true;
            Gdx.app.log("Login", "ChangeScreen");
            game.playServices.broadcastMessage("Hello World!");
            Gdx.app.log("PirateGame", game.sessionInfo.mId);
            game.sessionInfo.setUpGame();
            PirateGame.DEFAULT_VELOCITY = 100;
            game.setScreen(new PlayScreen(game));
        }

        if (Gdx.input.justTouched()) {
            numberOfTap++;
        }
        if (numberOfTap > 3) {
            game.setScreen(new LoginScreen(game));
        }
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
