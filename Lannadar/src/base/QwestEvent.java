package base;

import javax.swing.ImageIcon;
import menu.Menu;

public class QwestEvent {
	
	//Ивенты при получении квеста, id - номер квеста
	protected static void giveEvent(int id) {
		if (id == 0) {
			Portal.open(0);
		}
		if (id == 4) {
			Portal.open(1);
		}
		if (id == 7) {
			Game.monster.add(new Monsters(3, 3, 14, 5, "skelet", 70, 0, 9, 3, 12, "Скелет"));
			Game.monster.add(new Monsters(3, 3, 14, 7, "skelet", 70, 0, 9, 3, 12, "Скелет"));
			Game.monster.add(new Monsters(3, 3, 14, 9, "skelet", 70, 0, 9, 3, 12, "Скелет"));
			for (int i = 1; i <= 3; i++) {
				Game.monster.get(Game.monster.size()-i).setBounds(Game.monster.get(Game.monster.size()-i).x*Game.TILE, Game.monster.get(Game.monster.size()-i).y*Game.TILE+48, Game.TILE, Game.TILE);
				Game.monster.get(Game.monster.size()-i).setIcon(new ImageIcon(Menu.g.getClass().getResource("res/Image/monsters/" + Game.monster.get(Game.monster.size()-i).icon + ".png")));
				Game.mainPane.add(Game.monster.get(Game.monster.size()-i), new Integer(3));
				Game.mapx[Game.monster.get(Game.monster.size()-i).x][Game.monster.get(Game.monster.size()-i).y].busy = true;
			}
		}
		if (id == 8) {
			Portal.open(2);
		}
		if (id == 10) {
			Portal.open(3);
		}
		if (id == 11) {
			Portal.open(4);
		}
	}
	
	//Ивенты при сдаче квеста
	protected static void passEvent(int id) {
	}
	
}
