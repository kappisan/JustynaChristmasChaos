package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameSplash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_splash);
		Thread logoTimer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(500);
					Intent menuIntent = new Intent("com.soidutsrepsak.games.justynaadventures.MAINMENU");
					startActivity(menuIntent);
				} catch(InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoTimer.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
