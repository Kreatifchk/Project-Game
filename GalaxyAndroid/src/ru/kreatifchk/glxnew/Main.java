package ru.kreatifchk.glxnew;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

public class Main extends Activity implements OnClickListener {
	
	AbsoluteLayout al;
	static AssetManager asm;
	static Context cont;
	
	Random r = new Random();
	Thread game = new Thread(new Game());
	
	int widthDisp, heightDisp;
	int sz = 24;//24
	
	static TextView speedV;
	static Button speedI, speedD, pause;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		al = new AbsoluteLayout(this);
		cont = this;
		
		
        setContentView(al);
		display();
		
		preInit();
		addPole();
		genEmp();
		
		game.start();
		
		postInit();
    }

	@Override
	protected void onStop() {
		super.onStop();
		Game.pause = true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Game.pause = false;
	}
	
	private void preInit() {
		int wid = widthDisp / size(sz);
		int hei = heightDisp / size(sz) - 2;
		Game.pole = new Point[wid][hei];
		
		//375 клеток
		
		speedV = new TextView(this);
		speedV.setX((widthDisp - speedV.getWidth()) / 2 - size(5));
		speedV.setY(size(590));
		al.addView(speedV, size(35), size(35));
		
		speedI = new Button(this);
		speedI.setX(widthDisp / 2 + size (25));
		speedI.setY(size(584));
		speedI.setText(">>");
		speedI.setOnClickListener(this);
		al.addView(speedI, size(45), size(33));
		
		speedD = new Button(this);
		speedD.setX((widthDisp / 2) - speedV.getWidth() - size (50));
		speedD.setY(size(584));
		speedD.setText("<<");
		speedD.setOnClickListener(this);
		al.addView(speedD, size(45), size(33));
		
		pause = new Button(this);
		pause.setX(size(5));
		pause.setY(size(584));
		pause.setText("||");
		pause.setOnClickListener(this);
		al.addView(pause, size(45), size(33));
		
		asm = this.getAssets();
	}
	
	private void addPole() {
		int x = 0, y = 0;
		for (int j = 0; j < Game.pole[0].length; j++) {
			for (int i = 0; i < Game.pole.length; i++) {
				Game.pole[i][j] = new Point(Main.this);
				
				Game.pole[i][j].red = r.nextInt(255);
				Game.pole[i][j].green = r.nextInt(255);
				Game.pole[i][j].blue = r.nextInt(255);
				
				al.addView(Game.pole[i][j], size(sz), size(sz));
				Game.pole[i][j].setX(x);
				Game.pole[i][j].setY(y);
				
				Game.pole[i][j].setBackgroundColor(Color.argb(255, 255, 255, 255));
				x += size(sz);
			}
			y += size(sz);
			x = 0;
		}
	}
	
	private void genEmp() {
		int max = r.nextInt(9) + 3;
		//max = 1;
		
		for (int i = 0; i < max; i++) {
			Game.emp.add(new Empery(i));
			Game.emp.get(i).id = i;
		}
		
		for (int j = 0; j < Game.pole[0].length; j++) {
			for(int i = 0; i < Game.pole.length; i++) {
				Game.pole[i][j].invalidate(); //заставляет перерисовать объект
			}
		}
	}
	
	private void postInit() {
		speedV.setText(Game.speed + "");
	}
	
	public int size(int px) {
		int size = (int) (((float) px) * getResources().getDisplayMetrics().density + 0.5f);
		return size;
	}
	
	private void display() {
		Display display = getWindowManager().getDefaultDisplay();
		widthDisp = display.getWidth();
		heightDisp = display.getHeight();
	}
	
	public void onClick(View p1) {
		if (p1 == speedI & Game.speed > 50) {
			Game.speed -= 25;
			speedV.setText(Game.speed + "");
		}
		if (p1== speedD & Game.speed < 1500) {
			Game.speed += 25;
			speedV.setText(Game.speed + "");
		}
		if (p1 == pause) {
			if (Game.pause == false) {
				Game.pause = true;
			} else {
				Game.pause = false;
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		System.exit(0);
	}
	
}
