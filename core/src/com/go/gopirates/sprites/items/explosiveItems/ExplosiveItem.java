package com.go.gopirates.sprites.items.explosiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.tools.TileMapTranslator;


/**
 * Created by zhaojuan on 20/3/16.
 */
public abstract class ExplosiveItem extends Sprite {


    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy,setToDestroy,destroyed,exploded,redefined;
    protected Body body;
    protected float posX,posY,stateTime;

    final int BOMB_FIXTURE_RADIUS = 40;
    protected final float TIME_TO_EXPLODE = 4;
    protected final float TIME_TO_PRESENCE = 1.5f;
    protected final float EXPLOSION_TIME=0.1f;
    protected final float BOMB_SIZE=350f;

    public ExplosiveItem(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        Vector2 pos= TileMapTranslator.translate(x,y);
        posX=pos.x;
        posY=pos.y;
        setBounds(posX, posY, BOMB_SIZE / PirateGame.PPM, BOMB_SIZE / PirateGame.PPM);
        setPosition(posX-BOMB_SIZE/PirateGame.PPM/2,
                    posY-BOMB_SIZE/PirateGame.PPM/2);
        toDestroy = false;
        destroyed = false;
        defineExplosiveItem();
    }

    protected void defineExplosiveItem(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_FIXTURE_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT | PirateGame.COCONUT_TREE_BIT | PirateGame.PLAYER_BIT | PirateGame.ROCK_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    protected void definePresence() {
        world.destroyBody(body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_FIXTURE_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.PLAYER_BIT | PirateGame.SHIELD_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public abstract void use();

    public void update(float dt) {
        stateTime += dt;
//        setPosition(posX,posY);
        if ((stateTime > TIME_TO_PRESENCE || setToDestroy) && !destroyed && !redefined) {
            definePresence();
            redefined = true;
        }
        else if ((stateTime > TIME_TO_EXPLODE || setToDestroy) && !destroyed && !exploded) {
            use();
            exploded = true;
        }
        if ((stateTime > TIME_TO_EXPLODE + EXPLOSION_TIME || setToDestroy) && !destroyed && exploded) {
            setToDestroy();
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

}


