package com.go.gopirates.sprites.items.primitiveWeaponItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;

/**
 * Created by zhanghao on 3/4/16.
 */

public class Coconut extends PrimitiveWeaponItem {
    float stateTime;
    private final float COCONUT_RADIUS = 80;
    private final float COCONUT_SPEED = 100;
    private Animation coconutAnimation;
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

        float posX = screen.getPirate().b2body.getPosition().x;
        float posY = screen.getPirate().b2body.getPosition().y;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        Texture coconutTexture=new Texture("img/objects/coconut_animation.png");
        frames.add(new TextureRegion(coconutTexture, 0, 0, PirateGame.TILE_SIZE/PirateGame.PPM, PirateGame.TILE_SIZE/PirateGame.PPM));
        frames.add(new TextureRegion(coconutTexture,1*COCONUT_RADIUS,0,COCONUT_RADIUS*2,COCONUT_RADIUS*2));
        frames.add(new TextureRegion(coconutTexture,2*COCONUT_RADIUS,0,COCONUT_RADIUS,COCONUT_RADIUS));
        frames.add(new TextureRegion(coconutTexture,3*COCONUT_RADIUS,0,COCONUT_RADIUS,COCONUT_RADIUS));
        coconutAnimation = new Animation(0.2f, frames);
        setRegion(coconutAnimation.getKeyFrame(0, true));
        setBounds(posX, posY, COCONUT_RADIUS/PirateGame.PPM,COCONUT_RADIUS/PirateGame.PPM);

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
//        setPosition(body.getPosition().x - 150 / PirateGame.PPM, body.getPosition().y - 150 / PirateGame.PPM);
//        body.setTransform(screen.getPirate().b2body.getPosition().x, screen.getPirate().b2body.getPosition().y, 0);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(coconutAnimation.getKeyFrame(stateTime, true));
        System.out.print((body.getPosition().x - getWidth() / 2) + ", " + (body.getPosition().y - getHeight() / 2) + " ");
    }

    @Override
    public void hitOnPlayer() {
        super.hitOnPlayer();
        Gdx.app.log("Weapon", "Hit by coconut");
    }
}
