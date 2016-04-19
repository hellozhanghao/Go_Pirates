package com.go.gopirates;

/**
 * Created by zhanghao on 1/4/16.
 */
public interface PlayServices
{
    void signIn();
    void signOut();
    boolean isSignedIn();
    void startQuickGame();
    void broadcastMessage(String msg);

    void leaveRoom();
}
