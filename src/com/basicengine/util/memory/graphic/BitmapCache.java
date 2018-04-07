package com.basicengine.util.memory.graphic;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.opengl.GLBitmap;
import com.basicengine.util.memory.graphic.opengl.BackGroundRendering;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;

public class BitmapCache {
	/*This theory isn't tested yet*/
	/*It may not taking action*/
	/*This will save Bitmap into GPU(Follow my mind)*/
	/*
	Picture picture = new Picture();
	HashMap<String, Rect> pics = new HashMap<String, Rect>();
	Position nowPos;
	Canvas gCanvas;
	int direction = 0;
	
	Rect size;
	
	public BitmapCache(int Width, int Height) {
		gCanvas = picture.beginRecording(Width, Height); // 1 can be changed(Maybe) This should be the maxium size of the picture
		size.right = Width;
		size.bottom = Height;
		nowPos = new Position(Width, Height);
	}
	
	public String saveBitmap(Bitmap bitmap) {
		gCanvas.drawBitmap(bitmap, nowPos.X, nowPos.Y, null);
		String UUID = System.currentTimeMillis() + "";
		pics.put(UUID, new Rect(nowPos.X, nowPos.Y, nowPos.X + size.right, nowPos.Y + size.bottom));
		if (nowPos.X < nowPos.Y) {
			nowPos.X += size.right;
		} else {
			nowPos.Y += size.bottom;
		}
		return UUID;
	}
	
	public Bitmap readBitmap(String UUID) {
		new Pic
	}*/

	BackGroundRendering mbgr;
	GL10 gl10;
	/*final int[] GL_TEXTURE = { GL10.GL_TEXTURE0, GL10.GL_TEXTURE1, GL10.GL_TEXTURE2, GL10.GL_TEXTURE3, GL10.GL_TEXTURE4,
	        GL10.GL_TEXTURE5, GL10.GL_TEXTURE6, GL10.GL_TEXTURE7, GL10.GL_TEXTURE8, GL10.GL_TEXTURE9, GL10.GL_TEXTURE10,
	        GL10.GL_TEXTURE11, GL10.GL_TEXTURE12, GL10.GL_TEXTURE13, GL10.GL_TEXTURE14, GL10.GL_TEXTURE15 };*/
	int[] textures = new int[1];

	Rect dRect = new Rect(0, 0, 0, 0);
	public HashMap<String, Rect> bmp = new HashMap<String, Rect>();
	int size = 512;
	public static final int OPENGL_TEXTURESIZE = 512;

	public BitmapCache(int s) {
		size = s;
		mbgr = new BackGroundRendering(size);
		gl10 = mbgr.gl10;
		gl10.glEnable(GL10.GL_TEXTURE_2D);
	}

	public BitmapCache() {
		mbgr = new BackGroundRendering();
		gl10 = mbgr.gl10;
		gl10.glEnable(GL10.GL_TEXTURE_2D);
	}

	private void addBitmapToTexture(Bitmap bitmap, Position where) {
		gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl10.glTranslatex(0, 0, 0);
		if (textures[0] != 0) {
			GLBitmap oldBitmap = new GLBitmap(gl10, GL10.GL_TEXTURE_2D, textures[0]); // get olds pic
			oldBitmap.draw(0, 0);

			Bitmap content = BitmapModifier.modify(BitmapModifier.flipBitmap(mbgr.getContent(0, 0, 512, 512)), size,
			        size);
			Canvas c = new Canvas(content);
			c.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
			        new Rect(where.X, where.Y, where.X + bitmap.getWidth(), where.Y + bitmap.getHeight()), null);// mixed pic

			gl10.glGenTextures(1, textures, 0);
			gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, content, 0);
		} else {
			// Expand Bitmap
			Bitmap bg = Bitmap.createBitmap(size, size, Config.ARGB_8888);
			Canvas canvas = new Canvas(bg);
			canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
			        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);

			gl10.glGenTextures(1, textures, 0);
			gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bg, 0);
			bg.recycle();
		}
	}

	/*public String addBitmap(Bitmap bitmap, boolean recycle) {
		for (Stack s : notfull.toArray(new Stack[0])) {
			if (size - s.filled.right - bitmap.getWidth() >= 0) {
				if (size - s.filled.top - bitmap.getHeight() >= 0) { // out of range
					if (bitmap.getHeight() + s.filled.top > s.filled.bottom) { // a longer picture
						s.filled.bottom = bitmap.getHeight() + s.filled.top;
					}
					// allocate texture
					addBitmapToTexture(s.index, bitmap, new Position(s.filled.top, s.filled.left));
					if (recycle) {
						bitmap.recycle();
					}
					//assign ID and Rect
					String UUID = System.currentTimeMillis() + "";
					bmp.put(UUID, new Content(new Rect(s.filled.left, s.filled.top, s.filled.left + bitmap.getWidth(),
					        s.filled.top + bitmap.getHeight()), s.index));
					s.filled.left += bitmap.getWidth();
					return UUID;
				}
			} else { // a fat picture ('◡')
				s.filled.top = s.filled.bottom;
				s.filled.left = 0; // next line
				addBitmapToTexture(s.index, bitmap, new Position(s.filled.top, s.filled.left));
				if (recycle) {
					bitmap.recycle();
				}
	
				String UUID = System.currentTimeMillis() + "";
				bmp.put(UUID, new Content(new Rect(s.filled.left, s.filled.top, s.filled.left + bitmap.getWidth(),
				        s.filled.top + bitmap.getHeight()), s.index));
				s.filled.left += bitmap.getWidth();
				return UUID;
			}
		}
		gl10.glEnable(GL_TEXTURE[index]); // create new texture
		int[] textures = new int[1];
		gl10.glGenTextures(1, textures, 0);
		GLUtils.texImage2D(GL_TEXTURE[index], 0, bitmap, 0);
		textureid.add(textures[0]);
		if (recycle) {
			bitmap.recycle();
		}
	
		String UUID = System.currentTimeMillis() + ""; // allocate IDs
		bmp.put(UUID, new Content(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), index));
		if (bitmap.getWidth() < size || bitmap.getHeight() < size) {
			notfull.add(new Stack(index, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())));
		}
		index++;
		return UUID;
	}*/

	public String addBitmap(Bitmap bitmap, boolean recycle) throws OutOfSpaceException {
		if (dRect.left + bitmap.getWidth() > size) {
			dRect.top = dRect.bottom;
			dRect.left = 0;
			if (dRect.top + bitmap.getHeight() > size) {
				//throw new OutOfSpaceException();
			}
		}
		if (dRect.bottom < dRect.top + bitmap.getHeight()) {
			dRect.bottom = dRect.top + bitmap.getHeight();
		}
		addBitmapToTexture(bitmap, new Position(dRect.left, dRect.top));
		dRect.left += bitmap.getWidth();
		if (recycle) {
			bitmap.recycle();
		}

		String UUID = System.currentTimeMillis() + ""; // allocate IDs
		bmp.put(UUID, new Rect(dRect.left - bitmap.getWidth(), dRect.top, dRect.left, dRect.top + bitmap.getHeight()));
		return UUID;
	}

	public Bitmap getBitmap(String UUID) {
		Rect c = bmp.get(UUID);
		GLBitmap gBitmap = new GLBitmap(gl10, GL10.GL_TEXTURE_2D, textures[0]);
		gBitmap.draw(-1, -1);
		return BitmapModifier
		        .flipBitmap(mbgr.getContent(c.left, OPENGL_TEXTURESIZE - c.bottom * OPENGL_TEXTURESIZE / size,
		                (c.right - c.left) * OPENGL_TEXTURESIZE / size,
		                (c.bottom - c.top) * OPENGL_TEXTURESIZE / size)); // get allocated area's image content
		//return BitmapModifier.flipBitmap(mbgr.getContent(0, 0, 512, 512));
	}

}

class Position {
	int X;
	int Y;

	public Position(int x, int y) {
		X = x;
		Y = y;
	}
}
