package com.basicengine.graphics.opengl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.util.memory.graphic.opengl.BackGroundRendering;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;

public class Grid extends GLGUI {

	public ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();
	BackGroundRendering bgr = new BackGroundRendering(); // This case Problem
	GLBitmap content;
	
	Point scroll = new Point(0, 0);

	public Grid(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);

		//bgr = bgrz;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//thread.start();
		super.onCreate();
		content = new GLBitmap(gl,
		        Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Config.ARGB_8888));
		content.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

		/*if (contents != null && !contents.isRecycled()) {
			content.loadGLTexture(contents); // Took action
			contents.recycle();
		}*/

		content.draw();
		super.draw();
	} // Take care of the extends relationship
	  // Reset Original Point of the items

	public void addRenderObject(GLRenderObject object) {
		object.gl = bgr.gl10;
		object.parentRect = rect;
		object.SynchronizePos();
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
		Bitmap contents = BitmapModifier.flipBitmap(bgr.getContent(0, 0, 512, 512));

		FileOutputStream out = null;
		File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
		try {
			out = new FileOutputStream(file);
			contents.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		content.loadGLTexture(contents);
	}

	/*Handler handler = null;
	Thread thread = new Thread() {
		
		public void run() {
			Looper.prepare();
			Looper looper = Looper.myLooper();
			handler = new Handler(looper) {
				public void handleMessage(android.os.Message msg) {
					
				}
			};
	
			Looper.loop();
		};
	};*/

}
