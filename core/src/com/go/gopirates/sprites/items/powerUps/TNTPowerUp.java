package com.go.gopirates.sprites.items.powerUps;

import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */
public class TNTPowerUp extends PowerUp{
    public TNTPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }



    @Override
    public void use() {
        destroy();
        System.out.println("Take TNT");
        screen.getPirate().powerUpHolding= Pirate.PowerUpHolding.TNT;
    }
}
