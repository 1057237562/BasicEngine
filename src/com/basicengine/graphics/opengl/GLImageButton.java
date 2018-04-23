package com.basicengine.graphics.opengl;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class GLImageButton extends GLGUI {

	OnClickListener ls = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

		}
	};

	GLBitmap normal = null;
	GLBitmap hover = null;
	GLBitmap clicked = null;

	public GLImageButton(GLBitmap n, GLBitmap h, GLBitmap c, int X, int Y, int width, int height) {
		super(n, X, Y, width, height);
		normal = n;
		hover = h;
		clicked = c;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		normal.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		hover.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		clicked.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
	}

	public void setOnClickListener(View.OnClickListener ls) {
		this.ls = ls;
	}

	@Override
	public void onTouch(float x, float y, int action) {
		// TODO Auto-generated method stub
		super.onTouch(x, y, action);
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				Texture = clicked;
			break;
			case MotionEvent.ACTION_MOVE:
				Texture = hover;
			break;
			case MotionEvent.ACTION_UP:
				Texture = normal;
				ls.onClick(null);
			break;
			default:
			break;
		}
	}

}
