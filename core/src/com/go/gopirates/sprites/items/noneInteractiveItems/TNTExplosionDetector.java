package com.go.gopirates.sprites.items.noneInteractiveItems;

/**
 * Created by zhanghao on 8/4/16.
 */
import com.badlogic.gdx.graphics.g2d.Batch;
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
public class TNTExplosionDetector extends NonInteractiveSprites {

    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed,setToDestroy;
    Body body;
    private float posX,posY;
    private float RADIUS=10;
    private float VELOCITY=400;
    public final float TTL=0.35f;


    public TNTExplosionDetector(PlayScreen screen, float x, float y){
        this.screen=screen;
        this.world=this.screen.getWorld();
        posX=x;
        posY=y;
        this.stateTime=TTL;
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
        fdef.filter.categoryBits = PirateGame.TNT_EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.BARREL_BIT ;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(0,-VELOCITY);

    }
    @Override
    public void update(float dt) {
        stateTime-=dt;
        if (setToDestroy & !destroyed){
            world.destroyBody(body);
            destroyed=true;
        }
        if (stateTime<0){
            setToDestroy=true;
        }
    }

    public void draw(Batch batch){

    }




}
