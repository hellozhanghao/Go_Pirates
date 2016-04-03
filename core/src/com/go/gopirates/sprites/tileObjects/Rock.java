package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;


/**
 * Created by Amy on 1/3/16.
 */

public class Rock extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;

    public Rock(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.ROCK_BIT);
    }

    @Override
    public void onHit() {

    }

}
