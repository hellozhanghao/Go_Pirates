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
import com.go.gopirates.sprites.items.noneInteractiveItems.ExplosionSprite;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends ExplosiveItem {

    public Bomb(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("object_bomb"), 0, 0, 500, 500);
    }

    @Override
    public void defineExplosiveItem() {
        super.defineExplosiveItem();
        Gdx.app.log("Explosive", "Bomb placed");

    }

    @Override
    public void use() {
        world.destroyBody(body);
        screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,
                posX - (PirateGame.TILE_SIZE / 2) / PirateGame.PPM,
                posY - (PirateGame.TILE_SIZE / 2) / PirateGame.PPM));

        screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,
                posX - (PirateGame.TILE_SIZE/2) / PirateGame.PPM,
                posY + (PirateGame.TILE_SIZE/2) / PirateGame.PPM));
        screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,
                posX + (PirateGame.TILE_SIZE/2) / PirateGame.PPM,
                posY - (PirateGame.TILE_SIZE/2) / PirateGame.PPM));
        screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,
                posX - (PirateGame.TILE_SIZE/2) / PirateGame.PPM,
                posY - (PirateGame.TILE_SIZE*3/2) / PirateGame.PPM));
        screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,
                posX - (PirateGame.TILE_SIZE*3/2) / PirateGame.PPM,
                posY - (PirateGame.TILE_SIZE/2) / PirateGame.PPM));


        PirateGame.manager.get("audio/sounds/bomb.ogg", Sound.class).play();

        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        EdgeShape exploEdge1 = new EdgeShape();
        exploEdge1.set(new Vector2(-PirateGame.TILE_SIZE * 2 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(PirateGame.TILE_SIZE * 2 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge1;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("explosionCross1");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge2 = new EdgeShape();
        exploEdge2.set(new Vector2(0 / PirateGame.PPM, -PirateGame.TILE_SIZE * 2 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, PirateGame.TILE_SIZE * 2 / PirateGame.PPM));

        fdef.shape = exploEdge2;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("explosionCross2");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        EdgeShape exploEdge3 = new EdgeShape();
        exploEdge3.set(new Vector2(-PirateGame.TILE_SIZE / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(PirateGame.TILE_SIZE / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge3;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("explosionCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge4 = new EdgeShape();
        exploEdge4.set(new Vector2(0 / PirateGame.PPM, -PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM));

        fdef.shape = exploEdge4;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("explosionCross4");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
    }


}


