package com.go.gopirates.sprites.items.powerUps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
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
public abstract class PowerUp extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;
    protected TiledMap map;

    public PowerUp(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        setPosition(x, y);
        setBounds(getX(),getY(), PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

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

    public abstract void use();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
//            getCell().setTile(null);
        }
    }
    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * PirateGame.PPM / PirateGame.TILE_SIZE),
                (int) (body.getPosition().y * PirateGame.PPM / PirateGame.TILE_SIZE));
    }

    public void destroy(){
        toDestroy = true;
    }

}

