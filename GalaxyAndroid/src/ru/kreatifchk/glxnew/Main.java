package ru.kreatifchk.glxnew;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.res.*;

public class Main extends Activity {
	
	AbsoluteLayout al;
	
	static Point[][] pole;
	
	static ArrayList<Empery> emp = new ArrayList<Empery>();
	
	Random r = new Random();
	Thread game = new Thread(new Game());
	
	int widthDisp, heightDisp;
	int sz = 30;
	
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
		pole = new Point[wid][hei];
		
		/*log = new TextView(this);
		al.addView(log, size(50), size(50));
		log.setText("log");*/
		
		asm = this.getAssets();
	}
	
	private void addPole() {
		int x = 0, y = 0;
		
		for (int j = 0; j < pole[0].length; j++) {
			for (int i = 0; i < pole.length; i++) {
				pole[i][j] = new Point(this);
				
				pole[i][j].red = r.nextInt(255);
				pole[i][j].green = r.nextInt(255);
				pole[i][j].blue = r.nextInt(255);
				
				al.addView(pole[i][j], size(sz), size(sz));
				pole[i][j].setX(x);
				pole[i][j].setY(y);
				//pole[i][j].setY(pole[i][j].getX() + 180);
				x += size(sz);
			}
			y += size(sz);
			x = 0;
		}
	}
	
	private void genEmp() {
		int max = r.nextInt(9) + 3;
		max = 1;
		
		for (int i = 0; i < max; i++) {
			emp.add(new Empery(i));
			emp.get(i).id = i;
		}
		
		for (int j = 0; j < pole[0].length; j++) {
			for(int i = 0; i < pole.length; i++) {
				pole[i][j].invalidate();
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
