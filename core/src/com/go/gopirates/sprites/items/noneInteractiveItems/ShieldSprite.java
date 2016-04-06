package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class ShieldSprite extends NonInteractiveSprites {
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    Body b2body;
    TextureRegion textureRegion;

    public ShieldSprite(PlayScreen screen, float x, float y) {
        PirateGame.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        setBounds(x, y, 300 / PirateGame.PPM, 300 / PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();
        textureRegion = new TextureRegion(screen.getAtlas().findRegion("shield"), 0, 0, 300, 300);

        setRegion(textureRegion);

        defineShield();
    }

    public void defineShield() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(screen.getPirate().b2body.getPosition().x,screen.getPirate().b2body.getPosition().y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(150/PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.NOTHING_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - 150/PirateGame.PPM, b2body.getPosition().y - 150/PirateGame.PPM);
        b2body.setTransform(screen.getPirate().b2body.getPosition().x, screen.getPirate().b2body.getPosition().y, 0);
        if ((stateTime > PirateGame.POWERUP_TIME) & !destroyed){
            setToDestroy();
            world.destroyBody(b2body);
            destroyed=true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }


    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }
}
