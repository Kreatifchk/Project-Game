package base;

import javax.swing.JButton;

import initialize.TilesImage;
import menu.Menu;

public class EditorTile {
	
	public EditorTile() {
		int x = 10, y = 10;
		for (int i = 0; i < Editor.allTiles.length; i++) {
			Editor.allTiles[i] = new JButton();
			Editor.allTiles[i].addMouseListener(Menu.ed.m);
			Editor.allTiles[i].setBounds(x, y, 48, 48);
			Editor.allTiles[i].setIcon(TilesImage.allImage[i]);
			if (x == 242) {
				x = 10;
				y += 58;
			} else {
				x += 58;
			}
			Editor.inList.add(Editor.allTiles[i]);
		}
		
		initTooltip();
	}
	
	private void initTooltip() {
		Editor.allTiles[0].setToolTipText("Городская дорога");
		Editor.allTiles[1].setToolTipText("Дорожка");
		Editor.allTiles[2].setToolTipText("Дерево");
		Editor.allTiles[3].setToolTipText("Вода");
		Editor.allTiles[4].setToolTipText("Горы");
		Editor.allTiles[5].setToolTipText("Дом 01");
		Editor.allTiles[6].setToolTipText("Кирпичная дорога");
		Editor.allTiles[7].setToolTipText("Стена");
		Editor.allTiles[8].setToolTipText("Стена");
		Editor.allTiles[9].setToolTipText("Кирпичная дорога");
		Editor.allTiles[10].setToolTipText("Мост");
		Editor.allTiles[11].setToolTipText("Береза");
		Editor.allTiles[12].setToolTipText("Вода");
		Editor.allTiles[13].setToolTipText("Дом 02");
		Editor.allTiles[14].setToolTipText("Дом 03");
		Editor.allTiles[15].setToolTipText("Горящий дом");
		Editor.allTiles[16].setToolTipText("Пол в доме");
		Editor.allTiles[17].setToolTipText("Стена дома");
		Editor.allTiles[18].setToolTipText("Стена дома");
		Editor.allTiles[19].setToolTipText("Пол в доме");
		Editor.allTiles[20].setToolTipText("Стена дома");
		Editor.allTiles[21].setToolTipText("Пол в доме");
		Editor.allTiles[22].setToolTipText("Пустота");
		Editor.allTiles[23].setToolTipText("Окно");
		Editor.allTiles[24].setToolTipText("Ночная трава");
		Editor.allTiles[25].setToolTipText("Ночное дерево");
		Editor.allTiles[26].setToolTipText("Ночная береза");
		Editor.allTiles[27].setToolTipText("Горящая трава");
		Editor.allTiles[28].setToolTipText("Колодец");
		Editor.allTiles[29].setToolTipText("Городская дорога");
	}
	
}
