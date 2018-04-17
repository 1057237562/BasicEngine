package com.basicengine.graphics.opengl;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GLGameFrame extends GLSurfaceView implements GLSurfaceView.Renderer, OnTouchListener {

	ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();
	public int VisionX = 0;
	public int VisionY = 0;

	public GLGameFrame(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		setRenderer(this);
		setOnTouchListener(this);
	}

	public void addRenderObject(GLRenderObject object) {
		object.parent = this;
		objects.add(object);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glTranslatex(0, 0, 0);//-1, -1, 0);

		for (GLRenderObject object : objects.toArray(new GLRenderObject[0])) {
			object.draw();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);

		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent me) {
		// TODO Auto-generated method stub
		for (GLRenderObject object : objects.toArray(new GLRenderObject[0])) {
			if (me.getX() >= object.rect.left && me.getX() <= object.rect.right && me.getY() >= object.rect.top
			        && me.getY() <= object.rect.bottom) {
				object.onTouch(me.getX(), me.getY(), me.getAction());
				if (me.getAction() == MotionEvent.ACTION_UP) {
					object.onClick(me.getX(), me.getY());
				}
			}
		}
		return true;
	}

}
