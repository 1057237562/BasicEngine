package com.basicengine.util.memory;

class Status {//public
	
	public int useage = 0;
	public Object object;
	public long size = 0;
	public long startTime = 0;
	
	public Status(Object o) {
		object = o;
	}
}
