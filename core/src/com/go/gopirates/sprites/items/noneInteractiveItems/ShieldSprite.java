package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    int playerId;

    public ShieldSprite(PlayScreen screen, int playerId) {
        PirateGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        setSize(300/PirateGame.PPM,300/PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
        setRegion(screen.shieldTextureRegion);
        this.playerId = playerId;
    }


    public void update(float dt) {
        stateTime += dt;
        setPosition(screen.getPirate(playerId).b2body.getPosition().x - 150 / PirateGame.PPM, screen.getPirate(playerId).b2body.getPosition().y - 150 / PirateGame.PPM);
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
