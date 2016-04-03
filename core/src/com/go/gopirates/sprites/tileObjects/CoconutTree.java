package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

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

    }
}
