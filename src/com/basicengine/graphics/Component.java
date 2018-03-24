package com.basicengine.graphics;
import android.graphics.*;

public class Component
{
	public int X;
	public int Y;
	
	public int Width;
	public int Height;
	
	public Bitmap Texture;
	
	public GUI Parent;
	
	public Component(int x,int y,Bitmap t){
		X=x;
		Y=y;
		Texture = t;
		if(t != null) {
			Width = t.getWidth();
			Height = t.getHeight();
		}
	}
	
	public void onClick(){
		
	}
	
	public void onTouch(float X,float Y,int action){
		
	}
	
	public void setTexture(Bitmap texture) {
		Texture = texture;
		if(texture != null) {
			Width = texture.getWidth();
			Height = texture.getHeight();
		}
	}
	
	public void setWidth(int width) {
		Width = width;
	}
	
	public void setHeight(int height) {
		Height = height;
	}
	
	public void setX(int x) {
		X = x;
	}
	
	public void setY(int y) {
		Y = y;
	}
	
	public void draw(Canvas c) {
		
	}
}
