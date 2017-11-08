package base;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import menu.Menu;

public class QwestEvent {
	
	//Ивенты при получении квеста, id - номер квеста
	protected static void giveEvent(int id) {
		if (id == 0) {
			portalOpen(0);
		}
		if (id == 4) {
			portalOpen(0);
		}
		if (id == 6) {
			Game.monster.add(new Monsters(3, 14, 5, "skelet", 70, 0, 9, 3, 1, 12, "—келет"));
			Game.monster.add(new Monsters(3, 14, 7, "skelet", 70, 0, 9, 3, 1, 12, "—келет"));
			Game.monster.add(new Monsters(3, 14, 9, "skelet", 70, 0, 9, 3, 1, 12, "—келет"));
			for (int i = 1; i <= 3; i++) {
				Game.monster.get(Game.monster.size()-i).setBounds(Game.monster.get(Game.monster.size()-i).x*Game.TILE, Game.monster.get(Game.monster.size()-i).y*Game.TILE+48, Game.TILE, Game.TILE);
				Game.monster.get(Game.monster.size()-i).setIcon(new ImageIcon(Menu.g.getClass().getResource("res/Image/monsters/" + Game.monster.get(Game.monster.size()-i).icon + ".png")));
				Menu.g.addComponent(Game.monster.get(Game.monster.size()-i));
				Game.mapx[Game.monster.get(Game.monster.size()-i).x][Game.monster.get(Game.monster.size()-i).y].busy = true;
			}
		}
		if (id == 9) {
			portalOpen(0);
		}
		if (id == 10) {
			portalOpen(0);
		}
	}
	
	//Ивенты при сдаче квеста
	protected static void passEvent(int id) {
		if (id == 6) {
			portalOpen(0);
		}
	}
	
	private static void portalOpen(int portal) {
		JLabel port = new JLabel();
		port.setIcon(Game.portalI);
		port.setBounds(0, 0, 48, 48);
		Game.portal[portal].dostyp = true;
		Game.mapx[Game.portal[portal].portalX][Game.portal[portal].portalY].add(port);
	}
	
}
