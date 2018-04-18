package com.basicengine.graphics.opengl;

import android.graphics.Rect;

public class GLRenderObject {

	public GLBitmap Texture;
	public GLGameFrame parent;
	Rect rect = new Rect(0, 0, 0, 0);

	public GLRenderObject(GLBitmap texture, int X, int Y, int width, int height) {
		Texture = texture;
		rect.left = X;
		rect.top = Y;
		rect.right = width + X;
		rect.bottom = Y + height;
	}


	@SuppressWarnings("deprecation")
	public void draw() {
		Texture.setRectEx(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
		Texture.draw(0, 0); // Still needs confirmation
	}

	public void onClick(float x, float y) {

	}

	public void onTouch(float x, float y, int action) {

	}

}
