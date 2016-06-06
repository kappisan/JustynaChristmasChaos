// Object that fall, can be presents or harmful

package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

public class FallingBonus extends Activity {

	// BOX CLASSES: 0 = mix, 1 = bad, 2 = good

	int screenWidth;
	int screenHeight;
	int scaleBy;
	Bitmap box1, box2, box3, box4, badBox1, badBox2, badBox3;
	Bitmap box1_big, box2_big, box3_big, box4_big, badBox1_big, badBox2_big, badBox3_big;
	Bitmap box1_small, box2_small, box3_small, box4_small, badBox1_small, badBox2_small, badBox3_small;

	int boxX, boxY;
	float caughtBoxY;
	int fallSpeed;
	Random r;

	Context context;

	int drawPresent;
	boolean caughtBox;
	private boolean inPlay;
	private int multiplier;
	int boxSet;
	private int size; // 0 = small, 1 = big

	public int getMultiplier() {
		if(this.drawPresent == 0 && this.caughtBox) { return 2; }
		else if(this.drawPresent == 1 && this.caughtBox) { return 2; }
		else if(this.drawPresent == 2 && this.caughtBox) { return 3; }
		else if(this.drawPresent == 3 && this.caughtBox) { return 3; }
		else if(this.drawPresent == 4 && this.caughtBox) { return 5; }
		else if(this.drawPresent == 5 && this.caughtBox) { return 5; }
		else if(this.drawPresent == 6 && this.caughtBox) { return 5; }

		return 1;
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


	public void resizeBig() {

		box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_2_bonus);
		box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_2_bonus);
		box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_3_bonus);
		box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_3_bonus);
		badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);
		badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);
		badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);

		box1 = getResizedBitmap(box1,300,300);
		box2 = getResizedBitmap(box2,300,300);
		box3 = getResizedBitmap(box3,300,300);
		box4 = getResizedBitmap(box4,300,300);
		badBox1 = getResizedBitmap(badBox1,300,300);
		badBox2 = getResizedBitmap(badBox2,300,300);
		badBox3 = getResizedBitmap(badBox3,300,300);

		this.size = 1;
	}


	public void resizeSmall() {
		box1 = getResizedBitmap(box1,box1.getHeight() / 2,box1.getWidth() / 2);
		box2 = getResizedBitmap(box2,box2.getHeight() / 2,box2.getWidth() / 2);
		box3 = getResizedBitmap(box3,box3.getHeight() / 2,box3.getWidth() / 2);
		box4 = getResizedBitmap(box4,box4.getHeight() / 2,box4.getWidth() / 2);
		badBox1 = getResizedBitmap(badBox1,badBox1.getHeight() / 3,badBox1.getWidth() / 3);
		badBox2 = getResizedBitmap(badBox2,badBox2.getHeight() / 3,badBox2.getWidth() / 3);
		badBox3 = getResizedBitmap(badBox3,badBox3.getHeight() / 3,badBox3.getWidth() / 3);

		this.size = 0;
	}

	public FallingBonus(int x, int y, Context context, int speed, int boxClass, int set) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		boxSet = boxClass;
		inPlay = false;
		multiplier = 1;
		this.context = context;

		box1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_2_bonus);
		box2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_2_bonus);
		box3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_3_bonus);
		box4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_3_bonus);
		badBox1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);
		badBox2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);
		badBox3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);


		resizeSmall();
		
		r = new Random();
		
		fallSpeed = speed;
		
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}

		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -300;
		this.caughtBox = false;
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
			drawX = (screenWidth / 2) - 150;
			drawY = 20;

			if(this.size == 0) { resizeBig(); }

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
		resizeSmall();
		
		boxX = r.nextInt(screenWidth - 50) + 25;
		boxY = -30;
		if(boxSet == 0) {
			drawPresent = r.nextInt(7);	
		} else if(boxSet == 1) {
			drawPresent = r.nextInt(4) + 4;			
		}

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
	
	public int getY() {	return this.boxY; }
}
