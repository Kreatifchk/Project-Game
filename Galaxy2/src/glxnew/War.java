package glxnew;

import java.util.Random;

public class War {
	
	//git
	
	/** id империи рассматриваемой в текущий момент */
	int id;
	
	Random r = new Random();
	
	//Сделать чтоб в разных режимах клетка могла атаковать за ход одну клетку или
	//несколько
	
	//Сделать чтоб в разных гос-вах деньги могли сразу на армию уходить, а где-то
	//сохранятся (или тратиться на другие цели) (специализации)
	
	public War(int id) {
		this.id = id;
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				if (Game.pole[i][j].owner == id & Game.pole[i][j].newP != true) {
					hiring(i, j, id); //Найм войск
					capture(i, j, id); //непосредственный захват территорий
				}
			}
		}
	}
	
	/** Найм армии, z - номер империи */
	private void hiring(int i, int j, int id) {
		//На данный момент нанимает армию только в приграничных территориях и столице
		if (Game.pole[i][j].border == true || Game.pole[i][j].capital == true) {
			if (Game.pole[i][j].army + Game.pole[i][j].money < 2000) {
				//Лимит войск не превышен
				Game.pole[i][j].army += Game.pole[i][j].money;
				Game.pole[i][j].money = 0;
			} else {
				int v = 2000 - Game.pole[i][j].army;
				Game.pole[i][j].army += v;
				Game.pole[i][j].money -= v;
			}
		}
	}
	
	private void capture(int i, int j, int id) {
		//Пока захват только одной клетки  за ход
		//Пока захват осуществляется кол-вом войск, позже исправить
		//Сделать очки дипломатии, от которых будет влиять на кого чаще будут нападать
		int direction = r.nextInt(4);
		boolean end = false;
		if (direction == 0) {
			if (i-1 >= 0 && Game.pole[i-1][j].owner != id) {
				attack(i, j, i-1, j, id);
				end = true;
			} else {
				direction = 1;
			}
		}
		if (direction == 1 & end != true) {
			if (i+1 < Game.pole.length && Game.pole[i+1][j].owner != id) {
				attack(i, j, i+1, j, id);
				end = true;
			} else {
				direction = 2;
			}
		}
		if (direction == 2 & end != true) {
			if (j-1 >= 0 && Game.pole[i][j-1].owner != id) {
				attack(i, j, i, j-1, id);
				end = true;
			} else {
				direction = 3;
			}
		}
		if (direction == 3 & end != true) {
			if (j+1 < Game.pole[0].length && Game.pole[i][j+1].owner != id) {
				attack(i, j, i, j+1, id);
				end = true;
			} else {
				direction = 0;
			}
		}
	}
	
	//Непосредственно атака, i, j - атакующая клетка, i2, j2 - атакуемая клетка
	private void attack(int i, int j, int i2, int j2, int id) {
		boolean im = false;
		if (Game.pole[i2][j2].owner != -1) {
			if (Game.emp.get(Game.pole[i2][j2].owner).immort == true) {
				im = true;
			}
		}
		//Если нет бессмертия то атака
		if (im != true) {
			if (Game.pole[i][j].army > Game.pole[i2][j2].army) {
				/* Если кол-во войск у атакующих больше то уменьшить кол-во
				 * войск атакующих на кол-во войск защитников, сделать в
				 * завоеванной клетке кол-во оставшихся войск атакующих и
				 * с атакующей клетки убрать войска
				 */
				Game.pole[i][j].army -= Game.pole[i2][j2].army;
				Game.pole[i2][j2].army = Game.pole[i][j].army;
				Game.pole[i][j].army = 0;
				//Меняем владельца
				Game.pole[i2][j2].owner = id;
				Game.pole[i2][j2].newP = true;
				Game.pole[i2][j2].setBackground(Game.emp.get(id).cl);
			} else {
				//Если меньше то захвата нет и армию захватчик теряет
				Game.pole[i2][j2].army -= Game.pole[i][j].army;
				Game.pole[i][j].army = 0;
			}
		}
	}
	
}
