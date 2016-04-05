package com.go.gopirates.tools;

import com.badlogic.gdx.math.Vector2;
import com.go.gopirates.PirateGame;

/**
 * Created by zhanghao on 5/4/16.
 */
public class TileMapTranslator {

    public static Vector2 translate(float posX, float posY){

        int tranX=(int)(posX*PirateGame.PPM)/PirateGame.TILE_SIZE *PirateGame.TILE_SIZE/(int) PirateGame.PPM;
        int tranY=(int)(posY*PirateGame.PPM)/PirateGame.TILE_SIZE *PirateGame.TILE_SIZE/(int) PirateGame.PPM;
        return new Vector2(tranX,tranY);
    }
}
