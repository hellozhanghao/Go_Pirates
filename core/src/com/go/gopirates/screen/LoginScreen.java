package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 3/4/16.
 */
public class LoginScreen implements Screen {

    Viewport viewport;
    OrthographicCamera cam;

    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Texture background;

    private Image quickGameButton;
    private Image joinRoomButton;

    private Stage stage;
    private PirateGame game;
    private boolean screenChanged;

    public LoginScreen(PirateGame game) {

        this.game=game;
        batch=new SpriteBatch();
        cam=new OrthographicCamera();
        viewport=new FitViewport(1920f,1080f);
        stage = new Stage(viewport,batch);
        screenChanged=false;
    }

    @Override
    public void show() {
        background=new Texture("img/MainScreen.png");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(background);

        quickGameButton=new Image(new Texture("img/QuickGame.png"));
        quickGameButton.setSize(600,200);
        quickGameButton.setPosition(250, 600);
        stage.addActor(quickGameButton);

        quickGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playServices.startQuickGame();
            }
        });

        joinRoomButton=new Image(new Texture("img/JoinRoom.png"));
        joinRoomButton.setSize(600, 200);
        joinRoomButton.setPosition(1050, 600);
        stage.addActor(joinRoomButton);
        Gdx.input.setInputProcessor(stage);
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

        if (game.sessionInfo.mState.equals("Play") & !screenChanged){
            game.setScreen(new PlayScreen(game));
            screenChanged=true;
            Gdx.app.log("Login","ChangeScreen");
            game.playServices.broadcastMessage("Hello World!");
            Gdx.app.log("PirateGame",game.sessionInfo.mId);
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
