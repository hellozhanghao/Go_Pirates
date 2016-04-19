package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    private Image tutorialButton;

    private Stage stage;
    private PirateGame game;
    private boolean screenChanged;
    private Music music;

    public LoginScreen(PirateGame game) {

        this.game=game;
        batch=new SpriteBatch();
        cam=new OrthographicCamera();
        viewport=new FitViewport(1920f,1080f);
        stage = new Stage(viewport,batch);
        screenChanged=false;

        music = PirateGame.manager.get("audio/music/pirate.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
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
//                game.sessionInfo.endSession();
                game.playServices.startQuickGame();
                game.setScreen(new LoadingScreen(game));
            }
        });

        tutorialButton = new Image(new Texture("img/Tutorial.png"));
        tutorialButton.setSize(600, 200);
        tutorialButton.setPosition(1050, 600);
        stage.addActor(tutorialButton);

        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game));
            }
        });

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
