package base;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import menu.Menu;

/** Класс кнопок тайлов в редакторе */
public class EditorTile {
	
	public EditorTile() {
		int x = 10, y = 10;
		for (int i = 0; i < Editor.allTiles.length; i++) {
			if (TileList.tiles[i].tab == Editor.tab) {
				if (TileList.tiles[i].point != null) {
					//Вместо вывода всех тайлов мултитайлового объекта оставляет только один
					Editor.allTiles[i] = new JButton();
					Editor.allTiles[i].addMouseListener(Menu.ed.m);
					Editor.allTiles[i].setBounds(x, y, 48, 48);
					Editor.allTiles[i].setIcon(new ImageIcon(getClass().getResource(TileList.tiles[i].main)));
					Editor.allTiles[i].setToolTipText(TileList.tiles[i].name);
					if (x == 242) {
						x = 10;
						y += 58;
					} else {
						x += 58;
					}
					Editor.inList.add(Editor.allTiles[i]);
					int idKit = TileList.tiles[i].idKit;
					while (true) {
						if (i+1 >= TileList.tiles.length
								|| TileList.tiles[i+1].idKit != idKit) {
							break;
						} else {
							i++;
						}
					}
				} else {
					//Для одиночных тайлов
					Editor.allTiles[i] = new JButton();
					Editor.allTiles[i].addMouseListener(Menu.ed.m);
					Editor.allTiles[i].setBounds(x, y, 48, 48);
					Editor.allTiles[i].setIcon(TileList.tiles[i].ic);
					Editor.allTiles[i].setToolTipText(TileList.tiles[i].name);
					if (x == 242) {
						x = 10;
						y += 58;
					} else {
						x += 58;
					}
					Editor.inList.add(Editor.allTiles[i]);
				}
			}
		}
	}
	
}
