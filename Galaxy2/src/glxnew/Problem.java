package glxnew;

import java.awt.Point;
import java.util.Random;

public class Problem {
	
	Empery emp;
	
	Random r = new Random();
	
	//Сделать зависимость роста коррупции не только от рандома, но и от правящей партии
	//Сделать динамичные партии (доверие людей к ним может меняться и.т.д)
	//Сделать чтоб коррупция влияла на деньги
	//Сделать содержание армии (в случае нехватки денег на нее уменьшать армию)
	//возможно опционально
	
	public Problem(int id) {
		emp = Game.emp.get(id);
		counting(); //Подсчет кол-ва территорий
		corruption();
		//if (Game.xs != true) {
			//Временно отключены восстания кроме первого
			//У восставших империй не хватает денег и армии для защиты
		disapproval(); //Рост недовольства (пока только от коррупции)
		//}
		desertion(); //Уменьшение войск в клетках для гигантских империй (пока
		//нету параметра - содержание армии
	}
	
	private void counting() {
		emp.count = 0;
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
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
			//System.out.println("В империи: " + emp.id + " сброшена коррупция на: " + succes + ". Осталось: " + emp.corruption);
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
		Game.xs = true;
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
		int count; //Кол-во клеток которые отойдут в другую империю
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
					x = r.nextInt(Game.pole.length);
					y = r.nextInt(Game.pole[0].length);
					if (Game.pole[x][y].owner == emp.id) {
						Game.emp.add(new Empery(Game.emp.size(), Game.createColor()));
						empNew = Game.emp.get(Game.emp.size()-1);
						empNew.capital = new Point(x, y);
						empNew.immort = true;
						Game.pole[x][y].setBackground(empNew.cl);
						Game.pole[x][y].owner = empNew.id;
						Game.pole[x][y].capital = true;
						Game.pole[x][y].immort = true; //выдача бессмертия на 1 ход
						//чтоб дать им шанс
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
			Game.pole[x][y].setBackground(Game.emp.get(newId).cl);
		}
	}
	
	private void desertion() {
		if (emp.count > 500) {
			for (int i = 0; i < Game.pole.length; i++) {
				for (int j = 0; j < Game.pole[0].length; j++) {
					int des = r.nextInt(1000) + 400;
					Game.pole[i][j].army -= des;
				}
			}
		}
	}
	
}
