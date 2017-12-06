package base;

import javax.swing.ImageIcon;

public class TileList {
	
	static Tiles[] tiles = new Tiles[55]; //Все тайлы
	
	public String file = "/base/res/Image/Tiles/";
	
	public TileList() {
		tiles[0] = new Tiles("Трава", false, new ImageIcon(getClass().getResource(file + "grass.png")));
		tiles[1] = new Tiles("Дорожка", false, new ImageIcon(getClass().getResource(file + "path.png")));
		tiles[2] = new Tiles("Дерево 1", true, new ImageIcon(getClass().getResource(file + "three.png")));
		tiles[3] = new Tiles("Вода", true, new ImageIcon(getClass().getResource(file + "water.png")));
		tiles[4] = new Tiles("Горы", true, new ImageIcon(getClass().getResource(file + "mount.png")));
		tiles[5] = new Tiles("Дом 01", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home01.png")));
		tiles[6] = new Tiles("Кирпичная дорога", false, new ImageIcon(getClass().getResource(file + "brickPath.png")));
		tiles[7] = new Tiles("Кирпичная стена", true, new ImageIcon(getClass().getResource(file + "brickWallP.png")));
		tiles[8] = new Tiles("Кирпичная стена", true, new ImageIcon(getClass().getResource(file + "brickWall.png")));
		tiles[9] = new Tiles("Кирпичная дорога", false, new ImageIcon(getClass().getResource(file + "brickPathG.png")));
		tiles[10] = new Tiles("Мост", false, new ImageIcon(getClass().getResource(file + "bridge.png")));
		tiles[11] = new Tiles("Дерево 02", true, new ImageIcon(getClass().getResource(file + "three2.png")));
		tiles[12] = new Tiles("Вода с пляжем", true, new ImageIcon(getClass().getResource(file + "water1.png")));
		tiles[13] = new Tiles("Дом 02", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home02.png")));
		tiles[14] = new Tiles("Дом 03", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home03.png")));
		tiles[15] = new Tiles("Горящий дом", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/homeFire.png")), true, 0);
		tiles[16] = new Tiles("Пол в доме", false, new ImageIcon(getClass().getResource(file + "flooring.png")));
		tiles[17] = new Tiles("Стена дома", true, new ImageIcon(getClass().getResource(file + "HomeWall.png")));
		tiles[18] = new Tiles("Стена дома", true, new ImageIcon(getClass().getResource(file + "HomeWallRight.png")));
		tiles[19] = new Tiles("Пол в доме", false, new ImageIcon(getClass().getResource(file + "flooringRigth.png")));
		tiles[20] = new Tiles("Стена дома", true, new ImageIcon(getClass().getResource(file + "HomeWallLeft.png")));
		tiles[21] = new Tiles("Пол в доме", false, new ImageIcon(getClass().getResource(file + "flooringLeft.png")));
		tiles[22] = new Tiles("Пустота", true, new ImageIcon(getClass().getResource(file + "void.png")));
		tiles[23] = new Tiles("Окно", true, new ImageIcon(getClass().getResource(file + "window.png")));
		tiles[24] = new Tiles("Ночная трава", false, new ImageIcon(getClass().getResource(file + "grassDark.png")));
		tiles[25] = new Tiles("Ночное дерево", true, new ImageIcon(getClass().getResource(file + "threeDark.png")));
		tiles[26] = new Tiles("Ночное дерево 2", true, new ImageIcon(getClass().getResource(file + "three2Dark.png")));
		tiles[27] = new Tiles("Горящая трава", true, new ImageIcon(getClass().getResource(file + "Animate/GrassFire/1.png")), true, 1);
		tiles[28] = new Tiles("Колодец", true, new ImageIcon(getClass().getResource(file + "well.png")));
		tiles[29] = new Tiles("Городская дорога", false, new ImageIcon(getClass().getResource(file + "townRoad.png")));
		tiles[30] = new Tiles("Городской газон", false, new ImageIcon(getClass().getResource(file + "cityGrass.png")));
		tiles[31] = new Tiles("Дом 04", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home04.png")));
		tiles[32] = new Tiles("Дом 05", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home05L.png")));
		tiles[33] = new Tiles("Дом 05", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home05R.png")));
		tiles[34] = new Tiles("Клумба", true, new ImageIcon(getClass().getResource(file + "flowerbed.png")));
		tiles[35] = new Tiles("Фонтан", true, new ImageIcon(getClass().getResource(file + "fountain.png")));
		tiles[36] = new Tiles("Колонна", true, new ImageIcon(getClass().getResource(file + "columnUp.png")));
		tiles[37] = new Tiles("Колонна", true, new ImageIcon(getClass().getResource(file + "columnDown.png")));
		tiles[38] = new Tiles("Статуя", true, new ImageIcon(getClass().getResource(file + "statues.png")));
		tiles[39] = new Tiles("Дерево 03", true, new ImageIcon(getClass().getResource(file + "three3.png")));
		tiles[40] = new Tiles("Асфальт", false, new ImageIcon(getClass().getResource(file + "cityF.png")));
		tiles[41] = new Tiles("Асфальт", false, new ImageIcon(getClass().getResource(file + "cityFL.png")));
		tiles[42] = new Tiles("Асфальт", false, new ImageIcon(getClass().getResource(file + "cityFR.png")));
		tiles[43] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterR.png")));
		tiles[44] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterL.png")));
		tiles[45] = new Tiles("Городская вода", true, new ImageIcon(getClass().getResource(file + "cityWater.png")));
		tiles[46] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterU.png")));
		tiles[47] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterD.png")));
		tiles[48] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterRD.png")));
		tiles[49] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterLD.png")));
		tiles[50] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterRU.png")));
		tiles[51] = new Tiles("Набережная", true, new ImageIcon(getClass().getResource(file + "cityWaterLU.png")));
		tiles[52] = new Tiles("Дом 06", true, new ImageIcon(getClass().getResource(file + "TilesBuilding/home06.png")));
		tiles[53] = new Tiles("Клумба", true, new ImageIcon(getClass().getResource(file + "flowerbedV.png")));
		tiles[54] = new Tiles("Доска объявлений", true, new ImageIcon(getClass().getResource(file + "cityBoard.png")));
	}
	
}
