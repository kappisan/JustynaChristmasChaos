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
	
	int cloud1X;
	int cloud2X;
	int cloud3X;
	
	int showBackground;

	int set;
	

	
	public BackGround(int x, int y, Context context, int set) {
		screenWidth = x;
		screenHeight = y;
		scaleBy = screenHeight/1280;
		Random r = new Random();
		showBackground = set;
		this.set = set;
		
		if(set == 1) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.christmas_background_06);
		} else if(set == 2) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.gothic_background_03);
		} else if(set == 3) {
			backdrop = BitmapFactory.decodeResource(context.getResources(), R.drawable.library_background );
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
		cloud1 = getResizedBitmap(cloud1,252,479);
		cloud2 = getResizedBitmap(cloud1,126,240);
		cloud3 = getResizedBitmap(cloud1,84,160
		);

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

		if(set == 3) return; // dont draw clouds if library

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
}
