package com.go.gopirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    //Reference to our Game, used to set Screens
    private PirateGame game;
    private TextureAtlas atlas;
    public Animation explosionAnimation;
    public Animation coconutAnimation;
    public Array<TextureRegion> healthTexture;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
//    private Hud hud;

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

    private Music music;

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
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, PirateGame.V_WIDTH, PirateGame.V_HEIGHT);
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecam);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("tiled_map/testMap2.tmx");

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
        players.add(new Pirate(this, 0,"Sophia"));
        players.add(new Pirate(this, 1, "Taka"));
        players.add(new Pirate(this, 2, "Thomas"));
        players.add(new Pirate(this, 3, "Zack"));

        //create our game HUD for scores/timers/level info
//        hud = new Hud(PirateGame.batch,players.get(thisPlayerIndex));


        world.setContactListener(new WorldContactListener(this));
        music = PirateGame.manager.get("audio/music/pirate.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

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

        healthTexture=new Array<TextureRegion>();
        Texture healthTexureAll1=new Texture("img/hud/health_1.png");
        for (int i = 0; i < 8; i++) {
            healthTexture.add(new TextureRegion(healthTexureAll1,i*256,0,256,768));
        }
        Texture healthTexutreAll2=new Texture("img/hud/health_2.png");
        for (int i = 8; i < 11; i++) {
            healthTexture.add(new TextureRegion(healthTexutreAll2,(i-8)*256,0,256,768));
        }
    }


    public void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Shield.class) {
                powerUps.add(new Shield(this, idef.position.x, idef.position.y));
            }
            if (idef.type == Shoe.class) {
                powerUps.add(new Shoe(this, idef.position.x, idef.position.y));
            }
            if (idef.type == TNTPowerUp.class) {
                powerUps.add(new TNTPowerUp(this, idef.position.x, idef.position.y));
            }
            if (idef.type == CoconutPowerUp.class) {
                powerUps.add(new CoconutPowerUp(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void handleInput(float dt) {
        bombConfirmTimer += dt;
        if (bombConfirmTimer > PirateGame.BUTTON_INTERVAL) {
            bombConfirmTimer = 0;
            bombConfirm = true;
        }

        swordConfirmTimer+=dt;
        if (swordConfirmTimer > PirateGame.BUTTON_INTERVAL){
            swordConfirmTimer = 0;
            swordConfirm =true;
        }

        coconutConfirmTimer+=dt;
        if (coconutConfirmTimer > PirateGame.BUTTON_INTERVAL){
            coconutConfirmTimer = 0;
            coconutConfirm =true;
        }

        Pirate player = players.get(PirateGame.PLAYER_ID);

//        For phone:
        player.b2body.setLinearVelocity(controller.touchpad.getKnobPercentX() * PirateGame.DEFAULT_VELOCITY,
                controller.touchpad.getKnobPercentY() * PirateGame.DEFAULT_VELOCITY);

//        if (Math.abs(controller.touchpad.getKnobPercentX())>Math.abs(controller.touchpad.getKnobPercentY())){
//            player.b2body.setLinearVelocity(controller.touchpad.getKnobPercentX()*PirateGame.DEFAULT_VELOCITY,0);
//        }else {
//            player.b2body.setLinearVelocity(0,controller.touchpad.getKnobPercentY()*PirateGame.DEFAULT_VELOCITY);
//        }


        //for keyboard:
        if (controller.upPressed)
            player.b2body.setLinearVelocity(0, PirateGame.DEFAULT_VELOCITY);
        if (controller.downPressed)
            player.b2body.setLinearVelocity(0, -PirateGame.DEFAULT_VELOCITY);
        if (controller.leftPressed)
            player.b2body.setLinearVelocity(-PirateGame.DEFAULT_VELOCITY, 0);
        if (controller.rightPressed)
            player.b2body.setLinearVelocity(PirateGame.DEFAULT_VELOCITY, 0);

        //Bomb Pressed:
        if (!controller.previousBombPress & controller.bombPress & bombConfirm) {
            player.explosiveItems.add(new Bomb(this, player.b2body.getPosition().x, player.b2body.getPosition().y));
            bombConfirm = false;
        }
        //Sword Pressed:
        if (!controller.previousSwordPress & controller.swordPress & swordConfirm){
            player.primitiveWeaponItems.add(new Sword(this));
            swordConfirm=false;
        }
        //Coconut Pressed:
        if (!controller.previousCoconutPress & controller.coconutPress & coconutConfirm){
            System.out.println("Coconut pressed");
            if (player.numberOfCoconut>0){
                player.primitiveWeaponItems.add(new Coconut(this));
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
                    player.nonInteractiveSprites.add(new ShieldSprite(this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                    break;
                case SHOE:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.nonInteractiveSprites.add(new ShoeSprite());
                    break;
                case TNT:
                    player.powerUpHolding = Pirate.PowerUpHolding.NONE;
                    player.explosiveItems.add(new TNT(this,player.b2body.getPosition().x, player.b2body.getPosition().y));
                    break;
                default:
                    break;
            }
        }
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

            for (NonInteractiveSprites sprite : player.nonInteractiveSprites) {
                sprite.update(dt);
            }
            for (PrimitiveWeaponItem primitiveWeaponItem: player.primitiveWeaponItems){
                primitiveWeaponItem.update(dt);
            }

        }
//        hud.update(dt);


        Pirate player = getPirate();
        //update our gamecam with correct coordinates after changes
        //x position
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

            for (NonInteractiveSprites sprite : player.nonInteractiveSprites) {
                sprite.draw(PirateGame.batch);
            }

            for (ExplosiveItem item: player.explosiveItems) {
                item.draw(PirateGame.batch);
            }

            for (PrimitiveWeaponItem item : player.primitiveWeaponItems) {
                item.draw(PirateGame.batch);
            }

            player.draw(PirateGame.batch);
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
//        hud.dispose();
    }


//    public Hud getHud(){ return hud; }


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

    public void updateHealth(){ controller.changeHealth(getPirate().health);}

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

}