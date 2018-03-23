package com.basicengine.graphics.component;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class CTextView extends GUI{

	public Paint textPaint;
	public Bitmap background;
	
	public CTextView(int x, int y, String text ,Bitmap bg,Paint paint) {
		super(x, y, 0, 0, null);
		// TODO Auto-generated constructor stub
		if(paint == null) {
			textPaint = new Paint();
		}else {
			textPaint = paint;
		}
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        Bitmap bitmap;
        if(bg != null) {
        	bitmap = BitmapModifier.modify(bg,rect.height(),rect.width()).copy(Config.ARGB_8888, true);
        }else {
        	bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Config.ARGB_8888);
        }
        background = bg;
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, rect.height(), textPaint);
        Texture = bitmap;
        Width = rect.width();
        Height = rect.height();
	}
	
	public void setText(String text) {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        Bitmap bitmap;
        if(background != null) {
        	bitmap = BitmapModifier.modify(background,rect.height(),rect.width()).copy(Config.ARGB_8888, true);
        }else {
        	bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, rect.height(), textPaint);
        Texture = bitmap;
        Width = rect.width();
        Height = rect.height();
	}
	
	/*@Override
	public void draw(Canvas c) {
		// TODO Auto-generated method stub
		super.draw(c);
		c.drawBitmap(Texture, X, Y,null);
	}*/

}
