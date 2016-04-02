package com.go.gopirates.sprites;

/**
 * Created by Amy on 1/3/16.
 */

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.scene.Hud;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.Bomb;
import com.go.gopirates.sprites.items.TNT;
import com.go.gopirates.sprites.powerUp.Shield;
import com.go.gopirates.weapon.Pistol;
import com.go.gopirates.weapon.Sword;

import java.util.HashMap;


/**
 * Created by Amy on 25/2/16.
 */
public class Pirate extends Sprite {
    public Direction direction;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private PowerUp extraWeapon;
    private Animation swimUp,swimDown,swimRight,swimLeft,idleUp,idleDown,idleLeft,idleRight,pirateDead;
    private float stateTimer;
    private boolean pirateIsDead;
    private PlayScreen screen;
    private Sword sword;
    private Bomb bomb;
    private Shield shield;
    private TNT tnt;
    private float powerUpTime;
    private Array<Pistol> bullets;
    private HandledWeapon weapon;
    private PowerUp nextPowerUp;
    private boolean plantBomb;
    private boolean plantTNT;
    private boolean timeToRedefinePirate, timeToDefineShield, timeToDefineShoes;

    private int health;
    private int player_id;

    public Pirate(PlayScreen screen, int player_id) {
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        this.player_id=player_id;
        currentState = State.SWIMMING;
        previousState = State.SWIMMING;
        direction = Direction.DOWN;
        stateTimer = 0;
        weapon = HandledWeapon.NONE;
        extraWeapon = PowerUp.NONE;
        nextPowerUp = PowerUp.NONE;
//        extraWeapon = PowerUp.SHIELD; // for test only
        timeToRedefinePirate = timeToDefineShield = timeToDefineShield = false;
        health=100;
        plantBomb = false;
        plantTNT=false;
        // animation
        HashMap<String, Animation> anims = new HashMap<String, Animation>();

        Animation anim;

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        // walking up
        for (int i = 0; i < 3; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_up", anim);
        swimUp = anim;

        // walking left
        keyFrames.clear();
        for (int i = 3; i < 6; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames);
        anims.put("walking_left", anim);
        swimLeft = anim;

        // walking down
        keyFrames.clear();
        for (int i = 6; i < 9; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_down", anim);
        swimDown = anim;

        // walking right
        keyFrames.clear();
        for (int i = 9; i < 12; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP);
        anims.put("walking_right", anim);
        swimRight = anim;

        // idling up
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 1 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_up", anim);
        idleUp = anim;

        // idling left
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 3 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_left", anim);
        idleLeft = anim;

        // idling down
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 7 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_down", anim);
        idleDown = anim;

        // idling right
        keyFrames.clear();
        keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), 9 * 16, 0, 16, 24));
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("idling_right", anim);
        idleRight = anim;

        // dying
        keyFrames.clear();
        for (int i = 12; i < 18; i++) {
            keyFrames.add(new TextureRegion(screen.getAtlas().findRegion("Bomberman1"), i * 16, 0, 16, 24));
        }
        anim = new Animation(0.1f, keyFrames, Animation.PlayMode.NORMAL);
        anims.put("dying", anim);
        pirateDead = anim;

        //define pirates

        switch (player_id){
            case 0:
                definePirate(PirateGame.BOARDER_OFFSET, PirateGame.BOARDER_OFFSET,player_id);
                break;
            case 1:
                definePirate(PirateGame.EDGE_POSITION_X- PirateGame.BOARDER_OFFSET+ PirateGame.PLAYER_OFFSET_X, PirateGame.BOARDER_OFFSET,player_id);
                break;
            case 2:
                definePirate(PirateGame.BOARDER_OFFSET, PirateGame.EDGE_POSITION_Y- PirateGame.BOARDER_OFFSET+ PirateGame.PLAYER_OFFSET_Y,player_id);
                break;
            case 3:
                definePirate(PirateGame.EDGE_POSITION_X- PirateGame.BOARDER_OFFSET+ PirateGame.PLAYER_OFFSET_X, PirateGame.EDGE_POSITION_Y- PirateGame.BOARDER_OFFSET+ PirateGame.PLAYER_OFFSET_Y,player_id);
                break;
        }
        setBounds(0, 0, 16 / PirateGame.PPM, 16 / PirateGame.PPM);
        setRegion(idleUp.getKeyFrame(stateTimer, true));

        bullets = new Array<Pistol>();

    }


    public void plantBomb() {
        if (!plantBomb) {
            System.out.println("Plant bomb");
            bomb = new Bomb(screen, b2body.getPosition().x, b2body.getPosition().y);
            plantBomb = true;
        }
    }


    public void update(float dt) {
        if (weapon == HandledWeapon.SHIELD || weapon == HandledWeapon.SHOES)
            powerUpTime += dt;

        if (nextPowerUp != PowerUp.NONE) {
            extraWeapon = nextPowerUp;
            nextPowerUp = PowerUp.NONE;
            System.out.println("3 " + extraWeapon);
            Hud.updatePowerUp(extraWeapon);
        }

        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }
        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));

        if (timeToDefineShield)
            defineShield();
        if (timeToRedefinePirate)
            redefinePirate();
        if (timeToDefineShoes)
            defineShoes();

        for (Pistol bullet : bullets) {
            bullet.update(dt);
            if (bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }

        if (weapon == HandledWeapon.SWORD) {
            if (sword.isDestroyed()) {
                weapon = HandledWeapon.NONE;
            } else sword.update(dt, b2body.getPosition().x, b2body.getPosition().y,this);
        }
        // TODO: 21/3/16 Set an accurate timing
        if (powerUpTime >= 12) {
            weapon = HandledWeapon.NONE;
            powerUpTime = 0;
            timeToRedefinePirate = true;
            extraWeapon = PowerUp.NONE;
        }
//        else if (weapon == HandledWeapon.SHIELD) {
//            if (shield.isDestroyed()) {
//                weapon = HandledWeapon.NONE;
//            } else shield.update(dt, b2body.getPosition().x, b2body.getPosition().y,this);
//        }
        if (plantBomb) {
            if (bomb.isDestroyed()) {
                plantBomb = false;
            } else bomb.update(dt);
        }
        if (plantTNT) {
            if (tnt.isDestroyed()) {
                plantTNT = false;
            } else tnt.update(dt);
        }


    }

    public TextureRegion getFrame(float dt){
        //get pirate current state. ie. swimming, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = pirateDead.getKeyFrame(stateTimer, true);
                break;
            case WALKING:
            case SWIMMING:
                switch (direction){
                    case DOWN:
                        region = swimDown.getKeyFrame(stateTimer,true);
                        break;
                    case LEFT:
                        region = swimLeft.getKeyFrame(stateTimer,true);
                        break;
                    case RIGHT:
                        region = swimRight.getKeyFrame(stateTimer,true);
                        break;
                    case UP:
                        region = swimUp.getKeyFrame(stateTimer,true);
                        break;
                    default:
                        region = swimUp.getKeyFrame(stateTimer,true);
                        break;
                }
                break;
            case HIT:
            case IDLING:
            default:
                switch (direction){
                    case UP:
                        region = idleUp.getKeyFrame(stateTimer,true);
                        break;
                    case DOWN:
                        region = idleDown.getKeyFrame(stateTimer,true);
                        break;
                    case LEFT:
                        region = idleLeft.getKeyFrame(stateTimer,true);
                        break;
                    case RIGHT:
                        region = idleRight.getKeyFrame(stateTimer,true);
                        break;
                    default:
                        region = idleUp.getKeyFrame(stateTimer,true);
                        break;
                }
                break;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    public State getState(){
        if(pirateIsDead)
            return State.DEAD;
        if (b2body.getLinearVelocity().x > 0.08) {
            direction = Direction.RIGHT;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().x < -0.08) {
            direction = Direction.LEFT;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().y > 0.08) {
            direction = Direction.UP;
            return State.SWIMMING;
        } else if (b2body.getLinearVelocity().y < -0.08) {
            direction = Direction.DOWN;
            return State.SWIMMING;
        }
        else
            return State.IDLING;
    }

    // TODO: 18/3/16 GameOver screen and some animation (to be decided later)
    public void die() {

        if (!isDead()) {
//
//            PirateGame.manager.get("audio/music/mario_music.ogg", Music.class).stop();
//            PirateGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            //test commit
            pirateIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = PirateGame.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
        }
    }

    public boolean isDead(){
        return pirateIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void definePirate(float x, float y,int player_id){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);
        bdef.linearDamping = 11f;

        b2body = world.createBody(bdef);
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PirateGame.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        switch (player_id){
            case PirateGame.THIS_PLAYER:
                fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
                break;
            default:
                fixtureDef.filter.categoryBits = PirateGame.OTHER_PLAYER_BIT;
        }

        if (player_id== PirateGame.THIS_PLAYER){
            fixtureDef.filter.maskBits =
                            PirateGame.ROCK_BIT |
                            PirateGame.REEF_BIT |
                            PirateGame.BOMB_BIT |
                            PirateGame.BULLET_BIT|
                                    PirateGame.TREASURE_BIT |
                                    PirateGame.POWERUP_BIT| PirateGame.EXPLOSION_BIT;
        }else {
            fixtureDef.filter.maskBits =
                    PirateGame.ROCK_BIT;
        }
        b2body.createFixture(fixtureDef);
        shape.dispose();

        b2body.createFixture(fixtureDef).setUserData(this);
    }

    //Put the pirate in a spherical shield
    public void defineShield() {
        System.out.println("Use shield");
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 11f;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shield = new CircleShape();
        shield.setRadius(10 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT |
                PirateGame.BULLET_BIT;

        fdef.shape = shield;
        b2body.createFixture(fdef).setUserData(this);

        CircleShape body = new CircleShape();
        body.setRadius(7 / PirateGame.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = body;
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT |
                PirateGame.REEF_BIT |
                PirateGame.BOMB_BIT |
                PirateGame.BULLET_BIT|
                PirateGame.TREASURE_BIT |
                PirateGame.POWERUP_BIT;
        b2body.createFixture(fixtureDef);
//        shape.dispose();
        b2body.createFixture(fixtureDef).setUserData(this);
        timeToDefineShield = false;

    }

    public void defineShoes() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 5f;
        b2body = world.createBody(bdef);


        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PirateGame.PPM);
        switch (player_id) {
            case PirateGame.THIS_PLAYER:
                fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
                break;
            default:
                fixtureDef.filter.categoryBits = PirateGame.OTHER_PLAYER_BIT;
        }

        if (player_id == PirateGame.THIS_PLAYER) {
            fixtureDef.filter.maskBits =
                    PirateGame.ROCK_BIT |
                            PirateGame.REEF_BIT |
                            PirateGame.BOMB_BIT |
                            PirateGame.BULLET_BIT |
                            PirateGame.TREASURE_BIT |
                            PirateGame.POWERUP_BIT | PirateGame.EXPLOSION_BIT;
        } else {
            fixtureDef.filter.maskBits =
                    PirateGame.ROCK_BIT;
        }

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        b2body.createFixture(fixtureDef).setUserData(this);

        timeToDefineShoes = false;

    }

    //After shield is gone
    public void redefinePirate() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 11f;
        b2body = world.createBody(bdef);


        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / PirateGame.PPM);
//        fdef.filter.categoryBits = PirateGame.PLAYER_BIT;
//        fdef.filter.maskBits = PirateGame.PLAYER_BIT |
//                PirateGame.ROCK_BIT |
//                PirateGame.REEF_BIT ;
        switch (player_id){
            case PirateGame.THIS_PLAYER:
                fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
                break;
            default:
                fixtureDef.filter.categoryBits = PirateGame.OTHER_PLAYER_BIT;
        }

        if (player_id== PirateGame.THIS_PLAYER){
            fixtureDef.filter.maskBits =
                    PirateGame.ROCK_BIT |
                            PirateGame.REEF_BIT |
                            PirateGame.BOMB_BIT |
                            PirateGame.BULLET_BIT|
                            PirateGame.TREASURE_BIT |
                            PirateGame.POWERUP_BIT;
        }else {
            fixtureDef.filter.maskBits =
                    PirateGame.ROCK_BIT;
        }

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        b2body.createFixture(fixtureDef).setUserData(this);

        timeToRedefinePirate = false;
    }

    public void fire() {
        if (bullets.size == 0) {
            weapon = HandledWeapon.PISTOL;
            bullets.add(new Pistol(screen, b2body.getPosition().x, b2body.getPosition().y, direction));
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (Pistol bullet : bullets)
            bullet.draw(batch);
        // TODO: 18/3/16 draw other weapon also 
    }

    /*Not used for now
    public String getDirection() {
        switch (direction) {
            case DOWN:
                return "Down";
            case LEFT:
                return "Left";
            case RIGHT:
                return "Right";
            case UP:
                return "Up";
            default:
                return "IDK";
        }
    }*/

    public void useSword() {
        if (weapon != HandledWeapon.SWORD) {
            weapon = HandledWeapon.SWORD;
            sword = new Sword(screen, b2body.getPosition().x, b2body.getPosition().y, direction);
        }
    }

    public void usePowerUp() {
        System.out.println(extraWeapon);
        if (extraWeapon == PowerUp.NONE) return;
        else if (extraWeapon == PowerUp.SHIELD) useShield();
        else if (extraWeapon == PowerUp.SHOES) useShoes();
        else if (extraWeapon == PowerUp.TNT) useTNT();
        extraWeapon = PowerUp.NONE;
        Hud.updatePowerUp(extraWeapon);

    }

    public void useShield() {
        if (weapon != HandledWeapon.SHIELD) {
            timeToDefineShield = true;
            weapon = HandledWeapon.SHIELD;
            // TODO: 21/3/16 decide whether we want it to be like pistol or just a sphere around the player
//            shield = new Shield(screen, b2body.getPosition().x, b2body.getPosition().y, direction);
        }
    }

    // TODO: 18/3/16
    public void useShoes() {
        if (weapon != HandledWeapon.SHOES) {
            timeToDefineShoes = true;
            weapon = HandledWeapon.SHOES;
        }
    }

    // TODO: 18/3/16 DO THIS
    public void useTNT() {
        if (!plantTNT) {
            System.out.println("Plant bomb");
            tnt= new TNT(screen, b2body.getPosition().x, b2body.getPosition().y);
            plantTNT = true;
        }
    }

    public void decreaseHealth(int value){
        health-=value;
    }

    public int getHealth(){
        return health;
    }
    //Note: This will be needed for Contact Listener
    public boolean shieldOn() {
        return (weapon == HandledWeapon.SHIELD);
    }

    public void takePowerUp(PowerUp pu) {

        System.out.println("1 " + extraWeapon);
        if (extraWeapon == PowerUp.NONE)
            nextPowerUp = pu;
        System.out.println("2 " + nextPowerUp);
    }

    public void hitByBullet() {
        screen.getPirate(PirateGame.THIS_PLAYER).decreaseHealth(20);
        System.out.println("Shot by bullet");
    }

    public enum State {SWIMMING, WALKING, HIT, DEAD, IDLING}

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    public enum HandledWeapon {PISTOL, SWORD, SHIELD, SHOES, NONE}

    public enum PowerUp {SHOES, SHIELD, TNT, NONE}
}
