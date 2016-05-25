package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	MediaPlayer bgMusic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		bgMusic = MediaPlayer.create(MainMenu.this, R.raw.justyna_christmas_chaos_main);
		bgMusic.setLooping(true);
		bgMusic.start();
		
		Button buttonLevels = (Button) findViewById(R.id.chooseLevelButton);
		Button buttonNew = (Button) findViewById(R.id.newGameButton);
		Button buttonOptions = (Button) findViewById(R.id.gameOptionsButton);
		Button buttonAbout = (Button) findViewById(R.id.aboutButton);
		
		buttonLevels.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.CHOOSELEVELSCREEN"));
			}
		});
		
		buttonNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN"));
			}
		});
		
		buttonOptions.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.GAMEOPTIONS"));
			}
		});

		buttonAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.ABOUT"));
			}
		});

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
	
}
