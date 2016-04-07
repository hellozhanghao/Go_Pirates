package com.go.gopirates.sprites.items.noneInteractiveItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by zhanghao on 3/4/16.
 */
public abstract class NonInteractiveSprites extends Sprite {

    public abstract void update(float dt);

    public void draw(Batch batch){
        if (!(this instanceof ShoeSprite)){
            super.draw(batch);
        }
    }
}
