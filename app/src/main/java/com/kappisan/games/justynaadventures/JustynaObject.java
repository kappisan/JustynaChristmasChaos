package com.kappisan.games.justynaadventures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class JustynaObject extends Activity {

	Bitmap character, characterLeft, characterRight, characterCaught, characterCaughtBad;
	int drawJustyna; // used to determine which character to draw

	public JustynaObject(Context context, int justynaSet, int scaleBy) {

		drawJustyna = justynaSet;

		// gothic
		if(drawJustyna == 1) {
			// get bitmaps
			//**************************************************************************
			character = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_san_edit);
			characterLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_san_left_edit);
			characterRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_san_right_edit);
			characterCaught = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_san_catch_good);


			// resize bitmaps
			//**************************************************************************
			character = getResizedBitmap(character,500,432);
			characterLeft = getResizedBitmap(characterLeft,500,432);
			characterRight = getResizedBitmap(characterRight,500,432);
			characterCaught = getResizedBitmap(characterCaught,500,432);
			characterCaughtBad = getResizedBitmap(character,500,432);

			// wonderland
		} else if(drawJustyna == 2) {
			// get bitmaps
			//**************************************************************************
			character = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_in_wonderland);

			// resize bitmaps
			//**************************************************************************
			character = getResizedBitmap(character,500,350);
			characterLeft = getResizedBitmap(character,500,350);
			characterRight = getResizedBitmap(character,500,350);
			characterCaught = getResizedBitmap(character,500,350);
			characterCaughtBad = getResizedBitmap(character,500,350);

			// geek
		} else if(drawJustyna == 3) {
			// get bitmaps
			//**************************************************************************
			character = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_geek);

			// resize bitmaps
			//**************************************************************************
			character = getResizedBitmap(character,500,350);
			characterLeft = getResizedBitmap(character,500,350);
			characterRight = getResizedBitmap(character,500,350);
			characterCaught = getResizedBitmap(character,500,350);
			characterCaughtBad = getResizedBitmap(character,500,350);

			// christmas
		} else {
			// get bitmaps
			//**************************************************************************
			character = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_blue_jumper_christmas);
			characterLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_blue_jumper_christmas_left);
			characterRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_blue_jumper_christmas_right);
			characterCaught = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_blue_jumper_christmas_catch_good);
			characterCaughtBad = BitmapFactory.decodeResource(context.getResources(), R.drawable.justyna_blue_jumper_christmas_catch_bad);

			// resize bitmaps
			//**************************************************************************
			character = getResizedBitmap(character,500,350);
			characterLeft = getResizedBitmap(characterLeft,500,350);
			characterRight = getResizedBitmap(characterRight,500,350);
			characterCaught = getResizedBitmap(characterCaught,500,350);
			characterCaughtBad = getResizedBitmap(characterCaughtBad,500,350);
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
	
	public void drawTina(Canvas canvas, int characterX, int characterY) {
		canvas.drawBitmap(character, characterX - (character.getWidth() / 2), characterY - (character.getHeight() / 2), null);
	}
	
	public void drawTinaRight(Canvas canvas, int characterX, int characterY) {
		canvas.drawBitmap(characterRight, characterX - (character.getWidth() / 2), characterY - (character.getHeight() / 2), null);
	}

	public void drawTinaLeft(Canvas canvas, int characterX, int characterY) {
		canvas.drawBitmap(characterLeft, characterX - (character.getWidth() / 2), characterY - (character.getHeight() / 2), null);
	}
	
	public void drawTinaCatchGood(Canvas canvas, int characterX, int characterY) {
		canvas.drawBitmap(characterCaught, characterX - (character.getWidth() / 2), characterY - (character.getHeight() / 2), null);	
	}
	
	public void drawTinaCatchBad(Canvas canvas, int characterX, int characterY) {
		canvas.drawBitmap(characterCaughtBad, characterX - (character.getWidth() / 2), characterY - (character.getHeight() / 2), null);	
	}
}
