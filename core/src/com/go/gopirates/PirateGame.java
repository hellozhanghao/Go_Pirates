package com.go.gopirates;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.go.gopirates.screen.LoginScreen;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;
import com.go.gopirates.sprites.items.explosiveItems.Bomb;
import com.go.gopirates.sprites.items.explosiveItems.TNT;
import com.go.gopirates.sprites.items.noneInteractiveItems.ShieldSprite;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Coconut;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Sword;


public class PirateGame extends Game {
    //Virtual Screen size and Box2D Scale(Pixels Per Meter)
    public static final int V_WIDTH = 3200;
    public static final int V_HEIGHT = 1800;
    public static final float PPM = 10;

    //Tile Map setting
    public static final int TILE_SIZE=256;
    public static final int MAP_SIZE_Y =19;
    public static final int MAP_SIZE_X =25;
    public static final float BUTTON_INTERVAL=1f;
    public static final float POWERUP_TIME=10f;
    public static final int ININTIAL_HEALTH=10;
    public static final float PROTECTED_TIME_AFTER_DECREASE_HEALTH=1.5f;
    public static final float SWORD_VALID_TIME=2f;
    public static final int INITIAL_COCONUT = 1;
    public static final Pirate.PowerUpHolding INITIAL_POWERUP = Pirate.PowerUpHolding.NONE;
    public static  final float EXPLOSION_FRAME_DURATION=0.08f;
    public static final float MIN_TIME_BETWEEN_SWORD=10f;
    //Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short COCONUT_TREE_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ROCK_BIT = 4;
    public static final short BARREL_BIT = 8;
    public static final short BOMB_EXPLOSION_BIT = 16;
    public static final short SWORD_BIT = 32;
    public static final short TREASURE_BIT = 64;
    public static final short SHIELD_BIT = 128;
    public static final short BOMB_BIT = 256;
    public static final short COCONUT_BIT = 2048;
    public static final short TNT_EXPLOSION_BIT = 4096;
    public static final short POWERUP_BIT = 8192;
    public static final short OTHER_PLAYER_BIT = 16384;
    //User Selct
    public static int PLAYER_ID;
    public static int NUMBER_OF_PLAYERS;
    //Settings:
    public static int DEFAULT_VELOCITY = 100;
    public static SpriteBatch batch;

    /* WARNING Using AssetManager in a static way can cause issues, especially on Android.
    Instead you may want to pass around Assetmanager to those the classes that need it.
    We will use it in the static context to save time for now. */
    public static AssetManager manager;
    public static PlayScreen screen;
    public SessionInfo sessionInfo;
    public PlayServices playServices;


    public PirateGame(PlayServices playServices, SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
        this.playServices = playServices;
    }

    public static void resloveMessage(String message) {

        String[] words = message.split(";");
        if (words[0].equals("Location")) {
            int playerId = Integer.parseInt(words[1]);
            float x = Float.parseFloat(words[2]);
            float y = Float.parseFloat(words[3]);
            Pirate.Direction direction = Pirate.Direction.valueOf(words[4]);
            Pirate.State currentState = Pirate.State.valueOf(words[5]);
            screen.getPirate(playerId).b2body.setTransform(x, y, 0);
            screen.getPirate(playerId).direction = direction;
            screen.getPirate(playerId).currentState = currentState;
        } else {
            Gdx.app.log("PirateGame", message);
            int playerId = Integer.parseInt(words[1]);
            Pirate player = screen.getPirate(playerId);
            String action = words[0];
            Gdx.app.log("ACTION", action + " " + playerId);
            if (action.equals("Bomb")) {
                screen.getPirate().explosiveItems.add(new Bomb(screen, player.b2body.getPosition().x, player.b2body.getPosition().y));
            } else if (action.equals("TNT")) {
                screen.getPirate(playerId).explosiveItems.add(new TNT(screen, player.b2body.getPosition().x, player.b2body.getPosition().y));
            } else if (action.equals("Coconut")) {
                screen.getPirate(playerId).primitiveWeaponItems.add(new Coconut(screen, player.b2body.getPosition().x, player.b2body.getPosition().y, player.direction));
            } else if (action.equals("Sword")) {
                screen.getPirate(playerId).primitiveWeaponItems.add(new Sword(screen, Integer.valueOf(playerId)));}
            else if (words[0].equals("Shield")) {
                screen.getPirate(playerId).nonInteractiveSprites.add(new ShieldSprite(screen, playerId));
            } else if (words[0].equals("Treasure")) {
                screen.game.sessionInfo.mState = "lose";
            } else if (words[0].equals("Die")) {
                screen.getPirate(playerId).destroy();
            }
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/sounds/bomb.ogg", Sound.class);
        manager.load("audio/music/pirate.mp3", Music.class);
        manager.load("audio/sounds/sword.mp3", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
        manager.load("audio/sounds/powerup.wav", Sound.class);
        manager.load("audio/sounds/powerdown.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/mariodie.wav", Sound.class);
        manager.finishLoading();

        setScreen(new LoginScreen(this));
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

    // TODO: 9/4/16 Synchronize sword animaition
    // TODO: 9/4/16 Add coconut
    // TODO: 9/4/16 Add wait screen and gameover screen



}
