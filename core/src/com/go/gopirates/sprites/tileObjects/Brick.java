package com.go.gopirates.Sprites.TileObjects;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.go.gopirates.PirateGame;
import com.go.gopirates.Scenes.Hud;
import com.go.gopirates.Screens.PlayScreen;
import com.go.gopirates.Sprites.Pirate;

/**
 * Created by Amy on 25/2/16.
 */
public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Pirate pirate) {
        if(pirate.isBig()) {
            setCategoryFilter(PirateGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            PirateGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        PirateGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }

}