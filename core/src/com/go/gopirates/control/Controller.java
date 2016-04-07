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
import com.go.gopirates.sprites.Pirate;


/**
 * Created by Amy on 26/2/16.
 */
public class Controller implements ApplicationListener {
    Viewport viewport;
    Stage stage;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean coconutPress, powerUpPress, bombPress, swordPress;
    public boolean previousCoconutPress, previousPowerUpPress, previousBombPress, previousSwordPress;
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
    private Image cocoImg;
    private Image healthImg,profileImg;
    private Table coconut , powerup, healthPanel;
    private int numberofCoconut = PirateGame.INITIAL_COCONUT;
    private int previousCoconut = numberofCoconut;
    private Pirate.PowerUpHolding POWER_UP = PirateGame.INITIAL_POWERUP;
    private Pirate.PowerUpHolding PrevPowerUp = POWER_UP;
    private int health=10,previousHealth=10;
    private String character = "";

    private int size=90;
    private float hud_ratio=0.7f;

    @Override
    public void create() {
        Gdx.app.log("Controller","Launched");
        cam = new OrthographicCamera();
        viewport = new FitViewport(16*size, 9*size, cam);
        stage = new Stage(viewport, PirateGame.batch);

        createKeyboardController();
        createTouchPadController();
        createHealthPanel();
//        setUpUserProfilePanel("Sophia");
    }

    public void createKeyboardController(){
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
                        previousCoconutPress = coconutPress;
                        coconutPress = true;
                        break;
                    case Input.Keys.SPACE:
                        previousPowerUpPress = powerUpPress;
                        powerUpPress = true;
                        break;
                    case Input.Keys.Z:
                        previousBombPress = bombPress;
                        bombPress = true;
                        break;
                    case Input.Keys.C:
                        previousSwordPress = swordPress;
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
                        previousCoconutPress = coconutPress;
                        coconutPress = false;
                        break;
                    case Input.Keys.SPACE:
                        previousPowerUpPress = powerUpPress;
                        powerUpPress = false;
                        break;
                    case Input.Keys.Z:
                        previousBombPress = bombPress;
                        bombPress = false;
                        break;
                    case Input.Keys.C:
                        previousSwordPress = swordPress;
                        swordPress = false;
                        break;
                }
                return true;
            }
        });
    }

    public void createTouchPadController(){
        //right
        Table bomb = new Table();
        bomb.bottom().left();
        bomb.setX(1170);
        bomb.setY(10);


        //Bomb
        bombImg = new Image(new Texture("controller/Bomb.png"));
        bombImg.setSize(250, 250);
        bombImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousBombPress = bombPress;
                bombPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousBombPress = bombPress;
                bombPress = false;
            }
        });

        bomb.add(bombImg).size(bombImg.getWidth(), bombImg.getHeight());
        stage.addActor(bomb);


        //Sword
        Table sword = new Table();
        sword.bottom().left();
        sword.setX(1000);
        sword.setY(15);


        swordImg = new Image(new Texture("controller/Sword.png"));
        swordImg.setSize(150, 150);
        swordImg.setX(300);
        swordImg.setY(100);
        swordImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousSwordPress = swordPress;
                swordPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousSwordPress = swordPress;
                swordPress = false;
            }
        });
        sword.add(swordImg).size(swordImg.getWidth(), swordImg.getHeight());
        stage.addActor(sword);

        //Coconut
        coconut = new Table();
        updateCoconut();

        //Powerup
        powerup = new Table();
        updatePowerUp();

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("controller/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(30, 30, 300, 300);

        //Create a Stage and add TouchPad
//        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, PirateGame.batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    public void createHealthPanel(){
        healthPanel = new Table();
        healthPanel.bottom().left();
        healthPanel.setX(90);
        healthPanel.setY(250);

        //Bomb
        healthImg = new Image(new Texture("img/hud/10.png"));
        healthImg.setSize(256 * hud_ratio, 768 * hud_ratio);
        healthPanel.add(healthImg).size(healthImg.getWidth(), healthImg.getHeight());
        stage.addActor(healthPanel);
    }

    public void setUpUserProfilePanel(String character){
        Table userProfilePanel = new Table();
        userProfilePanel.bottom().left();
        userProfilePanel.setX(110);
        userProfilePanel.setY(620);

        float ratio=0.7f;
        profileImg = new Image(new Texture("img/hud/"+character+".png"));
        userProfilePanel.setSize(211*ratio, 216*ratio);
        userProfilePanel.add(profileImg).size(211*ratio,216*ratio);
        stage.addActor(userProfilePanel);
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        if (Gdx.input.justTouched()) {}
        if(previousCoconut!= numberofCoconut){
            updateCoconut();
            previousCoconut = numberofCoconut;
        }
        if (PrevPowerUp != POWER_UP){
            updatePowerUp();
            PrevPowerUp = POWER_UP;
        }
        if (previousHealth != health){
            updateHealthPanel(health);
            previousHealth=health;
        }

        stage.draw();
    }

    public void updateCoconut(){
        coconut.remove();
        coconut = new Table();
        coconut.setX(1150);
        coconut.setY(280);

        cocoImg = new Image(new Texture("controller/" + numberofCoconut + "_coconut.png"));
        cocoImg.setSize(150, 150);
        cocoImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousCoconutPress = coconutPress;
                coconutPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousCoconutPress = coconutPress;
                coconutPress = false;
            }
        });

        coconut.add(cocoImg).size(cocoImg.getWidth(), cocoImg.getHeight());
        stage.addActor(coconut);
    }

    public void updatePowerUp(){
        powerup.remove();
        powerup = new Table();
        powerup.setX(1350);
        powerup.setY(340);
        if(POWER_UP == Pirate.PowerUpHolding.SHIELD)
            emptyImg = new Image(new Texture("controller/shield_"+character+".png"));
        else
            emptyImg = new Image(new Texture("controller/"+POWER_UP+".png"));
        emptyImg.setSize(150, 150);
        emptyImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                previousPowerUpPress = powerUpPress;
                powerUpPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                previousPowerUpPress = powerUpPress;
                powerUpPress = false;
            }
        });

        powerup.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        stage.addActor(powerup);

    }

    public void updateHealthPanel(int health){
        healthPanel.remove();
        healthPanel=new Table();
        healthPanel.bottom().left();
        healthPanel.setX(90);
        healthPanel.setY(250);

        //Bomb
        healthImg = new Image(new Texture("img/hud/"+health+".png"));
        healthImg.setSize(256 * hud_ratio, 768 * hud_ratio);
        healthPanel.add(healthImg).size(healthImg.getWidth(), healthImg.getHeight());
        stage.addActor(healthPanel);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void addCharacter(String character){
        this.character = character;
    }

    public void changeCoconut(int i){
        numberofCoconut = i;
    }

    public void changePowerUp(Pirate.PowerUpHolding pu){
        POWER_UP = pu;
    }

    public void changeHealth(int health){ this.health=health;}
}