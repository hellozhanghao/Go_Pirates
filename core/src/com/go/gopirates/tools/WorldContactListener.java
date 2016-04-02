package com.go.gopirates.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;
import com.go.gopirates.sprites.powerUp.PowerUp;
import com.go.gopirates.sprites.tileObjects.InteractiveTileObject;
import com.go.gopirates.sprites.tileObjects.Reef;
import com.go.gopirates.sprites.tileObjects.Treasure;
import com.go.gopirates.weapon.Pistol;
import com.go.gopirates.weapon.Sword;


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
        System.out.println(cDef);

        switch (cDef) {

            //**************FINISHED*************//
            // Player vs. treasure
            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).onHit((Pirate) fixA.getUserData());
                else
                    ((Treasure) fixA.getUserData()).onHit((Pirate) fixB.getUserData());
                break;
            // Reef vs. bomb
            // Reef vs. bomb
            case PirateGame.REEF_BIT | PirateGame.EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.REEF_BIT)
                    ((Reef) fixA.getUserData()).onHit();
                else
                    ((Reef) fixB.getUserData()).onHit();
                break;

            // Pistol vs. Reef 
            // TODO: 31/3/16 destroy bullet
            case PirateGame.BULLET_BIT | PirateGame.REEF_BIT:
                if (fixA.getFilterData().categoryBits != PirateGame.REEF_BIT)
                    ((Pistol) fixA.getUserData()).setToDestroy();
                else
                    ((Pistol) fixB.getUserData()).setToDestroy();
                hitReef = true;
                break;

            // TODO: 27/3/16 fix pistol
            // Player vs. bullet(pistol)
            case PirateGame.PLAYER_BIT | PirateGame.BULLET_BIT:
                if (hitReef) {
                    hitReef = false;
                    break;
                }
                if(fixA.getFilterData().categoryBits != PirateGame.PLAYER_BIT)
                    ((Pistol) fixA.getUserData()).hitByBullet();
                else
                    ((Pistol) fixB.getUserData()).hitByBullet();
                break;
            // Player vs. bomb || TNT

            case PirateGame.PLAYER_BIT | PirateGame.TNT_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).hitByTNT((Pirate) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).hitByTNT((Pirate) fixA.getUserData());
                break;

            //  Player vs. sword
            case PirateGame.PLAYER_BIT | PirateGame.SWORD_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Sword) fixA.getUserData()).hitBySword();
                else
                    ((Sword) fixB.getUserData()).hitBySword();
                break;
            // Player vs. power up
            case PirateGame.POWERUP_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).use((Pirate) fixB.getUserData());
                else
                    ((PowerUp) fixB.getUserData()).use((Pirate) fixA.getUserData());
                break;

//            case PirateGame.PLAYER_BIT | PirateGame.POWERUP_BIT:
//                System.out.println("collide");
//                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
//                                    ((PowerUp) fixA.getUserData()).use((Pirate) fixB.getUserData());
//                else
//                    ((PowerUp) fixB.getUserData()).use((Pirate) fixA.getUserData());
//                break;

//            case PirateGame.PLAYER_BIT | PirateGame.BORDER_BIT:
//                Hud.setTestMsg("Testing: C");
//                break;
//            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
//                System.out.println("Win");
//                if(fixA.getFilterData().categoryBits == PirateGame.TREASURE_BIT)
//                    ((InteractiveTileObject) fixA.getUserData()).onHit((Pirate) fixA.getUserData());
//                else
//                    ((InteractiveTileObject) fixB.getUserData()).onHit((Pirate) fixB.getUserData());
//                break;
            // Player vs. bomb || TNT
            case PirateGame.PLAYER_BIT | PirateGame.EXPLOSION_BIT:
                screen.getPirate(PirateGame.THIS_PLAYER).decreaseHealth(25);
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
