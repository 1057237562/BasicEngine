package com.basicengine.graphics.opengl;

import java.util.ArrayList;

public class Grid extends GLGUI {

	public ArrayList<GLRenderObject> objects = new ArrayList<GLRenderObject>();

	public Grid(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		for (int i = objects.size() - 1; i >= 0; i--) {
			objects.get(i).draw();
		}
		super.draw();
	} // Take care of the extends relationship
	  // Reset Original Point of the items

}
