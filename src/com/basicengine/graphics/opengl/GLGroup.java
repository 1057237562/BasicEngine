package com.basicengine.graphics.opengl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.Rect;

public class GLGroup extends GLGUI {

	GLBitmap content;
	public Bitmap contents = null;
	
	Point scroll = new Point(0, 0);

	public GLGroup(GLBitmap texture, int X, int Y, int width, int height) {
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

		if (contents != null && !contents.isRecycled()) {
			content.loadGLTexture(contents); // Took action
			contents.recycle();
		}

		content.draw();
		super.draw();
	} // Take care of the extends relationship
	  // Reset Original Point of the items

	public void loadContent(Bitmap contents) {
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
