package ru.kreatifchk.glxnew;

import android.graphics.*;
import android.util.*;
import java.util.*;

public class Empery {
	
	int id;
	int oldId; //Для удаления
	//Цвет
	int cl;
	
	int count = 1; //Количество звезд
	int money; //Деньги
	int corruption; //Значение коррупции
	int incCorr; //Значение увеличения коррупции
	int disapproval; //Недовольство граждан
	
	boolean immort; //Временное бессмертие
	
	static Pix capital = new Pix();
	
	Random r;
	
	public Empery(int id) {
		r = new Random();
		
		this.id = id;
		
		cl = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));
		int x = r.nextInt(Game.pole.length);
		int y = r.nextInt(Game.pole[0].length);
		Game.pole[x][y].owner = id;
		Game.pole[x][y].col = cl;
		capital.x = x;
		capital.y = y;
		Game.pole[x][y].capital = true;
	}
	
}
