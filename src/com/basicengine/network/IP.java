package com.basicengine.network;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

public class IP {
	//<uses-permission android:name="android.permission.INTERNET"></uses-permission>
	//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

	public Handler handler;
	Context context;
	public ArrayList<Thread> thread;
	String mip;
	int done = 0;
	int id;
	int each = 0;

	public IP(Context c) {
		context = c;
	}

	public static String getIP(Context c) {
		WifiManager wifiService = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiinfo = wifiService.getConnectionInfo();
		return intToIp(wifiinfo.getIpAddress());
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	public void getAllIps(Handler receive) {
		handler = receive;
		mip = getIP(context);
		done = 0;
		thread = new ArrayList<Thread>();
		System.gc();
		id = 1;
		Thread td = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Runtime runtime = Runtime.getRuntime();
				for (int i = 0; i < 255; i++) {
					thread.add(new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							int threadid = id;
							id++;
							String addressIndex = getIndex(mip) + threadid;
							if (pingIpAddress(addressIndex)) {
								Message msg = new Message();
								msg.obj = addressIndex;
								handler.sendMessage(msg);
							}
							done++;
						}
					}));
					thread.get(thread.size() - 1).start();
					while (runtime.freeMemory() < 10) { // Prevent OOM
						
					}
				}
				Thread killer = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						boolean over = false;
						while (!over) {
							if (done >= 255) {
								thread = new ArrayList<Thread>();
								System.gc();
								over = true;
							}
						}
					}
				});
				killer.start();
			}
		});
		td.start();
		
	}

	private String getIndex(String str) {

		if (!str.equals("")) {
			return str.substring(0, str.lastIndexOf(".") + 1);
		}

		return null;
	}

	public static boolean pingIpAddress(String ipAddress) {
		try {
			Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 1 " + ipAddress);
			int status = process.waitFor();
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
