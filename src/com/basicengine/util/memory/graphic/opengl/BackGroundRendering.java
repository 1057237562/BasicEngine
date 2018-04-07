package com.basicengine.util.memory.graphic.opengl;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class BackGroundRendering {

	public EGL10 mEgl;
	private int[] version = new int[2];
	EGLConfig[] configs = new EGLConfig[1];
	int[] num_config = new int[1];
	int[] configSpec = { EGL10.EGL_SURFACE_TYPE, EGL10.EGL_PBUFFER_BIT, EGL10.EGL_RED_SIZE, 8, EGL10.EGL_GREEN_SIZE, 8,
	        EGL10.EGL_BLUE_SIZE, 8, EGL10.EGL_ALPHA_SIZE, 8, EGL10.EGL_NONE };
	int attribListPbuffer[] = { EGL10.EGL_WIDTH, 512, EGL10.EGL_HEIGHT, 512, EGL10.EGL_NONE };

	public GL10 gl10;
	public int size = 512;
	
	public BackGroundRendering() {
		initEGL();
	}

	public BackGroundRendering(int s) {
		size = s;
		attribListPbuffer = new int[5];
		attribListPbuffer[0] = EGL10.EGL_WIDTH;
		attribListPbuffer[1] = attribListPbuffer[3] = s;
		attribListPbuffer[2] = EGL10.EGL_HEIGHT;
		attribListPbuffer[4] = EGL10.EGL_NONE;
		initEGL();
	}

	private void initEGL() {
		mEgl = (EGL10) EGLContext.getEGL();
		EGLDisplay mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
		mEgl.eglInitialize(mEglDisplay, version);
		mEgl.eglChooseConfig(mEglDisplay, configSpec, configs, 1, num_config);
		EGLConfig mEglConfig = configs[0]; // creating background drawing unit
		EGLContext mEglContext = mEgl.eglCreateContext(mEglDisplay, mEglConfig, EGL10.EGL_NO_CONTEXT, null);
		if (mEglContext == EGL10.EGL_NO_CONTEXT) {
			Log.d("com.basicengine", "EGL_NO_CONTEXT");
		}
		EGLSurface mEglPBSurface = mEgl.eglCreatePbufferSurface(mEglDisplay, mEglConfig, attribListPbuffer);
		if (mEglPBSurface == EGL10.EGL_NO_SURFACE) {
			int ec = mEgl.eglGetError();
			if (ec == EGL10.EGL_BAD_DISPLAY) {
				Log.d("com.basicengine", "EGL_BAD_DISPLAY");
			}
			if (ec == EGL10.EGL_BAD_DISPLAY) {
				Log.d("com.basicengine", "EGL_BAD_DISPLAY");
			}
			if (ec == EGL10.EGL_NOT_INITIALIZED) {
				Log.d("com.basicengine", "EGL_NOT_INITIALIZED");
			}
			if (ec == EGL10.EGL_BAD_CONFIG) {
				Log.d("com.basicengine", "EGL_BAD_CONFIG");
			}
			if (ec == EGL10.EGL_BAD_ATTRIBUTE) {
				Log.d("com.basicengine", "EGL_BAD_ATTRIBUTE");
			}
			if (ec == EGL10.EGL_BAD_ALLOC) {
				Log.d("com.basicengine", "EGL_BAD_ALLOC");
			}
			if (ec == EGL10.EGL_BAD_MATCH) {
				Log.d("com.basicengine", "EGL_BAD_MATCH");
			}
		}
		if (!mEgl.eglMakeCurrent(mEglDisplay, mEglPBSurface, mEglPBSurface, mEglContext))//这里mEglPBSurface，意思是画图和读图都是从mEglPbSurface开始
		{
			Log.d("com.basicengine", "bind failed ECODE:" + mEgl.eglGetError());
		}
		gl10 = (GL10) mEglContext.getGL();
		gl10.glViewport(0, 0, size, size);
		gl10.glLoadIdentity();
	}

	public Bitmap getContent(int x, int y, int width, int height) { // get bitmap from
		IntBuffer PixelBuffer = IntBuffer.allocate(width * height);
		PixelBuffer.position(0);
		gl10.glReadPixels(x, y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, PixelBuffer);
		PixelBuffer.position(0);
		int pix[] = new int[width * height];
		PixelBuffer.get(pix); // Red Channel is swapped with Blue

		Bitmap bmp = Bitmap.createBitmap(pix, width, height, Bitmap.Config.ARGB_8888);

		//Prepare the color matrix

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		float[] src = new float[] {
				0, 0, 1, 0, 0,
				0, 1, 0, 0, 0,
				1, 0, 0, 0, 0,
		        0, 0, 0, 1, 0
		};
		ColorMatrix colorMatrix = new ColorMatrix(src);

		Paint mPaint = new Paint();
		mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		
		canvas.drawBitmap(bmp, new Rect(0, 0, width, height), new Rect(0, 0, width, height), mPaint);
		bmp.recycle();
		return bitmap;
	}

}
