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
import android.widget.TextView;
import android.widget.Toast;


public class GameOptions extends Activity {

	private Context context;

	Button backButton;
	Button saveButton;
	Button resetHighScoreButton;
	CheckBox gameSoundsCheckBox;
	CheckBox gameMusicCheckBox;
	TextView highScoreTextView;
	boolean musicPlaying;
	int highScore;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_options);
		context = getApplicationContext();

		gameSoundsCheckBox = (CheckBox) findViewById(R.id.gameSoundsCheckBox);
		gameMusicCheckBox = (CheckBox) findViewById(R.id.gameMusicCheckBox);
		highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);


		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // set to portrait

		backButton = (Button) findViewById(R.id.optionsBackButton);
		saveButton = (Button) findViewById(R.id.optionsSavebutton);
		resetHighScoreButton = (Button) findViewById(R.id.resetHighScoreButton);

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				savePreferences("Sound_CheckBox_Value", gameSoundsCheckBox.isChecked());
				savePreferences("Music_CheckBox_Value", gameMusicCheckBox.isChecked());

				if(!gameMusicCheckBox.isChecked()) { /* stop music */ MainMenu.stopMusic(); }
				if(gameMusicCheckBox.isChecked()) { /* start music */ MainMenu.startMusic(); }

				Toast.makeText(getApplicationContext(),"Preferences Saved", Toast.LENGTH_SHORT).show();

				finish();
			}
		});

		loadSavedPreferences();

		highScoreTextView.setText("" + highScore);

		resetHighScoreButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				savePreferences("High_Score_Value", 0);

				highScoreTextView.setText("0");

				Toast.makeText(getApplicationContext(), "high score cleared", Toast.LENGTH_SHORT).show();
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
			startActivity(new Intent("com.kappisan.games.justynaadventures.ABOUT"));
			return true;
		}
		
		return false;
	}




	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		boolean soundCheckBoxValue = sharedPreferences.getBoolean("Sound_CheckBox_Value", false);
		boolean musicCheckBoxValue = sharedPreferences.getBoolean("Music_CheckBox_Value", false);
		highScore = sharedPreferences.getInt("High_Score_Value", 0);

		musicPlaying = soundCheckBoxValue;

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

	private void savePreferences(String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

}
