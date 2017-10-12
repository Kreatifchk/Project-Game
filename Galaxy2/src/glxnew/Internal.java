package glxnew;

public class Internal {
	
	//git
	
	/** id империи рассматриваемой в текущий момент */
	int id;
	
	Empery emp;
	
	public Internal(int id) {
		emp = Game.emp.get(id); 
		this.id = id;
		technology(); //Развитие технологического уровня
		tallage(); //Сбор налогов
		money(); //Распределение бюджета
		
		emp.immort = false; //Отключение бессмертия
	}
	
	private void technology() {
		
	}
	
	private void tallage() {
		//Сбор налогов. С каждой клетки по 100.
		//При большом кол-ве клеток коррупция (сделать)
		emp.money += emp.count * 100;
	}
	
	//Сделать передвижение столицы (не для столичного мода)
	private void money() {
		//Кол-во денег отправляемое в разные регионы будет зависеть
		//от специализации империи. Сейчас 70% в приграничные регионы
		//Если у империи только одна клетка
		if (emp.count == 1) {
			Game.pole[emp.capital.x][emp.capital.y].money += emp.money;
			emp.money = 0;
		} else {
			int countBorder = 0; //Кол-во клеток на границе
			//Подсчитываем кол-во клеток на границе
			for (int i = 0; i < Game.pole.length; i++) {
				for (int j = 0; j < Game.pole[0].length; j++) {
					if (Game.pole[i][j].owner == id) {
						if (i-1 >= 0 && Game.pole[i-1][j].owner != id) {
							Game.pole[i][j].border = true;
							countBorder++;
						}
						else if (i+1 < Game.pole.length && Game.pole[i+1][j].owner != id) {
							Game.pole[i][j].border = true;
							countBorder++;
						}
						else if (j-1 >= 0 && Game.pole[i][j-1].owner != id) {
							Game.pole[i][j].border = true;
							countBorder++;
						}
						else if (j+1 < Game.pole[0].length && Game.pole[i][j+1].owner != id) {
							Game.pole[i][j].border = true;
							countBorder++;
						} else {
							Game.pole[i][j].border = false;
						}
					}
				}
			}
			//Если все территории внутренние, то не выделять деньги
			int moneyCount = 0;
			if (countBorder != 0) {
				int moneyBorder = Persentage.pers(70, emp.money); //Деньги на приграничные территории, 70%
				emp.money -= moneyBorder; //Вычитаем из казны деньги на пригр. территории
				moneyCount = moneyBorder / countBorder; //Кол-во денег на каждую приграничную клетку
			}
			//Если все территории приграничные, то не выделять деньги на мирные территории
			int moneyPeace = 0;
			if (countBorder != emp.count) {
				moneyPeace = emp.money / (emp.count - countBorder); //Кол-во денег на каждую мирную территорию
			}
			emp.money = 0;
			
			//Деньги потерянные от коррупции
			moneyCount -= Persentage.pers(emp.corruption / 10, moneyCount);
			moneyPeace -= Persentage.pers(emp.corruption / 10, moneyPeace);
			//Отправка денег
			for (int i = 0; i < Game.pole.length; i++) {
				for (int j = 0; j < Game.pole[0].length; j++) {
					if (Game.pole[i][j].owner == id) {
						if (Game.pole[i][j].border == true) {
							Game.pole[i][j].money += moneyCount;
						} else {
							Game.pole[i][j].money += moneyPeace;
						}
					}
				}
			}
		}
	}

}
