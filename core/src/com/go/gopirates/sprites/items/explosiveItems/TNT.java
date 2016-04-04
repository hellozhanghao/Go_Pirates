package com.go.gopirates.sprites.items.explosiveItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;


/**
 * Created by zhanghao on 31/3/16.
 */
public class TNT extends ExplosiveItem {

    public TNT(PlayScreen screen, float x, float y) {
        super(screen,x,y);
    }

    @Override
    public void defineExplosiveItem() {
        super.defineExplosiveItem();
        Gdx.app.log("Explosive", "TXT placed");
    }


    @Override
    public void use() {
        PirateGame.manager.get("audio/sounds/bomb.ogg", Sound.class).play();

        Vector2 position = body.getPosition();
        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        EdgeShape exploEdge1 = new EdgeShape();
        exploEdge1.set(new Vector2(-PirateGame.TILE_SIZE/ PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(PirateGame.TILE_SIZE / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge1;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("HerizontalCross2");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge2 = new EdgeShape();
        exploEdge2.set(new Vector2(-PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(20 / PirateGame.PPM, 20/ PirateGame.PPM));

        fdef.shape = exploEdge2;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("HerizontalCross1");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT|
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        EdgeShape exploEdge3 = new EdgeShape();
        exploEdge3.set(new Vector2(-PirateGame.TILE_SIZE / PirateGame.PPM, -PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(20 / PirateGame.PPM, -20 / PirateGame.PPM));

        fdef.shape = exploEdge3;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("HerizontalCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge4 = new EdgeShape();
        exploEdge4.set(new Vector2(-PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(-20 / PirateGame.PPM, -20 / PirateGame.PPM));

        fdef.shape = exploEdge4;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("VerticalCross1");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT|
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge5 = new EdgeShape();
        exploEdge5.set(new Vector2(0 / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, -20 / PirateGame.PPM));
        fdef.shape = exploEdge5;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("VerticalCross2");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge6 = new EdgeShape();
        exploEdge6.set(new Vector2(PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(20 / PirateGame.PPM, -20 / PirateGame.PPM));

        fdef.shape = exploEdge6;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("VerticalCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;


    }


}

