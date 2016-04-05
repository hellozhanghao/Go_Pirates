package com.go.gopirates.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.ItemDef;
import com.go.gopirates.sprites.items.powerUps.CoconutPowerUp;

import java.util.Random;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutTree extends InteractiveTileObject {
    private int hit = 0;

    public CoconutTree(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COCONUT_TREE_BIT);
    }

    @Override
    public void onHit() {
        if (hit >= MAX_HIT){
            getCell().setTile(null);
            setCategoryFilter(PirateGame.NOTHING_BIT);
            return;

        }
        hit ++;
        Random rand = new Random();
        int randomNum = rand.nextInt(6);
        switch (randomNum){
            case 0:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 10, body.getPosition().y - 10),
                        CoconutPowerUp.class));
                break;
            case 1:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y + 10),
                        CoconutPowerUp.class));
                break;
            case 2:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 10, body.getPosition().y - 10),
                        CoconutPowerUp.class));
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y + 10),
                        CoconutPowerUp.class));
                break;
            case 3:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y - 10),
                        CoconutPowerUp.class));
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y + 10),
                        CoconutPowerUp.class));
                break;
            case 4:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 10, body.getPosition().y - 10),
                        CoconutPowerUp.class));
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 20, body.getPosition().y + 10),
                        CoconutPowerUp.class));
                break;
            case 5:
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 10, body.getPosition().y - 10),
                        CoconutPowerUp.class));
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x - 20, body.getPosition().y + 10),
                        CoconutPowerUp.class));
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 10, body.getPosition().y + 20),
                        CoconutPowerUp.class));
                break;
            default:
                break;
        }
    }
}
