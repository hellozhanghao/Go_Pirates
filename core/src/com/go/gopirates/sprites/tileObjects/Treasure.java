package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;


/**
 * Created by Amy on 2/3/16.
 */
public class Treasure extends InteractiveTileObject {

    public Treasure(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.TREASURE_BIT);
    }
    @Override
    public void onHit() {
        Gdx.app.log("Treasure", "Find Treasure");
        screen.game.playServices.broadcastMessage("Treasure");
        screen.game.sessionInfo.mState = "win";
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
        screen.checkWin();
    }
    public void onHitbyOthers(){
        getCell().setTile(null);
        setCategoryFilter(PirateGame.NOTHING_BIT);
        screen.checkWin();
    }

}
