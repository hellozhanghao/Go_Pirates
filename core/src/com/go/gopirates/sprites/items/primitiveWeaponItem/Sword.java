package com.go.gopirates.sprites.items.primitiveWeaponItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Sword extends PrimitiveWeaponItem {

    private final float SWORD_LENGTH = PirateGame.TILE_SIZE / 8;
    private float stateTimer;
    private boolean setSwordTofalse;
    private int playerId;


    public Sword(PlayScreen screen, int playerId) {
        super(screen);
        stateTimer=0;
        screen.getPirate(playerId).swordInUse = true;
        setSwordTofalse=false;
        this.playerId = playerId;
        defineItem();
    }

    @Override
    public void defineItem() {
        PirateGame.manager.get("audio/sounds/sword.mp3", Sound.class).play();
        BodyDef bodyDef = new BodyDef();
        float posX = screen.getPirate(playerId).b2body.getPosition().x;
        float posY = screen.getPirate(playerId).b2body.getPosition().y;

        switch (screen.getPirate(playerId).direction) {
            case UP:
                bodyDef.position.set(posX, posY + ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM);
                break;
            case DOWN:
                bodyDef.position.set(posX, posY - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM);
                break;
            case LEFT:
                bodyDef.position.set(posX - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, posY);
                break;
            case RIGHT:
                bodyDef.position.set(posX + ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, posY);
                break;
        }
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(SWORD_LENGTH / PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.SWORD_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT;
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(this);
    }



    public void update(float dt){
        super.update(dt);
        stateTimer+=dt;
        float posX = screen.getPirate(playerId).b2body.getPosition().x;
        float posY = screen.getPirate(playerId).b2body.getPosition().y;

        if (!destroyed) {
            switch (screen.getPirate(playerId).direction) {
                case UP:
                    body.setTransform(posX, posY + ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, 0);
                    break;
                case DOWN:
                    body.setTransform(posX, posY - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, 0);
                    break;
                case LEFT:
                    body.setTransform(posX - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, posY, 0);
                    break;
                case RIGHT:
                    body.setTransform(posX + ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH * 1.5f)) / PirateGame.PPM, posY, 0);
                    break;
            }
        }
        if (stateTimer>PirateGame.SWORD_VALID_TIME){
            destroy();
            if (!setSwordTofalse){
                screen.getPirate(playerId).swordInUse = false;
                setSwordTofalse=true;
            }
        }
    }

    @Override
    public void hitOnPlayer() {
        super.hitOnPlayer();
        Gdx.app.log("Weapon","Hit by Sword");
    }

    public void draw(Batch batch){

    }
}