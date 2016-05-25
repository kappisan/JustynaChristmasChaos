package com.kappisan.games.justynaadventures;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;

public class FallingObjectBad extends Activity {

	//BOX CLASSES
	//0 = mix
	//1 = bad
	//2 = good
	
	int screenWidth;
	int screenHeight;
	int scaleBy;
	Bitmap box1, box2, box3, box4, badBox1, badBox2, badBox3;
	MediaPlayer goodCatch, badCatch;
	
	int boxX, boxY;
	float caughtBoxY;
	int fallSpeed;
	Random r;
	
	int speedMin;
	int speedMax;
	
	int drawPresent;
	boolean caughtBox;
	boolean goodBox;
	int boxSet;
	boolean presentFell;
	
	public FallingObjectBad(int x, int y, Context context, int speed, int boxClass, int set) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		boxSet = boxClass;

		// presents
		if(set == 1) {
			// get bitmaps
			//**************************************************************************
			box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
			box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
			box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
			box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
			badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish_skeleton);
			badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_green);
			badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_purple);
			
		} else if(set == 2){
			box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrist_watch);
			box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrist_watch);
			box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrist_watch);
			box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wrist_watch);
			badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish_skeleton);
			badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_green);
			badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_purple);
		} else if(set == 3){
			box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.book_blue);
			box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.book_red);
			box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.book_green);
			box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.book_black);
			badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish_skeleton);
			badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_green);
			badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_purple);
		}else {
			// get bitmaps
			//**************************************************************************
			box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.present_blue_yellow);
			box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.present_lightblue_red);
			box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.present_blue_yellow);
			box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.present_green_red);
			badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish_skeleton);
			badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_green);
			badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poison_bottle_purple);
		}
		
		// resize bitmaps
		//**************************************************************************
		box1 = getResizedBitmap(box1,box1.getHeight() / 2,box1.getWidth() / 2);
		box2 = getResizedBitmap(box2,box2.getHeight() / 2,box2.getWidth() / 2);
		box3 = getResizedBitmap(box3,box3.getHeight() / 2,box3.getWidth() / 2);	
		box4 = getResizedBitmap(box4,box4.getHeight() / 2,box4.getWidth() / 2);	
		badBox1 = getResizedBitmap(badBox1,badBox1.getHeight() / 3,badBox1.getWidth() / 3);
		badBox2 = getResizedBitmap(badBox2,badBox2.getHeight() / 3,badBox2.getWidth() / 3);
		badBox3 = getResizedBitmap(badBox3,badBox3.getHeight() / 3,badBox3.getWidth() / 3);
		
		r = new Random();
		
		fallSpeed = speed;

		//goodCatch = MediaPlayer.create(this, R.raw.catch_good);
		
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}

		goodBox = true;
		setBoxGoodness();
		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -300;
		caughtBox = false;
		presentFell = false;
	}
	
	public void setBoxGoodness() {
		if(drawPresent == 0 || drawPresent == 1 || drawPresent == 2 || drawPresent == 3) {
			goodBox = true;
		} else {
			goodBox = false;
		}
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	public void drawBox(Canvas canvas, int characterX, int characterY) {

		presentFell = false;
		
		boxY += fallSpeed;
		
		int drawX;
		int drawY;

		if(!caughtBox) {
			if(checkIfCaught(characterX, characterY)) {
				caughtBox = true;
			}
		}
		
		if(caughtBox) {
			drawX = characterX - (box1.getWidth() / 2);
			drawY = (int) caughtBoxY;
		} else {
			drawX = boxX - (box1.getWidth() / 2);
			drawY = boxY - box1.getHeight();
		}
		
		if(drawPresent == 0) {
			canvas.drawBitmap(box1, drawX, drawY, null);
		} else if(drawPresent == 1) {
			canvas.drawBitmap(box2, drawX, drawY, null);
		} else if(drawPresent == 2) {
			canvas.drawBitmap(box3, drawX, drawY, null);
		} else if(drawPresent == 3) {
			canvas.drawBitmap(box4, drawX, drawY, null);
		} else if(drawPresent == 4) {
			canvas.drawBitmap(badBox1, drawX, drawY, null);
		} else if(drawPresent == 5) {
			canvas.drawBitmap(badBox2, drawX, drawY, null);
		} else if(drawPresent == 6) {
			canvas.drawBitmap(badBox3, drawX, drawY, null);
		}
	}
	
	public void resetBox() {
		caughtBox = false;
		if(!caughtBox) {
			presentFell = true;
		}
		
		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -30;
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}
		setBoxGoodness();
	}
	
	public boolean getCaughtBox() {
		return caughtBox;
	}
	
	public boolean checkIfPresentFell() {
		return presentFell;
	}
	
	public boolean checkIfCaught(float characterX, float characterY) {
		if(caughtBox == false && Math.abs(boxY - (screenHeight - 50)) < 20 && Math.abs(boxX - characterX) < 80) {
			caughtBox = true;
			caughtBoxY = characterY;
			return true;
		}
		return false;
	}
	
	public boolean checkIfGoodCatch() {
		return goodBox;
	}
	
	public int getY() {
		return boxY;
	}
}
