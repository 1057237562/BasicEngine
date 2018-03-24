package com.basicengine.graphics;
import com.basicengine.util.SerializbaleBitmap;
import com.basicengine.util.memory.MemoryUnit;

import android.graphics.*;

public class Component
{
	public int X;
	public int Y;
	
	public int Width;
	public int Height;
	
	@Deprecated
	Bitmap Texture;
	
	public GUI Parent;
	
	public String TextureID;
	
	public Component(int x,int y,Bitmap t){
		X=x;
		Y=y;
		setTexture(t);
		if(t != null) {
			Width = t.getWidth();
			Height = t.getHeight();
		}
	}
	
	public void onClick(){
		
	}
	
	public void onTouch(float X,float Y,int action){
		
	}
	
	public void setTexture(Bitmap t) {
		SerializbaleBitmap sb = new SerializbaleBitmap();
		sb.setBitmap(t);
		TextureID = MemoryUnit.getInstance().addCache(sb);
		if(t != null) {
			Width = t.getWidth();
			Height = t.getHeight();
		}
	}
	
	@Deprecated
	public void setTexture_D(Bitmap texture) {
		Texture = texture;
		if(texture != null) {
			Width = texture.getWidth();
			Height = texture.getHeight();
		}
	}
	
	public void setTextureID(String textureID) {
		TextureID = textureID;
	}
	
	public Bitmap getTexture() {
		return ((SerializbaleBitmap)MemoryUnit.getInstance().getFromCache(TextureID)).getBitmap();
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
