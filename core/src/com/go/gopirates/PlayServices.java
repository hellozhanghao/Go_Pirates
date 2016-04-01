package com.go.gopirates;

import com.badlogic.gdx.utils.Array;


/**
 * Created by zhanghao on 1/4/16.
 */
public interface PlayServices
{
    public void signIn();
    public void signOut();
    public boolean isSignedIn();

    public void goToWaitingRoom();

    //submit a score to a leaderboard
//    public void submitScore(int score);

    //Save score after a game
//    public void saveAddedScore(int score);

    //gets the scores and displays them threw googles default widget
//    public int getScores();

    //gets the score and gives access to the raw score data
//    public int getScoresData();

    //This is for testing purposes
//    public void testScreenChange();

    //Checks if game is ready to start
//    public boolean isGameReadyToStart();

    // Has player received an invitation
//    public boolean isInvitationReceived();

    //Checks if game is cancelled
//    public boolean isGameCancelled();

    // Sets game ready to start
//    public void setGameReadyToStart(boolean ready);

    //This sends players game characters coordinates
//    public void sendMovingData(float x,float y,float rotation);

    // This sends messages like shot fired, character has been hit, character has died etc.
//    public void sendMessageData(EventMessage em);

    // Get players in game
//    public Array<Pirate> getPlayerPersons();

    // Get players participant ID
//    public String getMyId();

    // Get movedata array
//    public Array<Character> getMovedata();

    // Get message array
//    public Array<EventMessage> getMessageData();

    // Leave room
//    public void leaveRoom();

    //Start auto-matching
//    public void quickGame();

    // Yes I’ve added few ads hopefully they don’t disturb gaming experience
//    public void showAds(boolean show);
//    public void showAchievements();
//    public void unlockAchievement(int i);
//    public void displayInterstitial();
//    public void displayLeaderboard();

    // Sets current game cancelled
//    public void setGameCancelled(boolean b);
//    public void setInvitationReceived(boolean b);

    // Clears invitation from gamehelper
//    public void clearInvitation();

    // Get ids of disconnected players
//    public  Array<String> getDisconnected();

    // Get ids  og players who have left
//    public Array<String> getLeft();
}