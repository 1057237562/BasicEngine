package com.basicengine.graphics.component;

import java.util.ArrayList;

import com.basicengine.graphics.BitmapModifier;
import com.basicengine.graphics.Component;
import com.basicengine.graphics.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class CListBox extends GUI {

	public ArrayList<Component> items = new ArrayList<Component>();
	public int direction = 0;
	int mX = 0;
	int mY = 0;

	int nX = 0;
	int nY = 0;
	public int rowHeight = 50;
	public int columnWidth = 50;
	boolean first = true;

	public static final int DIRECTION_VERTICAL = 0;
	public static final int DIRECTION_HORIZONTAL = 1;
	public Bitmap background;

	public CListBox(int x, int y, int h, int w, Bitmap t) {
		super(x, y, h, w, t);
		background = t;
		// TODO Auto-generated constructor stub
	}

	public void AddListItem(Component item) {
		items.add(item);
		item.Parent = this;
		
		switch (direction) {
			case DIRECTION_HORIZONTAL:
				if (nX + columnWidth > Width) {
					nY += rowHeight;
					nX = 0;
				}
				item.X = nX;
				item.Y = nY;
				nX += columnWidth;
			break;

			case DIRECTION_VERTICAL:
				if (nY + rowHeight > Height) {
					nX += columnWidth;
					nY = 0;
				}
				item.X = nX;
				item.Y = nY;
				nY += rowHeight;
			break;
		}
		
		ReGenerateTexture();
		/*Picture picture = new Picture();
		Canvas clip = picture.beginRecording(Width, Height);
		if (background != null) {
			clip.drawBitmap(BitmapModifier.modify(background, Height, Width), 0, 0, null);
		}
		for (int s = 0; s < items.size(); s++) {
			Component com = items.get(s);
			clip.drawBitmap(com.Texture, new Rect(0, 0, com.Texture.getWidth(), com.Texture.getHeight()),
			        new Rect(com.X, com.Y, com.X + com.Width, com.Y + com.Height), null);
			com.draw(clip);
		}
		picture.endRecording();
		Bitmap bitmap = Bitmap.createBitmap(Width, Height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		picture.draw(canvas);
		picture = null;
		System.gc();
		Texture = bitmap;*/
	}
	
	public void RemoveListItem(int index) {
		Remove(index);
	}
	
	public void RemoveListItem(Component object) {
		Remove(items.indexOf(object));
	}
	
	private void Remove(int index) {
		items.remove(index);
		for(int i = index; i < items.size(); i++) {
			Component s = items.get(i);
			switch (direction) {
				case DIRECTION_VERTICAL:
					s.Y -= rowHeight;
					if(s.Y < 0) {
						s.Y += Height; // padding
						s.X -= columnWidth; // padding
					}
				break;

				case DIRECTION_HORIZONTAL:
					s.X -= columnWidth;
					if(s.X < 0) {
						s.X += Width; // padding
						s.Y -= rowHeight; // padding
					}
				break;
			}
		}
		
		switch (direction) {
			case DIRECTION_VERTICAL:
				nY -= rowHeight;
			break;

			case DIRECTION_HORIZONTAL:
				nX -= columnWidth;
			break;
		}
		
		ReGenerateTexture();
	}
	
	public void ReGenerateTexture() {
		Picture picture = new Picture();
		Canvas clip = picture.beginRecording(Width, Height);
		if (background != null) {
			clip.drawBitmap(BitmapModifier.modify(background, Height, Width), 0, 0, null);
		}
		for (int s = 0; s < items.size(); s++) {
			Component com = items.get(s);
			clip.drawBitmap(com.Texture, new Rect(0, 0, com.Texture.getWidth(), com.Texture.getHeight()),
			        new Rect(com.X, com.Y, com.X + com.Width, com.Y + com.Height), null);
			com.draw(clip);
		}
		picture.endRecording();
		Bitmap bitmap = Bitmap.createBitmap(Width, Height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		picture.draw(canvas);
		picture = null;
		System.gc();
		Texture = bitmap;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void onClick(float x, float y) {
		// TODO Auto-generated method stub
		super.onClick(x, y);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).X + mX + X <= x && items.get(i).Y + mY + Y <= y
			        && items.get(i).X + mX + items.get(i).Width + X >= x
			        && items.get(i).Y + items.get(i).Height + mY + Y >= y) {
				items.get(i).onClick();
			}
		}
	}

	@Override
	public void onTouch(float x, float y, int action) {
		// TODO Auto-generated method stub
		super.onTouch(x, y, action);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).X + mX + X <= x && items.get(i).Y + mY + Y <= y
			        && items.get(i).X + mX + items.get(i).Width + X >= x
			        && items.get(i).Y + items.get(i).Height + mY + Y >= y) {
				items.get(i).onTouch(x, y, action);
			}
		}
	}
	/*switch (direction) {
		case DIRECTION_HORIZONTAL:
			if(nY + com.Texture.getHeight() > Height) {
				nY = mY;
				nX += maxLength;// Add With padding
				maxLength = 0;
			}
			if(nX + com.Texture.getWidth() <= X) {
				break;
			}
			if(nX < X) {
				int availableWidth = nY + com.Texture.getWidth() - X;
				c.drawBitmap(com.Texture, new Rect(com.Texture.getWidth() - availableWidth, 0,availableWidth,com.Texture.getHeight()) ,new Rect(X, Y + nY, X + availableWidth,Y + com.Texture.getHeight()), null);
			}else if(nX + com.Texture.getWidth() <= Width) {
				if(maxLength < com.Texture.getWidth()) {
					maxLength = com.Texture.getWidth();
				}
				c.drawBitmap(com.Texture, new Rect(0,0,com.Texture.getWidth(),com.Texture.getWidth()), new Rect(X + nX, Y + nY,X + nX + com.Texture.getWidth(),Y + nY + com.Texture.getHeight()), null);
				nY += com.Texture.getHeight(); // Add With padding
			}else {
				int availableWidth = Width - nX + com.Texture.getWidth();
				c.drawBitmap(com.Texture, new Rect(0,0,availableWidth,com.Texture.getHeight()) ,new Rect(X + nX, Y + nY, X + nX + availableWidth,Y + nY + com.Texture.getHeight()), null);
			}
		break;
	
		case DIRECTION_VERTICAL:
			if(nX + com.Texture.getWidth() > Width) {
				nX = mX;
				nY += maxLength;// Add With padding
				maxLength = 0;
			}
			if(nY + com.Texture.getHeight() <= Y) {
				break;
			}
			if(nY < Y) {
				int availableHeight = nY + com.Texture.getHeight() - Y;
				c.drawBitmap(com.Texture, new Rect(0,com.Texture.getHeight() - availableHeight ,com.Texture.getWidth(),availableHeight) ,new Rect(X + nX, Y, X + nX + com.Texture.getWidth(),Y + availableHeight), null);
			}else if(nY + com.Texture.getHeight() <= Height) {
				if(maxLength < com.Texture.getHeight()) {
					maxLength = com.Texture.getHeight();
				}
				c.drawBitmap(com.Texture, new Rect(0,0,com.Texture.getWidth(),com.Texture.getHeight()), new Rect(X + nX, Y + nY,X + nX + com.Texture.getWidth(),Y + nY + com.Texture.getHeight()), null);
				nX += com.Texture.getWidth(); // Add With padding
			}else {
				int availableHeight = Height - nY + com.Texture.getHeight();
				c.drawBitmap(com.Texture, new Rect(0,0,com.Texture.getWidth(),availableHeight) ,new Rect(X + nX, Y + nY, X + nX + com.Texture.getWidth(),Y + nY + availableHeight), null);
			}
		break;
	}*/
}
