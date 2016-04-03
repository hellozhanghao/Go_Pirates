package com.go.gopirates.sprites.items.powerUps;

import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Shield extends PowerUp {
    public Shield(PlayScreen screen, float x, float y) {
        super(screen, x, y);

//        setRegion(screen.getAtlas().findRegion("shield"), 0, 0, 4, 4);
//        velocity = new Vector2(0.7f, 0);

    }


    @Override
    public void use() {
        destroy();
        System.out.println("Take shield");
        screen.getPirate().powerUpHolding= Pirate.PowerUpHolding.SHIED;
    }

}
