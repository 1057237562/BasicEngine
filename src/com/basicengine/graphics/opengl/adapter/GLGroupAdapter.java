package com.basicengine.graphics.opengl.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.opengl.GLGroup;
import com.basicengine.graphics.opengl.GLRenderObject;
import com.basicengine.util.memory.graphic.opengl.BackGroundRendering;

import android.graphics.Bitmap;
import android.os.Environment;

public class GLGroupAdapter { // Must create at Main Thread (Looper Thread)

	public ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();
	BackGroundRendering bgr = new BackGroundRendering(); // This case Problem
	GLGroup bind = null;

	public GLGroupAdapter() {

	}

	public void BindGroup(GLGroup glGrid) {
		bind = glGrid;
	}

	public void addRenderObject(GLRenderObject object) {
		object.gl = bgr.gl10;
		object.parentRect = bind.rect;
		object.SynchronizePos();
		objects.add(object);
		RegenerateTexture();
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
		if (bind != null) {
			bind.contents = contents;
		}
	}

	public GL10 getGL() {
		return bgr.gl10;
	}

}
