package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.Gdx;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 3/4/16.
 */
public class ShoeSprite extends NonInteractiveSprites {

    int times=10;
    float stateTimer;
    boolean shoeValid;
    public ShoeSprite(){
        Gdx.app.log("Power up","Shoe used");
        stateTimer=0;
        shoeValid=true;
        PirateGame.DEFAULT_VELOCITY=PirateGame.DEFAULT_VELOCITY*times;
    }
    @Override
    public void update(float dt) {
        stateTimer+=dt;
        if ((stateTimer>PirateGame.POWERUP_TIME)&shoeValid){
            PirateGame.DEFAULT_VELOCITY=PirateGame.DEFAULT_VELOCITY/times;
            shoeValid=false;
            Gdx.app.log("Power up","Shoe removed");
        }
    }
}
