package com.go.gopirates;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.go.gopirates.screen.PlayScreen;


public class PirateGame extends Game {
    //Virtual Screen size and Box2D Scale(Pixels Per Meter)
    public static final int V_WIDTH = 3200;
    public static final int V_HEIGHT = 1800;
    public static final float PPM = 10;
    public static final float EDGE_POSITION_X = 400f;
    public static final float EDGE_POSITION_Y = 400f;

    //Tile Map setting
    public static final int TILE_SIZE=256;
    public static final int MAP_SIZE=19;

    //User Selct
    public static final int PLAYER_ID=2;

    //Settings:
    public static final int VELOCITY=150;
    public static final float BUTTON_INTERVAL=1f;
    public static final float POWERUP_TIME=10f;


    //Box2D Collision Bits
    //Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short COCONUT_TREE_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ROCK_BIT = 4;
    public static final short BARREL_BIT = 8;
    public static final short EXPLOSION_BIT = 16;
    public static final short TNT_BIT = 32;
    public static final short TREASURE_BIT = 64;
    public static final short SHIELD_BIT = 128;
    public static final short BOMB_BIT = 256;
    public static final short COCONUT_BIT = 2048;
    public static final short SWORD_BIT = 4096;
    public static final short POWERUP_BIT = 8192;

    public static SpriteBatch batch;

    /* WARNING Using AssetManager in a static way can cause issues, especially on Android.
    Instead you may want to pass around Assetmanager to those the classes that need it.
    We will use it in the static context to save time for now. */
    public static AssetManager manager;

    @Override
    public void create () {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.finishLoading();

        setScreen(new PlayScreen(this));
    }


    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render () {
        super.render();
    }

    private PlayServices playServices;

    public PirateGame(PlayServices playServices){
        this.playServices=playServices;
    }

    public PirateGame(){}
}
