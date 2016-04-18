package com.go.gopirates;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public static ArrayList<String> mParticipantsString;
    public String mState;
    public Map<String,Integer> mParticipantsMap;

    public SessionInfo(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
        mParticipantsString=new ArrayList<String>();
        mParticipantsMap = new HashMap<String,Integer>();
    }

    public void setUpGame(){
        PirateGame.NUMBER_OF_PLAYERS=mParticipants.size();
        Gdx.app.log("PirateGame", String.valueOf(mParticipants.size()));
        Collections.sort(mParticipantsString);
        Gdx.app.log("SET UP GAME", mParticipantsMap.toString());
        for (int i = 0; i < mParticipants.size(); i++) {
            if (mId.equals(mParticipantsString.get(i))) {
                PirateGame.PLAYER_ID = i;
            }
            mParticipantsMap.put(mParticipantsString.get(i),i);
        }
        Gdx.app.log("PirateGame", mParticipantsMap.toString());
    }

    public void endSession(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
        mParticipantsString = new ArrayList<String>();
    }

}
