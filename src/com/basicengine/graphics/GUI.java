package com.basicengine.graphics;
import android.graphics.*;
import java.util.*;

public class GUI
{
	public int X;
	public int Y;
	public boolean Visible = false;
	public Bitmap Texture;
	public ArrayList<Component> list = new ArrayList<Component>();
	//Only Listen Touch Event
	public ArrayList<Component> slider = new ArrayList<Component>();
	
	public int Height;
	public int Width;
	
	public boolean active = false;
	public boolean block = true;
	
	public GUI(int x,int y,int h,int w,Bitmap t){
		X = x;
		Y = y;
		Height = h;
		Width = w;
		Texture = t;
	}
	
	public void show(){
		Visible = true;
	}
	
	public void setTexture(Bitmap t){
		Texture = t;
	}
	
	public void onClick(float x,float y){
		for(int i = 0;i<list.size();i++){
			if(list.get(i).X + X <= x && list.get(i).Y + Y <= y && list.get(i).X + list.get(i).Width + X >= x && list.get(i).Y + list.get(i).Height + Y >= y){
				list.get(i).onClick();
			}
		}
	}
	
	public void onTouch(float x,float y,int action){
		for(int i = 0;i<slider.size();i++){
			if(slider.get(i).X + X <= x && slider.get(i).Y + Y <= y && slider.get(i).X + slider.get(i).Width + X >= x && slider.get(i).Y + slider.get(i).Height + Y >= y){
				slider.get(i).onTouch(X,Y,action);
			}
		}
	}
	
	public void draw(Canvas c){
		for(int i = 0;i<list.size();i++){
			Component com = list.get(i);
			c.drawBitmap(com.Texture,new Rect(0,0,com.Texture.getWidth(),com.Texture.getHeight()),new Rect(X+com.X,Y+com.Y,X+com.X+com.Width,Y+com.Y+com.Height),null);
			com.draw(c);
		}
		
		for(int s = 0;s<slider.size();s++){
			Component com = slider.get(s);
			c.drawBitmap(com.Texture,new Rect(0,0,com.Texture.getWidth(),com.Texture.getHeight()),new Rect(X+com.X,Y+com.Y,X+com.X+com.Width,Y+com.Y+com.Height),null);
			com.draw(c);
		}
	}
}
