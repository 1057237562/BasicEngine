package com.basicengine.input;
import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.GUI;
import com.basicengine.graphics.GameFrame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;

public class TextField extends GUI
{
	public StringBuilder content = new StringBuilder();
	public int cursor;
	public GameFrame gf;
	public Paint mp;
	public int startX = 10;
	public int startY = 0;
	public boolean center = true;
	boolean upper = false;
	
	public TextField(int x,int y,int h,int w,Bitmap t,GameFrame p){
		super(x,y,h,w,BitmapModifier.modify(t,h,w));
		gf = p;
		mp = new Paint();
		mp.setTextSize(20);
	}

	@Override
	public void onClick(float X, float Y)
	{
		// TODO: Implement this method
		Rect rect = new Rect();
		mp.getTextBounds(content.toString(),0,content.length(), rect);
		float width = rect.width();
		cursor = (int) ((X - this.X - startX) / width * content.length());
		if(cursor > content.length()){
			cursor = content.length();
		}
		
		InputMethodManager imm = (InputMethodManager) gf.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			gf.setFocusable(true);
			gf.setFocusableInTouchMode(true);
			gf.findFocus();
			imm.showSoftInput(gf, InputMethodManager.SHOW_FORCED);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
		}
		gf.setOnKeyListener(new OnKeyListener(){

				@Override
				public boolean onKey(View p1, int p2, KeyEvent p3)
				{
					// TODO: Implement this method
					if(p2 == KeyEvent.KEYCODE_SHIFT_LEFT){
						if(cursor == 0 && !upper){
							upper = true;
						}else{
							content.replace(cursor-1,cursor,content.substring(cursor-1,cursor).toUpperCase());
							upper = false;
						}
						return false;
					}
					if(p3.getAction() == KeyEvent.ACTION_UP){
						switch(p2){
							case KeyEvent.KEYCODE_SPACE:
								content.insert(cursor," ");
							break;
							case KeyEvent.KEYCODE_COMMA:
								content.insert(cursor,",");
							break;
							case KeyEvent.KEYCODE_PERIOD:
								content.insert(cursor,".");
							break;
							case KeyEvent.KEYCODE_SEMICOLON:
								content.insert(cursor,":");
							break;
							case KeyEvent.KEYCODE_MINUS:
								content.insert(cursor,"_");
							break;
							case KeyEvent.KEYCODE_DEL:
								try{
									content.delete(cursor-1,cursor);
									cursor--;
									if(cursor<0){
										cursor=0;
									}
								}catch(Exception e){}
							return false;
							case KeyEvent.KEYCODE_FORWARD_DEL:
								try{
								    content.delete(cursor,cursor+1);
								}catch(Exception e){}
							return false;
							
							default:
							content.insert(cursor,KeyEvent.keyCodeToString(p2).replace("KEYCODE_","").toLowerCase());
							break;
						}
						cursor++;
					}
					return false;
				}
			});
	}

	@Override
	public void draw(Canvas c)
	{
		// TODO: Implement this method
		if(center){
			Rect rect = new Rect();
			mp.getTextBounds(content.toString(),0,content.length(), rect);
			int height= rect.height();
			startY = (this.Height - height)/2;
		}
		c.drawText(content.toString(),startX,startY,mp);
	}
	
	public void setTextSize(float s){
		mp.setTextSize(s);
		Rect rect = new Rect();
		mp.getTextBounds(content.toString(),0,content.length(), rect);
		int height= rect.height();
		startY = (this.Height - height)/2;
	}
}
