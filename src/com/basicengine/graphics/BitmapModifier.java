package com.basicengine.graphics;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class BitmapModifier
{
	public static Bitmap modify(Bitmap bm,int newHeight,int newWidth){
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
		return newbm;
	}
	
	public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap,boolean recycleold) {

        if (backBitmap == null || backBitmap.isRecycled() 
                || frontBitmap == null || frontBitmap.isRecycled()) {
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(frontBitmap, 0, 0, null);
        if(recycleold) {
        	backBitmap.recycle();
        	frontBitmap.recycle();
        }
        return bitmap;
    }
	
	public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled() 
                || frontBitmap == null || frontBitmap.isRecycled()) {
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(frontBitmap, 0, 0, null);
        backBitmap.recycle();
        frontBitmap.recycle();
        return bitmap;
    }

	public static Bitmap rotateBitmap(Bitmap tBitmap, int degree) {
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		try {
			return Bitmap.createBitmap(tBitmap,0,0,tBitmap.getWidth(),tBitmap.getHeight(),matrix,true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static Bitmap flipBitmap(Bitmap tBitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(1, -1);
		try {
			return Bitmap.createBitmap(tBitmap, 0, 0, tBitmap.getWidth(), tBitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
