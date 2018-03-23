package com.basicengine.network;

public interface NetworkListener {
	public void onReceived(String msg);
	public void onClientJoin(int id);
}
