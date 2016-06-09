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
import android.widget.CheckBox;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class GameOptions extends Activity {

	private Context context;

	Button backButton;
	Button saveButton;
	CheckBox gameSoundsCheckBox;
	CheckBox gameMusicCheckBox;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_options);
		context = getApplicationContext();

		gameSoundsCheckBox = (CheckBox) findViewById(R.id.gameSoundsCheckBox);
		gameMusicCheckBox = (CheckBox) findViewById(R.id.gameMusicCheckBox);


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
				savePreferences("Sound_CheckBox_Value", gameSoundsCheckBox.isChecked());
				savePreferences("Music_CheckBox_Value", gameMusicCheckBox.isChecked());

				finish();
			}
		});

		loadSavedPreferences();

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

		boolean soundCheckBoxValue = sharedPreferences.getBoolean("Sound_CheckBox_Value", false);
		boolean musicCheckBoxValue = sharedPreferences.getBoolean("Music_CheckBox_Value", false);

		if (soundCheckBoxValue) { gameSoundsCheckBox.setChecked(true); }
		else { gameSoundsCheckBox.setChecked(false); }

		if (musicCheckBoxValue) { gameMusicCheckBox.setChecked(true); }
		else { gameMusicCheckBox.setChecked(false); }

	}

	// save checkbox boolean value
	private void savePreferences(String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

}
