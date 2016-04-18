package com.go.gopirates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AndroidLauncher extends AndroidApplication implements PlayServices,
		RoomUpdateListener, RoomStatusUpdateListener, RealTimeMessageReceivedListener {
	// For room creation:
	// Does not include self in player count
	final static int MIN_PLAYERS = 1;
	final static int MAX_PLAYERS = 3;
	final static long MASK = 0;
	// Waiting room UI
	final static int RC_WAITING_ROOM = 10002;
	private final static int requestCode = 1;
	Activity activity;
	SessionInfo sessionInfo;
	GoogleApiClient mGoogleApiClient;
	// Is a game ongoing?
	boolean mPlaying = false;
	private GameHelper gameHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);
		sessionInfo=new SessionInfo();
		GameHelper.GameHelperListener gameHelperListener = new
				GameHelper.GameHelperListener() {

					@Override
					public void onSignInFailed() {
					}

					@Override
					public void onSignInSucceeded() {

					}
				};
		gameHelper.setup(gameHelperListener);
		mGoogleApiClient=gameHelper.getApiClient();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		super.onCreate(savedInstanceState);
		initialize(new PirateGame(this,sessionInfo), config);

	}

	// Starting a quick game with available people
	public void startQuickGame() {
		// Auto-match criteria [Min Players, Max Players, Exclusive Mask]
		Bundle am = RoomConfig.createAutoMatchCriteria(MIN_PLAYERS, MAX_PLAYERS, MASK);

		// Build the room config
		RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
		roomConfigBuilder.setAutoMatchCriteria(am);
		RoomConfig roomConfig = roomConfigBuilder.build();

		// Create room
		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), roomConfig);

		// Prevent screen from sleeping during handshake
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
					broadcastMessage("test");
				}

			});
			Log.d("Game", " Log in successful");
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
			Log.d("Game", " Log in failed");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
			Log.d("Game", " Log out successful");
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
			Log.d("Game", "Log out failed");
		}
	}

	public void broadcastMessage(final String message) {

		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					byte[] MsgBuf = message.getBytes(Charset.forName("UTF-8"));

					for (Object o : sessionInfo.mParticipants) {
						Participant p = (Participant) o;
						if (p.getParticipantId().equals(sessionInfo.mId)) {
							continue;
						}
						try {
							Games.RealTimeMultiplayer.sendUnreliableMessage(gameHelper.getApiClient(), MsgBuf, sessionInfo.mRoomId, p.getParticipantId());
						} catch (Exception e) {

						}
						continue;
					}
				}

			});

		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Quick game failed: " + e.getMessage());
		}
	}

	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		byte buf[] = rtm.getMessageData();
		try {
			final String t = new String(buf);
			PirateGame.resloveMessage(t);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Other important methods
	 */

	// RoomConfigBuilder
	private RoomConfig.Builder makeBasicRoomConfigBuilder() {
		return RoomConfig.builder(this)
				.setMessageReceivedListener(this)
				.setRoomStatusUpdateListener(this);
	}

	// Game start factors check
	private boolean shouldStartGame(Room room) {
		int connectedPlayers = 0;
		for (Participant p : room.getParticipants()) {
			if (p.isConnectedToRoom()) {
				++connectedPlayers;
			}
		}
		return connectedPlayers >= MIN_PLAYERS;
	}

	// Game cancel factor check
	private boolean shouldCancelGame(Room room) {
		boolean cancelGame = false;
		// Cancellation Logic
		return cancelGame;
	}

	/**
	 * For RoomStatusUpdateListener Interface
	 */

	@Override
	public void onConnectedToRoom(Room room) {}
	@Override
	public void onDisconnectedFromRoom(Room room) {
//		try{
//			// leave the room
//			Games.RealTimeMultiplayer.leave(mGoogleApiClient, null, sessionInfo.mRoomId);
//
//			// clear the flag that keeps the screen on
//			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//			// show error message and return to main screen
//		}catch (Exception e){}

	}
	@Override
	public void onP2PConnected(String participantId) {}
	@Override
	public void onP2PDisconnected(String participantId) {

	}
	@Override
	public void onPeerDeclined(Room room, List<String> peers) {
		// peer declined invitation -- see if game should be canceled
		if (!mPlaying && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(gameHelper.getApiClient(), null, sessionInfo.mRoomId);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
	@Override
	public void	onPeerInvitedToRoom(Room room, List<String> participantIds) {}
	@Override
	public void	onPeerJoined(Room room, List<String> participantIds) {}
	@Override
	public void onPeerLeft(Room room, List<String> peers) {
		Log.i("Left", peers.toString());
		// peer left -- see if game should be canceled
		if (!mPlaying && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(gameHelper.getApiClient(), null, sessionInfo.mRoomId);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

		for (String player : sessionInfo.mParticipantsString) {
			if (!peers.contains(player)) {
				PirateGame.screen.getPirate(sessionInfo.map.get(player)).destroy();
				Log.i("Left", player + "left");
			}
		}
		updateRoom(room);
	}

	public void updateRoom(Room room) {
		sessionInfo.mParticipants = room.getParticipants();
		sessionInfo.mRoomId = room.getRoomId();
		sessionInfo.mParticipantsString = new ArrayList<>();
		for (Object o : sessionInfo.mParticipants) {
			Participant p = (Participant) o;
			sessionInfo.mParticipantsString.add(p.getParticipantId());
			Log.i("Part", p.getParticipantId());
		}
	}

	@Override
	public void	onPeersConnected(Room room, List<String> participantIds) {
		Log.i("Peers", participantIds.size() + "");
		// Checks for conditions to start game, if so - start game
		if (shouldStartGame(room)) {
			sessionInfo.mId=room.getParticipantId(Games.Players.getCurrentPlayerId(mGoogleApiClient));
			updateRoom(room);
			sessionInfo.mState="Play";
		}

	}
	@Override
	public void	onPeersDisconnected(Room room, List<String> participantIds) {
		if (mPlaying) {
			// do game-specific handling of this -- remove player's avatar
			// from the screen, etc. If not enough players are left for
			// the game to go on, end the game and leave the room.
		}
		else if (shouldCancelGame(room)){
			// cancel the game
			Games.RealTimeMultiplayer.leave(gameHelper.getApiClient(), null, sessionInfo.mRoomId);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
	@Override
	public void	onRoomAutoMatching(Room room) {}
	@Override
	public void	onRoomConnecting(Room room) {}

	/**
	 * For RoomUpdateListener interface
	 */
	@Override
	public void	onJoinedRoom(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// Sleep the screen
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			// Show error message or do nothing, return to main screen.
			return;
		}
		// Get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void	onLeftRoom(int statusCode, String roomId) {}

	@Override
	public void	onRoomConnected(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// let screen go to sleep
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			// show error message, return to main screen.
			Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();

			Log.i("Zhang Hao","room connevted");
		}
		sessionInfo.mRoomId = room.getRoomId();
		sessionInfo.mParticipants = room.getParticipants();
		sessionInfo.mId=room.getParticipantId(Games.Players.getCurrentPlayerId(mGoogleApiClient));

		Log.i("Zhang Hao",sessionInfo.mRoomId);
	}

	@Override
	public void	onRoomCreated(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// Sleep the screen
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			// Show error message or do nothing, return to main screen.
			return;
		}

		// get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);
	}
}