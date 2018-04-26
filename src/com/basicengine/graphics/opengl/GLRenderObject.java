package com.basicengine.graphics.opengl;

import android.graphics.Rect;

public class GLRenderObject {

	public GLBitmap Texture;
	public GLGameFrame parent;
	public Rect rect = new Rect(0, 0, 0, 0);

	public GLRenderObject(GLBitmap texture, int X, int Y, int width, int height) {
		Texture = texture;
		rect.left = X;
		rect.top = Y;
		rect.right = width + X;
		rect.bottom = Y + height;
	}

	public void draw() {
		Texture.draw();
	}

	public void onCreate() {
		Texture.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
	}

	public void onClick(float x, float y) {

	}

	public void onTouch(float x, float y, int action) {

	}

}
