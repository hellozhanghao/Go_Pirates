package com.go.gopirates.sprites.items.primitiveWeaponItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */

public class Coconut extends PrimitiveWeaponItem {
    private final float COCONUT_RADIUS = 80;
    private final float COCONUT_SPEED = 100;
    float stateTime;

    public Coconut(PlayScreen screen) {
        super(screen);
        stateTime=0;
        setRegion(screen.coconutAnimation.getKeyFrame(0, true));
        setSize(COCONUT_RADIUS*2 / PirateGame.PPM , COCONUT_RADIUS*2 / PirateGame.PPM);
        defineItem();
    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();

        float posX = screen.getPirate().b2body.getPosition().x;
        float posY = screen.getPirate().b2body.getPosition().y;

        switch (screen.getPirate().direction){
            case UP:
                bodyDef.position.set(posX, posY + ((PirateGame.TILE_SIZE*3/5+COCONUT_RADIUS))/PirateGame.PPM);
                break;
            case DOWN:
                bodyDef.position.set(posX, posY - ((PirateGame.TILE_SIZE*3/5+COCONUT_RADIUS))/PirateGame.PPM);
                break;
            case LEFT:
                bodyDef.position.set(posX - ((PirateGame.TILE_SIZE/2+COCONUT_RADIUS))/PirateGame.PPM,posY);
                break;
            case RIGHT:
                bodyDef.position.set(posX + ((PirateGame.TILE_SIZE/2+COCONUT_RADIUS))/PirateGame.PPM,posY);
                break;
        }
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(COCONUT_RADIUS/ PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.COCONUT_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT |  PirateGame.COCONUT_TREE_BIT;

        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(this);

        switch (screen.getPirate().direction){
            case UP:
                body.setLinearVelocity(new Vector2(0,COCONUT_SPEED));
                break;
            case DOWN:
                body.setLinearVelocity(new Vector2(0,-COCONUT_SPEED));
                break;
            case LEFT:
                body.setLinearVelocity(new Vector2(-COCONUT_SPEED,0));
                break;
            case RIGHT:
                body.setLinearVelocity(new Vector2(COCONUT_SPEED,0));
                break;
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTime += dt;
        setPosition(body.getPosition().x - COCONUT_RADIUS / PirateGame.PPM,
                body.getPosition().y  - COCONUT_RADIUS / PirateGame.PPM);
        setRegion(screen.coconutAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void hitOnPlayer() {
        super.hitOnPlayer();
        destroy();
        Gdx.app.log("Weapon", "Hit by coconut");
    }


}
