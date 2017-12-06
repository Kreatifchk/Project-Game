package initialize;

import base.Game;
import base.HashList;
import base.ItemList;
import base.MonsterList;
import base.NPCList;
import base.PortalList;
import base.QwestList;
import inventory.InventList;

public class InitFunc {
	
	public InitFunc() {
		new HashList();
		new InventList();
		new QwestList();
		activeQwests();
		new PortalList();
		new MonsterList(); //Массив с монстрами
		new NPCList(); //Массив с NPC
		new ItemList();
	}
	
	//Делает массив взятых квестов пустым
	private void activeQwests() {
		for (int i = 0; i <= Game.takeQwests.length - 1; i++) {
			Game.takeQwests[i] = -1;
		}
	}
}
