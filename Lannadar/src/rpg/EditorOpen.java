package rpg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class EditorOpen {
	
	static File levelOpen; //‘айл с уровнем дл¤ открыти¤
	
	static Scanner levelScanner;
	
	public void fillMap() {
		int inputLevel = Integer.parseInt(JOptionPane.showInputDialog("¬ведите номер уровн¤"));
		levelOpen = new File("res/levels/" + inputLevel + ".txt");
		try {
			levelScanner = new Scanner(levelOpen);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(levelScanner.hasNext()) {
			for (int i = 0; i <= 11; i++) {
				for (int j = 0; j <= 14; j++) {
					Editor.mapFile[j][i] = levelScanner.nextInt(); 
				}
			}
		}
		
		drawingTile();
	}

	private static void drawingTile() {
		int x = 0, y = 0;
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				if (Editor.mapFile[j][i] == 0) {
					Editor.map[j][i].setIcon(TilesImage.grass);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 1) {
					Editor.map[j][i].setIcon(TilesImage.path);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 2) {
					Editor.map[j][i].setIcon(TilesImage.three);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 3) {
					Editor.map[j][i].setIcon(TilesImage.water);
				    Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 4) {
					Editor.map[j][i].setIcon(TilesImage.mount);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 5) {
					Editor.map[j][i].setIcon(TilesImage.home01);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 6) {
					Editor.map[j][i].setIcon(TilesImage.brickPath);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 7) {
					Editor.map[j][i].setIcon(TilesImage.brickWallP);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 8) {
					Editor.map[j][i].setIcon(TilesImage.brickWall);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 9) {
					Editor.map[j][i].setIcon(TilesImage.brickPathG);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 10) {
					Editor.map[j][i].setIcon(TilesImage.bridge);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 11) {
					Editor.map[j][i].setIcon(TilesImage.three2);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 12) {
					Editor.map[j][i].setIcon(TilesImage.water1);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 13) {
					Editor.map[j][i].setIcon(TilesImage.home02);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 14) {
					Editor.map[j][i].setIcon(TilesImage.home03);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 15) {
					Editor.map[j][i].setIcon(TilesImage.homeFire);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 16) {
					Editor.map[j][i].setIcon(TilesImage.flooring);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 17) {
					Editor.map[j][i].setIcon(TilesImage.homeWall);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 18) {
					Editor.map[j][i].setIcon(TilesImage.homeWallRigth);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 19) {
					Editor.map[j][i].setIcon(TilesImage.flooringRigth);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 20) {
					Editor.map[j][i].setIcon(TilesImage.homeWall);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 21) {
					Editor.map[j][i].setIcon(TilesImage.flooringLeft);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 22) {
					Editor.map[j][i].setIcon(TilesImage.voidI);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 23) {
					Editor.map[j][i].setIcon(TilesImage.window);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 24) {
					Editor.map[j][i].setIcon(TilesImage.grassDark);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 25) {
					Editor.map[j][i].setIcon(TilesImage.threeDark);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 26) {
					Editor.map[j][i].setIcon(TilesImage.threeDark2);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Editor.mapFile[j][i] == 27) {
					Editor.map[j][i].setIcon(TilesImage.grassFire);
					Editor.map[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				x += Game.TILE;
			}
			x = 0;
			y += Game.TILE;
		}
	}
	
}
