package com.basicengine.graphics.component;

import android.graphics.*;
import android.view.*;
import android.view.View.*;
import com.basicengine.graphics.*;

public class ImageButtonC extends Component {
	public Bitmap n;
	public Bitmap ho;
	public Bitmap c;
	public OnClickListener cl = new OnClickListener() {

		@Override
		public void onClick(View p1) {
			// TODO: Implement this method
		}
	};

	public ImageButtonC(int x, int y, int h, int w, Bitmap normal, Bitmap hover, Bitmap clicked) {
		super(x, y, BitmapModifier.modify(normal, h, w));
		n = BitmapModifier.modify(normal, h, w);
		ho = BitmapModifier.modify(hover, h, w);
		c = BitmapModifier.modify(clicked, h, w);
	}

	@Override
	public void onTouch(float X, float Y, int action) {
		// TODO: Implement this method
		super.onTouch(X, Y, action);
		switch (action) {
			case MotionEvent.ACTION_MOVE:
				setTexture(ho);
			break;
			case MotionEvent.ACTION_DOWN:
				setTexture(c);
			break;
			case MotionEvent.ACTION_UP:
				setTexture(n);
				cl.onClick(null);
			break;
		}
	}

	public void setOnClickListener(OnClickListener c) {
		cl = c;
	}
}
