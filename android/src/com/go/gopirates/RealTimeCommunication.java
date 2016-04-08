//package com.go.gopirates;
//
//import android.util.Log;
//
//import com.go.gopirates.helpers.MultiplayerSessionInfo;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
//
//import java.util.Arrays;
//
///**
// * Created by zhanghao on 7/4/16.
// */
//public class RealTimeCommunication implements RealTimeMessageReceivedListener {
//    private String TAG = "MurderMansion RealTime Communications";
//    private GoogleApiClient mGoogleApiClient;
//    private MultiplayerSessionInfo info;
//
//
//    public RealTimeCommunication(GoogleApiClient api, MultiplayerSessionInfo info){
////    	this.activity=activity;
//        this.mGoogleApiClient=api;
//        this.info=info;
//    }
//
//    // Called when we receive a real-time message from the network.
//    // Messages in our game are made up of 2 bytes: the first one is 'A' or 'P'
//    // indicating
//    // whether it's a serverAdress or serverPort. The rest of the byte array is the message.
//    // There is also the
//    // 'S' message, which indicates that the game should start.
//
//    @Override
//    public void onRealTimeMessageReceived(RealTimeMessage rtm) {
//        byte[] buf = rtm.getMessageData();
//        String sender = rtm.getSenderParticipantId();
//        String Msg;
//
//        try{
//            String messageType = new String (Arrays.copyOfRange(buf, 0, 1),"UTF-8");
//            Log.d(TAG, "MessageType: " + messageType);
//
//            if (messageType.equals("A")){
//                //Create a InetSocketAddress
//                byte[] addr = Arrays.copyOfRange(buf, 1, buf.length);
//                Msg = new String (addr,"UTF-8");
//                Log.d(TAG,"Address received: "+Msg);
//                info.serverAddress=Msg;
//
//            }else if (messageType.equals("P")){
//                //Retrieve and store port number
//                byte[] port = Arrays.copyOfRange(buf, 1, buf.length);
//                Msg = new String (port,"UTF-8");
//                Log.d(TAG,"Port Number received: "+Msg);
//                info.serverPort=Integer.parseInt(Msg);
//
//            }else{
//                Log.d(TAG, "Message type is not recognised.");
//            }
//
//        }catch (Exception e){
//            Log.d(TAG, "Error reading from received message: "+e.getMessage());
//        }
//    }
//
//    // Broadcast serverLocalAddress to all connected clients
////    void broadcastAddress(MMServer server) {
////        if (!info.isServer)
////            return; // Player is not server
////
////        String Msg ="A";
//////        Msg += server.getServerAddress();
////        byte[] mMsgBuf = new byte[Msg.length()];
////
//////        // Encode String Message in UTF_8 byte format for transmission
////        mMsgBuf =Msg.getBytes(Charset.forName("UTF-8"));
////
//////        // Send to every participant.
////        for (Object o : info.mParticipants) {
////            Participant p = (Participant) o;
////            if (p.getStatus() != Participant.STATUS_JOINED)
////                continue;
////            if(p.getParticipantId().equals(info.mId))
////                continue;
////            Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, mMsgBuf,
////                    info.mRoomId, p.getParticipantId());
////        }
////    }
//
//    // Broadcast port Number to all connected clients
////    void broadcastPort(MMServer server) {
////        if (!info.isServer)
////            return; // Player is not server
////        String Msg ="P";
////        Msg += String.valueOf(server.getServerPort());
////        byte[] mMsgBuf = new byte[Msg.length()];
////
////        // Encode String Message in UTF_8 byte format for transmission
////        mMsgBuf =Msg.getBytes(Charset.forName("UTF-8"));
////
////        // Send to every other participant.
////        for (Object o : info.mParticipants) {
////            Participant p = (Participant) o;
////            if (p.getStatus() != Participant.STATUS_JOINED)
////                continue;
////            if(p.getParticipantId().equals(info.mId))
////                continue;
////            Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, mMsgBuf,
////                    info.mRoomId, p.getParticipantId());
////        }
////    }
//}