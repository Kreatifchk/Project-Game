package ru.kreatifchk.glxnew;

import android.util.*;
import java.util.*;

public class Game implements Runnable {
	
	static ArrayList<Empery> emp = new ArrayList<Empery>();
	static Point[][] pole;
	
	static boolean pause;
	
	static int speed = 150;
	
	Random r = new Random();
	
	@Override
	public void run() {
		Sleep.sleep(500);
		while (true) {
			if (pause != true) {
				for (int i = 0; i < Game.emp.size(); i++) {
					new Internal(i);
					try {
					new Problem(i);
					} catch (Exception e) {
						Log.e("Galaxy", e.toString());
					}
					new War(i);
					new Clear(i); //Очистка мертвых империй
				}
			}
			Sleep.sleep(speed);
		}
	}
	
}
