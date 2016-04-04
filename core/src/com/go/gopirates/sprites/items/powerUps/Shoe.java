package com.go.gopirates.sprites.items.powerUps;

import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Shoe extends PowerUp {
    public Shoe(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }


    @Override
    public void use() {
        destroy();
        System.out.println("Take SHOE");
        if (screen.getPirate().powerUpHolding == Pirate.PowerUpHolding.NONE)
            screen.getPirate().powerUpHolding= Pirate.PowerUpHolding.SHOE;
    }
}
