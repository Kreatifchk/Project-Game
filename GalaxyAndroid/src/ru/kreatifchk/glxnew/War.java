package ru.kreatifchk.glxnew;

import android.util.*;
import java.util.*;

public class War {
	
	Empery emp;
	int id;
	
	Random r = new Random();
	
	public War(int id) {
		emp = Game.emp.get(id);
		move(id); //Движение империй
	}
	
	private void move(int id) {
		this.id = id;
		for (int j = 0; j < Game.pole[0].length; j++) {
			for (int i = 0; i < Game.pole.length; i++) {
				//i - ширина
				//j - высота
				if (Game.pole[i][j].owner == id & Game.pole[i][j].newP != true) {
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
			if (i-1 >= 0 && Game.pole[i-1][j].owner != id) {
				seizure(i-1, j, emp);
			} else {
				dir = 1;
			}
		}
		//Вправо
		if (dir == 1) {
			if (i+1 < Game.pole.length && Game.pole[i+1][j].owner != id) {
				seizure(i+1, j, emp);
			} else {
				dir = 2;
			}
		}
		//Вверх
		if (dir == 2) {
			if (j-1 >= 0 && Game.pole[i][j-1].owner != id) {
				seizure(i, j-1, emp);
			} else {
				dir = 3;
			}
		}
		//Вниз
		if (dir == 3) {
			if (j+1 < Game.pole[0].length && Game.pole[i][j+1].owner != id) {
				seizure(i, j+1, emp);
			} else {
				dir = 0;
			}
		}
	}
	
	int x2, y2, emp2;
	//Вспомогательный метод для захвата
	private void seizure(int x, int y, int emp) {
		x2 = x;
		y2 = y;
		emp2 = emp;
		
		/*Game.pole[x][y].post(new Runnable() {
			public void run() {
				Game.pole[x2][y2].owner = emp2;
				Game.pole[x2][y2].newP = true;
				Game.pole[x2][y2].col = Game.emp.get(emp2).cl;
				Game.pole[x2][y2].invalidate();
			}
		});*/
		
		boolean im = false;
		if (Game.pole[x][y].owner != -1) {
			if (Game.emp.get(Game.pole[x][y].owner).immort == true) {
				im = true;
			}
		}
		
		if (im != true) {
			Game.pole[x][y].owner = emp;
			Game.pole[x][y].newP = true;
			Game.pole[x][y].col = Game.emp.get(emp).cl;
			Game.pole[x][y].postInvalidate();
		}
	}
	
}
