package ru.kreatifchk.glxnew;

import android.graphics.*;
import android.util.*;
import java.util.*;

public class Game implements Runnable {
	
	Random r = new Random();
	
	@Override
	public void run() {
		Sleep.sleep(500);
		while (true) {
			for (int i = 0; i < Main.emp.size(); i++) {
				new War(i);
				clear(); //Очистка мертвых империй
			}
			Sleep.sleep(150);
		}
	}
	
	private void clear() {
		
	}
	
}
