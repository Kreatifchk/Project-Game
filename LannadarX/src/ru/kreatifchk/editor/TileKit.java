package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ru.kreatifchk.game.Tile;
import ru.kreatifchk.game.TilesList;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Resize;

/** Окна с наборами тайлов */
@SuppressWarnings("serial")
public class TileKit extends JLabel {
	
	JScrollPane jsp;
	JLabel forestKit;
	JLabel cityKit;
	JLabel actualKit; //Выбранный набор
	
	JButton tiles[]; //Массив кнопок - тайлов
	public TileKit(int number) {
		setSize((int)(342*Main.INC), (int)(240*Main.INC));
		Center.cnt(this, Editor.centerPanel);
		
		//Инициализация наборов тайлов
		forestKit = new JLabel();
		forestKit.setPreferredSize(new Dimension(getWidth(), (int)(48*10*Main.INC)));
		cityKit = new JLabel();
		cityKit.setPreferredSize(new Dimension(getWidth(), (int)(48*10*Main.INC)));
		
		//Выбор набора
		//Определение количества элементов массива (сколько в наборе тайлов
		if (number == 0) {
			actualKit = forestKit;
			tiles = new JButton[TilesList.forestCount];
		}
		else if (number == 1) {
			actualKit = cityKit;
			tiles = new JButton[TilesList.cityCount];
		}
		
		//Инициализация и расположение на окне кнопок с тайлами
		int x = (int)(30 * Main.INC), y = (int)(10*Main.INC), j = 0; //j - номер элемента массива (не совпадает с массивом тайлов)
		for (int i = 0; i < TilesList.tiles.length; i++) {
			if (TilesList.tiles[i].numberKit == number) {
				final int f = i; //Для передачи в анонимный класс Listener
				
				tiles[j] = new JButton(TilesList.tiles[i].icon);
				tiles[j].setBounds(x, y, Tile.SIZE, Tile.SIZE);
				tiles[j].setToolTipText(TilesList.tiles[i].name);
				tiles[j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent a) {
						Editor.actTile = f;
						Editor.selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[Editor.actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
					}
				});
				
				actualKit.add(tiles[j]);
				
				x += (int)((30+48)*Main.INC);
				if (x > (int)(264*Main.INC)) {
					x = (int)(30 * Main.INC);
					y += (int)((30+48) * Main.INC);
				}
				
				j++;
			}
		}
		
		jsp = new JScrollPane(actualKit);
		jsp.setBounds(0, 0, getWidth(), getHeight());
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		
		Editor.mainPane.add(jsp, new Integer(9));
		Center.cnt(jsp, Editor.mainPane);
	}
	
}
