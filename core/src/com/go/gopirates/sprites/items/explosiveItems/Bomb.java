package com.go.gopirates.sprites.items.explosiveItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.noneInteractiveItems.ExplosionDetector;
import com.go.gopirates.sprites.items.noneInteractiveItems.ExplosionSprite;


/**
 * Created by zhaojuan on 20/3/16.
 */
public class Bomb extends ExplosiveItem {

    public Array<ExplosionDetector> explosionDetectors;
    private boolean detectorCreated,showedExplosion;
    private final float EXPLOSION_THRESHOLD=0.25f;

    public Bomb(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        explosionDetectors=new Array<ExplosionDetector>();
        setRegion(screen.getAtlas().findRegion("object_bomb"), 0, 0, 500, 500);
        detectorCreated=false;
        showedExplosion=false;

    }

    @Override
    public void defineExplosiveItem() {
        super.defineExplosiveItem();
        Gdx.app.log("Explosive", "Bomb placed");
    }

    @Override
    public void use() {
//        world.destroyBody(body);
        float TTL=0.4f;
        explosionDetectors.add(new ExplosionDetector(screen,posX,posY, ExplosionDetector.ExpolsionDirection.UP,TTL));
        explosionDetectors.add(new ExplosionDetector(screen,posX,posY, ExplosionDetector.ExpolsionDirection.DOWN,TTL));
        explosionDetectors.add(new ExplosionDetector(screen,posX,posY, ExplosionDetector.ExpolsionDirection.LEFT,TTL));
        explosionDetectors.add(new ExplosionDetector(screen, posX, posY, ExplosionDetector.ExpolsionDirection.RIGHT, TTL));
        detectorCreated=true;
    }

    public void update(float dt){
        super.update(dt);
        if (detectorCreated & !showedExplosion){
            if (explosionDetectors.get(0).destroyedTimeStamp>0 & explosionDetectors.get(1).destroyedTimeStamp>0
                    &explosionDetectors.get(2).destroyedTimeStamp>0 & explosionDetectors.get(3).destroyedTimeStamp>0){
                PirateGame.manager.get("audio/sounds/bomb.ogg", Sound.class).play();
                screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen, posX, posY));
                if (explosionDetectors.get(0).destroyedTimeStamp>EXPLOSION_THRESHOLD)
                    screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,posX,posY+PirateGame.TILE_SIZE/PirateGame.PPM));
                if (explosionDetectors.get(1).destroyedTimeStamp>EXPLOSION_THRESHOLD)
                    screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,posX,posY-PirateGame.TILE_SIZE/PirateGame.PPM));
                if (explosionDetectors.get(2).destroyedTimeStamp>EXPLOSION_THRESHOLD)
                    screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,posX-PirateGame.TILE_SIZE/PirateGame.PPM,posY));
                if (explosionDetectors.get(3).destroyedTimeStamp>EXPLOSION_THRESHOLD)
                    screen.getPirate().nonInteractiveSprites.add(new ExplosionSprite(screen,posX+PirateGame.TILE_SIZE/PirateGame.PPM,posY));
                showedExplosion=true;
            }

        }
    }


}


