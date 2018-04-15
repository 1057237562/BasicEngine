package com.basicengine.graphics.opengl;

import android.graphics.Rect;

public class GLRenderObject {

	public GLBitmap Texture;
	Rect rect = new Rect(0, 0, 0, 0);

	public GLRenderObject(GLBitmap texture, int X, int Y, int width, int height) {
		Texture = texture;
		rect.left = X;
		rect.top = Y;
		rect.right = width + X;
		rect.bottom = Y + height;
	}


	public void draw() {
		Texture.draw(rect.left, rect.top);
	}

}
