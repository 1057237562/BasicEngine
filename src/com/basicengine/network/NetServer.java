package com.basicengine.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

public class NetServer {
	//<uses-permission android:name="android.permission.INTERNET"></uses-permission>

	ServerSocket mServerSocket;
	ArrayList<InputStream> inputStreams = new ArrayList<InputStream>();
	ArrayList<OutputStream> outputStreams = new ArrayList<OutputStream>();
	ArrayList<NetworkListener> networkListeners = new ArrayList<NetworkListener>();
	boolean active = true;
	public Thread ServerThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (active) {
				try {
					Socket socket = mServerSocket.accept();
					oj_handler.sendMessage(new Message());
					inputStreams.add(socket.getInputStream());
					outputStreams.add(socket.getOutputStream());
					input.add(new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							InputStream nInputStream = inputStreams.get(inputStreams.size() - 1);
							while (active) {
								try {
									byte[] buffer = new byte[nInputStream.available()];
									int count = nInputStream.read(buffer);
									if (count != 0) {
										Message message = new Message();
										message.obj = new String(buffer);
										handler.sendMessage(message);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}));
					input.get(input.size() - 1).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});

	ArrayList<Thread> input = new ArrayList<Thread>();

	public NetServer() {
		try {
			mServerSocket = new ServerSocket(6556);
			ServerThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NetServer(int port) {
		try {
			mServerSocket = new ServerSocket(port);
			ServerThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			mServerSocket.close();
			active = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			for (NetworkListener ls : networkListeners) {
				ls.onReceived(msg.obj.toString());
			}
		};
	};

	public Handler oj_handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			for (NetworkListener ls : networkListeners) {
				ls.onClientJoin(inputStreams.size() - 1);
			}
		};
	};

	public void addNetWorkListener(NetworkListener ls) {
		networkListeners.add(ls);
	}

	public void sendMessage(String msg) {
		for (OutputStream os : outputStreams) {
			if (msg.length() != 0) {
				try {
					os.write(msg.getBytes());
					os.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		}
	}

	public void sendMessage(String msg, int id) {
		OutputStream os = outputStreams.get(id);
		if (msg.length() != 0) {
			try {
				os.write(msg.getBytes());
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return;
		}
	}
}
