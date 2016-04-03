package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screens.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class ShieldSprite extends Sprite {
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;

    public ShieldSprite(PlayScreen screen, float x, float y) {
        setBounds(x, y, PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE/ PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();

        // TODO: 18/3/16 Shield Animation
        /*
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        */
        defineShield();

    }

    public void defineShield() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(screen.getPirate().b2body.getPosition().x,screen.getPirate().b2body.getPosition().y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 5f;
        b2body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(150/PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.NOTHING_BIT;


        fixtureDef.shape=shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float dt) {
        stateTime += dt;
        b2body.setTransform(screen.getPirate().b2body.getPosition().x, screen.getPirate().b2body.getPosition().y, 0);
        if ((stateTime>PirateGame.POWERUP_TIME) & !destroyed){
            setToDestroy();
            world.destroyBody(b2body);
            destroyed=true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }



}
