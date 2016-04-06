package com.go.gopirates.sprites.items.powerUps;

import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutPowerUp extends PowerUp {

    public CoconutPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // TODO: 4/4/16 Add texture
    }

    @Override
    public void use() {
        destroy();
        if (screen.getPirate().numberOfCoconut<3){
            screen.getPirate().numberOfCoconut++;
            screen.updateCoconut();
        }

    }
}
