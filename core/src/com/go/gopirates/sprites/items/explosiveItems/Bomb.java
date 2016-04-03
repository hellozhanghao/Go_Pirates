package com.go.gopirates.sprites.items.explosiveItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends ExplosiveItem {
    PlayScreen screen;
    World world;
    Animation bombAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean exploded;
    boolean redefined;
    Body b2body;
    private int bombExplosionCountDown=4;
    private final  int BOMB_RADIUS=80;

    public Bomb(PlayScreen screen, float x, float y) {
        setBounds(x, y, PirateGame.TILE_SIZE / PirateGame.PPM, PirateGame.TILE_SIZE/ PirateGame.PPM);
        this.screen = screen;
        this.world = screen.getWorld();

        defineItem();
    }

    @Override
    public void defineItem() {
        Gdx.app.log("Explosive","Bomb placed");
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void redefineItem() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.BOMB_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(BOMB_RADIUS / PirateGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = PirateGame.NOTHING_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        EdgeShape exploEdge1 = new EdgeShape();
        exploEdge1.set(new Vector2(-PirateGame.TILE_SIZE*2 / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(PirateGame.TILE_SIZE*2 / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge1;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross1");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge2 = new EdgeShape();
        exploEdge2.set(new Vector2(0 / PirateGame.PPM, -PirateGame.TILE_SIZE*2 / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, PirateGame.TILE_SIZE*2 / PirateGame.PPM));

        fdef.shape = exploEdge2;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross2");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
        EdgeShape exploEdge3 = new EdgeShape();
        exploEdge3.set(new Vector2(-PirateGame.TILE_SIZE / PirateGame.PPM, 0 / PirateGame.PPM), new Vector2(PirateGame.TILE_SIZE / PirateGame.PPM, 0 / PirateGame.PPM));

        fdef.shape = exploEdge3;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross3");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;

        EdgeShape exploEdge4 = new EdgeShape();
        exploEdge4.set(new Vector2(0 / PirateGame.PPM, -PirateGame.TILE_SIZE / PirateGame.PPM), new Vector2(0 / PirateGame.PPM, PirateGame.TILE_SIZE / PirateGame.PPM));

        fdef.shape = exploEdge4;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("explosionCross4");
        fdef.filter.categoryBits = PirateGame.EXPLOSION_BIT;
        fdef.filter.maskBits = PirateGame.BARREL_BIT |
                PirateGame.PLAYER_BIT |
                PirateGame.ROCK_BIT;
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 2 || setToDestroy) && !destroyed && ! redefined){
            redefineItem();
            redefined=true;
        }
        //set Explosion time to 4
        else if ((stateTime > bombExplosionCountDown || setToDestroy) && !destroyed && !exploded){
            use();
            exploded=true;
        }
        if ((stateTime > bombExplosionCountDown+1 || setToDestroy) && !destroyed && exploded){
            setToDestroy();
            world.destroyBody(b2body);
            destroyed=true;
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

}
