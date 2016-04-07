//package com.go.gopirates.screen;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ExtendViewport;
//import com.go.gopirates.PirateGame;
//
///**
// * Created by zhanghao on 3/4/16.
// */
//public class LoginScreen implements Screen {
//
//    private float gameWidth;
//    private float gameHeight;
//    private float BUTTON_WIDTH;
//    private float BUTTON_HEIGHT;
//
//    private SpriteBatch batch;
//    private Texture background;
//    private Sprite sprite;
//
//    private TextButton.TextButtonStyle normal;
//
//    private Stage stage;
//    private TextButton buttonQuick;
//    private TextButton buttonLogin;
//    private TextButton buttonLogout;
//    private TextButton buttonPlay;
//    private TextButton buttonJoin;
//    private TextButton buttonTutorial;
//    private TextButton buttonInvite;
//
//    private PirateGame game;
//
//    public MenuScreen(PirateGame game, float gameWidth, float gameHeight) {
//        this.gameWidth = gameWidth;
//        this.gameHeight = gameHeight;
//
//        BUTTON_WIDTH = 120;
//        BUTTON_HEIGHT = 40;
//
//        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));
//        buttonPlay = new TextButton("Debug", normal);
//        buttonJoin = new TextButton("Join Game", normal);
//        buttonTutorial = new TextButton("Tutorial", normal);
//        buttonLogin = new TextButton("Login", normal);
//        buttonLogout = new TextButton("Logout", normal);
//        buttonQuick = new TextButton("Quick Game", normal);
//        buttonInvite = new TextButton("Invite", normal);
//
//
//    }
//
//    @Override
//    public void show() {
//
//        // The elements are displayed in the order you add them.
//        // The first appear on top, the last at the bottom.
//
//
//        batch = new SpriteBatch();
//        background = AssetLoader.menuBackground;
//        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        sprite = new Sprite(background);
//        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        buttonLogin.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                AssetLoader.clickSound.play(AssetLoader.VOLUME);
//                game.actionResolver.loginGPGS();
//                buttonLogin.remove();
//                stage.addActor(buttonLogout);
//            }
//        });
//
//
//
//        buttonQuick.setSize(this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
//        buttonQuick.setPosition(425, 220);
//        stage.addActor(buttonQuick);
//
//        buttonLogin.setSize(this.BUTTON_WIDTH / 3 * 2, this.BUTTON_HEIGHT);
//        buttonLogin.setPosition(490, 15);
//        buttonLogout.setSize(this.BUTTON_WIDTH / 3 * 2, this.BUTTON_HEIGHT);
//        buttonLogout.setPosition(490, 15);
//        if (game.actionResolver.getSignedInGPGS()) {
//            stage.addActor(buttonLogout);
//        } else {
//            stage.addActor(buttonLogin);
//        }
//
//        buttonTutorial.setSize(this.BUTTON_WIDTH / 3 * 2, this.BUTTON_HEIGHT);
//        buttonTutorial.setPosition(400, 15);
//        stage.addActor(buttonTutorial);
//
//        buttonInvite.setSize(this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
//        buttonInvite.setPosition(425, 160);
//        stage.addActor(buttonInvite);
//
//        buttonJoin.setSize(this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
//        buttonJoin.setPosition(425, 100);
//        stage.addActor(buttonJoin);
//
//        muteButton.setPosition(580, 15);
//        unmuteButton.setPosition(580, 15);
//
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        sprite.draw(batch);
//        batch.end();
//
//        stage.act();
//        stage.draw();
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//        dispose();
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }
//
//}
