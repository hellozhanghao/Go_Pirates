package com.go.gopirates.sprites.items.explosiveItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.items.noneInteractiveItems.ExplosionSprite;
import com.go.gopirates.sprites.items.noneInteractiveItems.TNTExplosionDetector;


/**
 * Created by zhanghao on 31/3/16.
 */
public class TNT extends ExplosiveItem {

    public Array<TNTExplosionDetector> explosionDetectors;
    public boolean showedExplosion;
    private float stateTimer = Float.MIN_VALUE;

    public TNT(PlayScreen screen, float x, float y) {
        super(screen,x,y);
        setRegion(screen.getAtlas().findRegion("object_TNT"), 0, 0, 500, 500);
        explosionDetectors=new Array<TNTExplosionDetector>();
        showedExplosion = false;
    }

    @Override
    public void defineExplosiveItem() {
        super.defineExplosiveItem();
        Gdx.app.log("Explosive", "TNT placed");
    }

    @Override
    public void use() {
        PirateGame.manager.get("audio/sounds/TNT.mp3", Sound.class).play();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                screen.getPirate().nonInteractiveSprites.add(
                        new ExplosionSprite(screen,posX-(2-i)*PirateGame.TILE_SIZE/PirateGame.PPM,
                                                   posY+(j-2)*PirateGame.TILE_SIZE/PirateGame.PPM));
        }
        for (int i = 0; i < 5; i++) {
            explosionDetectors.add(new TNTExplosionDetector(screen, posX - (2 - i) * PirateGame.TILE_SIZE / PirateGame.PPM,
                    posY + 2 * PirateGame.TILE_SIZE / PirateGame.PPM));
        }
        setToDestroy();
        stateTimer = 0;
    }

    public void update(float dt) {
        super.update(dt);
        stateTimer += stateTimer;
        if (stateTimer > 5f)
            showedExplosion = true;
    }

}