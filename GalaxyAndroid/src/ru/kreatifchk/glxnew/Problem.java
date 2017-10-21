package ru.kreatifchk.glxnew;

import java.util.*;
import org.xml.sax.helpers.*;

public class Problem {
	
	Empery emp;

	Random r = new Random();
	
	public Problem(int id) {
		emp = Game.emp.get(id);
		counting(); //Подсчет кол-ва территорий
		corruption();
		disapproval(); //Рост недовольства (пока только от коррупции)
		desertion(); //Уменьшение войск в клетках для гигантских империй (пока
		//нету параметра - содержание армии
	}
	
	private void counting() {
		emp.count = 0;
		for (int j = 0; j < Game.pole[0].length; j++) {
			for (int i = 0; i < Game.pole.length; i++) {
				if (Game.pole[i][j].owner == emp.id) {
					emp.count++;
				}
			}
		}
	}
	
	private void corruption() {
		//Сделать затраты денег на борьбу с коррупцией (значительно повысит эффективность)
		//Использование этого от техн. уровня и специализации
		//В малых империя (< 10 клеток) коррупция не растет
		if (emp.count >= 10 & emp.corruption < 800) {
			emp.incCorr = emp.count/10;
			int x = emp.incCorr;
			emp.incCorr = r.nextInt(x/2 + x) + x/2;
			emp.corruption += emp.incCorr;
			//Максимальный уровень коррупции - 800
			if (emp.corruption > 800) {
				emp.corruption = 800;
			}
		}
		//Борьба с коррупцией (пока рандомно, позднее от партий и специализации)
		//Возможно от влияния диплом. очков и союзов
		int chance = r.nextInt(70) + 50; //Шанс борьбы с коррупцией
		int succes = r.nextInt(130) + 40; //На сколько уменьшит коррупцию
		if (chance >= 110) {
			emp.corruption -= succes;
			if (emp.corruption < 0) {
				emp.corruption = 0;
			}
		}
	}
	
	private void disapproval() {
		//Рост недовольства напрямую зависит от коррупции
		//Ввести подавление недовольства или бунтов
		emp.disapproval += emp.corruption / 1.5;
		if (emp.disapproval >= 1000) {
			split(); //Разделение
			//Скидывает уровень недовольства на рандомное число
			int calm = r.nextInt(500) + 200;
			emp.disapproval -= calm;
			if (emp.disapproval < 0) {
				emp.disapproval = 0;
			}
		}
	}
	
	private void split() {
		//Сила бунта зависит от рандома (кол-во отобранных клеток)
		//Кол-во клеток не больше 1/6 общего кол-ва клеток
		int c = 1; //Минимальное кол-во клеток которое взбунтуется
		if (emp.count < 10) {
			c = 2;
		} else if (emp.count < 22) {
			c = 5;
		} else {
			c = 9;
		}
		int count; //Кол-во клеток (в общем) которые отойдут в другую империю
		if (emp.count < 6) {
			count = 2;
		} else {
			count = r.nextInt(emp.count/6) +  c;
		}
		boolean stop = false; //Не даст создать столицу несколько раз

		//Непосредственное разделение
		Empery empNew = null;
		int x = 0, y = 0; //Будущие координаты столицы
		for (int v = 0; v < count; v++) {
			//Рандомно определяет первую клетку
			if (stop != true) {
				while (true) {
					//Избавление от бага
					if (emp.count <= 0) {
						break;
					}
					x = r.nextInt(Game.pole.length);
					y = r.nextInt(Game.pole[0].length);
					if (Game.pole[x][y].owner == emp.id) {
						Game.emp.add(new Empery(Game.emp.size()));
						empNew = Game.emp.get(Game.emp.size()-1);
						empNew.capital = new Pix(x, y);
						empNew.immort = true;
						Game.pole[x][y].col = empNew.cl;
						//Game.pole[x][y].setBackgroundColor(empNew.cl);
						Game.pole[x][y].owner = empNew.id;
						Game.pole[x][y].capital = true;
						Game.pole[x][y].postInvalidate();
						stop = true;
						break;
					}
				}
			}
			//Далее добавляем клетки
			//В начале идет рандомное определение в какую сторону идти
			int dir = r.nextInt(4);
			if (dir == 0) {
				if (x-1 >= 0 && Game.pole[x-1][y].owner == emp.id) {
					check(x-1, y, empNew.id);
					//Проверяет будет ли куда двигаться на след. клетке
				} else {
					dir = 1;
				}
			}
			if (dir == 1) {
				if (x+1 < Game.pole.length && Game.pole[x+1][y].owner == emp.id) {
					check(x+1, y, empNew.id);
				} else {
					dir = 2;
				}
			}
			if (dir == 2) {
				if (y-1 >= 0 && Game.pole[x][y-1].owner == emp.id) {
					check(x, y-1, empNew.id);
				} else {
					dir = 3;
				}
			}
			if (dir == 3) {
				if (y+1 < Game.pole[0].length && Game.pole[x][y+1].owner == emp.id) {
					check(x, y+1, empNew.id);
				} else {
					dir = 0;
				}
			}
		}
	}
	
	private void check(int x, int y, int newId) {
		int sc = 0;
		if (x-1 >= 0 && Game.pole[x-1][y].owner == emp.id) {
			sc++;
		}
		if (x+1 < Game.pole.length && Game.pole[x+1][y].owner == emp.id) {
			sc++;
		}
		if (y-1 >= 0 && Game.pole[x][y-1].owner == emp.id) {
			sc++;
		}
		if (y+1 < Game.pole[0].length && Game.pole[x][y+1].owner == emp.id) {
			sc++;
		}

		if (sc >= 2) {
			Game.pole[x][y].owner = newId;
			Game.pole[x][y].col = Game.emp.get(newId).cl;
			Game.pole[x][y].postInvalidate();
			//Game.pole[x][y].setBackgroundColor(Game.emp.get(newId).cl);
		}
	}
	
	private void desertion() {
		
	}
	
}
