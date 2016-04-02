package com.go.gopirates.Tools;



import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.go.gopirates.PirateGame;
import com.go.gopirates.Other.FireBall;
import com.go.gopirates.Sprites.Enemies.Enemy;
import com.go.gopirates.Sprites.Items.Item;
import com.go.gopirates.Sprites.Pirate;
import com.go.gopirates.Sprites.TileObjects.InteractiveTileObject;

/**
 * Created by Amy on 25/2/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case PirateGame.MARIO_HEAD_BIT | PirateGame.BRICK_BIT:
            case PirateGame.MARIO_HEAD_BIT | PirateGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Pirate) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Pirate) fixB.getUserData());
                break;
            case PirateGame.ENEMY_HEAD_BIT | PirateGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Pirate) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Pirate) fixA.getUserData());
                break;
            case PirateGame.ENEMY_BIT | PirateGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case PirateGame.MARIO_BIT | PirateGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.MARIO_BIT)
                    ((Pirate) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Pirate) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case PirateGame.ENEMY_BIT | PirateGame.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case PirateGame.ITEM_BIT | PirateGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case PirateGame.ITEM_BIT | PirateGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Pirate) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Pirate) fixA.getUserData());
                break;
            case PirateGame.FIREBALL_BIT | PirateGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
