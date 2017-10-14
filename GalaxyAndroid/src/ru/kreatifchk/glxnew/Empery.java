package ru.kreatifchk.glxnew;

import android.graphics.*;
import android.widget.*;
import java.util.*;
import ru.kreatifchk.glxnew.*;

public class Empery {
	
	int id;
	int cl;
	
	int stars = 1; //Количество звезд
	
	Random r;
	
	public Empery(int id) {
		r = new Random();
		
		this.id = id;
		
		cl = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));
		int x = r.nextInt(Main.pole.length);
		int y = r.nextInt(Main.pole[0].length);
		//int x = 4, y = 5;
		Main.pole[x][y].setOwner(id);
		Main.pole[x][y].col = cl;
	}
	
}
