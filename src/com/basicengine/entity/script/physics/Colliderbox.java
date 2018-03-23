package com.basicengine.entity.script.physics;

import com.basicengine.entity.*;
import android.graphics.*;
import java.util.*;
import com.basicengine.graphics.*;
import com.basicengine.util.*;
import com.basicengine.entity.script.physics.listener.*;

public class Colliderbox extends Script {

	public boolean hasMesh = false;
	public Mesh mesh = new Mesh();
	public boolean collide = false;
	public Colliderbox lc = null;
	public onCollideListener lis = null;

	public static final int EVENT_COLLIDE_IN = 0;
	public static final int EVENT_COLLIDE_MOVE = 1;
	public static final int EVENT_COLLIDE_OUT = 2;

	public boolean crosslayout = false;

	public Colliderbox(GameObject p) {
		gameobject = p;
	}

	public boolean isCollide(int x, int y) {
		//if(x>=gameobject.X && y>=gameobject.Y && x<=gameobject.X+gameobject.Width && y<=gameobject.Y+gameobject.Height){
		if (hasMesh) {
			if (mesh.box.getPixel(x - gameobject.X, y - gameobject.Y) == Color.BLACK) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public void setOnCollideListener(onCollideListener l) {
		lis = l;
	}

	public boolean isOverlapping(Colliderbox target) {
		return ((gameobject.X >= target.gameobject.X && gameobject.X <= target.gameobject.X + target.gameobject.Width)
		        || (gameobject.X + gameobject.Width >= target.gameobject.X
		                && gameobject.X + gameobject.Width <= target.gameobject.X + target.gameobject.Width))
		        && ((gameobject.Y >= target.gameobject.Y
		                && gameobject.Y <= target.gameobject.Y + target.gameobject.Height)
		                || (gameobject.Y + gameobject.Height >= target.gameobject.Y
		                        && gameobject.Y + gameobject.Height <= target.gameobject.Y + target.gameobject.Height));
	}

	public void onCollide(GameObject object, int event) {
		lis.onCollide(object, event);
	}

	@Override
	public void Update() {
		// TODO: Implement this method
		if (collide) {
			onCollide(lc.gameobject, EVENT_COLLIDE_OUT);
			collide = false;
		}

		if (!crosslayout) {
			ArrayList<RenderingObject> gs = gameobject.parent.Layout.get(gameobject.layout);
			for (RenderingObject r : gs.toArray(new RenderingObject[0])) {
				GameObject g = (GameObject) r;
				if (g == this.gameobject) {
					continue;
				}
				try {
					Colliderbox c = (Colliderbox) g.getComponent(Colliderbox.class.getName());
					if (isOverlapping(c)) {
						if (c.hasMesh) {
							Point closest = new Point();
							double cdis = MathExtension.getDistance(mesh.collider.get(0).x, mesh.collider.get(0).y,
							        (c.gameobject.X + c.gameobject.Width) / 2,
							        (c.gameobject.Y + c.gameobject.Height) / 2);
							for (Point p : mesh.collider.toArray(new Point[0])) {
								double dis = MathExtension.getDistance(p.x, p.y,
								        (c.gameobject.X + c.gameobject.Width) / 2,
								        (c.gameobject.Y + c.gameobject.Height) / 2);
								if (dis < cdis) {
									cdis = dis;
									closest = p;
								}
							}
							if (c.isCollide(closest.x, closest.y)) {
								onCollide(c.gameobject, collide ? 1 : 0);
								lc = c;
								collide = true;
							}
						} else {
							onCollide(c.gameobject, collide ? 1 : 0);
							lc = c;
							collide = true;
						}
					}
				} catch (Exception e) {

				}
			}
		} else {
			for (ArrayList<RenderingObject> gs : gameobject.parent.Layout) {
				for (RenderingObject r : gs.toArray(new RenderingObject[0])) {
					GameObject g = (GameObject) r;
					if (g == this.gameobject) {
						continue;
					}
					try {
						Colliderbox c = (Colliderbox) g.getComponent(Colliderbox.class.getName());
						if (isOverlapping(c)) {
							if (c.hasMesh) {
								Point closest = new Point();
								double cdis = MathExtension.getDistance(mesh.collider.get(0).x, mesh.collider.get(0).y,
								        (c.gameobject.X + c.gameobject.Width) / 2,
								        (c.gameobject.Y + c.gameobject.Height) / 2);
								for (Point p : mesh.collider.toArray(new Point[0])) {
									double dis = MathExtension.getDistance(p.x, p.y,
									        (c.gameobject.X + c.gameobject.Width) / 2,
									        (c.gameobject.Y + c.gameobject.Height) / 2);
									if (dis < cdis) {
										cdis = dis;
										closest = p;
									}
								}
								if (c.isCollide(closest.x, closest.y)) {
									onCollide(c.gameobject, collide ? 1 : 0);
									lc = c;
									collide = true;
								}
							} else {
								onCollide(c.gameobject, collide ? 1 : 0);
								lc = c;
								collide = true;
							}
						}
					} catch (Exception e) {

					}
				}
			}
		}
	}
}
