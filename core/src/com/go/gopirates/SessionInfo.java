package com.go.gopirates;



import java.util.ArrayList;

/**
 * Created by zhanghao on 8/4/16.
 */
public class SessionInfo {
    public String mId;
    public String mRoomId;
    public ArrayList mParticipants;
    public String mState;

    public final int ROOM_NULL=1000;
    public final int ROOM_WAIT=1001;
    public final int ROOM_PLAY=1002;

    public SessionInfo(){
        mId="";
        mRoomId="";
        mParticipants=new ArrayList();
        mState="";
    }

    public void setUpGame(){
        PirateGame.NUMBER_OF_PLAYERS=mParticipants.size();

    }

    public void endSession(){
        mId=null;
        mRoomId=null;
        mParticipants=null;
    }

}
