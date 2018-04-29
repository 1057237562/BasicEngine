package com.basicengine.graphics.opengl;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

public class GLRenderObject {

	public GLBitmap Texture;
	public GLGameFrame parent;
	public Rect rect = new Rect(0, 0, 0, 0);
	public GL10 gl;

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
		SynchronizePos();
	}

	public void onClick(float x, float y) {

	}

	public void onTouch(float x, float y, int action) {

	}
	
	public void setX(int x){
		rect.right+=x-rect.left;
		rect.left = x;
		SynchronizePos();
	}
	
	public void setY(int y){
		rect.bottom+=y-rect.top;
		rect.top = y;
		SynchronizePos();
	}

	public void setWidth(int w){
		rect.right = rect.left+w;
		SynchronizePos();
	}
	
	public void setHeight(int h){
		rect.bottom = rect.top+h;
		SynchronizePos();
	}
	
	private void SynchronizePos(){
		Texture.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
	}
}
