package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;


/**
 * Created by Amy on 1/3/16.
 */

public class Rock extends InteractiveTileObject {

    public Rock(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.ROCK_BIT);
    }

    @Override
    public void onHit() {

    }
}
