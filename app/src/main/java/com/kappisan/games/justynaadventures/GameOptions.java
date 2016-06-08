package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class GameOptions extends Activity {

	private Context context;

	Button backButton;
	Button saveButton;

	public static String filename = "MyShared";
	//SharedPreferences someData;

	//Random r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_options);
		context = getApplicationContext();

		//final SharedPreferences someData = getSharedPreferences(filename, 0);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // set to portrait

		backButton = (Button) findViewById(R.id.optionsBackButton);
		saveButton = (Button) findViewById(R.id.optionsSavebutton);

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			//String stringData = "variable " + r.nextInt(10);
			//SharedPreferences.Editor editor = someData.edit();
			//editor.putString("sharedString", stringData);



			@Override
			public void onClick(View v) {
				finish();
			}
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
			startActivity(new Intent("com.soidutsrepsak.games.justynaadventures.ABOUT"));
			return true;
		}
		
		return false;
	}
	
}
