package com.basicengine.graphics.opengl;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.basicengine.util.memory.graphic.opengl.BackGroundRendering;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.*;

public class Grid extends GLGUI {

	public ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();
	BackGroundRendering bgr = new BackGroundRendering();
	GLBitmap content;
	
	Point scroll;

	public Grid(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		content = new GLBitmap(gl, Bitmap.createBitmap(width, height, Config.ARGB_8888));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		content.draw();
		super.draw();
	} // Take care of the extends relationship
	  // Reset Original Point of the items

	public void addRenderObject(GLRenderObject object) {
		object.gl = bgr.gl10;
		
		objects.add(object);
		RegenerateTexture();
	}
	
	public GL10 getGridGL() {
		return bgr.gl10;
	}

	public void RegenerateTexture() {
		for (int i = objects.size() - 1; i >= 0; i--) {
			objects.get(i).draw();
		}

		content.loadGLTexture(bgr.getContent(scroll.x, scroll.y, scroll.x + rect.right - rect.left, scroll.y + rect.bottom - rect.top));
	}

}
