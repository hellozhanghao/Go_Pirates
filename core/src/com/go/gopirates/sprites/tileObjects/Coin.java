package com.go.gopirates.Sprites.TileObjects;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.go.gopirates.PirateGame;
import com.go.gopirates.Scenes.Hud;
import com.go.gopirates.Screens.PlayScreen;
import com.go.gopirates.Sprites.Items.ItemDef;
import com.go.gopirates.Sprites.Items.Mushroom;
import com.go.gopirates.Sprites.Pirate;


/**
 * Created by Amy on 25/2/16.
 */
public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(PirateGame.COIN_BIT);
    }

    @Override
    public void onHeadHit(Pirate pirate) {
        if(getCell().getTile().getId() == BLANK_COIN)
            PirateGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / PirateGame.PPM),
                        Mushroom.class));
                PirateGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                PirateGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }
    }
}
