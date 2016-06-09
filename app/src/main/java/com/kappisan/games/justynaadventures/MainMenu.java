package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.pm.ActivityInfo;

public class MainMenu extends Activity {

	static MediaPlayer bgMusic;
	private boolean soundCheckBoxValue;
	private boolean musicCheckBoxValue;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // set to portrait

		loadSavedPreferences();

		bgMusic = MediaPlayer.create(MainMenu.this, R.raw.justyna_christmas_chaos_main);
		bgMusic.setLooping(true);

		bgMusic.start();

		if(!musicCheckBoxValue) {
			bgMusic.pause();
		}

		Button buttonLevels = (Button) findViewById(R.id.chooseLevelButton);
		Button buttonNew = (Button) findViewById(R.id.newGameButton);
		Button buttonOptions = (Button) findViewById(R.id.gameOptionsButton);
		Button buttonAbout = (Button) findViewById(R.id.aboutButton);

		ImageButton playPresentButton = (ImageButton) findViewById(R.id.playPresentButton);
		ImageButton levelsPresentButton = (ImageButton) findViewById(R.id.levelsPresentButton);
		ImageButton optionsPresentButton = (ImageButton) findViewById(R.id.optionsPresentButton);
		ImageButton aboutPresentButton = (ImageButton) findViewById(R.id.aboutPresentButton);
		
		buttonLevels.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.CHOOSELEVELSCREEN"));
			}
		});
		
		buttonNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN"));
			}
		});
		
		buttonOptions.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.GAMEOPTIONS"));
			}
		});

		buttonAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.ABOUT"));
			}
		});












		levelsPresentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {	startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.CHOOSELEVELSCREEN"));
			}
		});

		playPresentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN"));
			}
		});

		optionsPresentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {	startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.GAMEOPTIONS"));
			}
		});

		aboutPresentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.ABOUT"));
			}
		});

	}

	public static void startMusic() {

		bgMusic.start();
	}
	
	public static void stopMusic() {
		bgMusic.pause();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//bgMusic.stop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//bgMusic.start();
	}

	@Override
	protected void onDestroy() {
		bgMusic.stop();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		
		case R.id.menuShowAbout:
			startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.ABOUT"));
			return true;
		}
		
		return false;
	}


	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		soundCheckBoxValue = sharedPreferences.getBoolean("Sound_CheckBox_Value", false);
		musicCheckBoxValue = sharedPreferences.getBoolean("Music_CheckBox_Value", false);

	}


}
