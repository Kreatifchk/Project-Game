package base;

import java.io.File;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import initialize.TilesImage;

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
				if (Editor.mapFile[j][i] == 0) {
					Editor.map[j][i].setIcon(TilesImage.grass);
				}
				if (Editor.mapFile[j][i] == 1) {
					Editor.map[j][i].setIcon(TilesImage.path);
				}
				if (Editor.mapFile[j][i] == 2) {
					Editor.map[j][i].setIcon(TilesImage.three);
				}
				if (Editor.mapFile[j][i] == 3) {
					Editor.map[j][i].setIcon(TilesImage.water);
				}
				if (Editor.mapFile[j][i] == 4) {
					Editor.map[j][i].setIcon(TilesImage.mount);
				}
				if (Editor.mapFile[j][i] == 5) {
					Editor.map[j][i].setIcon(TilesImage.home01);
				}
				if (Editor.mapFile[j][i] == 6) {
					Editor.map[j][i].setIcon(TilesImage.brickPath);
				}
				if (Editor.mapFile[j][i] == 7) {
					Editor.map[j][i].setIcon(TilesImage.brickWallP);
				}
				if (Editor.mapFile[j][i] == 8) {
					Editor.map[j][i].setIcon(TilesImage.brickWall);
				}
				if (Editor.mapFile[j][i] == 9) {
					Editor.map[j][i].setIcon(TilesImage.brickPathG);
				}
				if (Editor.mapFile[j][i] == 10) {
					Editor.map[j][i].setIcon(TilesImage.bridge);
				}
				if (Editor.mapFile[j][i] == 11) {
					Editor.map[j][i].setIcon(TilesImage.three2);
				}
				if (Editor.mapFile[j][i] == 12) {
					Editor.map[j][i].setIcon(TilesImage.water1);
				}
				if (Editor.mapFile[j][i] == 13) {
					Editor.map[j][i].setIcon(TilesImage.home02);
				}
				if (Editor.mapFile[j][i] == 14) {
					Editor.map[j][i].setIcon(TilesImage.home03);
				}
				if (Editor.mapFile[j][i] == 15) {
					Editor.map[j][i].setIcon(TilesImage.homeFire);
				}
				if (Editor.mapFile[j][i] == 16) {
					Editor.map[j][i].setIcon(TilesImage.flooring);
				}
				if (Editor.mapFile[j][i] == 17) {
					Editor.map[j][i].setIcon(TilesImage.homeWall);
				}
				if (Editor.mapFile[j][i] == 18) {
					Editor.map[j][i].setIcon(TilesImage.homeWallRigth);
				}
				if (Editor.mapFile[j][i] == 19) {
					Editor.map[j][i].setIcon(TilesImage.flooringRigth);
				}
				if (Editor.mapFile[j][i] == 20) {
					Editor.map[j][i].setIcon(TilesImage.homeWall);
				}
				if (Editor.mapFile[j][i] == 21) {
					Editor.map[j][i].setIcon(TilesImage.flooringLeft);
				}
				if (Editor.mapFile[j][i] == 22) {
					Editor.map[j][i].setIcon(TilesImage.voidI);
				}
				if (Editor.mapFile[j][i] == 23) {
					Editor.map[j][i].setIcon(TilesImage.window);
				}
				if (Editor.mapFile[j][i] == 24) {
					Editor.map[j][i].setIcon(TilesImage.grassDark);
				}
				if (Editor.mapFile[j][i] == 25) {
					Editor.map[j][i].setIcon(TilesImage.threeDark);
				}
				if (Editor.mapFile[j][i] == 26) {
					Editor.map[j][i].setIcon(TilesImage.threeDark2);
				}
				if (Editor.mapFile[j][i] == 27) {
					Editor.map[j][i].setIcon(TilesImage.grassFire);
				}
				if (Editor.mapFile[j][i] == 28) {
					Editor.map[j][i].setIcon(TilesImage.well);
				}
				if (Editor.mapFile[j][i] == 29) {
					Editor.map[j][i].setIcon(TilesImage.townRoad);
				}
				Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				x += Game.TILE;
			}
			x = 0;
			y += Game.TILE;
		}
	}
	
}
