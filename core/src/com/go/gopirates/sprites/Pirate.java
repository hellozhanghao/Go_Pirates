package com.go.gopirates.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.explosiveItems.ExplosiveItem;
import com.go.gopirates.sprites.items.noneInteractiveItems.NonInteractiveSprites;
import com.go.gopirates.sprites.items.primitiveWeaponItem.PrimitiveWeaponItem;

/**
 * Created by zhanghao on 2/4/16.
 */
public class Pirate extends Sprite {

    public int playerId;
    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public enum State { WALKING, STANDING}
    public enum PowerUpHolding {TNT, SHIELD, SHOE, NONE}
    private enum PirateState{ PIRATE, PIRATE_WITH_SHIELD}
    private PirateState pirateState;
    public State currentState;
    public State previousState;
    public Direction direction;
    public PowerUpHolding powerUpHolding;

    public World world;
    public Body b2body;

    public  int health;
    private float healthTimer;
    public boolean swordInUse;

    private TextureRegion pirateStandingDown,pirateStandingUp,pirateStandingLeft,pirateStandingRight;
    private Animation pirateWalkingDown,pirateWalkingUp,pirateWalkingLeft,pirateWalkingRight;
    private Animation pirateStandingDownWithSword,pirateStandingUpWithSword,pirateStandingLeftWithSword, pirateStadingRightWithSword;
    private Animation pirateWalkingDownWithSword, pirateWalkingUpWithSword, pirateWalkingLeftWithSword, pirateWalkingRightWithSword;
    private PlayScreen screen;
    private final float FRAME_DURATION=0.1f;
    private final float FRAME_DURATION_WITH_SWORD=0.1f;

    public Array<ExplosiveItem> explosiveItems;
    public Array<NonInteractiveSprites> nonInteractiveSprites;
    public Array<PrimitiveWeaponItem> primitiveWeaponItems;

    public int numberOfCoconut;
    private float stateTimer,powerUpTimer;
    public  String character;
    public Pirate(PlayScreen screen, int playerId, String character){
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        direction = Direction.DOWN;
        powerUpHolding = PowerUpHolding.NONE;
        stateTimer = 0;
        explosiveItems = new Array<ExplosiveItem>();
        nonInteractiveSprites = new Array<NonInteractiveSprites>();
        primitiveWeaponItems = new Array<PrimitiveWeaponItem>();
        pirateState = PirateState.PIRATE;
        powerUpTimer = 0;
        this.playerId = playerId;
        this.character=character;
        health = PirateGame.ININTIAL_HEALTH;
        healthTimer = 0;
        swordInUse=false;

        loadAnimation();
    }

    private void loadAnimation(){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        numberOfCoconut=PirateGame.INITIAL_COCONUT;
        Texture pirateTexture=new Texture("img/characters/"+ this.character +".png");

        /**
         * ********************************** Pirate***********************************************
         */

        frames.add(new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,1*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,2*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingDown=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingDown=new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);


        frames.add(new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,4*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,5*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingLeft=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingLeft=new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,0*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);


        frames.add(new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,1*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,2*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingRight=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingRight=new TextureRegion(pirateTexture,0*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        frames.add(new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,4*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(pirateTexture,5*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingUp=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingUp=new TextureRegion(pirateTexture,3*PirateGame.TILE_SIZE,1*PirateGame.TILE_SIZE,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        /**
         * ********************************** Pirate with sword ************************************
         */

        Texture pirateWithSwordTexture=new Texture("img/characters/"+ this.character +"_sword"+".png");
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,0,400,400));
        pirateWalkingDownWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,0,400,400));
        pirateStandingDownWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,400,400,400));
        pirateWalkingLeftWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,400,400,400));
        pirateStandingLeftWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,800,400,400));
        pirateWalkingRightWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,800,400,400));
        pirateStadingRightWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,1200,400,400));
        pirateWalkingUpWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(pirateWithSwordTexture,i*400,1200,400,400));
        pirateStandingUpWithSword=new Animation(FRAME_DURATION_WITH_SWORD,frames);
        frames.clear();

        definePirate();

        setBounds(0, 0, PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM);
        setRegion(pirateStandingDown);
    }


    public void update(float dt){
        healthTimer+=dt;
        powerUpTimer+=dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));

        if (powerUpTimer>PirateGame.POWERUP_TIME & pirateState!=PirateState.PIRATE){
            redefinePirate();
        }
        if (swordInUse){
            setSize(400/PirateGame.PPM,400/PirateGame.PPM);
        }else {
            setSize(PirateGame.TILE_SIZE/PirateGame.PPM,PirateGame.TILE_SIZE/PirateGame.PPM);
        }
    }

    private void definePirate() {
        BodyDef bodyDef = new BodyDef();
        switch (playerId){
            case 0:
                bodyDef.position.set(PirateGame.TILE_SIZE/PirateGame.PPM*3+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                     PirateGame.TILE_SIZE/PirateGame.PPM*0+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 1:
                bodyDef.position.set((PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE_X -4)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                      PirateGame.TILE_SIZE/PirateGame.PPM*0+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 2:
                bodyDef.position.set(PirateGame.TILE_SIZE/PirateGame.PPM*3+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                    (PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE_Y -1)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 3:
                bodyDef.position.set((PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE_X -4)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                     (PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE_Y -1)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
        }

        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 5f;
        b2body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(128/PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                                     PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT | PirateGame.POWERUP_BIT|
                                     PirateGame.BOMB_EXPLOSION_BIT | PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void redefinePirate(){
        pirateState=PirateState.PIRATE;
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 5f;
        b2body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(128/PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT | PirateGame.POWERUP_BIT|
                PirateGame.BOMB_EXPLOSION_BIT | PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef);
        shape.dispose();
    }

    public  void redefinePirateWithShield(){
        pirateState=PirateState.PIRATE_WITH_SHIELD;
        powerUpTimer=0;
        Gdx.app.log("Pirate", "Redefine pirate with shield");
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 5f;
        b2body = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(128 / PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.SHIELD_BIT;
        fixtureDef.filter.maskBits = PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT | PirateGame.POWERUP_BIT| PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef);
        shape.dispose();


    }

    private State getState(){
        if(Math.abs(b2body.getLinearVelocity().x) > Math.abs(b2body.getLinearVelocity().y)){
            if (b2body.getLinearVelocity().x > 0.08) {
                direction = Direction.RIGHT;
                return State.WALKING;
            } else if (b2body.getLinearVelocity().x < -0.08) {
                direction = Direction.LEFT;
                return State.WALKING;
            }
        }
        else{
            if (b2body.getLinearVelocity().y > 0.08) {
                direction = Direction.UP;
                return State.WALKING;
            }
            else if (b2body.getLinearVelocity().y < -0.08) {
                direction = Direction.DOWN;
                return State.WALKING;
            }
        }
        return State.STANDING;
    }

    public TextureRegion getFrame(float dt){
        currentState= getState();
        TextureRegion region;
        //depending on the state, get corresponding animation keyFrame.
        if (swordInUse){
            switch(currentState){
                case WALKING:
                    switch (direction){
                        case DOWN:
                            region = pirateWalkingDownWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case LEFT:
                            region = pirateWalkingLeftWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case RIGHT:
                            region = pirateWalkingRightWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case UP:
                            region = pirateWalkingUpWithSword.getKeyFrame(stateTimer,true);
                            break;
                        default:
                            region = pirateWalkingDownWithSword.getKeyFrame(stateTimer,true);
                            break;
                    }
                    break;
                case STANDING:
                default:
                    switch (direction){
                        case UP:
                            region = pirateStandingUpWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case DOWN:
                            region = pirateStandingDownWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case LEFT:
                            region = pirateStandingLeftWithSword.getKeyFrame(stateTimer,true);
                            break;
                        case RIGHT:
                            region = pirateStadingRightWithSword.getKeyFrame(stateTimer,true);
                            break;
                        default:
                            region = pirateStandingDownWithSword.getKeyFrame(stateTimer,true);
                            break;
                    }
                    break;
            }
        }else {
            switch(currentState){
                case WALKING:
                    switch (direction){
                        case DOWN:
                            region = pirateWalkingDown.getKeyFrame(stateTimer,true);
                            break;
                        case LEFT:
                            region = pirateWalkingLeft.getKeyFrame(stateTimer,true);
                            break;
                        case RIGHT:
                            region = pirateWalkingRight.getKeyFrame(stateTimer,true);
                            break;
                        case UP:
                            region = pirateWalkingUp.getKeyFrame(stateTimer,true);
                            break;
                        default:
                            region = pirateWalkingDown.getKeyFrame(stateTimer,true);
                            break;
                    }
                    break;
                case STANDING:
                default:
                    switch (direction){
                        case UP:
                            region = pirateStandingUp;
                            break;
                        case DOWN:
                            region = pirateStandingDown;
                            break;
                        case LEFT:
                            region = pirateStandingLeft;
                            break;
                        case RIGHT:
                            region = pirateStandingRight;
                            break;
                        default:
                            region = pirateStandingDown;
                            break;
                    }
                    break;
            }
        }
        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    public void hitInExplosion(){
        decreaseHealth();
    }

    public void decreaseHealth(){
        if (healthTimer > PirateGame.PROTECTED_TIME_AFTER_DECREASE_HEALTH){
            if (health > 0){
                health--;
                Gdx.app.log("Pirate", "Health decrease 1, Current Health "+health);
                screen.updateHealth();
                healthTimer = 0;
            }else {
                healthTimer = 0;
                Gdx.app.log("Pirate", "Pirated is dead.");
            }
        }
    }

}
