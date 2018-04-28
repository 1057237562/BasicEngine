package com.basicengine.graphics.opengl;

public class GLListBox extends Grid {

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

	public GLListBox(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addRenderObject(GLRenderObject object)
	{
		// TODO: Implement this method
		
		
		super.addRenderObject(object);
	}

}
