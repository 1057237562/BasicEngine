package com.basicengine.graphics.opengl;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class GLImageButton extends GLGUI {

	View.OnClickListener ls;

	GLBitmap normal = null;
	GLBitmap hover = null;
	GLBitmap clicked = null;

	public GLImageButton(GLBitmap n, GLBitmap h, GLBitmap c, int X, int Y, int width, int height) {
		super(n, X, Y, width, height);
		n.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		h.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		c.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		normal = n;
		hover = h;
		clicked = c;
		// TODO Auto-generated constructor stub
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
			case MotionEvent.ACTION_HOVER_ENTER:
				Texture = hover;
			break;
			case MotionEvent.ACTION_HOVER_EXIT:
				Texture = normal;
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
