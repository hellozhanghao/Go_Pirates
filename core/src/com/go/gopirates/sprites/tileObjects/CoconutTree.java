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
    private final int MAX_HIT = 3;
    private int hit;

    public CoconutTree(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COCONUT_TREE_BIT);
        hit=0;
    }

    @Override
    public void onHit() {
        if (hit >= MAX_HIT){
            getCell().setTile(null);
            setCategoryFilter(PirateGame.NOTHING_BIT);
            return;
        }
        hit ++;
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y - 10),
                CoconutPowerUp.class));
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 20, body.getPosition().y + 10),
                CoconutPowerUp.class));
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 10, body.getPosition().y + 20),
                CoconutPowerUp.class));

    }
}
