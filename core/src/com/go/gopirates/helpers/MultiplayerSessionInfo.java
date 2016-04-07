package com.go.gopirates.helpers;


import java.util.ArrayList;



public class MultiplayerSessionInfo {
	
	public String mId;
	public String mIncomingInvitationId;
	public String mRoomId;
	public ArrayList mParticipants;
	public String mName;
	public int mState=1000;

	public boolean isServer;
	public String serverAddress;
	public int serverPort=0;

	
	public final int ROOM_NULL=1000;
	public final int ROOM_WAIT=1001;
	public final int ROOM_PLAY=1002;
	public final int ROOM_MENU=1003;
	
	public MultiplayerSessionInfo(){
	}


	
	public void endSession(){
		mId=null;
		mIncomingInvitationId=null;
		mRoomId=null;
		mParticipants=null;
		mState=ROOM_MENU;
		mName = null;

		isServer=false;
		serverAddress=null;
		serverPort=0;


	}


}
