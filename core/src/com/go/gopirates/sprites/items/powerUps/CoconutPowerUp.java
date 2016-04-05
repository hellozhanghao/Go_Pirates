package com.go.gopirates.sprites.items.powerUps;
import com.go.gopirates.PirateGame;
import com.go.gopirates.screen.PlayScreen;
import com.go.gopirates.sprites.Pirate;
import com.go.gopirates.sprites.items.primitiveWeaponItem.Coconut;

/**
 * Created by zhanghao on 3/4/16.
 */
public class CoconutPowerUp extends PowerUp {
    private boolean taken = false;
    public CoconutPowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // TODO: 4/4/16 Add texture
    }

    @Override
    public void use() {
        destroy();

        if(screen.getPirate().coconuts.size() < 3 & !taken)
            screen.getPirate().coconuts.add(1);
        System.out.println("Take coconut: " + screen.getPirate().coconuts.size());
//        screen.getPirate().powerUpHolding = Pirate.PowerUpHolding.COCONUT;
        taken = true;
    }
}
