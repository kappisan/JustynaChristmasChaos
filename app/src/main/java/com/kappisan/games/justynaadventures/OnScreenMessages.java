package com.kappisan.games.justynaadventures;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class OnScreenMessages extends Activity {

	Bitmap scoreTimes2, scoreTimes3, scoreTimes5, gameOver;
	Bitmap heartBar, emptyHeartBar;
	Bitmap numbersRed, numbersGreen;
	int screenWidth;
	int screenHeight;
	int scaleBy;
	boolean showMessage;
	int showMessageTime;
	
	public OnScreenMessages(int x, int y, Context context) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		showMessage = false;
		showMessageTime = 0;

		
		// get bitmaps
		//**************************************************************************			
		heartBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.health_bar_heart);
		emptyHeartBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.health_bar_heart_empty);
		numbersRed = BitmapFactory.decodeResource(context.getResources(), R.drawable.numbers_red);
		numbersGreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.numbers_green);
		
		scoreTimes2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_2_bonus);
		scoreTimes3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_3_bonus);
		scoreTimes5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.times_5_bonus);
		gameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_over);
		
		// resize bitmaps
		//**************************************************************************
		heartBar = getResizedBitmap(heartBar,75,75);	
		emptyHeartBar = getResizedBitmap(emptyHeartBar,75,75);
		
		numbersRed = getResizedBitmap(numbersRed,54,400);
		numbersGreen = getResizedBitmap(numbersGreen,54,400);	
	}
	
	public void displayMessage() {
		showMessage = true;
		showMessageTime = 100;
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
	
	
	public void drawScore(Canvas canvas, int score) {
		
		int sf = String.valueOf(score).length();
		
		int shiftBy = 0;
		int srcX;
		int drawFromX = screenWidth - 80;

		if(sf == 1) {
			srcX = score * 40;	
			
			Rect src = new Rect(srcX,0,srcX+40,54);
			Rect dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
		} else if(sf == 2) {
			
			// tens
			srcX = (score / 10) * 40;
			
			shiftBy = -50;
			Rect src = new Rect(srcX,0,srcX+40,54);
			Rect dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// ones
			srcX = (score % 10) * 40;
			
			shiftBy = 0;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
		} else if(sf == 3) {
			// hundreds
			srcX = (score / 100) * 40;
			
			shiftBy = -100;
			Rect src = new Rect(srcX,0,srcX+40,54);
			Rect dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// tens
			srcX /= 40;
			srcX = score - (srcX * 100);
			srcX = (srcX / 10) * 40;
			
			shiftBy = -50;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// ones
			srcX = (score % 10) * 40;
			
			shiftBy = 0;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
		} else {
			// thousands
			srcX = (score / 1000) * 40;
			
			shiftBy = -150;
			Rect src = new Rect(srcX,0,srcX+40,54);
			Rect dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// hundreds
			srcX /= 40;
			srcX = score - (srcX * 1000);
			srcX = (srcX / 100) * 40;
			
			shiftBy = -100;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// tens
			srcX /= 40;
			srcX = (score % 1000) - (srcX * 100);
			srcX = (srcX / 10) * 40;
			
			shiftBy = -50;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
			
			// ones
			srcX = (score % 10) * 40;
			
			shiftBy = 0;
			src = new Rect(srcX,0,srcX+40,54);
			dst = new Rect(drawFromX+shiftBy,50,drawFromX+40+shiftBy,104);
			canvas.drawBitmap(numbersRed, src, dst, null);
		}
	}
	
	public void drawGameOver(Canvas canvas) {
		canvas.drawBitmap(gameOver,(screenWidth / 2) - (gameOver.getWidth() / 2) , (screenHeight / 2) - (gameOver.getHeight() / 2), null);
	}
	
	public void showMessage(Canvas canvas, int showBonus) {
		if(showMessage) {
			if(showBonus == 2) {
				canvas.drawBitmap(scoreTimes2,(screenWidth / 2) - (scoreTimes2.getWidth() / 2) , (screenHeight / 4) - (scoreTimes2.getHeight() / 2), null);
			} else if(showBonus == 3) {
				canvas.drawBitmap(scoreTimes3,(screenWidth / 2) - (scoreTimes3.getWidth() / 2) , (screenHeight / 4) - (scoreTimes3.getHeight() / 2), null);
			} else if(showBonus == 5) {
				canvas.drawBitmap(scoreTimes5,(screenWidth / 2) - (scoreTimes5.getWidth() / 2) , (screenHeight / 4) - (scoreTimes5.getHeight() / 2), null);
			}
			showMessageTime--;
			if(showMessageTime < 0) {
				showMessage = false;
			}
		}
	}
	
	public void drawHealth(Canvas canvas, int health) {
		// first heart
		if(health > 0) {
			canvas.drawBitmap(heartBar, 10, 30, null);
		} else {
			canvas.drawBitmap(emptyHeartBar, 10, 30, null);
		}
		
		// second heart
		if(health > 1) {
			canvas.drawBitmap(heartBar, 90, 30, null);
		} else {
			canvas.drawBitmap(emptyHeartBar, 90, 30, null);
		}
		
		// third heart
		if(health > 2) {
			canvas.drawBitmap(heartBar, 170, 30, null);
		} else {
			canvas.drawBitmap(emptyHeartBar, 170, 30, null);
		}
	}

}
