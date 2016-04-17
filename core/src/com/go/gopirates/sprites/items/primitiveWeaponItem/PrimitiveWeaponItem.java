package com.go.gopirates.sprites.items.primitiveWeaponItem;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.screen.PlayScreen;

/**
 * Created by zhanghao on 3/4/16.
 */
public abstract class PrimitiveWeaponItem  extends Sprite{

    protected PlayScreen screen;
    protected World world;

    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;
    protected TiledMap map;

    public PrimitiveWeaponItem(PlayScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public  void hitOnPlayer(){
        screen.getPirate().decreaseHealth(1);
    }

    public void destroy(){
        toDestroy = true;
    }

    public void draw(Batch batch){
        if(!destroyed )
            super.draw(batch);
    }


}
