package com.basicengine.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

public class NetClient {
	//<uses-permission android:name="android.permission.INTERNET"></uses-permission>

	public String serverIp;
	public int port;
	Thread Connection;
	public InputStream is;
	public OutputStream os;
	boolean active = true;
	ArrayList<NetworkListener> networkListeners = new ArrayList<NetworkListener>();

	public NetClient(String ip) {
		serverIp = ip;
		Connection = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Socket mSocket = new Socket(serverIp, 6556);
					is = mSocket.getInputStream();
					os = mSocket.getOutputStream();
					while(active) {
						byte[] buffer = new byte[is.available()];
						int count = is.read(buffer);
						if(count != 0) {
							Message message = new Message();
							message.obj = new String(buffer);
							handler.sendMessage(message);
						}
					}
					mSocket.close();
					is.close();
					os.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Connection.start();
	}

	public NetClient(String ip, int p) {
		serverIp = ip;
		port = p;
		Connection = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Socket mSocket = new Socket(serverIp, port);
					is = mSocket.getInputStream();
					os = mSocket.getOutputStream();
					
					while(active) {
						byte[] buffer = new byte[1024];
						int count = is.read(buffer);
						if(count != 0) {
							Message message = new Message();
							message.obj = buffer.toString();
							handler.sendMessage(message);
						}
					}
					mSocket.close();
					is.close();
					os.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Connection.start();
	}

	public void sendMessage(String msg){
	    if(msg.length() == 0 || os == null)
	        return;
	    try {
	        os.write(msg.getBytes());
	        os.flush();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void close() {
		active = false;
	}
	
	public void addNetWorkListener(NetworkListener ls) {
		networkListeners.add(ls);
	}
	
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			for(NetworkListener ls:networkListeners) {
				ls.onReceived(msg.obj.toString());
			}
		};
	};
}
