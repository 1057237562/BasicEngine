package com.basicengine.graphics;
import com.basicengine.util.SerializbaleBitmap;
import com.basicengine.util.memory.MemoryUnit;

import android.graphics.*;

public class RenderingObject
{
	public int X;
	public int Y;
	public int Width;
	public int Height;
	@Deprecated
	Bitmap Texture;
	public int layout;
	public boolean active;
	public String TextureID;
	
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
	
	@Deprecated
	public void setTexture_D(Bitmap t){
		Texture = BitmapModifier.modify(t,Height,Width);
	}
	
	public void setTexture(Bitmap t) {
		SerializbaleBitmap sb = new SerializbaleBitmap();
		sb.setBitmap(t);
		TextureID = MemoryUnit.getInstance().addCache(sb);
	}
	
	public void setTextureID(String textureID) {
		TextureID = textureID;
	}
	
	public void draw(Canvas c) {
		
	}
	
	public void onClick(){
		
	}
	
	public void onTouch(float X,float Y,int action) {
		
	}
}
