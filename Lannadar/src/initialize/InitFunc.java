package initialize;

import base.Game;
import base.MonsterList;
import base.NPCList;
import base.Portals;
import base.QwestGivePanel;
import inventory.InventList;

public class InitFunc {
	
	public InitFunc() {
		new InventList();
		activeQwests();
		Portals.portals();
		new MonsterList(); //Массив с монстрами
		new NPCList(); //Массив с NPC
		//Инициализирует изображения букв
		QwestGivePanel qInit = new QwestGivePanel();
		qInit.init();
		qInit = null;
	}
	
	//Делает массив взятых квестов пустым
	private void activeQwests() {
		for (int i = 0; i <= Game.takeQwests.length - 1; i++) {
			Game.takeQwests[i] = -1;
		}
	}
}
