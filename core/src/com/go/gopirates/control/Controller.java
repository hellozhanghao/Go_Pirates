package com.go.gopirates.control;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.go.gopirates.PirateGame;


/**
 * Created by Amy on 26/2/16.
 */
public class Controller implements ApplicationListener {
    Viewport viewport;
    Stage stage;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean pistolPress, powerUpPress, bombPress, swordPress;
    public boolean previousPistolPress, previousPowerUpPress, previousBombPress, previousSwordPress;
    OrthographicCamera cam;
    private Skin touchpadSkin;
    public Touchpad touchpad;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Image upImg;
    private Image emptyImg;
    private Image bombImg;
    private Image swordImg;



    @Override
    public void create() {
        Gdx.app.log("Controller","Launched");
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 450, cam);
        stage = new Stage(viewport, PirateGame.batch);
//        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.X:
                        previousPistolPress=pistolPress;
                        pistolPress = true;
                        break;
                    case Input.Keys.SPACE:
                        previousPowerUpPress=powerUpPress;
                        powerUpPress = true;
                        break;
                    case Input.Keys.Z:
                        previousBombPress=bombPress;
                        bombPress = true;
                        break;
                    case Input.Keys.C:
                        previousSwordPress=swordPress;
                        swordPress = true;
                        break;
                }
                return true;

            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.X:
                        previousPistolPress=pistolPress;
                        pistolPress = false;
                        break;
                    case Input.Keys.SPACE:
                        previousPowerUpPress=powerUpPress;
                        powerUpPress = false;
                        break;
                    case Input.Keys.Z:
                        previousBombPress=bombPress;
                        bombPress = false;
                        break;
                    case Input.Keys.C:
                        previousSwordPress=swordPress;
                        swordPress = false;
                        break;
                }
                return true;
            }
        });



        //right
        Table right = new Table();
        right.bottom().left();
        right.setX(620);
        right.setY(30);
        Image bomb = new Image(new Texture("controller/Pistol.png"));
        bomb.setSize(50, 50);
        bomb.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousPistolPress=pistolPress;
                pistolPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousPistolPress=pistolPress;
                pistolPress = false;
            }
        });

        //Power up
        //// TODO: 17/3/16 make a condition for power ups
        emptyImg = new Image(new Texture("controller/empty.png"));
        emptyImg.setSize(50, 50);
        emptyImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousPowerUpPress=powerUpPress;
                powerUpPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousPowerUpPress=powerUpPress;
                powerUpPress = false;
            }
        });

        //Bomb
        bombImg = new Image(new Texture("controller/Bomb.png"));
        bombImg.setSize(50, 50);
        bombImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousBombPress=bombPress;
                bombPress =true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousBombPress=bombPress;
                bombPress =false;
            }

        });


        //Sword
        swordImg = new Image(new Texture("controller/Sword.png"));
        swordImg.setSize(50, 50);
        swordImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousSwordPress=swordPress;
                swordPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousSwordPress=swordPress;
                swordPress = false;
            }
        });

        right.add();
        right.add(bomb).size(bomb.getWidth(), bomb.getHeight());
        right.add();
        right.row().pad(5, 5, 5, 5);
        right.add(bombImg).size(bombImg.getWidth(), bombImg.getHeight());
        right.add();
        right.add(swordImg).size(swordImg.getWidth(), swordImg.getHeight());
        right.row().padBottom(5);
        right.add();
        right.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        right.add();

        stage.addActor(right);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("controller/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("controller/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
//        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, PirateGame.batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());



        if (Gdx.input.justTouched()){

        }
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}