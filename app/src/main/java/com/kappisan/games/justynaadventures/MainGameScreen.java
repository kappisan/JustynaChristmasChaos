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
public class MainGameScreen extends Activity implements OnTouchListener {

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
	FallingObjectBad[] falling;
	JustynaObject tina;

	boolean isGameOver;
	int showBonus; // used to determine which bonus to show
	int scoreMultiplierBonus;
	int totalFallenBoxes;
	
	float characterX, characterY;
	int moveSpeed = 20;
	boolean isMoving;
	int xClickPosition, yClickPosition;
	int score, health;
	int characterPosition;
	
	int catchStreak;
	
	boolean caughtBox;
	boolean caughtGood;
	boolean drawBox2, drawBox3, drawBox4, drawBox5;
	
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
		
		
		int justynaSet = 0;
		int backgroundSet = 0;
		int fallingSet = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			justynaSet = extras.getInt("justyna");
			backgroundSet = extras.getInt("background_set");
			fallingSet = extras.getInt("falling_set");
		}
		
		tina = new JustynaObject(context, justynaSet);
		
		bg = new BackGround(screenWidth, screenHeight, context, backgroundSet);
		
		falling = new FallingObjectBad[5];
		falling[0] = new FallingObjectBad(screenWidth, screenHeight, context, 20, 0, fallingSet);
		falling[1] = new FallingObjectBad(screenWidth, screenHeight, context, 10, 1, fallingSet);
		falling[2] = new FallingObjectBad(screenWidth, screenHeight, context, 12, 1, fallingSet);
		falling[3] = new FallingObjectBad(screenWidth, screenHeight, context, 14, 1, fallingSet);
		falling[4] = new FallingObjectBad(screenWidth, screenHeight, context, 16, 1, fallingSet);
		
		isGameOver = false;
		
		showBonus = 0;
		totalFallenBoxes = 0;
		characterX = screenWidth / 2;
		characterY = screenHeight - (screenHeight / 6);
		characterPosition = 0;
		
		xClickPosition = 0;
		yClickPosition = 0;
		catchStreak = 0;
		
		scoreMultiplierBonus = 1;
		
		isMoving = false;
		caughtBox = false;
		caughtGood = true;
		drawBox2 = false;
		drawBox3 = false;
		drawBox4 = false;
		drawBox5 = false;
		
		score = 0;
		health = 3;
		
		goodCatch = MediaPlayer.create(MainGameScreen.this, R.raw.catch_good);
		badCatch = MediaPlayer.create(MainGameScreen.this, R.raw.catch_bad);
		scoreMultiplier = MediaPlayer.create(MainGameScreen.this, R.raw.score_multiplier);
		presentFall = MediaPlayer.create(MainGameScreen.this, R.raw.present_fall);
		
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

			for(int i = 0; i < 5; i++) {
				if(falling[i].getY() > (screenHeight + 245)) { 
					if(i == 0) { totalFallenBoxes++; }
					if(falling[i].getCaughtBox()) {
						if(falling[i].checkIfGoodCatch()) {
							score++;
							goodCatch.start();
						} else {
							health--;
							badCatch.start();
						}
					}
					falling[i].resetBox(); 
				}
			}

			// draw more boxes with time
			if(totalFallenBoxes > 10) { drawBox2 = true; }
			if(totalFallenBoxes > 20) { drawBox3 = true; }
			if(totalFallenBoxes > 40) { drawBox4 = true; }
			if(totalFallenBoxes > 80) { drawBox5 = true; }
			
			if(health < 1) { isGameOver = true; }
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			
			bg.drawBackground(canvas);
			bg.drawClouds(canvas);
			drawCharacter(canvas);
			if(!isGameOver) {
				falling[0].drawBox(canvas, (int) characterX, (int) characterY);
				
				if(drawBox2) {
					falling[1].drawBox(canvas, (int) characterX, (int) characterY);
				}
				
				if(drawBox3) {
					falling[2].drawBox(canvas, (int) characterX, (int) characterY);
				}
				if(drawBox4) {
					falling[3].drawBox(canvas, (int) characterX, (int) characterY);
				}
				if(drawBox5) {
					falling[4].drawBox(canvas, (int) characterX, (int) characterY);
				}
				
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
