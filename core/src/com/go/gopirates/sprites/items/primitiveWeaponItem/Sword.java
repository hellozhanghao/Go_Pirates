package com.go.gopirates.sprites.items.primitiveWeaponItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public class Sword extends PrimitiveWeaponItem {

    private float stateTimer;

    private final float SWORD_LENGTH =PirateGame.TILE_SIZE/4;

    public Sword(PlayScreen screen) {
        super(screen);
        stateTimer=0;
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
        float posX=screen.getPirate().b2body.getPosition().x;
        float posY=screen.getPirate().b2body.getPosition().y;

        switch (screen.getPirate().direction){
            case UP:
                bodyDef.position.set(posX, posY + ((PirateGame.TILE_SIZE/2+ SWORD_LENGTH))/PirateGame.PPM);
                break;
            case DOWN:
                bodyDef.position.set(posX, posY - ((PirateGame.TILE_SIZE/2+ SWORD_LENGTH))/PirateGame.PPM);
                break;
            case LEFT:
                bodyDef.position.set(posX - ((PirateGame.TILE_SIZE/2+ SWORD_LENGTH))/PirateGame.PPM,posY);
                break;
            case RIGHT:
                bodyDef.position.set(posX + ((PirateGame.TILE_SIZE/2+ SWORD_LENGTH))/PirateGame.PPM,posY);
                break;
        }
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        body=world.createBody(bodyDef);

        FixtureDef fixtureDef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(SWORD_LENGTH / PirateGame.PPM);
        fixtureDef.filter.categoryBits = PirateGame.SWORD_BIT;
        fixtureDef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.BOMB_BIT | PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT |
                PirateGame.BARREL_BIT | PirateGame.TREASURE_BIT |  PirateGame.COCONUT_TREE_BIT;
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(this);

    }



    public void update(float dt){
        super.update(dt);
        stateTimer+=dt;

        if (stateTimer>PirateGame.SWORD_VALID_TIME)
            destroy();

        float posX=screen.getPirate().b2body.getPosition().x;
        float posY=screen.getPirate().b2body.getPosition().y;

        switch (screen.getPirate().direction){
            case UP:
                body.setTransform(posX, posY + ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH)) / PirateGame.PPM, 0);
                break;
            case DOWN:
                body.setTransform(posX, posY - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH)) / PirateGame.PPM, 0);
                break;
            case LEFT:
                body.setTransform(posX - ((PirateGame.TILE_SIZE / 2 + SWORD_LENGTH)) / PirateGame.PPM, posY, 0);
                break;
            case RIGHT:
                body.setTransform(posX + ((PirateGame.TILE_SIZE/2+ SWORD_LENGTH))/PirateGame.PPM,posY,0);
                break;
        }
    }

    @Override
    public void hitOnPlayer() {
        super.hitOnPlayer();
        Gdx.app.log("Weapon","Hit by Sword");
    }
}
