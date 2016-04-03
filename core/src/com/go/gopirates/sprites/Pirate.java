package com.go.gopirates.sprites;

import com.badlogic.gdx.Gdx;
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

import java.util.ArrayList;

/**
 * Created by zhanghao on 2/4/16.
 */
public class Pirate extends Sprite {

    public int playerId;

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public enum State { WALKING, STANDING}

    public enum PowerUpHolding {TNT, COCONUT, SHIED, SHOE, NONE}
    public State currentState;
    public State previousState;
    public Direction direction;
    public PowerUpHolding powerUpHolding;

    public World world;
    public Body b2body;

    private int health;
    private float healthTimer;


    private TextureRegion pirateStandingDown;
    private TextureRegion pirateStandingUp;
    private TextureRegion pirateStandingLeft;
    private TextureRegion pirateStandingRight;
    private Animation pirateWalkingDown;
    private Animation pirateWalkingUp;
    private Animation pirateWalkingLeft;
    private Animation pirateWalkingRight;

    private PlayScreen screen;
    private final float FRAME_DURATION=0.1f;

    public ArrayList<ExplosiveItem> explosiveItems;
    public ArrayList<NonInteractiveSprites> nonInteractiveSprites;
    public ArrayList<PrimitiveWeaponItem> primitiveWeaponItems;

    private enum PirateState{ PIRATE, PIRATE_WITH_SHIELD}
    private PirateState pirateState;
    private float stateTimer;
    private float powerUpTimer;
    public Pirate(PlayScreen screen, int playerId){
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        direction=Direction.DOWN;
        powerUpHolding=PowerUpHolding.NONE;
        stateTimer = 0;
        explosiveItems=new ArrayList<ExplosiveItem>();
        nonInteractiveSprites =new ArrayList<NonInteractiveSprites>();
        primitiveWeaponItems = new ArrayList<PrimitiveWeaponItem>();
        pirateState=PirateState.PIRATE;
        powerUpTimer=0;
        this.playerId=playerId;
        health=PirateGame.ININTIAL_HEALTH;
        healthTimer=0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        String pirate="pirate0";


        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),0*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),1*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),0*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),2*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingDown=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingDown=new TextureRegion(screen.getAtlas().findRegion(pirate),0*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);


        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),3*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),4*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),3*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),5*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingLeft=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingLeft=new TextureRegion(screen.getAtlas().findRegion(pirate),3*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);


        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),6*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),7*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),6*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),8*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingRight=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingRight=new TextureRegion(screen.getAtlas().findRegion(pirate),6*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),9*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),10*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),9*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),11*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingUp=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingUp=new TextureRegion(screen.getAtlas().findRegion(pirate),9*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        definePirate();

        setBounds(0,0,PirateGame.TILE_SIZE/PirateGame.PPM,PirateGame.TILE_SIZE/PirateGame.PPM);
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
    }

    private void definePirate() {
        BodyDef bodyDef = new BodyDef();
        switch (playerId){
            case 0:
                bodyDef.position.set(PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                     PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 1:
                bodyDef.position.set((PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE-2)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                      PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 2:
                bodyDef.position.set(PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                    (PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE-2)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            case 3:
                bodyDef.position.set((PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE-2)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                                     (PirateGame.TILE_SIZE/PirateGame.PPM)*(PirateGame.MAP_SIZE-2)+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);
                break;
            default:
                bodyDef.position.set(PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2,
                        PirateGame.TILE_SIZE/PirateGame.PPM+(PirateGame.TILE_SIZE/PirateGame.PPM)/2);

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
                                     PirateGame.EXPLOSION_BIT | PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef);
        shape.dispose();
        b2body.createFixture(fixtureDef).setUserData(this);

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
                PirateGame.EXPLOSION_BIT | PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef);
        shape.dispose();
        b2body.createFixture(fixtureDef).setUserData(this);
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
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    private State getState(){
        if (b2body.getLinearVelocity().x > 0.08) {
            direction = Direction.RIGHT;
            return State.WALKING;
        } else if (b2body.getLinearVelocity().x < -0.08) {
            direction = Direction.LEFT;
            return State.WALKING;
        } else if (b2body.getLinearVelocity().y > 0.08) {
            direction = Direction.UP;
            return State.WALKING;
        } else if (b2body.getLinearVelocity().y < -0.08) {
            direction = Direction.DOWN;
            return State.WALKING;
        }
        else
            return State.STANDING;
    }

    public TextureRegion getFrame(float dt){
        currentState= getState();
        TextureRegion region;
        //depending on the state, get corresponding animation keyFrame.
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
        if (healthTimer>PirateGame.PROTECTED_TIME_AFTER_DECREASE_HEALTH){
            if (health>0){
                health--;
                Gdx.app.log("Pirate", "Health decrease 1, Current Health "+health);
                healthTimer=0;
            }else {
                healthTimer=0;
                Gdx.app.log("Pirate", "Pirated is dead.");
            }
        }
    }

}
