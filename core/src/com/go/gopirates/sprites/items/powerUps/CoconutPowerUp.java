package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutPowerUp extends PowerUp {

    public CoconutPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // TODO: 4/4/16 Add texture
        setRegion(new TextureRegion(new Texture("img/objects/coconut.png"), 0, 0, 256, 256));
        setSize(200 / PirateGame.PPM, 200 / PirateGame.PPM);
        setPosition(x - 100 / PirateGame.PPM, y - 100 / PirateGame.PPM);
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
