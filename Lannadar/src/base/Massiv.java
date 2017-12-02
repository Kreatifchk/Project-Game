package base;

import javax.swing.ImageIcon;

import initialize.TilesImage;

public class Massiv {
	
	static ImageIcon[][] animIcon = new ImageIcon[2][];
	
	public void massiv() {
		init();
		int id = 0;
		int x = 0, y = 51;//, y = 0;
		//true - твердый, false - не твердый
		for (int i = 0; i < Game.mapx[0].length; i++) {
			for (int j = 0; j < Game.mapx.length; j++) {
				if (Game.map[j][i] == 0) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.grass);
				}
				if (Game.map[j][i] == 1) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.path);
				}
				if (Game.map[j][i] == 2) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.three);
				}
				if (Game.map[j][i] == 3) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.water);
				}
				if (Game.map[j][i] == 4) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.mount);
				}
				if (Game.map[j][i] == 5) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home01);
				}
				if (Game.map[j][i] == 6) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.brickPath);
				}
				if (Game.map[j][i] == 7) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.brickWallP);
				}
				if (Game.map[j][i] == 8) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.brickWall);
				}
				if (Game.map[j][i] == 9) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.brickPathG);
				}
				if (Game.map[j][i] == 10) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.bridge);
				}
				if (Game.map[j][i] == 11) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.three2);
				}
				if (Game.map[j][i] == 12) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.water1);
				}
				if (Game.map[j][i] == 13) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home02);
				}
				if (Game.map[j][i] == 14) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home03);
				}
				if (Game.map[j][i] == 15) {
					Game.mapx[j][i] = new Tiles(id, true, true, 0);
					Game.mapx[j][i].setIcon(animIcon[0][0]);
				}
				if (Game.map[j][i] == 16) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooring);
				}
				if (Game.map[j][i] == 17) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWall);
				}
				if (Game.map[j][i] == 18) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWallRigth);
				}
				if (Game.map[j][i] == 19) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooringRigth);
				}
				if (Game.map[j][i] == 20) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWallLeft);
				}
				if (Game.map[j][i] == 21) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooringLeft);
				}
				if (Game.map[j][i] == 22) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.voidI);
				}
				if (Game.map[j][i] == 23) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.window);
				}
				if (Game.map[j][i] == 24) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.grassDark);
				}
				if (Game.map[j][i] == 25) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.threeDark);
				}
				if (Game.map[j][i] == 26) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.threeDark2);
				}
				if (Game.map[j][i] == 27) {
					Game.mapx[j][i] = new Tiles(id, true, true, 1);
					Game.mapx[j][i].setIcon(animIcon[1][0]);
				}
				if (Game.map[j][i] == 28) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.well);
				}
				if (Game.map[j][i] == 29) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.townRoad);
				}
				
				Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				x += Game.TILE;
				id++;
			}
			x = 0;
			y += Game.TILE;
		}
	}
	
	private void init() {
		String path = "res/Image/Tiles/";
		animIcon[0] = new ImageIcon[4];
		animIcon[0][0] = new ImageIcon(getClass().getResource(path + "TilesBuilding/FireHome/1.png"));
		animIcon[0][1] = new ImageIcon(getClass().getResource(path + "TilesBuilding/FireHome/2.png"));
		animIcon[0][2] = new ImageIcon(getClass().getResource(path + "TilesBuilding/FireHome/3.png"));
		animIcon[0][3] = new ImageIcon(getClass().getResource(path + "TilesBuilding/FireHome/4.png"));
		
		animIcon[1] = new ImageIcon[6];
		animIcon[1][0] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/1.png"));
		animIcon[1][1] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/2.png"));
		animIcon[1][2] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/3.png"));
		animIcon[1][3] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/4.png"));
		animIcon[1][4] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/5.png"));
		animIcon[1][5] = new ImageIcon(getClass().getResource(path + "Animate/GrassFire/6.png"));
	}
	
}
