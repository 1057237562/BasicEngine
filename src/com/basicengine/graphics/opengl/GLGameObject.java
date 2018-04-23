package com.basicengine.graphics.opengl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.basicengine.entity.GLConverter;
import com.basicengine.entity.Script;
import com.basicengine.graphics.GameFrame;

import android.os.Handler;
import android.os.Message;

public class GLGameObject extends GLRenderObject {

	public ArrayList<Script> script = new ArrayList<Script>();
	public ArrayList<String> name = new ArrayList<String>();
	public GameFrame parent;

	public GLGameObject(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		// TODO Auto-generated constructor stub
	}

	public GLGameObject newInstance(int x, int y) {
		GLGameObject n = new GLGameObject(Texture, x, y, rect.right - rect.left, rect.bottom - rect.top);
		ArrayList<Script> cs = new ArrayList<Script>(Arrays.asList(new Script[script.size()]));
		Collections.copy(cs, script);
		n.script = cs;
		return n;
	}

	public GLGameObject newInstance() {
		GLGameObject n = new GLGameObject(Texture, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
		ArrayList<Script> cs = new ArrayList<Script>(Arrays.asList(new Script[script.size()]));
		Collections.copy(cs, script);
		n.script = cs;
		return n;
	}

	public Handler u = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO: Implement this method
			super.handleMessage(msg);
			Update();
			for (Script s : script.toArray(new Script[0])) {
				s.Update();
			}
		}
	};

	public void Update() {

	}

	public void addScript(Script s) {
		script.add(s);
		name.add(s.getClass().getName());
		s.gameobject = GLConverter.toGameObject(this);
	}

	public Script getComponent(String classname) {
		return script.get(name.indexOf(classname));
	}
}
