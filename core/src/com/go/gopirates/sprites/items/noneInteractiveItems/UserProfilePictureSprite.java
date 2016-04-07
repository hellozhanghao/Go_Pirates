package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 7/4/16.
 */
public class UserProfilePictureSprite extends NonInteractiveSprites {

    private PlayScreen screen;
    World world;

    public UserProfilePictureSprite(PlayScreen screen,String character) {
        this.screen = screen;
        this.world = screen.getWorld();
        setSize(211 * 1.5f / PirateGame.PPM, 216 * 1.5f / PirateGame.PPM);
        setOrigin(256 * 1.5f / 2 / PirateGame.PPM, 256 * 1.5f / 2 / PirateGame.PPM);
        if (character.equals("Sophia")){
            setRegion(this.screen.getAtlas().findRegion("hud_profile"),0,0,211,216);
        }else if (character.equals("Taka")){
            setRegion(this.screen.getAtlas().findRegion("profile"),211,0,211,216);
        }else if (character.equals("Zack")){
            setRegion(this.screen.getAtlas().findRegion("profile"),422,0,211,216);
        }else if (character.equals("Thomas")){
            setRegion(this.screen.getAtlas().findRegion("profile"),633,0,211,216);
        }

    }


    @Override
    public void update(float dt) {
        setPosition(0.93f*PirateGame.TILE_SIZE/PirateGame.PPM,
                5.35f*PirateGame.TILE_SIZE/PirateGame.PPM);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

}
