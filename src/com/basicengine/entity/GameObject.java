package com.basicengine.entity;
import com.basicengine.graphics.*;
import java.util.*;
import android.os.*;

public class GameObject extends RenderingObject
{
	
	public ArrayList<Script> script = new ArrayList<Script>();
	public ArrayList<String> name = new ArrayList<String>();
	public GameFrame parent;
	
	public GameObject(int X,int Y,int H,int W){
		super(X,Y,H,W);
	}
	
	public GameObject newInstance(int x,int y){
		GameObject n = new GameObject(x,y,Height,Width);
		n.setTexture(Texture);
		ArrayList<Script> cs = new ArrayList<Script>(Arrays.asList(new Script[script.size()]));
		Collections.copy(cs, script);
		n.script = cs;
		return n;
	}
	
	public GameObject newInstance(){
		GameObject n = new GameObject(X,Y,Height,Width);
		n.setTexture(Texture);
		ArrayList<Script> cs = new ArrayList<Script>(Arrays.asList(new Script[script.size()]));
		Collections.copy(cs, script);
		n.script = cs;
		return n;
	}
	
	public Handler u = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			Update();
			for(Script s:script.toArray(new Script[0])){
				s.Update();
			}
		}
	};
	
	public void Update(){
		
	}
	
	public void addScript(Script s){
		script.add(s);
		name.add(s.getClass().getName());
		s.gameobject = this;
	}
	
	public Script getComponent(String classname){
		return script.get(name.indexOf(classname));
	}
}
