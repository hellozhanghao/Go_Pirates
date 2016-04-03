package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.ItemDef;
import com.go.gopirates.sprites.items.powerUps.Shield;
import com.go.gopirates.sprites.items.powerUps.Shoe;
import com.go.gopirates.sprites.items.powerUps.TNTPowerUp;


/**
 * Created by Amy on 1/3/16.
 */

public class Barrel extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;

    public Barrel(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.BARREL_BIT);
    }


    @Override
    public void onHit() {
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
        if (object.getProperties().containsKey("shield")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shield.class));
        } else if (object.getProperties().containsKey("shoes")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    Shoe.class));
        } else if (object.getProperties().containsKey("TNT")) {
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    TNTPowerUp.class));
        }
    }
//
//    // TODO: 27/3/16 Handle reef destroyed
//    public void destroyReef() {
//
//    }
}
