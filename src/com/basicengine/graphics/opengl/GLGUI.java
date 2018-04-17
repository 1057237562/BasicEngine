package com.basicengine.graphics.opengl;

import java.util.ArrayList;

public class GLGUI extends GLRenderObject {

	public ArrayList<GLGUI> components = new ArrayList<GLGUI>();

	public GLGUI(GLBitmap texture, int X, int Y, int width, int height) {
		super(texture, X, Y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void addComponent(GLGUI component) {
		components.add(component);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		for (GLGUI g : components.toArray(new GLGUI[0])) {
			g.draw();
		}
	}

}
