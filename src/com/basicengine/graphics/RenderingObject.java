package com.basicengine.graphics;
import android.graphics.*;

public class RenderingObject
{
	public int X;
	public int Y;
	public int Width;
	public int Height;
	public Bitmap Texture;
	public int layout;
	public boolean active;
	
	public RenderingObject(int x,int y,int h,int w){
		X = x;
		Y = y;
		Width = w;
		Height = h;
	}
	
	public void setX(int x){
		X = x;
	}
	
	public void setY(int y){
		Y = y;
	}
	
	public void setTexture(Bitmap t){
		Texture = BitmapModifier.modify(t,Height,Width);
	}
	
	public void draw(Canvas c) {
		
	}
	
	public void onClick(){
		
	}
	
	public void onTouch(float X,float Y,int action) {
		
	}
}
