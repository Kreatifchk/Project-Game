package ru.kreatifchk.game;

import ru.kreatifchk.editor.Map;
import ru.kreatifchk.tools.Sleep;

public class GameCycle implements Runnable {

	@Override
	public void run() {
		while(true) {
			//Где-то есть серьезная утечка памяти!!!!! (или нету)
			//Метод update для всех монстров на локации
			for (Monster i: Map.monsters) {
				i.update();
			}
			//Аналогично для npc
			//Восстановление здоровья для персонажа и маны
			Sleep.sleep(20);
		}
	}
}
