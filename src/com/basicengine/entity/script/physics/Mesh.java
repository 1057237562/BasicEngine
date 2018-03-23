package com.basicengine.entity.script.physics;
import java.util.*;
import android.graphics.*;

public class Mesh
{
	public ArrayList<Point> collider = new ArrayList<Point>();
	public Bitmap box = null;
	
	public static Mesh generateMesh(Bitmap a,int accuracy){
		Mesh mesh = new Mesh();
		mesh.box = a;
		int acw = a.getWidth()/accuracy;
		int ach = a.getHeight()/accuracy;
		int lastX = -acw;
		int lastY = -ach;

		for(int w = 0;w<a.getWidth();w = w++){
			for(int h = 0;h<a.getHeight();h++){
				int Pixel = a.getPixel(w,h);
				if(Color.BLACK == Pixel){
					if(!isFillingPixel(w,h,a)){
						if(Math.abs(lastX-w)>=acw && Math.abs(lastY-h) >=ach){
							mesh.collider.add(new Point(w,h));
							lastX = w;
							lastY = h;
						}else{
							if(w == lastX && a.getPixel(w,h+1)!=Color.BLACK){
								mesh.collider.add(new Point(w,h));
							}
							if(h == lastY && a.getPixel(w+1,h)!=Color.BLACK){
								mesh.collider.add(new Point(w,h));
							}
						}
					}
				}
			}
		}
		return mesh;
	}

	private static boolean isFillingPixel(int x,int y,Bitmap a){
		return a.getPixel(x-1,y) == Color.BLACK && a.getPixel(x,y-1) == Color.BLACK && a.getPixel(x+1,y) == Color.BLACK && a.getPixel(x,y+1)== Color.BLACK;
	}
}
