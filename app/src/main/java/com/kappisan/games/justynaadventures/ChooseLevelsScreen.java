package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ChooseLevelsScreen extends Activity {

	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_levels_screen);
		context = getApplicationContext();
		
		Button buttonChristmas = (Button) findViewById(R.id.levelChristmas01Button);
		Button buttonShinigami = (Button) findViewById(R.id.levelShinigami01Button);
		Button buttonWonderland = (Button) findViewById(R.id.levelWonderland01Button);
		Button buttonGeek = (Button) findViewById(R.id.levelGeek01Button);
		
		buttonChristmas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",0);
				i.putExtra("falling_set",0);
				i.putExtra("background_set",1);
				
				startActivity(i);
			}
		});
		
		buttonShinigami.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",1);
				i.putExtra("falling_set",1);
				i.putExtra("background_set",2);
				
				startActivity(i);
			}
		});
		
		buttonWonderland.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",2);
				i.putExtra("falling_set",2);
				i.putExtra("background_set",4);
				
				startActivity(i);
			}
		});
		
		buttonGeek.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("com.soidutsrepsak.games.justynaadventures.MAINGAMESCREEN");
				i.putExtra("justyna",3);
				i.putExtra("falling_set",3);
				i.putExtra("background_set",2);
				
				startActivity(i);
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
