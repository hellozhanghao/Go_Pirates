package com.go.gopirates;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zhanghao on 8/4/16.
 */
public class SessionInfo {
    public final int ROOM_NULL = 1000;
    public final int ROOM_WAIT = 1001;
    public final int ROOM_PLAY = 1002;
    public String mId;
    public String mRoomId;
    public ArrayList mParticipants;
    public ArrayList<String> mParticipantsString;
    public String mState;

    public HashMap<String, Integer> map;

    public SessionInfo(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
        mParticipantsString=new ArrayList<String>();
        map = new HashMap<String, Integer>();
    }

    public void setUpGame(){
        PirateGame.NUMBER_OF_PLAYERS=mParticipants.size();
        PirateGame.PLAYERS_ALIVE = mParticipants.size();
        Gdx.app.log("PirateGame",String.valueOf(mParticipants.size()));
        Collections.sort(mParticipantsString);
        for (int i = 0; i < mParticipantsString.size(); i++) {
            map.put(mParticipantsString.get(i), i);
            Gdx.app.log("Map", mParticipantsString.get(i) + " " + i);
        }
        PirateGame.PLAYER_ID = map.get(mId);
    }

    public void endSession(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
        mParticipantsString = new ArrayList<String>();
        map = new HashMap<String, Integer>();
    }

}
