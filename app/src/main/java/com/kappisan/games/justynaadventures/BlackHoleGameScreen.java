package com.kappisan.games.justynaadventures;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


@SuppressLint("NewApi")
public class BlackHoleGameScreen extends Activity implements OnTouchListener {

	// define constants
	int PLAYER_NORMAL = 0;
	int PLAYER_LEFT = 1;
	int PLAYER_RIGHT = 2;
	int PLAYER_CAUGHT = 3;
	int PLAYER_CAUGHT_BAD = 4;
	
	int screenWidth;
	int screenHeight;
	int scaleBy;
	
	OurView v;
	BackGround bg;
	OnScreenMessages osm;
	JustynaObject tina;

	boolean isGameOver;
	int showBonus; // used to determine which bonus to show
	int scoreMultiplierBonus;
	int totalFallenBoxes;
	
	float characterX, characterY;
	int moveSpeed = 20;
	boolean isMoving;
	int xClickPosition, yClickPosition;
	int blackHoleX, blackHoleY;
	int score, health;
	int characterPosition;
	
	int catchStreak;
	
	boolean caughtBox;
	boolean caughtGood;

	MediaPlayer goodCatch, badCatch, scoreMultiplier, presentFall;
	
	Random r = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new OurView(this);
		v.setOnTouchListener(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); // prevent rotation
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		screenHeight = size.y;
		scaleBy = screenHeight/1280;
		Context context = this;

		osm = new OnScreenMessages(screenWidth, screenHeight, (Activity) context);
		tina = new JustynaObject(context, 1);
		
		int justynaSet = 0;
		int backgroundSet = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			justynaSet = extras.getInt("justyna");
			backgroundSet = extras.getInt("background_set");
		}
		
		bg = new BackGround(screenWidth, screenHeight, (Activity) context, backgroundSet);
		
		isGameOver = false;
		
		showBonus = 0;
		totalFallenBoxes = 0;
		characterX = screenWidth / 2;
		characterY = screenHeight - (screenHeight / 3);
		characterPosition = 0;
		
		xClickPosition = 0;
		yClickPosition = 0;
		catchStreak = 0;
		
		blackHoleX = screenWidth;
		blackHoleY = 0;
		
		scoreMultiplierBonus = 1;
		
		isMoving = false;
		caughtBox = false;
		caughtGood = true;
		
		score = 0;
		health = 3;
		
		goodCatch = MediaPlayer.create(BlackHoleGameScreen.this, R.raw.catch_good);
		badCatch = MediaPlayer.create(BlackHoleGameScreen.this, R.raw.catch_bad);
		scoreMultiplier = MediaPlayer.create(BlackHoleGameScreen.this, R.raw.score_multiplier);
		presentFall = MediaPlayer.create(BlackHoleGameScreen.this, R.raw.present_fall);
		
		setContentView(v);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}


	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}
	
	@SuppressLint("WrongCall")
	public class OurView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;
		boolean isItOk = false;
		 
		
		public OurView(Context context) {
			super(context);
			holder = getHolder();

		}
		
		@Override
		public void run() {
			
			while(isItOk) {
				if(!holder.getSurface().isValid()) {
					continue;
				}
				
				
				Canvas c = holder.lockCanvas();
				gameLogic();
				onDraw(c); // this is what is drawn on every refresh of the while loop
				holder.unlockCanvasAndPost(c);
				
			}
		}
		
		public void gameLogic() {
			
			if(health < 1) { isGameOver = true; }
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			
			bg.drawBackground(canvas);
			bg.drawBlackHole(canvas, 100, 100);
			
			drawCharacter(canvas);
			if(!isGameOver) {

				
			}
			osm.drawScore(canvas, score);
			osm.drawHealth(canvas, health);
			osm.showMessage(canvas, showBonus);

			if(isGameOver) {
				osm.drawGameOver(canvas);
			}
		}
		
		public void drawCharacter(Canvas canvas) {
			
			// get character coordinates
			if(isMoving) {
					int changeX = moveSpeed;
					if(10 < Math.abs(xClickPosition - characterX)) {
						if(xClickPosition < characterX) {
							changeX *= -1;
							characterPosition = PLAYER_LEFT;
						} else {
							characterPosition = PLAYER_RIGHT;						
						}
					} else {
						changeX *= 0;
					}
					characterX += changeX;
					
					int changeY = moveSpeed;
					if(10 < Math.abs(yClickPosition - characterY)) {
						if(yClickPosition < characterY) {
							changeY *= -1;
							characterPosition = PLAYER_LEFT;
						} else {
							characterPosition = PLAYER_RIGHT;						
						}
					} else {
						changeY *= 0;
					}
					characterY += changeY;
			}
			
			if(caughtBox) {
				if(caughtGood) {
					tina.drawTinaCatchGood(canvas, (int) characterX, (int) characterY);
				} else {
					tina.drawTinaCatchBad(canvas, (int) characterX, (int) characterY);
				}
			} else {
				if(characterPosition == PLAYER_LEFT) {
					tina.drawTinaLeft(canvas, (int) characterX, (int) characterY);
				} else if(characterPosition == PLAYER_RIGHT) {
					tina.drawTinaRight(canvas, (int) characterX, (int) characterY);
				} else {
					tina.drawTina(canvas, (int) characterX, (int) characterY);
				}
			}
		}
		
		public void pause() {
			isItOk = false;
			while(true) {
				try {
					t.join();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}
		
		public void resume() {
			isItOk = true;
			t = new Thread(this);
			t.start();
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent me) {

		switch(me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isMoving = true;
			xClickPosition = (int) me.getX();
			yClickPosition = (int) me.getY();			
			break;
		case MotionEvent.ACTION_UP:
			isMoving = false;
			characterPosition = PLAYER_NORMAL;
			break;
			
		case MotionEvent.ACTION_MOVE:
			xClickPosition = (int) me.getX();
			yClickPosition = (int) me.getY();				
			break;
		}
		
		return true;
	}

}
