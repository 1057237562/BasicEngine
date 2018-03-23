package com.basicengine.graphics;

import android.view.*;
import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.*;
import android.graphics.Bitmap.Config;

import java.util.*;
import android.util.*;
import android.os.*;
import com.basicengine.entity.*;

public class GameFrame extends View {
	public Bitmap MapBackground;
	public int VisionX = 0;
	public int VisionY = 0;

	public ArrayList<ArrayList<RenderingObject>> Layout = new ArrayList<ArrayList<RenderingObject>>();
	public ArrayList<GUI> gui = new ArrayList<GUI>();
	public ArrayList<Handler> update = new ArrayList<Handler>();

	public int updateSpeed = 12;

	public float scalex = 1f;
	public float scaley = 1f;
	public boolean debug = false;
	public Paint debug_paint = new Paint();
	boolean blocking = false;

	long lastUpdate = System.currentTimeMillis();
	boolean useDrawThread = false;
	Thread graphicThread;
	Bitmap vision;
	boolean DrawingQueue = false;

	public void setUseDrawThread(boolean useDrawThread) {
		this.useDrawThread = useDrawThread;
		if (useDrawThread) {
			if (graphicThread == null) {
				graphicThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (true) {
							if (DrawingQueue) {
								Picture picture = new Picture();
								Canvas clip = picture.beginRecording(getMeasuredWidth(), getMeasuredHeight());

								//Drawing Component
								if (MapBackground != null) {
									clip.drawBitmap(MapBackground, -VisionX, -VisionY, null);
								}

								for (int l = 0; l < Layout.size(); l++) {
									ArrayList<RenderingObject> RenderObject = Layout.get(l);
									for (int i = 0; i < RenderObject.size(); i++) {
										RenderingObject r = RenderObject.get(i);
										if (r.Texture != null) {
											clip.drawBitmap(r.Texture, r.X - VisionX, r.Y - VisionY, null);
										}
										r.draw(clip);
										r = null;
									}
									RenderObject = null;
								}

								//GUI
								for (int g = 0; g < gui.size(); g++) {
									GUI gi = gui.get(g);
									if (gi.Visible) {
										if (gi.Texture != null) {
											clip.drawBitmap(gi.Texture, gi.X, gi.Y, null);
										}
										gi.draw(clip);
									}
									gi = null;
								}

								//Debug
								if (debug) {
									String text = getMemory() + "";
									clip.drawText(text, 0, getMeasuredHeight(), debug_paint);
									text = null;
								}
								//Ending

								picture.endRecording();
								Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
								        Config.ARGB_8888);
								Canvas canvas = new Canvas(bitmap);
								picture.draw(canvas);
								picture = null;
								System.gc();
								vision = bitmap;
								vi.sendMessage(new Message());
							}
						}
					}
				});
				graphicThread.start();
			}
		}
	}

	public Thread td = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO: Implement this method
			while (true) {
				if (System.currentTimeMillis() - lastUpdate >= updateSpeed) {
					for (Handler h : update.toArray(new Handler[0])) {
						try {
							h.sendMessage(new Message());
						} catch (Exception e) {
							// TODO: handle exception
							System.gc();
						}
					}
					vi.sendMessage(new Message());
					lastUpdate = System.currentTimeMillis();
				}
			}
		}
	});

	public Handler vi = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO: Implement this method
			super.handleMessage(msg);
			invalidate();
		}
	};

	public GameFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		Initiation();
	}

	public GameFrame(Context context) {
		super(context);
		Initiation();
	}

	public void Initiation() {
		//Hardware Accelerated
		setLayerType(View.LAYER_TYPE_HARDWARE, null);

		addLayout();
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View p1, MotionEvent p2) {
				// TODO: Implement this method
				try {
					for (int g = gui.size() - 1; g >= 0; g--) {
						if (gui.get(g).X <= p2.getX() && gui.get(g).Y <= p2.getY()
						        && gui.get(g).X + gui.get(g).Width >= p2.getX()
						        && gui.get(g).Y + gui.get(g).Height >= p2.getY() || gui.get(g).active) {
							gui.get(g).onTouch(p2.getX(), p2.getY(), p2.getAction());
							if (p2.getAction() == MotionEvent.ACTION_UP) {
								gui.get(g).onClick(p2.getX(), p2.getY());
							}
							if (!gui.get(g).block) {
								break;
							}
							invalidate();
							return true;
						}
					}
					//Log.e("basicengine",p2.getAction()+"");

					//Object
					for (int l = Layout.size() - 1; l >= 0; l--) {
						ArrayList<RenderingObject> RenderObject = Layout.get(l);
						for (int i = RenderObject.size() - 1; i >= 0; i--) {
							RenderingObject r = RenderObject.get(i);
							if (p2.getX() >= r.X - VisionX && p2.getY() >= r.Y - VisionY
							        && p2.getX() <= r.X - VisionX + r.Width && p2.getY() <= r.Y - VisionY + r.Height) {
								if (blocking && !r.active) {
									continue;
								}
								r.onTouch(p2.getX(), p2.getY(), p2.getAction());
								if (p2.getAction() == MotionEvent.ACTION_UP) {
									r.onClick();
								}
								if (r.active) {
									blocking = true;
								} else {
									blocking = false;
								}
							}

						}
					}
					invalidate();
				} catch (Exception e) {
					// TODO: handle exception
				}
				return true;
			}

		});

		td.start();

		debug_paint.setTextSize(42f);
		debug_paint.setColor(Color.WHITE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO: Implement this method
		super.onDraw(canvas);

		canvas.scale(scalex, scaley);

		if (useDrawThread) {
			if(!DrawingQueue) {
				DrawingQueue = true;
			}else {
				canvas.drawBitmap(vision, 0, 0, null);
				DrawingQueue = false;
			}
			return;
		}

		if (MapBackground != null) {
			canvas.drawBitmap(MapBackground, -VisionX, -VisionY, null);
		}

		for (int l = 0; l < Layout.size(); l++) {
			ArrayList<RenderingObject> RenderObject = Layout.get(l);
			for (int i = 0; i < RenderObject.size(); i++) {
				RenderingObject r = RenderObject.get(i);
				if (r.Texture != null) {
					canvas.drawBitmap(r.Texture, r.X - VisionX, r.Y - VisionY, null);
				}
				r.draw(canvas);
				r = null;
			}
			RenderObject = null;
		}

		//GUI
		for (int g = 0; g < gui.size(); g++) {
			GUI gi = gui.get(g);
			if (gi.Visible) {
				if (gi.Texture != null) {
					canvas.drawBitmap(gi.Texture, gi.X, gi.Y, null);
				}
				gi.draw(canvas);
			}
			gi = null;
		}

		//Debug
		if (debug) {
			String text = getMemory() + "";
			canvas.drawText(text, 0, getMeasuredHeight(), debug_paint);
			text = null;
		}
		System.gc();
	}

	@SuppressLint("NewApi")
	public int getMemory() {
		Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
		Debug.getMemoryInfo(memoryInfo);
		int totalPrivateDirty = memoryInfo.getTotalPrivateDirty();
		int totalPss = memoryInfo.getTotalPss();
		int totalSharedDirty = memoryInfo.getTotalSharedDirty();

		int total = totalPrivateDirty + totalPss + totalSharedDirty;
		return total;
	}

	public void setBackground(Bitmap bm) {
		MapBackground = bm;
		invalidate();
	}

	public void setAccelerate(boolean accelerate) {
		if (accelerate) {
			setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	public void addObject(RenderingObject r, int layout) {
		Layout.get(layout).add(r);
		r.layout = layout;
		invalidate();
	}

	public void removeObject(RenderingObject r) {
		Layout.get(r.layout).add(r);
		invalidate();
	}

	public void addGameObject(GameObject g, int layout) {
		Layout.get(layout).add(g);
		g.layout = layout;
		g.parent = this;
		invalidate();
		registerUpdate(g.u);
	}

	public void removeGameObject(GameObject g) {
		Layout.get(g.layout).remove(g);
		invalidate();
		unregisterUpdate(g.u);
	}

	public void addGUI(GUI g) {
		gui.add(g);
		invalidate();
	}

	public void removeGUI(GUI g) {
		gui.remove(g);
		invalidate();
	}

	public int addLayout() {
		Layout.add(new ArrayList<RenderingObject>());
		return Layout.size() - 1;
	}

	public void addLayout(int index) {
		Layout.remove(index);
	}

	public void setUpdateSpeed(int fps) {
		updateSpeed = 1000 / fps;
	}

	public void registerUpdate(Handler uh) {
		update.add(uh);
	}

	public void unregisterUpdate(Handler uh) {
		update.remove(uh);
	}

	public void recreate() {
		Layout = new ArrayList<ArrayList<RenderingObject>>();
		gui = new ArrayList<GUI>();
		update = new ArrayList<Handler>();
		MapBackground = null;
		addLayout();
	}

}
