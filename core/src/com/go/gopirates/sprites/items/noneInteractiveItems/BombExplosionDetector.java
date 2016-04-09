package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 5/4/16.
 */
public class BombExplosionDetector extends NonInteractiveSprites {

    public float destroyedTimeStamp, TTL;
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed,setToDestroy;
    Body body;
    ExpolsionDirection direction;
    private float posX,posY;
    private float RADIUS=10;
    private float VELOCITY=400;

    public BombExplosionDetector(PlayScreen screen, float x, float y, ExpolsionDirection direction, float TTL){
        this.screen=screen;
        this.world=this.screen.getWorld();
        posX=x;
        posY=y;
        this.direction=direction;
        this.stateTime=TTL;
        this.TTL=TTL;
        destroyedTimeStamp=-1f;
        defineExplosionDetector();
    }

    private void defineExplosionDetector(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.BOMB_EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |PirateGame.ROCK_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        switch (direction){
            case UP:
                body.setLinearVelocity(new Vector2(0,VELOCITY));
                break;
            case DOWN:
                body.setLinearVelocity(0,-VELOCITY);
                break;
            case LEFT:
                body.setLinearVelocity(-VELOCITY,0);
                break;
            case RIGHT:
                body.setLinearVelocity(VELOCITY,0);
                break;
        }
    }

    @Override
    public void update(float dt) {
        stateTime-=dt;
        if (setToDestroy & !destroyed){
            destroyedTimeStamp=TTL-stateTime;
            world.destroyBody(body);
            destroyed=true;
        }
        if (!destroyed){
            if (stateTime<0){
                setToDestroy=true;
            }
        }
    }

    public void draw(Batch batch){

    }

    public void onHit(){
        setToDestroy=true;
    }

    public enum ExpolsionDirection {UP, DOWN, LEFT, RIGHT}



}
