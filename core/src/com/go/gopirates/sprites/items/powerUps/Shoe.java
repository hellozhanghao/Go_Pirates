package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Shoe extends PowerUp {
    public Shoe(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(80 / PirateGame.PPM);
        fdef.filter.categoryBits = PirateGame.POWERUP_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        destroy();
        System.out.println("Take SHOE");
        screen.getPirate().powerUpHolding= Pirate.PowerUpHolding.SHOE;
    }
}
