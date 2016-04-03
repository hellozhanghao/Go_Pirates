package com.go.gopirates.sprites.items.primitiveWeaponItem;

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

    private final float COCONUT_SPEED=100;
    public Coconut(PlayScreen screen) {
        super(screen);
        /*
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bomb"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        */
    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(screen.getPirate().b2body.getPosition().x,screen.getPirate().b2body.getPosition().y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(80/ PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.COCONUT_BIT;
        fixtureDef.filter.maskBits = PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
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



}
