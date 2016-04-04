package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.Gdx;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 3/4/16.
 */
public class ShoeSprite extends NonInteractiveSprites {

    int times=10;
    float stateTimer;
    boolean shoesOn;
    public ShoeSprite(){
        Gdx.app.log("Power up","Shoes used");
        stateTimer = 0;
        shoesOn = true;
        PirateGame.DEFAULT_VELOCITY = PirateGame.DEFAULT_VELOCITY * times;
    }

    @Override
    public void update(float dt) {
        stateTimer+=dt;
        if ((stateTimer>PirateGame.POWERUP_TIME) & shoesOn){
            PirateGame.DEFAULT_VELOCITY = PirateGame.DEFAULT_VELOCITY/times;
            shoesOn = false;
            Gdx.app.log("Power up","Shoes removed");
        }
    }
}
