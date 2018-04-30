package com.basicengine.graphics.opengl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.basicengine.util.memory.graphic.opengl.BackGroundRendering;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Grid extends GLGUI {

	public ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();
	BackGroundRendering bgr = null; // This case Problem
	GLBitmap content;
	
	Point scroll = new Point(0, 0);

	public Grid(GLBitmap texture, int X, int Y, int width, int height, BackGroundRendering bgrz) {
		super(texture, X, Y, width, height);

		bgr = bgrz;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		thread.start();
		content = new GLBitmap(gl,
		        Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Config.ARGB_8888));
		super.onCreate();
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
		handler.sendMessage(new Message());
	}

	Handler handler = null;
	Thread thread = new Thread() {
		
		public void run() {
			Looper.prepare();
			Looper looper = Looper.myLooper();
			handler = new Handler(looper) {
				public void handleMessage(android.os.Message msg) {
					for (int i = objects.size() - 1; i >= 0; i--) {
						objects.get(i).draw();
					}
					Bitmap contents = bgr.getContent(scroll.x, scroll.y, scroll.x + rect.right - rect.left,
					        scroll.y + rect.bottom - rect.top);

					FileOutputStream out = null;
					File file = new File(Environment.getExternalStorageDirectory(),
					        System.currentTimeMillis() + ".jpg");
					try {
						out = new FileOutputStream(file);
						contents.compress(Bitmap.CompressFormat.JPEG, 90, out);
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
			};
		};
	};

}
