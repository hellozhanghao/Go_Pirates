package com.go.gopirates;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;


public class PirateGame extends Game {
    //Virtual Screen size and Box2D Scale(Pixels Per Meter)
    public static final int V_WIDTH = 3200;
    public static final int V_HEIGHT = 1800;
    public static final float PPM = 10;

    //Tile Map setting
    public static final int TILE_SIZE=256;
    public static final int MAP_SIZE_Y =19;
    public static final int MAP_SIZE_X =25;

    //User Selct
    public static final int PLAYER_ID=3;

    //Settings:
    public static  int DEFAULT_VELOCITY =100;
    public static final float BUTTON_INTERVAL=1f;
    public static final float POWERUP_TIME=10f;
    public static final int ININTIAL_HEALTH=5;
    public static final float PROTECTED_TIME_AFTER_DECREASE_HEALTH=1.5f;
    public static final float SWORD_VALID_TIME=2f;
    public static final int INITIAL_COCONUT = 1;
    public static final Pirate.PowerUpHolding INITIAL_POWERUP = Pirate.PowerUpHolding.NONE;


    //Box2D Collision Bits
    //Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short COCONUT_TREE_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ROCK_BIT = 4;
    public static final short BARREL_BIT = 8;
    public static final short EXPLOSION_BIT = 16;
    public static final short SWORD_BIT = 32;
    public static final short TREASURE_BIT = 64;
    public static final short SHIELD_BIT = 128;
    public static final short BOMB_BIT = 256;
    public static final short COCONUT_BIT = 2048;
    public static final short UNUSED = 4096;
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
        manager.load("audio/sounds/bomb.ogg", Sound.class);
        manager.load("audio/music/pirate.mp3", Music.class);
        manager.load("audio/sounds/sword.mp3", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
        manager.load("audio/sounds/powerup.wav", Sound.class);
        manager.load("audio/sounds/powerdown.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/mariodie.wav", Sound.class);

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
