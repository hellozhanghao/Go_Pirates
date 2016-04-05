package com.go.gopirates.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.tileObjects.Barrel;
import com.go.gopirates.sprites.tileObjects.CoconutTree;
import com.go.gopirates.sprites.tileObjects.Rock;
import com.go.gopirates.sprites.tileObjects.Treasure;


/**
 * Created by Amy on 1/3/16.
 */
public class B2WorldCreator {
//    private Array<Goomba> goombas;
//    private Array<Turtle> turtles;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;



        for(MapObject object : map.getLayers().get(2).getObjects().getByType(MapObject.class)){
            new Rock(screen, object);
        }
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(MapObject.class)){
            new Barrel(screen, object);
        }
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(MapObject.class)){
            new CoconutTree(screen, object);
        }
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(MapObject.class)){
            new Treasure(screen, object);
        }
//        for(MapObject object : map.getLayers().get(6).getObjects().getByType(MapObject.class)){
//            new Border(screen, object);
//        }




    }

}