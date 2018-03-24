package com.basicengine.input;
import com.basicengine.graphics.*;
import android.graphics.*;
import android.view.*;
import android.os.*;
import com.basicengine.util.*;

public class Controller extends GUI
{
	
	public Roller r;
	
	public onRollListener ls;
		
	public Handler hd = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			ls.onRoll(r.X-X-Controller.this.Width/2+r.Texture.getWidth()/2,r.Y-Y-Controller.this.Height/2+r.Texture.getHeight()/2);
		}
	};
	
	public Controller (int X,int Y,int H,int W,Bitmap bg,Bitmap roller_bg){
		super(X,Y,H,W,BitmapModifier.modify(bg,H,W));
		r =new Roller(BitmapModifier.modify(roller_bg,H/3,W/3),this);
		slider.add(r);
	}

	public void linkGameFrame(GameFrame gf)
	{
		// TODO: Implement this method
		gf.registerUpdate(hd);
	}

	@Override
	public void onTouch(float X, float Y, int action)
	{
		// TODO: Implement this method
		//super.onTouch(X,Y,action);
		r.onTouch(X,Y,action);
		if(action == MotionEvent.ACTION_UP){
			r.X = this.X+Width/2-r.Texture.getWidth()/2;
			r.Y = this.Y+Height/2-r.Texture.getHeight()/2;
			active = false;
		}else{
			active = true;
		}
	}
	
	public void addOnRollListener(onRollListener l){
		ls = l;
	}
	
	@Override
	public void draw(Canvas c) {
		// TODO Auto-generated method stub
		for(int i = 0;i<list.size();i++){
			Component com = list.get(i);
			c.drawBitmap(com.Texture,com.X,com.Y,null);
		}
		
		for(int s = 0;s<slider.size();s++){
			Component com = slider.get(s);
			c.drawBitmap(com.Texture,com.X,com.Y,null);
		}
	}
}

class Roller extends Component{
	
	public Controller parent;
	public int height;
	public int width;
	
	public Roller(Bitmap t,Controller p){
		super(p.X+p.Width/2-t.getWidth()/2,p.Y+p.Height/2-t.getHeight()/2,t);
		parent = p;
		height = t.getHeight();
		width = t.getWidth();
	}

	@Override
	public void onTouch(float x, float y, int action)
	{
		// TODO: Implement this method
		double distance = MathExtension.getDistance(x,y,parent.X+parent.Width/2,parent.Y+parent.Height/2);
		if(distance<=(parent.Width+parent.Height)/4){
			X =(int) x-width/2;
			Y =(int) y-height/2;
		}else{
			X = (int)((x-parent.X-parent.Width/2)*((parent.Width+parent.Height)/(4*distance))+parent.X+parent.Width/2-width/2);
			Y = (int)((y-parent.Y-parent.Height/2)*((parent.Width+parent.Height)/(4*distance))+parent.Y+parent.Height/2-height/2);
		}
		if(action == MotionEvent.ACTION_UP){
			X = parent.X+parent.Width/2-Texture.getWidth()/2;
			Y = parent.Y+parent.Height/2-Texture.getHeight()/2;
		}
	}
	
	public void setHeight(int h){
		height = h;
		Texture = BitmapModifier.modify(Texture,h,width);
	}
	
	public void setWidth(int w){
		width = w;
		Texture = BitmapModifier.modify(Texture,height,w);
	}
}
