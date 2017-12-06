package base;

import javax.swing.JButton;

import menu.Menu;

public class EditorTile {
	
	public EditorTile() {
		int x = 10, y = 10;
		for (int i = 0; i < Editor.allTiles.length; i++) {
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
