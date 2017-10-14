package ru.kreatifchk.glxnew;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.util.concurrent.*;

public class Main extends Activity {
	
	AbsoluteLayout al;
	
	static Point[][] pole;
	
	static ArrayList<Empery> emp = new ArrayList<Empery>();
	
	Random r = new Random();
	Thread game = new Thread(new Game());
	
	int widthDisp, heightDisp;
	int sz = 24;//24
	
	static Context cont;
	
	static TextView log;
	
	static AssetManager asm;
	
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
    }
	
	private void preInit() {
		int wid = widthDisp / size(sz);
		int hei = heightDisp / size(sz) - 1;
		Toast.makeText(this, "" + hei, 2).show();
		pole = new Point[wid][hei];
		
		//375 клеток
		
		/*log = new TextView(this);
		al.addView(log, size(50), size(50));
		log.setText("log");*/
		
		asm = this.getAssets();
	}
	
	private void addPole() {
		/*new AsyncTask() {

			@Override
			protected Object doInBackground(Object[] p1) {
				int x = 0, y = 0;
				for (int j = 0; j < pole[0].length; j++) {
				pole[0][j] = new Point(Main.this);
				pole[0][j].red = r.nextInt(255);
				pole[0][j].green = r.nextInt(255);
				pole[0][j].blue = r.nextInt(255);
				al.addView(pole[0][j], size(sz), size(sz));
				pole[0][j].setX(0);
				pole[0][j].setY(y);
				y += size(sz);
				//Sleep.sleep(100);
				}
				return null;
			}

			
		}.execute();*/
		int x = 0, y = 0;
		for (int j = 0; j < pole[0].length; j++) {
			for (int i = 0; i < pole.length; i++) {
				pole[i][j] = new Point(Main.this);
				
				pole[i][j].red = r.nextInt(255);
				pole[i][j].green = r.nextInt(255);
				pole[i][j].blue = r.nextInt(255);
				
				al.addView(pole[i][j], size(sz), size(sz));
				pole[i][j].setX(x);
				pole[i][j].setY(y);
				
				pole[i][j].setBackgroundColor(Color.argb(255, 255, 255, 255));
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
			emp.add(new Empery(i));
			emp.get(i).id = i;
		}
		
		for (int j = 0; j < pole[0].length; j++) {
			for(int i = 0; i < pole.length; i++) {
				//pole[i][j].setBackgroundColor(Color.argb(255, 255, 255, 255));
				pole[i][j].invalidate(); //заставляет перерисовать объект
			}
		}
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
	
	@Override
	public void onBackPressed() {
		System.exit(0);
	}
	
}
