package ru.space;

import java.util.Random;

public class GeneratePlanet {
	
	static Random r =new Random();
	
	static boolean three, water, mountain, stone;
	
	final static int MAX_WATER = 3, MAX_THREE = 30;
	static int countWater, countThree;
	
	private static int direction;
	
	public static void generate(int planet, int square) {
		direction = square;
		preGen();
		
		if (planet == 1) {
			greenPlanet();
		}
		
		water = false;
		three = false;
		countWater = 0;
		countThree = 0;
	}
	
	//Устанвливает тайл
	private static void install(int i, int j, int type) {
		if (direction == 0) {
			Game.pole[i][j] = type;
		}
		if (direction == 1) {
			Game.poleDown[i][j] = type;
		}
		if (direction == 2) {
			Game.poleUp[i][j] = type;
		}
		if (direction == 3) {
			Game.poleLeft[i][j] = type;
		}
		if (direction == 4) {
			Game.poleRigth[i][j] = type;
		}
	}
	
	//Определяет тип тайла
	private static int getType(int i, int j) {
		int type = 0;
		if (direction == 0) {
			type = Game.pole[i][j];
		}
		if (direction == 1) {
			type = Game.poleDown[i][j];
		}
		if (direction == 2) {
			type = Game.poleUp[i][j];
		}
		if (direction == 3) {
			type = Game.poleLeft[i][j];
		}
		if (direction == 4) {
			type = Game.poleRigth[i][j];
		}
		return type;
	}
	
	private static void preGen() {
		int p = r.nextInt(100) + 1;
		if (p >= 20 & p <= 75) {
			water = true;
		}
		p = r.nextInt(100) + 1;
		if (p >= 10 & p <= 90) {
			three = true;
		}
	}
	
	private static void greenPlanet() {
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				//Game.pole[i][j] = 2;
				install(i, j, 2);
			}
		}
		genWater();
		genThree();
	}
	
	private static void genWater() {
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				if (water == true  & countWater < MAX_WATER) {
					//int v = r.nextInt(200) + 1;
					int x = r.nextInt(20);
					int y = r.nextInt(14);
					genWater(x, y);
					countWater++;
				}
			}
		}
	}
	
	private static void genWater(int i, int j) {
		int x = r.nextInt(5) + 3;
		for (int z = 0; z <= x; z++) {
			install(i, j, 3);
			int direction = r.nextInt(4) + 1;
			if (direction == 1) {
				if (j - 1 < 0 || getType(i, j-1) == 3) {
					direction = 2;
				} else {
					j--;
				}
			}
			if (direction == 2) {
				if (j + 1 > Game.pole[0].length - 1 || getType(i, j+1) == 1) {
					direction = 3;
				} else {
					j++;
				}
			}
			if (direction == 3) {
				if (i - 1 < 0 || getType(i-1, j) == 1) {
					direction = 4;
				} else {
					i--;
				}
			}
			if (direction == 4) {
				if (i + 1 > Game.pole.length - 1 || getType(i+1, j) == 1){
					if (j - 1 < 0 || getType(i, j-1) == 1) {
						if (direction == 2) {
							if (j + 1 > Game.pole[0].length - 1
									||getType(i, j+1) == 1) {
								direction = 3;
							} else {
								j++;
							}
						}
					} else {
						j--;
					}
				} else {
					i++;
				}
			}
		}
	}
	
	private static int count = 0;
	
	private static void genThree() {
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				if (three == true & countThree != MAX_THREE) {
					count++;
					int g = r.nextInt(100) + 1;
					if (g <= count) {
						if (getType(i, j) != 3) {
							int v = r.nextInt(10) + 1;
							if (v <= 5) {
								//Game.pole[i][j] = 6;
								install(i, j, 6);
							} else {
								install(i, j, 7);
								//Game.pole[i][j] = 7;
							}
							count = 0;
							countThree++;
						}
					}
				}
			}
		}
	}
	
}
