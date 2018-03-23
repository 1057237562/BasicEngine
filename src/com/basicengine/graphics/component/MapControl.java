package com.basicengine.graphics.component;

import com.basicengine.graphics.Component;
import com.basicengine.graphics.GUI;
import com.basicengine.graphics.GameFrame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MapControl extends GUI {

	public Point startPoint = null;
	public boolean allowOutofEdge = true;
	public GameFrame gFrame = null;
	public Bitmap mBitmap = null;

	public int mX = 0;
	public int mY = 0;
	public String mapID = "";
	public float ratio = 1f;

	public MapControl(int x, int y, int h, int w, Bitmap t, GameFrame gf) {
		super(x, y, h, w, null);
		mBitmap = t;
		gFrame = gf;
		// TODO Auto-generated constructor stub
	}

	public void setAllowOutofEdge(boolean allowOutofEdge) {
		this.allowOutofEdge = allowOutofEdge;
	}
	
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(mBitmap, new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), new Rect(mX,mY,mX + (int)(mBitmap.getWidth() * ratio),mY + (int)(mBitmap.getHeight() * ratio)), null);

		for (int i = 0; i < list.size(); i++) {
			Component com = list.get(i);
			c.drawBitmap(com.Texture, mX + com.X, mY + com.Y, null);
			com.draw(c);
		}

		for (int s = 0; s < slider.size(); s++) {
			Component com = slider.get(s);
			c.drawBitmap(com.Texture, mX + com.X, mY + com.Y, null);
			com.draw(c);
		}
	}

	@Override
	public void onTouch(float X, float Y, int action) {
		// TODO Auto-generated method stub
		for (int i = 0; i < slider.size(); i++) {
			if (slider.get(i).X + mX <= X && slider.get(i).Y + mY <= Y
			        && slider.get(i).X + slider.get(i).Texture.getWidth() + mX >= X
			        && slider.get(i).Y + slider.get(i).Texture.getHeight() + mY >= Y) {
				slider.get(i).onTouch(X, Y, action);
			}
		}
		if (action == MotionEvent.ACTION_DOWN) {
			startPoint = new Point((int) X, (int) Y);
		}
		if (action == MotionEvent.ACTION_MOVE) {
			if (startPoint != null) {
				if (!allowOutofEdge) {
					if (mX + X - startPoint.x + (int)(mBitmap.getWidth() * ratio) >= gFrame.getMeasuredWidth()
					        && mX + X - startPoint.x <= 0) {
						mX += X - startPoint.x;
					}
					if (mY + Y - startPoint.y + (int)(mBitmap.getHeight() * ratio) >= gFrame.getMeasuredHeight()
					        && mY + Y - startPoint.y <= 0) {
						mY += Y - startPoint.y;
					}
				} else {
					mX += X - startPoint.x;
					mY += Y - startPoint.y;
				}
			}
			startPoint = new Point((int) X, (int) Y);
		}
		if (action == MotionEvent.ACTION_UP) {
			if (!allowOutofEdge) {
				if (mX + X - startPoint.x + (int)(mBitmap.getWidth() * ratio) >= gFrame.getMeasuredWidth()
				        && mX + X - startPoint.x <= 0) {
					mX += X - startPoint.x;
				}
				if (mY + Y - startPoint.y + (int)(mBitmap.getHeight() * ratio) >= gFrame.getMeasuredHeight()
				        && mY + Y - startPoint.y <= 0) {
					mY += Y - startPoint.y;
				}
			} else {
				mX += X - startPoint.x;
				mY += Y - startPoint.y;
			}
			startPoint = new Point(0, 0);
		}
	}

	@Override
	public void onClick(float x, float y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).X + mX <= x && list.get(i).Y + mY <= y
			        && list.get(i).X + list.get(i).Texture.getWidth() + mX >= x
			        && list.get(i).Y + list.get(i).Texture.getHeight() + mY >= y) {
				list.get(i).onClick();
			}
		}
	}

}
