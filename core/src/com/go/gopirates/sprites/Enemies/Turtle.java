package com.go.gopirates.Sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.Screens.PlayScreen;
import com.go.gopirates.Sprites.Pirate;

/**
 * Created by Amy on 26/2/16.
 */
public class Turtle extends Enemy {
    public static final int KICK_LEFT = -2;
    public static final int KICK_RIGHT = 2;

    public enum State {WALKING, MOVING_SHELL, STANDING_SHELL}

    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private boolean setToDestroy;
    private boolean destroyed;


    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 0, 0, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 16, 0, 16, 24));
        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"), 64, 0, 16, 24);
        walkAnimation = new Animation(0.2f, frames);
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16 / PirateGame.PPM, 24 / PirateGame.PPM);

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.ENEMY_BIT;
        fdef.filter.maskBits = PirateGame.GROUND_BIT |
                PirateGame.COIN_BIT |
                PirateGame.BRICK_BIT |
                PirateGame.ENEMY_BIT |
                PirateGame.OBJECT_BIT |
                PirateGame.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / PirateGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / PirateGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / PirateGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / PirateGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1.8f;
        fdef.filter.categoryBits = PirateGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;

        switch (currentState) {
            case MOVING_SHELL:
            case STANDING_SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if (velocity.x > 0 && region.isFlipX() == false) {
            region.flip(true, false);
        }
        if (velocity.x < 0 && region.isFlipX() == true) {
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if (currentState == State.STANDING_SHELL && stateTime > 5) {
            currentState = State.WALKING;
            velocity.x = 1;
            System.out.println("WAKE UP SHELL");
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 / PirateGame.PPM);
        b2body.setLinearVelocity(velocity);
    }

    @Override
    public void hitOnHead(Pirate pirate) {
        if (currentState == State.STANDING_SHELL) {
            if (pirate.b2body.getPosition().x > b2body.getPosition().x)
                velocity.x = -2;
            else
                velocity.x = 2;
            currentState = State.MOVING_SHELL;
            System.out.println("Set to moving shell");
        } else {
            currentState = State.STANDING_SHELL;
            velocity.x = 0;
        }
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true, false);
    }

    public void kick(int direction) {
        velocity.x = direction;
        currentState = State.MOVING_SHELL;
    }
}
