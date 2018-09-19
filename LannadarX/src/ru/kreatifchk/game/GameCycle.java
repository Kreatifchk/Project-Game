package ru.kreatifchk.game;

import ru.kreatifchk.tools.Sleep;

public class GameCycle implements Runnable {

	@Override
	public void run() {
		while(true) {
			//Метод update для всех монстров (или монстров на локации)
			//Аналогично для npc
			//Восстановление здоровья для персонажа и маны
			Sleep.sleep(50);
		}
	}
}
