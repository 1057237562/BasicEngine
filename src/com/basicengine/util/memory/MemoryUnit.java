package com.basicengine.util.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class MemoryUnit {//public
	
	public Map<String, Status> hardmap = new HashMap<String, Status>();
	public boolean createStorageCache = false;
	
	public long maxHeapSize = Runtime.getRuntime().totalMemory()*3/4;
	
	public int pattern = 0;
	
	public String addCache(Object object) {
		String UUID = System.currentTimeMillis() + "";
		addCache(object,UUID);
		return UUID;
	}
	
	public void addCache(Object object,String UUID) {
		long se = RamUsageEstimator.shallowSizeOf(object);
		long memorymissing = se + Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - maxHeapSize;
		while(memorymissing > 0){
			Entry<String, Status> status = null;
			float maxWeights = 0;
			for (Entry<String, Status> e: hardmap.entrySet()) {
				Status s = e.getValue();
				float userate = (System.currentTimeMillis() - s.startTime)/(s.useage * 100);
				float memoryfit = s.size/memorymissing;
				float w = memoryfit >= 1 ? 3 - memoryfit : memoryfit +  userate > 2 ? 2 : userate;
				if(maxWeights < w) {
					maxWeights = w;
					status = e;
				}
			}
			if(status == null) {
				break;
			}
			if(createStorageCache) {
				createTempFile(status.getValue().object,status.getKey());
			}
			hardmap.remove(status.getKey());
			memorymissing = se + Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - maxHeapSize;
		}
		Status status = new Status(object);
		status.size = se;
		status.startTime = System.currentTimeMillis();
		hardmap.put(UUID, status);
	}
	
	public void createTempFile(Object o,String UUID) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("" + UUID + ".tmp"));
			os.writeObject(o);
			os.flush();
			os.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Object readTempFile(String UUID) {
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("" + UUID + ".tmp"));
			Object o = is.readObject();
			is.close();
			return o;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public Object getFromCache(String UUID) {
		Status s = hardmap.get(UUID);
		if(s != null) {
			return s.object;
		}
		return readTempFile(UUID);
	}
}
