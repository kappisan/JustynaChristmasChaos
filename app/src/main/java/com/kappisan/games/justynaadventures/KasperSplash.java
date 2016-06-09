package com.kappisan.games.justynaadventures;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class KasperSplash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kasper_splash);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // set to portrait

		Thread logoTimer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
					Intent menuIntent = new Intent("com.kappisan.games.justynaadventures.MAINMENU");
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

}
