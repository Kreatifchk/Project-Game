package ru.kreatifchk.glxnew;

import android.util.*;
import java.util.*;
import android.os.*;
import java.io.*;

public class Game implements Runnable {
	
	Random r = new Random();
	
	@Override
	public void run() {
		while (true) {
			move(); //Движение империй
			clear(); //Очистка мертвых империй
			Sleep.sleep(250);
		}
	}
	
	private void move() {
		for (int z = 0; z < Main.emp.size(); z++) {
			for (int j = 0; j < Main.pole[0].length; j++) {
				for (int i = 0; i < Main.pole.length; i++) {
					if (Main.pole[i][j].owner == Main.emp.get(z).id) {
						attack(i, j, z);
					}
				}
			}
		}
	}
	
	private void attack(int i, int j, int emp) {
		//i - координата по горизонтали
		//j - координата по вертикали
		//int dir = r.nextInt(2);
		int dir = 0;
		if (dir == 0) {
			//Влево
			if (i-1 >= 0) {
				seizure(i-1, j, emp);
			} else {
				dir = 1;
			}
		}
		//Вправо
		if (dir == 1) {
			if (i++ <= Main.pole[0].length) {
				seizure(i++, j, emp);
			} else {
				dir = 2;
			}
		}
		//Вверх
		if (dir == 2) {
			if (j-- >= 0) {
				seizure(i, j--, emp);
			} else {
				dir = 3;
			}
		}
		//Вниз
		if (dir == 3) {
			if (j++ <= Main.pole.length) {
				seizure(i, j++, emp);
			} else {
				dir = 0;
			}
		}
	}
	
	int xx, yy, empp;
	private void seizure(int x, int y, int emp) {
		xx = x;
		yy = y;
		empp = emp;
		
		//if (Main.pole[x][y].owner != Main.emp.get(emp).id) {
			//Захватывать только если не твоя зона
			int oldEmp = Main.pole[x][y].owner;
			if (oldEmp >= 0) {
				//Если владелец у клетки есть
				Main.emp.get(oldEmp).stars--;
				//Уменьшить количество клеток у него
			}
			Main.pole[x][y].owner = Main.emp.get(emp).id;
			Main.emp.get(emp).stars++;
			Main.pole[x][y].post(new Runnable() {
				public void run() {
					Main.pole[xx][yy].col = Main.emp.get(empp).cl;
					Main.pole[xx][yy].invalidate();
				}
			 });
		//}
	}
	
	private void clear() {
		
	}
	
}
