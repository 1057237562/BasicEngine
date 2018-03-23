package com.basicengine.entity.script.physics;
import com.basicengine.entity.*;

public class Rigidbody extends Script
{
	
	@Override
	public void Update()
	{
		// TODO: Implement this method
		gameobject.getComponent(Colliderbox.class.getName());
	}
	
}
