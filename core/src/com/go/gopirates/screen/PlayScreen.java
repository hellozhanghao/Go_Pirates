package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.go.gopirates.PirateGame;
import com.go.gopirates.control.Controller;
import com.go.gopirates.sprites.Pirate;
import com.go.gopirates.sprites.items.ItemDef;
import com.go.gopirates.sprites.items.explosiveItems.Bomb;
import com.go.gopirates.sprites.items.explosiveItems.ExplosiveItem;
import com.go.gopirates.sprites.items.explosiveItems.TNT;
import com.go.gopirates.sprites.items.noneInteractiveItems.BombExplosionDetector;
import com.go.gopirates.sprites.items.noneInteractiveItems.NonInteractiveSprites;
import com.go.gopirates.sprites.items.noneInteractiveItems.ShieldSprite;
import com.go.gopirates.sprites.items.noneInteractiveItems.ShoeSprite;
import com.go.gopirates.sprites.items.powerUps.CoconutPowerUp;
import com.go.gopirates.sprites.items.powerUps.PowerUp;
import com.go.gopirates.sprites.items.powerUps.Shield;
import com.go.gopirates.sprites.items.powerUps.Shoe;
import com.go.gopirates.sprites.items.powerUps.TNTPowerUp;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Coconut;
import com.go.gopirates.sprites.items.primitiveWeaponItem.PrimitiveWeaponItem;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Sword;
import com.go.gopirates.tools.B2WorldCreator;
import com.go.gopirates.tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amy on 25/2/16.
 */
public class PlayScreen implements Screen {
    public static boolean alreadyDestroyed = false;
    public Animation explosionAnimation;
    public Animation coconutAnimation;
    public TextureRegion shieldTextureRegion;
    //Reference to our Game, used to set Screens
    public PirateGame game;
    //Multiplayer
    private float updateLocationTimer = 0;
    private TextureAtlas atlas;
    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Controller controller;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Array<Pirate> players;

    //powerups
    private Array<PowerUp> powerUps;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    //Control number of bombs:
    private float bombConfirmTimer;
    private boolean bombConfirm;

    private float swordConfirmTimer;
    private boolean swordConfirm;

    private float coconutConfirmTimer;
    private boolean coconutConfirm;

    public PlayScreen(PirateGame game) {
        loadAssets();
        this.game = game;
        PirateGame.screen=this;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, PirateGame.V_WIDTH, PirateGame.V_HEIGHT);
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();

//        map = maploader.load("tiled_map/testMap2.tmx");
        map = maploader.load("tiled_map/smallMap.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateGame.PPM);
        //initialize gamecame
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        //create pirates in our game world
        players = new Array<Pirate>();
        switch (PirateGame.NUMBER_OF_PLAYERS){
            case 2:
                players.add(new Pirate(this, 0,"Sophia"));
                players.add(new Pirate(this, 1, "Taka"));
                break;
            case 3:
                players.add(new Pirate(this, 0,"Sophia"));
                players.add(new Pirate(this, 1, "Taka"));
                players.add(new Pirate(this, 2, "Thomas"));
                break;
            case 4:
                players.add(new Pirate(this, 0,"Sophia"));
                players.add(new Pirate(this, 1, "Taka"));
                players.add(new Pirate(this, 2, "Thomas"));
                players.add(new Pirate(this, 3, "Zack"));
        }

        world.setContactListener(new WorldContactListener(this));


        powerUps = new Array<PowerUp>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        //controller
        controller = new Controller();
        controller.addCharacter(getPirate().character);
        controller.create();
        bombConfirmTimer = 0;
        bombConfirm = true;
        swordConfirmTimer=0;
        swordConfirm=true;
        coconutConfirmTimer=0;
        coconutConfirm=true;
        controller.setUpUserProfilePanel(getPirate().character);

    }

    public void loadAssets(){
        atlas = new TextureAtlas("img/pirates.pack");
        Texture explosionTexture=new Texture("img/explosion.png");

        Array<TextureRegion> explosionFrames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 6; j++)
                explosionFrames.add(new TextureRegion( explosionTexture,
                        i*PirateGame.TILE_SIZE, j*PirateGame.TILE_SIZE, PirateGame.TILE_SIZE, PirateGame.TILE_SIZE));
        explosionAnimation = new Animation(PirateGame.EXPLOSION_FRAME_DURATION,explosionFrames);

        Array<TextureRegion> coconutFrames = new Array<TextureRegion>();
        Texture coconutTexture=new Texture("img/objects/coconut_animation.png");
        for (int i = 0; i < 4; i++)
            coconutFrames.add(new TextureRegion(coconutTexture, PirateGame.TILE_SIZE*i, 0, PirateGame.TILE_SIZE, PirateGame.TILE_SIZE));
        coconutAnimation = new Animation(0.1f, coconutFrames);

        shieldTextureRegion = new TextureRegion(new Texture("img/objects/shield.png"), 0, 0, 300, 300);
    }

    public void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Shield.class)
                powerUps.add(new Shield(this, idef.position.x, idef.position.y));
            if (idef.type == Shoe.class)
                powerUps.add(new Shoe(this, idef.position.x, idef.position.y));
            if (idef.type == TNTPowerUp.class)
                powerUps.add(new TNTPowerUp(this, idef.position.x, idef.position.y));
            if (idef.type == CoconutPowerUp.class)
                powerUps.add(new CoconutPowerUp(this, idef.position.x, idef.position.y));
        }
    }

    public void handleInput(float dt) {
        bombConfirmTimer += dt;
        if (bombConfirmTimer > PirateGame.BUTTON_INTERVAL) {
            bombConfirmTimer = 0;
            bombConfirm = true;
        }
        swordConfirmTimer+=dt;
        if (swordConfirmTimer > PirateGame.MIN_TIME_BETWEEN_SWORD){
            swordConfirmTimer = 0;
            swordConfirm =true;
        }
        coconutConfirmTimer+=dt;
        if (coconutConfirmTimer > PirateGame.BUTTON_INTERVAL){
            coconutConfirmTimer = 0;
            coconutConfirm =true;
        }

        Pirate player = players.get(PirateGame.PLAYER_ID);

        //For phone:
        player.b2body.setLinearVelocity(controller.touchpad.getKnobPercentX() * PirateGame.DEFAULT_VELOCITY,
                controller.touchpad.getKnobPercentY() * PirateGame.DEFAULT_VELOCITY);

        /*
        //for keyboard:
        boolean moved = false;
        if (controller.upPressed){
            player.b2body.setLinearVelocity(0, PirateGame.DEFAULT_VELOCITY);
            Gdx.app.log("Controller: ", "UP");
            moved = true;
        }
        if (controller.downPressed) {
            player.b2body.setLinearVelocity(0, -PirateGame.DEFAULT_VELOCITY);
            Gdx.app.log("Controller: ", "DOWN");
            moved = true;
        }
        if (controller.leftPressed) {
            player.b2body.setLinearVelocity(-PirateGame.DEFAULT_VELOCITY, 0);
            Gdx.app.log("Controller: ", "LEFT");
            moved = true;
        }
        if (controller.rightPressed) {
            player.b2body.setLinearVelocity(PirateGame.DEFAULT_VELOCITY, 0);
            Gdx.app.log("Controller: ", "RIGHT");
            moved = true;
        }
        if(moved)
            sendVelocity();

        Gdx.app.log("Moved", moved + "");
        */
        //Bomb Pressed:
        if (!controller.previousBombPress & controller.bombPress & bombConfirm) {
            player.explosiveItems.add(new Bomb(this, player.b2body.getPosition().x, player.b2body.getPosition().y));
            game.playServices.broadcastMessage("Bomb;" + PirateGame.PLAYER_ID + ";" + player.b2body.getPosition().x + ";" + player.b2body.getPosition().y);
            bombConfirm = false;
        }
        //Sword Pressed:
        if (!controller.previousSwordPress & controller.swordPress & swordConfirm){
            player.primitiveWeaponItems.add(new Sword(this, PirateGame.PLAYER_ID));
            game.playServices.broadcastMessage("Sword;" + PirateGame.PLAYER_ID);
            swordConfirm=false;
        }
        //Coconut Pressed:
        if (!controller.previousCoconutPress & controller.coconutPress & coconutConfirm){
            System.out.println("Coconut pressed");
            if (player.numberOfCoconut>0){
                player.primitiveWeaponItems.add(new Coconut(this, player.b2body.getPosition().x, player.b2body.getPosition().y, player.direction));
                game.playServices.broadcastMessage("Coconut;" + PirateGame.PLAYER_ID + ";" +
                        player.b2body.getPosition().x + ";" + player.b2body.getPosition().y + ";"
                        + player.direction.toString());
                player.numberOfCoconut--;
                updateCoconut();
            }
            coconutConfirm=false;
        }

        //Power up Pressed
        if (!controller.previousPowerUpPress & controller.powerUpPress) {
            switch (player.powerUpHolding) {
                case SHIELD:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.redefinePirateWithShield();
                    player.nonInteractiveSprites.add(new ShieldSprite(this, PirateGame.PLAYER_ID));
                    game.playServices.broadcastMessage("Shield;" + PirateGame.PLAYER_ID);
                    break;
                case SHOE:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.nonInteractiveSprites.add(new ShoeSprite());
                    break;
                case TNT:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.explosiveItems.add(new TNT(this,player.b2body.getPosition().x, player.b2body.getPosition().y));
                    game.playServices.broadcastMessage("TNT;" + PirateGame.PLAYER_ID + ";" +
                            player.b2body.getPosition().x + ";" + player.b2body.getPosition().y);
                    break;
                default:
                    break;
            }
        }
    }

    public void sendLocation(){
        game.playServices.broadcastMessage("Location;"+PirateGame.PLAYER_ID+";"+
                getPirate().b2body.getPosition().x+";"+getPirate().b2body.getPosition().y+";" +
                getPirate().direction + ";" + getPirate().currentState + ";" + getPirate().b2body.getLinearVelocity().x + ";" + getPirate().b2body.getLinearVelocity().y);
    }

    public void checkWin() {
        if (game.sessionInfo.mState.equals("win") ) {
            game.setScreen(new WinScreen(game));
            game.sessionInfo.endSession();
        } else if (game.sessionInfo.mState.equals("lose")) {
            game.setScreen(new LoseScreen(game));
            game.sessionInfo.endSession();
        } else if (game.sessionInfo.mState.equals("disconnected")) {
            game.setScreen(new Disconnected(game));
            game.sessionInfo.endSession();
        }
        if (PirateGame.PLAYERS_ALIVE <= 1) {
            game.sessionInfo.mState = "win";
        }
    }

    public void cleanUpObjects() {
        for (Pirate player : players) {
            int i = 0;
            while (i < player.explosiveItems.size & player.explosiveItems.size != 0) {
                if (player.explosiveItems.get(i).isDestroyed()) {
                    player.explosiveItems.removeIndex(i);
                    Gdx.app.log("RemoveItem", i + "");
                } else {
                    i++;
                }
            }

        }
//            for (ExplosiveItem item : player.explosiveItems) {
//                if (item.isDestroyed())
//                    player.explosiveItems.re

//                if (item instanceof Bomb)
//                    for (BombExplosionDetector bombExplosionDetector :((Bomb) item).explosionDetectors)
//                        bombExplosionDetector.update(dt);
//            }
//            for (NonInteractiveSprites sprite : player.nonInteractiveSprites)
//                sprite.update(dt);
//            for (PrimitiveWeaponItem primitiveWeaponItem : player.primitiveWeaponItems) {
//                Gdx.app.log("Primitive Weapon", primitiveWeaponItem.getClass()+ " " +player.playerId);
//                primitiveWeaponItem.update(dt);
//                if(primitiveWeaponItem.isDestroyed())
//                    player.primitiveWeaponItems.removeValue(primitiveWeaponItem,true);
//            }
    }
//    }

    public void update(float dt) {
        //handle user input first
        handleInput(dt);
        handleSpawningItems();
        sendLocation();
        checkWin();
        cleanUpObjects();
//        Gdx.app.log("FPS", "FPS:" + 1 / dt);
        //takes 1 step in the physics simulation(60 times per second
        world.step(1 / 60f, 6, 2);
        for (PowerUp item : powerUps)
            item.update(dt);
        for (Pirate player : players) {
            player.update(dt);
            for (ExplosiveItem item : player.explosiveItems) {
                item.update(dt);
                if (item instanceof Bomb)
                    for (BombExplosionDetector bombExplosionDetector :((Bomb) item).explosionDetectors)
                        bombExplosionDetector.update(dt);
            }
            for (NonInteractiveSprites sprite : player.nonInteractiveSprites)
                sprite.update(dt);
            for (PrimitiveWeaponItem primitiveWeaponItem : player.primitiveWeaponItems) {
                Gdx.app.log("Primitive Weapon", primitiveWeaponItem.getClass()+ " " +player.playerId);
                primitiveWeaponItem.update(dt);
                if(primitiveWeaponItem.isDestroyed())
                    player.primitiveWeaponItems.removeValue(primitiveWeaponItem,true);
            }
        }
        Pirate player = getPirate();

        //update gamecam
        gamecam.setToOrtho(false, PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM);
        if (player.b2body.getPosition().x < (PirateGame.V_WIDTH / PirateGame.PPM) / 2)
            gamecam.position.x = gamePort.getWorldWidth() / 2;
        else if (player.b2body.getPosition().x > (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE_X / PirateGame.PPM) - (PirateGame.V_WIDTH / PirateGame.PPM) / 2)
            gamecam.position.x = (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE_X / PirateGame.PPM) - (PirateGame.V_WIDTH / PirateGame.PPM) / 2;
        else
            gamecam.position.x = player.b2body.getPosition().x;
        //y position
        if (player.b2body.getPosition().y < (PirateGame.V_HEIGHT / PirateGame.PPM) / 2)
            gamecam.position.y = gamePort.getWorldHeight() / 2;
        else if (player.b2body.getPosition().y > (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE_Y / PirateGame.PPM) - (PirateGame.V_HEIGHT / PirateGame.PPM) / 2)
            gamecam.position.y = (PirateGame.TILE_SIZE * PirateGame.MAP_SIZE_Y / PirateGame.PPM) - (PirateGame.V_HEIGHT / PirateGame.PPM) / 2;
        else
            gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
        updatePowerUp();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);
        Gdx.app.log("Number", PirateGame.PLAYERS_ALIVE + "");

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        PirateGame.batch.begin();
        PirateGame.batch.setProjectionMatrix(gamecam.combined);

        for (Pirate player : players) {
            player.draw(PirateGame.batch);
            for (NonInteractiveSprites sprite : player.nonInteractiveSprites)
                sprite.draw(PirateGame.batch);
            for (ExplosiveItem item: player.explosiveItems)
                item.draw(PirateGame.batch);
            for (PrimitiveWeaponItem item : player.primitiveWeaponItems)
                item.draw(PirateGame.batch);
            player.draw(PirateGame.batch);
        }
        for (PowerUp powerup : powerUps)
            powerup.draw(PirateGame.batch);
        PirateGame.batch.end();
        controller.render();
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    @Override
    public void pause() {}

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
    }

    //Getters
    public Pirate getPirate() {
        return players.get(PirateGame.PLAYER_ID);
    }

    public void updateCoconut(){
        controller.changeCoconut(getPirate().numberOfCoconut);
    }

    public void updatePowerUp(){
        controller.changePowerUp(getPirate().powerUpHolding);
    }

    public void updateHealth(){
        controller.changeHealth(getPirate().health);
        checkWin();
    }

    public Pirate getPirate(int id){
        return players.get(id);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    //Spawing item helper
    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void removePlayer(int playerId){
        try {
            for (Pirate p : players) {
                if (p.playerId == playerId) {
                    //                p.destroy();
                    players.removeValue(p, true);
                    PirateGame.NUMBER_OF_PLAYERS--;
                    return;
                }
            }
        }catch (Exception e){
            Gdx.app.error("Failed to remove player", e.getMessage());
        }
    }

}