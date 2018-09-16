package ru.kreatifchk.game;

/** Класс соо всеми тайлами */
public class TilesList {
	
	public static Tile[] tiles = new Tile[15];
	
	public static int forestCount, cityCount;
	
	public TilesList() {
		forestKit();
		city();
		
		counting(); //Подсчитывает сколько в каком наборе тайлов
	}
	
	private void forestKit() {
		tiles[0] = new Tile(false, "Трава дневная", "grass", 0);
		tiles[1] = new Tile(true, "Дерево", "three", 0);
		tiles[2] = new Tile(true, "Дерево2", "three2", 0);
		tiles[3] = new Tile(false, "Дорожка", "path", 0);
		tiles[4] = new Tile(false, "Песок", "sand", 0);
		tiles[5] = new Tile(true, "Вода", "water", 0);
		tiles[11] = new Tile(true, true, "Горящая трава", "grassFire", 0);
		tiles[12] = new Tile(false, "Мост", "bridge", 0);
		tiles[13] = new Tile(true, "Колодец", "well", 0);
		tiles[14] = new Tile(true, "Горы", "mount", 0);
	}
	
	private void city() {
		tiles[6] = new Tile(false, "Трава городская", "cityGrass", 1);
		tiles[7] = new Tile(true, "Клумба", "flowerbed", 1);
		tiles[8] = new Tile(true, "Фонтан", "fountain", 1);
		tiles[9] = new Tile(false, "Городская дорога", "townRoad", 1);
		tiles[10] = new Tile(true, "Дом 09", "home09", 1, 2, 2);
	}
	
	private void counting() {
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i].numberKit == 0) {
				forestCount++;
			}
			if (tiles[i].numberKit == 1) {
				cityCount++;
			}
		}
	}
	
}
