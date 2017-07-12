package rpg;

import javax.swing.ImageIcon;

public class Massiv {
	
	static ImageIcon[][] animIcon = new ImageIcon[2][];
	
	public void massiv() {
		init();
		int id = 0;
		int x = 0, y = 51;//, y = 0;
		//true - твердый, false - не твердый
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				//System.out.print(Game.map[j][i] + " ");
				if (Game.map[j][i] == 0) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.grass);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 1) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.path);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 2) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.three);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 3) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.water);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 4) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.mount);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 5) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home01);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 6) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.brickPath);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 7) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.brickWallP);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 8) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.brickWall);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 9) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.brickPathG);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 10) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.bridge);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 11) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.three2);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 12) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.water1);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 13) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home02);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 14) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.home03);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 15) {
					Game.mapx[j][i] = new Tiles(id, true, true, 0);
					Game.mapx[j][i].setIcon(animIcon[0][0]);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 16) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooring);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 17) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWall);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 18) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWallRigth);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 19) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooringRigth);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 20) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.homeWallLeft);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 21) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.flooringLeft);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 22) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.voidI);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 23) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.window);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 24) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].setIcon(TilesImage.grassDark);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 25) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.threeDark);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 26) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.threeDark2);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 27) {
					Game.mapx[j][i] = new Tiles(id, true, true, 1);
					Game.mapx[j][i].setIcon(animIcon[1][0]);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				if (Game.map[j][i] == 28) {
					Game.mapx[j][i] = new Tiles(id, true);
					Game.mapx[j][i].setIcon(TilesImage.well);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
				}
				/*if (Game.map[j][i] == 99) {
					Game.mapx[j][i] = new Tiles(id, false);
					Game.mapx[j][i].portal = true;
					Game.mapx[j][i].setIcon(portal);
					Game.mapx[j][i].setBounds(x, y, Game.TILE, Game.TILE);
					
				}*/
				x += Game.TILE;
				id++;
			}
			//System.out.println();
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
