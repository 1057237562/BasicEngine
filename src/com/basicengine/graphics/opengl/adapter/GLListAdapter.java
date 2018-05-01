package com.basicengine.graphics.opengl.adapter;

import com.basicengine.graphics.opengl.GLRenderObject;

import android.graphics.Rect;

public class GLListAdapter extends GLGroupAdapter {

	public int direction = 0;
	int mX = 0;
	int mY = 0;

	int nX = 0;
	int nY = 0;
	public int rowHeight = 50;
	public int columnWidth = 50;

	public static final int DIRECTION_VERTICAL = 0;
	public static final int DIRECTION_HORIZONTAL = 1;

	@Override
	public void addRenderObject(GLRenderObject object) {
		// TODO Auto-generated method stub

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

		object.rect = new Rect();
		super.addRenderObject(object);
	}

	@Override
	public void removeRenderObject(GLRenderObject object) {
		// TODO Auto-generated method stub

		int index = objects.indexOf(object);

		for (int i = index; i < objects.size(); i++) {
			GLRenderObject s = objects.get(i);
			switch (direction) {
				case DIRECTION_VERTICAL:
					s.Y -= rowHeight;
					if (s.Y < 0) {
						s.Y += Height; // padding
						s.X -= columnWidth; // padding
					}
				break;

				case DIRECTION_HORIZONTAL:
					s.X -= columnWidth;
					if (s.X < 0) {
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
		super.removeRenderObject(object);
	}

}
