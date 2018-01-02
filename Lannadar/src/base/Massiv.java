package base;

import javax.swing.ImageIcon;

public class Massiv {
	
	static ImageIcon[][] animIcon = new ImageIcon[6][];
	
	public void massiv() {
		init();
		int id = 0;
		int x = 0, y = 51;//, y = 0;
		//true - твердый, false - не твердый
		for (int i = 0; i < Game.mapx[0].length; i++) {
			for (int j = 0; j < Game.mapx.length; j++) {
				Tiles tile = TileList.tiles[Game.map[j][i]];
				if (tile.anim == true) {
					Game.mapx[j][i] = new Tiles(id, tile.solid, true, tile.number);
					Game.mapx[j][i].setIcon(tile.ic);
				} else {
					Game.mapx[j][i] = new Tiles(id, tile.solid);
					Game.mapx[j][i].setIcon(tile.ic);
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
		
		animIcon[2] = new ImageIcon[8];
		animIcon[3] = new ImageIcon[8];
		animIcon[4] = new ImageIcon[8];
		animIcon[5] = new ImageIcon[8];
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				animIcon[i][j] = new ImageIcon(getClass().getResource(path + "TilesBuilding/MagicBounce/" + (i-2) + "_" + j + ".png"));
			}
			int numb = 4;
			for (int k = 7; k > 3; k--) {
				animIcon[i][numb] = new ImageIcon(getClass().getResource(path + "TilesBuilding/MagicBounce/" + (i-2) + "_" + (k-4) + ".png"));
				numb++;
			}
			System.out.println();
		}
	}
	
}
