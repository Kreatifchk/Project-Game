package ru.kreatifchk.glxnew;

import android.content.*;
import android.graphics.*;
import android.view.View.*;
import android.widget.*;
import android.view.*;

public class Point extends ImageView implements OnClickListener {
	
	int red, green, blue;
	
	Paint p;
	
	Color cl;
	int col;
	
	private int owner = -1;
	
	public Point(Context cont) {
		super(cont);
		
		p = new Paint();
		
		cl = new Color();
		col = cl.argb(255, 255, 255, 255);
		setBackgroundColor(col);
		
		setOnClickListener(this);
	}
	
	protected void setOwner(int owner) {
		this.owner = owner;
	}
	
	protected int getOwner() {
		return owner;
	}

	@Override
	protected void onDraw(Canvas can) {
		//super.onDraw(can);
		can.drawColor(col);
		p.setColor(Color.BLACK);
		p.setStrokeWidth(5);
		can.drawLine(0, 0, getWidth(), 0, p);
		can.drawLine(0, 0, 0, getHeight(), p);
		can.drawLine(getWidth(), 0, getWidth(), getHeight(), p);
		can.drawLine(0, getHeight(), 0, getWidth(), p);
	}
	
	@Override
	public void onClick(View p) {
		Toast.makeText(Main.cont, col + " " + owner, 1).show();
	}
	
}
