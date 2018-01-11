package base;

import java.io.File;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class EditorOpen {
	
	static File levelOpen; //Файл с уровнем для открытия
	
	static Scanner levelScanner;
	Scanner preScan;
	
	public void fillMap() {
		int inputLevel = Integer.parseInt(JOptionPane.showInputDialog("Введите номер уровн¤"));
		InputStream level = Game.class.getResourceAsStream("res/levels/" + inputLevel + ".txt");
		//levelOpen = new File("res/levels/" + inputLevel + ".txt");
		levelScanner = new Scanner(level);
		preScan = new Scanner(Game.class.getResourceAsStream("res/levels/" + inputLevel + ".txt"));
		
		//Считвает первую строку файла
		String x = null;
		int count = 0; //Кол-во элементв в строке
		while(preScan.hasNextLine()) {
			x = preScan.nextLine();
			break;
		}
		preScan = new Scanner(x);
		//Считает количество элементов в строке
		while(preScan.hasNext()) {
			count++;
			preScan.nextInt();
		}
		if (count == 0) {
			count = Editor.map.length;
		}
		
		//Читает непосредственно все
		try {
		while(levelScanner.hasNext()) {
			for (int i = 0; i < Editor.map[0].length; i++) {
				for (int j = 0; j < count; j++) {
					Editor.mapFile[j][i] = levelScanner.nextInt(); 
				}
			}
		}
		} catch (NoSuchElementException e) {
			
		}
		
		drawingTile();
	}

	private static void drawingTile() {
		int x = 0, y = 0;
		for (int i = 0; i < Editor.map[0].length; i++) {
			for (int j = 0; j < Editor.map.length; j++) {
				Editor.map[j][i].setIcon(TileList.tiles[Editor.mapFile[j][i]].ic);
				Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				Editor.map[j][i].oldIcon = TileList.tiles[Editor.mapFile[j][i]].ic;
				x += Game.TILE;
			}
			x = 0;
			y += Game.TILE;
		}
	}
	
}
