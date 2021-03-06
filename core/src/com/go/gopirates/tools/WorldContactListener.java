package com.go.gopirates.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.noneInteractiveItems.BombExplosionDetector;
import com.go.gopirates.sprites.items.powerUps.PowerUp;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Coconut;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Sword;
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
            // Player vs. treasure
            case PirateGame.PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).onHit();
                else
                    ((Treasure) fixA.getUserData()).onHit();
                break;
            case PirateGame.SHIELD_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.SHIELD_BIT)
                    ((Treasure) fixB.getUserData()).onHit();
                else
                    ((Treasure) fixA.getUserData()).onHit();
                break;
            case PirateGame.OTHER_PLAYER_BIT | PirateGame.TREASURE_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.OTHER_PLAYER_BIT)
                    ((Treasure) fixB.getUserData()).onHitbyOthers();
                else
                    ((Treasure) fixA.getUserData()).onHitbyOthers();
                break;
            case PirateGame.BARREL_BIT | PirateGame.BOMB_EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.BARREL_BIT)
                    ((Barrel) fixA.getUserData()).onHit();
                else
                    ((Barrel) fixB.getUserData()).onHit();
                break;

            case PirateGame.COCONUT_TREE_BIT | PirateGame.BOMB_EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COCONUT_TREE_BIT)
                    ((CoconutTree) fixA.getUserData()).onHit();
                else
                    ((CoconutTree) fixB.getUserData()).onHit();
                break;
            case PirateGame.BARREL_BIT | PirateGame.TNT_EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.BARREL_BIT)
                    ((Barrel) fixA.getUserData()).onHit();
                else
                    ((Barrel) fixB.getUserData()).onHit();
                break;

            case PirateGame.ROCK_BIT | PirateGame.BOMB_EXPLOSION_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.BOMB_EXPLOSION_BIT)
                    ((BombExplosionDetector) fixA.getUserData()).onHit();
                else
                    ((BombExplosionDetector) fixB.getUserData()).onHit();
                break;

             //Player vs. power up
            case PirateGame.POWERUP_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).use();
                else
                    ((PowerUp) fixB.getUserData()).use();
                break;
            case PirateGame.POWERUP_BIT | PirateGame.OTHER_PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).usebyOthers();
                else
                    ((PowerUp) fixB.getUserData()).usebyOthers();
                break;
            case PirateGame.POWERUP_BIT | PirateGame.SHIELD_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.POWERUP_BIT)
                    ((PowerUp) fixA.getUserData()).use();
                else
                    ((PowerUp) fixB.getUserData()).use();
                break;
            case PirateGame.SHIELD_BIT | PirateGame.PLAYER_BIT:
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
            case PirateGame.COCONUT_BIT | PirateGame.TREASURE_BIT:
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
            case PirateGame.COCONUT_BIT | PirateGame.OTHER_PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.COCONUT_BIT)
                    ((Coconut) fixA.getUserData()).hitOnOthers();
                else
                    ((Coconut) fixB.getUserData()).hitOnOthers();
                break;
            case PirateGame.SWORD_BIT | PirateGame.PLAYER_BIT:
                Gdx.app.log("Sword", "Hit!");
                if(fixA.getFilterData().categoryBits == PirateGame.SWORD_BIT)
                    ((Sword) fixA.getUserData()).hitOnPlayer();
                else
                    ((Sword) fixB.getUserData()).hitOnPlayer();
                break;
            case PirateGame.SWORD_BIT | PirateGame.OTHER_PLAYER_BIT:
                Gdx.app.log("Sword", "Hit!");
                if(fixA.getFilterData().categoryBits == PirateGame.SWORD_BIT)
                    ((Sword) fixA.getUserData()).hitOnPlayer();
                else
                    ((Sword) fixB.getUserData()).hitOnPlayer();
                break;
            case PirateGame.PLAYER_BIT | PirateGame.BOMB_EXPLOSION_BIT:
                screen.getPirate().hitInExplosion();
                break;
            case PirateGame.PLAYER_BIT | PirateGame.TNT_EXPLOSION_BIT:
                screen.getPirate().hitInExplosion();
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
