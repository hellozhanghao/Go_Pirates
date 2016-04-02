package com.go.gopirates.sprites.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by zhaojuan on 21/3/16.
 */
public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }
}
