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

	int MAX_BOXES = 10; // the max number of falling objects
	
	int screenWidth;
	int screenHeight;
	int scaleBy;
	
	OurView v;
	BackGround bg;
	OnScreenMessages osm;
	FallingObject[] falling;
	JustynaObject tina;
	FallingBonus fallingBonus;

	boolean isGameOver;

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
	
	MediaPlayer goodCatch, badCatch, scoreMultiplier, presentFall;
	
	Random r = new Random();

	int justynaSet = 0;
	int backgroundSet = 0;
	int fallingSet = 0;



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

		osm = new OnScreenMessages(screenWidth, screenHeight, context);
		
		

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			justynaSet = extras.getInt("justyna");
			backgroundSet = extras.getInt("background_set");
			fallingSet = extras.getInt("falling_set");
		}
		
		tina = new JustynaObject(context, justynaSet);
		bg = new BackGround(screenWidth, screenHeight, context, backgroundSet);
		
		falling = new FallingObject[MAX_BOXES]; // allocate array of falling objects
		fallingBonus = new FallingBonus(screenWidth, screenHeight, context, 14, 0, fallingSet);

		for(int i = 0; i < MAX_BOXES; i++) { falling[i] = new FallingObject(screenWidth, screenHeight, context, 14 + i, 0, fallingSet); }

		falling[0].setInPlay(); // start with one falling object



		isGameOver = false;

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
				onDraw(c); // this is what is drawn on every refresh of the while loop
				holder.unlockCanvasAndPost(c);
				gameLogic();

			}
		}
		
		public void gameLogic() {

			if((totalFallenBoxes % 15) + 6 == 14) {
				fallingBonus.setInPlay();
			}

			if(fallingBonus.isInPlay()) {
				if(fallingBonus.getY() > (screenHeight + 3645)) { // the duration of the bonus
					fallingBonus.setNotInPlay();
					fallingBonus.resetBox();
				}
			} else {

			}

			for(int i = 0; i < MAX_BOXES; i++) {
				if(falling[i].isInPlay()) {
					if (falling[i].getY() > (screenHeight + 245)) { // if box falls of the edge of the screen

						if (i == 0) {
							totalFallenBoxes++;
						}
						if (falling[i].getCaughtBox()) {
							if (falling[i].checkIfGoodBox()) {
								score = score + fallingBonus.getMultiplier();
								goodCatch.start();
							} else {
								health--;
								badCatch.start();
							}
						}
						falling[i].resetBox();

					}
				}
			}

			// draw more boxes with time
			for(int i = 0; i < MAX_BOXES; i++) { if(totalFallenBoxes > 4 + ( 4 * i )) { falling[i].setInPlay(); } }
			
			if(health < 1) { isGameOver = true; }
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			
			bg.drawBackground(canvas);
			bg.drawClouds(canvas);
			drawCharacter(canvas);
			if(!isGameOver) {

				for(int i = 0; i < MAX_BOXES; i++) {
					if(falling[i].isInPlay()) { falling[i].drawBox(canvas, (int) characterX, (int) characterY); }
				}
			}
			osm.drawHealth(canvas, health);
			osm.drawScore(canvas, score, screenWidth - 80, 104); // top right

			if(isGameOver) {
				osm.drawGameOver(canvas);

			} else {

				if(fallingBonus.isInPlay()) { fallingBonus.drawBox(canvas, (int) characterX, (int) characterY); }
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

	private void restartGame() {

		for(int i = 0; i < MAX_BOXES; i++) { falling[i].setNotInPlay(); }

		falling[0].setInPlay(); // start with one falling object

		isGameOver = false;

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

		score = 0;
		health = 3;


	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {

		switch(me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isMoving = true;
			xClickPosition = (int) me.getX();
			yClickPosition = (int) me.getY();

			if(isGameOver && yClickPosition < 2*(screenHeight / 3) && yClickPosition > (screenHeight / 2) - 100) {
				restartGame();
			}

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
