package com.go.gopirates.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.powerUps.PowerUp;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Coconut;
import com.go.gopirates.sprites.tileObjects.Barrel;
import com.go.gopirates.sprites.tileObjects.CoconutTree;
import com.go.gopirates.sprites.tileObjects.Treasure;


/**
 * Created by Amy on 25/2/16.
 */
public class WorldContactListener implements ContactListener {
    private PlayScreen screen;
    private boolean hitReef;

    public WorldContactListener(PlayScreen screen){
        this.screen=screen;
        this.hitReef = false;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            //**************FINISHED*************//
            // Player vs. treasure
            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).onHit();
                else
                    ((Treasure) fixA.getUserData()).onHit();
                break;
            // Reef vs. bomb
            // Reef vs. bomb
            case PirateGame.BARREL_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.BARREL_BIT)
                    ((Barrel) fixA.getUserData()).onHit();
                else
                    ((Barrel) fixB.getUserData()).onHit();
                break;

            case PirateGame.COCONUT_TREE_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_TREE_BIT)
                    ((CoconutTree) fixA.getUserData()).onHit();
                else
                    ((CoconutTree) fixB.getUserData()).onHit();
                break;

             //Player vs. power up
            case PirateGame.POWERUP_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).use();
                else
                    ((PowerUp) fixB.getUserData()).use();
                break;

            case PirateGame.COCONUT_BIT | PirateGame.ROCK_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_BIT)
                    ((Coconut) fixA.getUserData()).destroy();
                else
                    ((Coconut) fixB.getUserData()).destroy();
                break;

            case PirateGame.COCONUT_BIT | PirateGame.COCONUT_TREE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_BIT)
                    ((Coconut) fixA.getUserData()).destroy();
                else
                    ((Coconut) fixB.getUserData()).destroy();
                break;
            case PirateGame.COCONUT_BIT | PirateGame.BARREL_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_BIT)
                    ((Coconut) fixA.getUserData()).destroy();
                else
                    ((Coconut) fixB.getUserData()).destroy();
                break;
            case PirateGame.COCONUT_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_BIT)
                    ((Coconut) fixA.getUserData()).hitOnPlayer();
                else
                    ((Coconut) fixB.getUserData()).hitOnPlayer();
                break;




            // Pistol vs. Reef 
            // TODO: 31/3/16 destroy bullet
//            case PirateGame.COCONUT_BIT | PirateGame.BARREL_BIT:
//                if (fixA.getFilterData().categoryBits != PirateGame.BARREL_BIT)
//                    ((Pistol) fixA.getUserData()).setToDestroy();
//                else
//                    ((Pistol) fixB.getUserData()).setToDestroy();
//                hitReef = true;
//                break;

//            // TODO: 27/3/16 fix pistol
//            // Player vs. bullet(pistol)
//            case PirateGame.PLAYER_BIT | PirateGame.COCONUT_BIT:
//                if (hitReef) {
//                    hitReef = false;
//                    break;
//                }
//                if(fixA.getFilterData().categoryBits != PirateGame.PLAYER_BIT)
//                    ((Pistol) fixA.getUserData()).hitByBullet();
//                else
//                    ((Pistol) fixB.getUserData()).hitByBullet();
//                break;
            // Player vs. bomb || TNT

//            case PirateGame.PLAYER_BIT | PirateGame.TNT_BIT:
//                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
//                    ((InteractiveTileObject) fixA.getUserData()).hitByTNT((Pirate) fixB.getUserData());
//                else
//                    ((InteractiveTileObject) fixB.getUserData()).hitByTNT((Pirate) fixA.getUserData());
//                break;
//
//            //  Player vs. sword
//            case PirateGame.PLAYER_BIT | PirateGame.SWORD_BIT:
//                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
//                    ((Sword) fixA.getUserData()).hitBySword();
//                else
//                    ((Sword) fixB.getUserData()).hitBySword();
//                break;
//


//            case PirateGame.PLAYER_BIT | PirateGame.EXPLOSION_BIT:
//                screen.getPirate(PirateGame.THIS_PLAYER).decreaseHealth(25);
//                break;



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
