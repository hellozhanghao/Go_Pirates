package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.go.gopirates.PirateGame;
import com.go.gopirates.control.Controller;
import com.go.gopirates.sprites.Pirate;
import com.go.gopirates.sprites.items.ItemDef;
import com.go.gopirates.sprites.items.explosiveItems.Bomb;
import com.go.gopirates.sprites.items.explosiveItems.ExplosiveItem;
import com.go.gopirates.sprites.items.noneInteractiveItems.ShieldSprite;
import com.go.gopirates.sprites.items.powerUps.PowerUp;
import com.go.gopirates.sprites.items.powerUps.Shield;
import com.go.gopirates.tools.B2WorldCreator;
import com.go.gopirates.tools.WorldContactListener;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by Amy on 25/2/16.
 */
public class PlayScreen implements Screen {
    public static boolean alreadyDestroyed = false;
    //Reference to our Game, used to set Screens
    private PirateGame game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
//    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
//    private Pirate player;
    private ArrayList<Pirate> players;

    private Music music;

    private boolean changeScreen;
    private Stage stage;
    private Texture fadeOutTexture;

    private Array<PowerUp> powerUps;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;


    private Controller controller;

    //Control number of bombs:
    private float bombConfirmTimer;
    private boolean bombConfirm;


    public PlayScreen(PirateGame game) {
        atlas = new TextureAtlas("img/pirates.pack");
        this.game = game;
//        this.thisPlayerIndex=thisPlayerIndex;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, PirateGame.V_WIDTH, PirateGame.V_HEIGHT);
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
//        map=maploader.load("tiled_map/map0.tmx");
        map = maploader.load("tiled_map/testMap.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);
        //initialize gamecame
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        //create mario in our game world
//        for (int i = 0; i < 4; i++) {
//            players.add(new Pirate(this));
//        }
        players = new ArrayList<Pirate>();
        for (int i = 0; i < 4; i++) {
            players.add(new Pirate(this, i));
        }
//

        //create our game HUD for scores/timers/level info
//        hud = new Hud(PirateGame.batch,players.get(thisPlayerIndex));

        controller = new Controller();
        controller.create();

        world.setContactListener(new WorldContactListener(this));
//
//        music = PirateGame.manager.get("audio/music/mario_music.ogg", Music.class);
//        music.setLooping(true);
//        music.setVolume(0.3f);
//        music.play();
//
        powerUps = new Array<PowerUp>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        bombConfirmTimer = 0;
        bombConfirm = true;
    }

    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Shield.class) {
                powerUps.add(new Shield(this, idef.position.x, idef.position.y));
            }
//            if (idef.type == Shoes.class) {
//                powerUps.add(new Shoes(this, idef.position.x, idef.position.y));
//            }
//            if (idef.type == Tnt.class) {
//                powerUps.add(new Tnt(this, idef.position.x, idef.position.y));
//            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void handleInput(float dt) {
        bombConfirmTimer += dt;
        if (bombConfirmTimer > PirateGame.BUTTON_INTERVAL) {
            bombConfirmTimer = 0;
            bombConfirm = true;
        }


        Pirate player = players.get(PirateGame.PLAYER_ID);

        //For phone:
        player.b2body.setLinearVelocity(controller.touchpad.getKnobPercentX() * PirateGame.VELOCITY,
                controller.touchpad.getKnobPercentY() * PirateGame.VELOCITY);

        //for keyboard:
        if (controller.upPressed)
            player.b2body.setLinearVelocity(0, PirateGame.VELOCITY);
        if (controller.downPressed)
            player.b2body.setLinearVelocity(0, -PirateGame.VELOCITY);
        if (controller.leftPressed)
            player.b2body.setLinearVelocity(-PirateGame.VELOCITY, 0);
        if (controller.rightPressed)
            player.b2body.setLinearVelocity(PirateGame.VELOCITY, 0);

        if (!controller.previousBombPress & controller.bombPress & bombConfirm) {
            player.explosiveItems.add(new Bomb(this, player.b2body.getPosition().x, player.b2body.getPosition().y));
            bombConfirm = false;
        }
        if (!controller.previousPowerUpPress & controller.powerUpPress) {
            switch (player.powerUpHolding) {
                case SHIED:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.redefinePirateWithShield();
                    player.otherSprites.add(new ShieldSprite(this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                    break;
                case NONE:

            }
        }
//        else if (controller.isPistolPressed())
//            player.fire();
//        else if (controller.isSwordPressed())
//            player.useSword();
//        else if (controller.isPowerUpPressed())
//            player.usePowerUp();

    }


    public void update(float dt) {
        //handle user input first
        handleInput(dt);
        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        for (PowerUp item : powerUps)
            item.update(dt);
        for (Pirate player : players) {
            player.update(dt);
            for (ExplosiveItem explosiveItems : player.explosiveItems) {
                explosiveItems.update(dt);
            }

            for (ShieldSprite sprite : player.otherSprites) {
                sprite.update(dt);
            }
        }


//
//        hud.update(dt);

        /*//attach our gamecam to our players.x coordinate
        if(player.currentState != Pirate.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }*/

        Pirate player = getPirate();
        //update our gamecam with correct coordinates after changes
        //x position
        gamecam.setToOrtho(false, PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM);


        if (player.b2body.getPosition().x < (PirateGame.V_WIDTH / PirateGame.PPM) / 2)
            gamecam.position.x = gamePort.getWorldWidth() / 2;
        else if (player.b2body.getPosition().x > (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE / PirateGame.PPM) - (PirateGame.V_WIDTH / PirateGame.PPM) / 2)
            gamecam.position.x = (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE / PirateGame.PPM) - (PirateGame.V_WIDTH / PirateGame.PPM) / 2;
        else
            gamecam.position.x = player.b2body.getPosition().x;
        //y position
        if (player.b2body.getPosition().y < (PirateGame.V_HEIGHT / PirateGame.PPM) / 2)
            gamecam.position.y = gamePort.getWorldHeight() / 2;
        else if (player.b2body.getPosition().y > (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE / PirateGame.PPM) - (PirateGame.V_HEIGHT / PirateGame.PPM) / 2)
            gamecam.position.y = (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE / PirateGame.PPM) - (PirateGame.V_HEIGHT / PirateGame.PPM) / 2;
        else
            gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        PirateGame.batch.begin();
        PirateGame.batch.setProjectionMatrix(gamecam.combined);
        for (int i = 0; i < 4; i++) {
            players.get(i).draw(PirateGame.batch);
        }

        PirateGame.batch.end();

        //Set our batch to now draw what the Hud camera sees.
//        PirateGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);


//        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        controller.render();


//        if(gameOver()){
//            game.setScreen(new GameOverScreen(game));
//            dispose();
//        }

    }


    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
//        hud.dispose();
    }


//    public Hud getHud(){ return hud; }


    // TODO: 3/4/16 Add multiplayer support
    public Pirate getPirate() {
        return players.get(PirateGame.PLAYER_ID);
    }

}