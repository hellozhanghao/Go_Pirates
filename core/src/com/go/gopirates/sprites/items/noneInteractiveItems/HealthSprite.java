package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by Amy on 6/4/16.
 */
public class HealthSprite extends NonInteractiveSprites{



    PlayScreen screen;
    World world;
    Body b2body;

    public HealthSprite(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        setOrigin(256*1.5f/2/PirateGame.PPM,768*1.5f/2/PirateGame.PPM);
        setSize(256*1.5f/PirateGame.PPM,768*1.5f/PirateGame.PPM);
        setPosition(0.75f*PirateGame.TILE_SIZE/PirateGame.PPM,
                    2.25f*PirateGame.TILE_SIZE/PirateGame.PPM);
    }


    @Override
    public void update(float dt) {
        setRegion(screen.healthTexture.get(screen.getPirate().health));
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
