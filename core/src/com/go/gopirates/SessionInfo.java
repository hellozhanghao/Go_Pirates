package com.go.gopirates;



import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhanghao on 8/4/16.
 */
public class SessionInfo {
    public String mId;
    public String mRoomId;
    public ArrayList mParticipants;
    public ArrayList<String> mParticipantsString;
    public String mState;

    public final int ROOM_NULL=1000;
    public final int ROOM_WAIT=1001;
    public final int ROOM_PLAY=1002;

    public SessionInfo(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
        mParticipantsString=new ArrayList<String>();
    }

    public void setUpGame(){
        PirateGame.NUMBER_OF_PLAYERS=mParticipants.size();
        Gdx.app.log("PirateGame",String.valueOf(mParticipants.size()));
        Collections.sort(mParticipantsString);
        for (int i = 0; i < mParticipants.size(); i++) {
            if (mId.equals(mParticipantsString.get(i)))
                PirateGame.PLAYER_ID=i;
        }
    }

    public void endSession(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
    }

}
