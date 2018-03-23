package com.basicengine.util;

public class MathExtension
{
	public static double getDistance(float X,float Y,float TargetX,float TargetY){
		return Math.sqrt(Math.pow(X-TargetX,2)+Math.pow(Y-TargetY,2));
	}
}
