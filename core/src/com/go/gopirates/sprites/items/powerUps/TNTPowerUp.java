package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */
public class TNTPowerUp extends PowerUp{
    public TNTPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(new TextureRegion(new Texture("powerup/tnt.png"), 0, 0, 256, 256));
        setSize(150 / PirateGame.PPM, 150 / PirateGame.PPM);
        setPosition(x - 75 / PirateGame.PPM, y - 75 / PirateGame.PPM);
    }



    @Override
    public void use() {
        destroy();
        System.out.println("Take TNT");
        if (screen.getPirate().powerUpHolding == Pirate.PowerUpHolding.NONE){
            screen.getPirate().powerUpHolding= Pirate.PowerUpHolding.TNT;
            screen.updatePowerUp();
        }
    }
}
