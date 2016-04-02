package com.go.gopirates.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.go.gopirates.screens.PlayScreen;

/**
 * Created by zhanghao on 2/4/16.
 */
public class Pirate extends Sprite {

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public enum State { WALKING, STANDING};
    public State currentState;
    public State previousState;
    public Direction direction;

    public World world;
    public Body b2body;

    private TextureRegion pirateStandingDown;
    private TextureRegion pirateStandingUp;
    private TextureRegion pirateStandingLeft;
    private TextureRegion pirateStandingRight;
    private Animation pirateWalkingDown;
    private Animation pirateWalkingUp;
    private Animation pirateWalkingLeft;
    private Animation pirateWalkingRight;

    private float stateTimer;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean marioIsDead;
    private PlayScreen screen;
    private final float FRAME_DURATION=0.1f;

    public Pirate(PlayScreen screen){
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        direction=Direction.DOWN;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        String pirate="pirate"+PirateGame.PLAYER_ID;

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),i*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingDown=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingDown=new TextureRegion(screen.getAtlas().findRegion(pirate),0*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        for (int i = 3; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),i*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingLeft=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingLeft=new TextureRegion(screen.getAtlas().findRegion(pirate),3*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        for (int i = 6; i < 9; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),i*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingRight=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingRight=new TextureRegion(screen.getAtlas().findRegion(pirate),6*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        for (int i = 9; i < 12; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(pirate),i*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE));
        pirateWalkingUp=new Animation(FRAME_DURATION,frames);
        frames.clear();
        pirateStandingUp=new TextureRegion(screen.getAtlas().findRegion(pirate),9*PirateGame.TILE_SIZE,0,PirateGame.TILE_SIZE,PirateGame.TILE_SIZE);

        definePirate();

        setBounds(0,0,PirateGame.TILE_SIZE/PirateGame.PPM,PirateGame.TILE_SIZE/PirateGame.PPM);
        setRegion(pirateStandingDown);

    }

    public void update(float dt){
        setRegion(getFrame(dt));
    }



    private void definePirate() {
        BodyDef bodyDef = new BodyDef();
        switch (PirateGame.PLAYER_ID){
            case 0:
                bodyDef.position.set(2*PirateGame.TILE_SIZE/PirateGame.PPM,2*PirateGame.TILE_SIZE/PirateGame.PPM);
        }

        bodyDef.type= BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(96/PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.PLAYER_BIT;
        fixtureDef.filter.maskBits = PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                                     PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT | PirateGame.POWERUP_BIT|
                                     PirateGame.EXPLOSION_BIT;

        fixtureDef.shape=shape;
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
}
