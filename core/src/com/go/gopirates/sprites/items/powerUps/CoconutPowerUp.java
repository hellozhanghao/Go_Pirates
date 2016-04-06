package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.graphics.Texture;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutPowerUp extends PowerUp {

    public CoconutPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // TODO: 4/4/16 Add texture
        Texture coconutTexture=new Texture("img/objects/coconut.png");
        setRegion(coconutTexture);
        setSize(PirateGame.TILE_SIZE / 2 / PirateGame.PPM, PirateGame.TILE_SIZE / 2 / PirateGame.PPM);
        setPosition(x/PirateGame.PPM, y/PirateGame.PPM);
        System.out.println(x+" "+y);
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
