package com.basicengine.graphics.opengl.adapter;

import com.basicengine.graphics.opengl.GLRenderObject;

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
				if (nX + columnWidth > bind.rect.right - bind.rect.left) {
					nY += rowHeight;
					nX = 0;
				}
				
				object.rect.right += nX - object.rect.left;
				object.rect.bottom += nY - object.rect.top;
				
				object.rect.left = nX;
				object.rect.top = nY;
				nX += columnWidth;
			break;

			case DIRECTION_VERTICAL:
				if (nY + rowHeight > bind.rect.bottom - bind.rect.top) {
					nX += columnWidth;
					nY = 0;
				}

				object.rect.right += nX - object.rect.left;
				object.rect.bottom += nY - object.rect.top;

				object.rect.left = nX;
				object.rect.top = nY;
				nY += rowHeight;
			break;
		}

		object.SynchronizePos();
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
					s.rect.top -= rowHeight;
					s.rect.bottom -= rowHeight;
					if (s.rect.top < 0) {
						s.rect.top += bind.rect.height();
						s.rect.bottom += bind.rect.height();// padding
						s.rect.left -= columnWidth; // padding
						s.rect.right -= columnWidth;
					}
				break;

				case DIRECTION_HORIZONTAL:
					s.rect.left -= columnWidth;
					s.rect.right -= columnWidth;
					if (s.rect.left < 0) {
						s.rect.left += bind.rect.width();
						s.rect.right += bind.rect.width(); // padding
						s.rect.top -= rowHeight; // padding
						s.rect.bottom -= rowHeight;
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
