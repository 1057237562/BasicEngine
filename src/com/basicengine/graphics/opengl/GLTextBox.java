package com.basicengine.graphics.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;

public class GLTextBox extends GLGUI {

	public StringBuilder content = new StringBuilder();
	public int cursor;
	public Paint mp;
	public int startX = 10;
	public int startY = 0;
	public boolean center = true;
	boolean upper = false;
	GLBitmap text;
	public boolean changed = false;

	public GLTextBox(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		text = new GLBitmap(gl,
		        Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Config.ARGB_8888)); //Temporary
		text.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));

		mp = new Paint();
		mp.setTextSize(20);
		mp.setColor(Color.BLACK);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (changed) {
			regenerateTexture();
			changed = false;
		} // This is not the best way to regenerate the Texture

		text.draw();
		super.draw();
		// This things Override all Image
		// This reminds me that Object in OpenGL will Override the down layout
	}

	@Override
	public void onClick(float x, float y) {
		// TODO Auto-generated method stub
		Rect rect = new Rect();
		mp.getTextBounds(content.toString(), 0, content.length(), rect);
		float width = rect.width();
		cursor = (int) ((x - this.rect.left - startX) / width * content.length());
		if (cursor > content.length()) {
			cursor = content.length();
		}

		InputMethodManager imm = (InputMethodManager) parent.getContext()
		        .getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			parent.setFocusable(true);
			parent.setFocusableInTouchMode(true);
			parent.findFocus();
			imm.showSoftInput(parent, InputMethodManager.SHOW_FORCED);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}

		parent.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int p2, KeyEvent p3) {
				// TODO Auto-generated method stub
				if (p2 == KeyEvent.KEYCODE_SHIFT_LEFT) {
					if (cursor == 0 && !upper) {
						upper = true;
					} else {
						content.replace(cursor - 1, cursor, content.substring(cursor - 1, cursor).toUpperCase());
						upper = false;
					}
					return false;
				}
				if (p3.getAction() == KeyEvent.ACTION_UP) {
					switch (p2) {
						case KeyEvent.KEYCODE_SPACE:
							content.insert(cursor, " ");
						break;
						case KeyEvent.KEYCODE_COMMA:
							content.insert(cursor, ",");
						break;
						case KeyEvent.KEYCODE_PERIOD:
							content.insert(cursor, ".");
						break;
						case KeyEvent.KEYCODE_SEMICOLON:
							content.insert(cursor, ":");
						break;
						case KeyEvent.KEYCODE_MINUS:
							content.insert(cursor, "_");
						break;
						case KeyEvent.KEYCODE_DEL:
							try {
								content.delete(cursor - 1, cursor);
								cursor--;
								if (cursor < 0) {
									cursor = 0;
								}
							} catch (Exception e) {
							}

							changed = true;
							return false;
						case KeyEvent.KEYCODE_FORWARD_DEL:
							try {
								content.delete(cursor, cursor + 1);
							} catch (Exception e) {
							}
							return false;

						default:
							content.insert(cursor, KeyEvent.keyCodeToString(p2).replace("KEYCODE_", "").toLowerCase());
						break;
					}
					cursor++;

					changed = true;
				}
				return false;
			}
		});
	}

	public void regenerateTexture() {
		Bitmap text_texture = Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Config.ARGB_8888);

		Canvas canvas = new Canvas(text_texture);

		if (center) {
			Rect rect = new Rect();
			mp.getTextBounds(content.toString(), 0, content.length(), rect);
			int height = rect.height();
			startY = (this.rect.bottom - this.rect.top - height) / 2;
		}
		canvas.drawText(content.toString(), startX, startY, mp);

		text.loadGLTexture(text_texture);
		/*text = new GLBitmap(parent.gl10, text_texture);
		text.setRect(rect, new Rect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight()));*/
	}

	public void setMainPaint(Paint mp) {
		this.mp = mp;
	}

	public void setTextSize(float s) {
		mp.setTextSize(s);
		Rect rect = new Rect();
		mp.getTextBounds(content.toString(), 0, content.length(), rect);
		int height = rect.height();
		startY = (this.rect.bottom - this.rect.top - height) / 2;
	}
}
