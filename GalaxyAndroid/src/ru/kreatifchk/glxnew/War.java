package ru.kreatifchk.glxnew;

import android.util.*;
import java.util.*;

public class War {
	
	Empery emp;
	
	Random r = new Random();
	
	public War(int id) {
		emp = Main.emp.get(id);
		move(id); //Движение империй
	}
	
	private void move(int id) {
		for (int j = 0; j < Main.pole[0].length; j++) {
			for (int i = 0; i < Main.pole.length; i++) {
				//i - ширина
				//j - высота
				if (Main.pole[i][j].getOwner() == id) {
					attack(i, j, id);
				}
			}
		}
	}
	
	private void attack(int i, int j, int emp) {
		//i - координата по горизонтали
		//j - координата по вертикали
		int dir = r.nextInt(4);
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
			if (i+1 < Main.pole.length) {
				seizure(i+1, j, emp);
			} else {
				dir = 2;
			}
		}
		//Вверх
		if (dir == 2) {
			if (j-1 >= 0) {
				seizure(i, j-1, emp);
			} else {
				dir = 3;
			}
		}
		//Вниз
		if (dir == 3) {
			if (j+1 < Main.pole[0].length) {
				seizure(i, j+1, emp);
			} else {
				dir = 0;
			}
		}
	}

	//Вспомогательный метод для захвата
	private void seizure(int x, int y, int emp) {
		Main.pole[x][y].setOwner(emp);
		Main.pole[x][y].col = Main.emp.get(emp).cl;
		Main.pole[x][y].postInvalidate();
	}
	
}
