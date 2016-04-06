package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 4/4/16.
 */
public class ExplosionSprite  extends NonInteractiveSprites{

    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed,setToDestroy;
    Body b2body;
    private Animation explosion;
    private float posX,posY;
    private final float FRAME_DURATION=0.08f;

    public ExplosionSprite(PlayScreen screen, float x, float y) {
        setBounds(x, y, PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
        this.posX=x;
        this.posY=y;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 6; j++)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("explosion"),
                        i*PirateGame.TILE_SIZE, j*PirateGame.TILE_SIZE, PirateGame.TILE_SIZE, PirateGame.TILE_SIZE));
        explosion = new Animation(FRAME_DURATION,frames);
        stateTime=0;
        defineExplosion();
    }

    public void defineExplosion() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX,posY);
        bodyDef.type= BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);
        fixtureDef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

    }


    @Override
    public void update(float dt) {
        stateTime+=dt;
        setRegion(explosion.getKeyFrame(stateTime));
        if (stateTime>FRAME_DURATION*6)
            setToDestroy=true;
        if (setToDestroy & !destroyed){
            world.destroyBody(b2body);
            destroyed=true;
        }
    }


    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }


}
