package ru.kreatifchk.game;

import ru.kreatifchk.editor.Map;
import ru.kreatifchk.tools.Sleep;

public class GameCycle implements Runnable {

	int sl = 40; //Время сна, если цикл выполняется долго то время сна сокращается
	static int count;
	
	@Override
	public void run() {
		while(true) {
			//Подсчет фпс
			count++;
			if (count == 25) {
				count = 0;
				Game.fps = Game.count;
				Game.count = 0;
			}
			
			//Метод update для всех монстров на локации
			//long time = System.nanoTime();
			
			for (Monster i: Map.monsters) {
				i.update();
			}
			
			//Если игрок в режими сражения атаковать раз в секунду монстра
			if (Player.getPlayer().battle & count == 0) {
				Player.getPlayer().battle();
			}
			
			//time = (System.nanoTime() - time) / 1000000;
			//if (time > 5) sl -= time;
			
			//Аналогично для npc
			//Восстановление здоровья для персонажа и маны
			Sleep.sleep(sl); //Сон 40 милисекунд , цикл выполняется 25 раз за секунду
		}
	}
}
