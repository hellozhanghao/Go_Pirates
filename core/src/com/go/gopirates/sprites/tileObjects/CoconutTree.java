package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.ItemDef;
import com.go.gopirates.sprites.items.powerUps.CoconutPowerUp;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutTree extends InteractiveTileObject {


    public CoconutTree(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COCONUT_TREE_BIT);
    }

    @Override
    public void onHit() {
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                CoconutPowerUp.class));
    }
}
