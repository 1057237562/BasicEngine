package com.basicengine.graphics.effect;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.GUI;
import com.basicengine.graphics.GameFrame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;

public class Fadein extends GUI{

	public int Alpha = 255;
	public Bitmap tx;
	public int tpr = 5;
	public int da = 5;
	
	public int t = 0;
	public int target = 0;
	private FadeListener fadeListener;
	private GameFrame mGameFrame;
	
	public static final int ACTION_FADING = 0;
	public static final int ACTION_FADED = 1;
	
	public Fadein(int x, int y, int h, int w,Bitmap t) {
		super(x, y, h, w, null);
		// TODO Auto-generated constructor stub
		tx = BitmapModifier.modify(t, h, w);
		active = true;
	}
	
	public void setAlpha(int a) {
		Alpha = a;
	}
	
	public void setTargetAlpha(int a) {
		target = a;
	}
	
	public void setRefreshRate(int t) {
		tpr = t;
	}
	
	public void setADR(int adr) { // Alpha Decrease rate
		da = adr;
	}
	
	@Override
	public void draw(Canvas c) {
		Paint p = new Paint();
		p.setAlpha(Alpha);
		c.drawBitmap(tx, 0, 0, p);
	}
	
	public void start(GameFrame gf) {
		Visible = true;
		gf.registerUpdate(a);
		mGameFrame = gf;
	}
	
	public void setDoneListener(FadeListener fl) {
		fadeListener = fl;
	}
	
	public Handler a = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			t++;
			if(t >= tpr) {
				t = 0;
				Alpha -= da;
				fadeListener.onFade(Alpha, ACTION_FADING);
				if(da > 0 ? Alpha <= target:Alpha >= target) {
					fadeListener.onFade(Alpha, ACTION_FADED);
					mGameFrame.unregisterUpdate(a);
				}
			}
		}
	};

}
