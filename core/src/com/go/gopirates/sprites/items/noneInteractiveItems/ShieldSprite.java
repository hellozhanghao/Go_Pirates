package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class ShieldSprite extends NonInteractiveSprites {
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;
    TextureRegion textureRegion;

    public ShieldSprite(PlayScreen screen) {
        PirateGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        setSize(300/PirateGame.PPM,300/PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
        setRegion(new TextureRegion(new Texture("img/objects/shield.png"),0,0,300,300));
    }


    public void update(float dt) {
        stateTime += dt;
        setPosition(screen.getPirate().b2body.getPosition().x - 150 / PirateGame.PPM, screen.getPirate().b2body.getPosition().y - 150 / PirateGame.PPM);
        if ((stateTime > PirateGame.POWERUP_TIME) & !destroyed){
            setToDestroy();
            destroyed=true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }


    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }
}
