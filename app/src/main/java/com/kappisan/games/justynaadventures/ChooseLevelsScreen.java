package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseLevelsScreen extends Activity {

	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_levels_screen);
		context = getApplicationContext();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // set to portrait

		Button backButton = (Button) findViewById(R.id.backButtonLevels);

		ImageButton imageWatchButton = (ImageButton) findViewById(R.id.imageWatchButton);
		ImageButton appleButton = (ImageButton) findViewById(R.id.appleButton);
		ImageButton bookButton = (ImageButton) findViewById(R.id.imageBookButton);
		ImageButton presentButton = (ImageButton) findViewById(R.id.presentButton);

		presentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.kappisan.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",0);
				i.putExtra("falling_set",0);
				i.putExtra("background_set",1);
				
				startActivity(i);
			}
		});

		appleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.kappisan.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",1);
				i.putExtra("falling_set",1);
				i.putExtra("background_set",2);
				
				startActivity(i);
			}
		});

		imageWatchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.kappisan.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",2);
				i.putExtra("falling_set",2);
				i.putExtra("background_set",4);
				
				startActivity(i);
			}
		});

		bookButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.kappisan.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",3);
				i.putExtra("falling_set",3);
				i.putExtra("background_set",3);
				
				startActivity(i);
			}
		});

		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { finish();	}
		});

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
			startActivity(new Intent("com.kappisan.games.justynaadventures.ABOUT"));
			return true;
		}
		
		return false;
	}
	
}
