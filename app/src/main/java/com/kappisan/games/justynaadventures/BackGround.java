package com.kappisan.games.justynaadventures;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class BackGround extends Activity {

	Bitmap cloud1, cloud2, cloud3;
	Bitmap backdrop;
	int screenWidth;
	int screenHeight;
	int scaleBy;
	
	int cloud1X, cloud1Y;
	int cloud2X, cloud2Y;
	int cloud3X, cloud3Y;
	
	int showBackground;
	

	
	public BackGround(int x, int y, Context context, int set) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		Random r = new Random();
		showBackground = set;
		
		if(set == 1) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.christmas_background_06);
		} else if(set == 2) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.gothic_background_03);
		} else if(set == 3) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.gothic_background_01);
		} else if(set == 4) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.wonder_land_03);
		} else {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.christmas_background_01);	
		}
		
		backdrop = getResizedBitmap(backdrop,screenHeight ,screenWidth);
		
		// get bitmaps
		//**************************************************************************		
		cloud1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
		
		// resize bitmaps
		//**************************************************************************
		cloud2 = getResizedBitmap(cloud1,cloud1.getHeight() / 2 ,cloud1.getWidth() / 2);
		cloud3 = getResizedBitmap(cloud1,cloud1.getHeight() / 3 ,cloud1.getWidth() / 3);

		cloud1X = -100;
		cloud2X = screenWidth + 100;
		cloud3X = -20;
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
	
	public void drawBackground(Canvas canvas) {
		canvas.drawBitmap(backdrop, 0, 0, null);
	}

	public void drawClouds(Canvas canvas) {
		cloud1X += 3;
		cloud2X -= 2;
		cloud3X++;
		
		if(cloud1X > (screenWidth + 30)) {
			cloud1X = -700;
		}
		
		if(cloud2X < -500) {
			cloud2X = screenWidth + 50;
		}
		
		if(cloud3X > (screenWidth + 50)) {
			cloud3X = -200;
		}
		
		canvas.drawBitmap(cloud3, cloud3X, 30, null);
		canvas.drawBitmap(cloud2, cloud2X, 20, null);
		canvas.drawBitmap(cloud1, cloud1X, 30, null);
	}
	
	public void drawBlackHole(Canvas canvas, int x, int y) {
		canvas.drawBitmap(cloud3, x - cloud3.getWidth(), y - cloud3.getHeight(), null);
	}
}
