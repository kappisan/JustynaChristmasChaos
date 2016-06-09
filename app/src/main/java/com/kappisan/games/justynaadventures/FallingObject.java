// Object that fall, can be presents or harmful

package com.kappisan.games.justynaadventures;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class FallingObject extends Activity {

	// BOX CLASSES: 0 = mix, 1 = bad, 2 = good
	
	int screenWidth;
	int screenHeight;
	float scaleBy;
	Bitmap box1, box2, box3, box4, badBox1, badBox2, badBox3;
	
	int boxX, boxY;
	float caughtBoxY;
	int fallSpeed;
	Random r;

	int drawPresent;
	boolean caughtBox;
	boolean goodBox;
	private boolean inPlay;
	int boxSet;
	
	public FallingObject(int x, int y, Context context, int speed, int boxClass, int set) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		boxSet = boxClass;
		inPlay = false;

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
		} else {
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
		box1 = getResizedBitmap(box1,200,200);
		box2 = getResizedBitmap(box2,200,200);
		box3 = getResizedBitmap(box3,200,200);
		box4 = getResizedBitmap(box4,200,200);
		badBox1 = getResizedBitmap(badBox1,200,200);
		badBox2 = getResizedBitmap(badBox2,200,200);
		badBox3 = getResizedBitmap(badBox3,200,200);
		
		r = new Random();
		
		fallSpeed = speed;
		
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}

		goodBox = true;
		setBoxGoodness();
		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -300;
		this.caughtBox = false;
	}
	
	public void setBoxGoodness() {
		if(drawPresent == 0 || drawPresent == 1 || drawPresent == 2 || drawPresent == 3) {
			goodBox = true;
		} else {
			goodBox = false;
		}
	}

	public void setInPlay() {
		this.inPlay = true;
	}

	public void setNotInPlay() {
		this.inPlay = false;
	}

	// check if this falling object is in play
	public boolean isInPlay() {
		return this.inPlay;
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

		boxY += fallSpeed;
		
		int drawX;
		int drawY;

		if(this.caughtBox == false) {
			if(checkIfCaught(characterX, characterY)) {
				this.caughtBox = true;
			}
		}
		
		if(this.caughtBox) {
			drawX = characterX - (box1.getWidth() / 2);
			drawY = (int) this.caughtBoxY;
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
		this.caughtBox = false;
		
		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -30 - r.nextInt(100);
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}
		setBoxGoodness();
	}
	
	public boolean getCaughtBox() {	return this.caughtBox;	}
	
	public boolean checkIfCaught(float characterX, float characterY) {
		//if(this.caughtBox == false && Math.abs(boxY - (screenHeight - 50)) < 20 && Math.abs(boxX - characterX) < 80) {
		if(this.caughtBox == false && (Math.abs(characterY - boxY) < 10) && Math.abs(boxX - characterX) < 80 && boxY < screenHeight) {
			this.caughtBox = true;
			this.caughtBoxY = characterY;
			return true;
		}
		return false;
	}
	
	public boolean checkIfGoodBox() {
		return this.goodBox;
	}
	
	public int getY() {	return this.boxY; }
}
