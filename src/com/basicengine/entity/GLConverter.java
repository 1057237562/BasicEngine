package com.basicengine.entity;

import com.basicengine.graphics.opengl.GLGameObject;

public class GLConverter {

	public static GameObject toGameObject(GLGameObject glgameObject) {

		GameObject result = new GameObject(glgameObject.rect.left, glgameObject.rect.top,
		        glgameObject.rect.bottom - glgameObject.rect.top, glgameObject.rect.right - glgameObject.rect.left);
		return result;
	}

}
