package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screens.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Shield extends PowerUp {
    private boolean used;
    public Shield(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        used = false;

//        setRegion(screen.getAtlas().findRegion("shield"), 0, 0, 4, 4);
//        velocity = new Vector2(0.7f, 0);
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
        System.out.println("Take shield");
        used = true;

    }

    public void update (float dt) {
        super.update(dt);
    }
}
