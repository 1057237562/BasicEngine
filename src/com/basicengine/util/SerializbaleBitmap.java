package com.basicengine.util;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SerializbaleBitmap implements Serializable{

	private static final long serialVersionUID = 1L;
	private byte[] data;
	
	public void setBitmap(Bitmap btm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		btm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		data = baos.toByteArray();
	}
	
	public Bitmap getBitmap() {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}
