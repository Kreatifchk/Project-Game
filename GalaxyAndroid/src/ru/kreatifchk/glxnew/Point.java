package ru.kreatifchk.glxnew;

import android.content.*;
import android.widget.*;
import android.graphics.*;

public class Point extends ImageView {
	
	int red, green, blue;
	
	Paint p;
	Rect r;
	
	Color cl;
	int col;
	
	int owner = -1;
	
	public Point(Context cont) {
		super(cont);
		
		p = new Paint();
		r = new Rect();
		
		cl = new Color();
		col = cl.argb(255, 255, 255, 255);
	}

	@Override
	protected void onDraw(Canvas can) {
		//super.onDraw(canvas);
		can.drawColor(col);
		p.setColor(Color.BLACK);
		p.setStrokeWidth(5);
		can.drawLine(0, 0, getWidth(), 0, p);
		can.drawLine(0, 0, 0, getHeight(), p);
		can.drawLine(getWidth(), 0, getWidth(), getHeight(), p);
		can.drawLine(0, getHeight(), 0, getWidth(), p);
	}
	
}
