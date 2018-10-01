package ru.kreatifchk.game;

public class MonsterList {
	
	//public static Monster[] monsters = new Monster[4];
	public static Entity[] monsters = new Monster[1];
	
	public MonsterList() {
//		monsters[0] = new Monster("Зомби", "zombie", 50, 0, 1);
//		monsters[1] = new Monster("Скелет", "skelet", 75, 0, 2);
//		monsters[2] = new Monster("Некромант", "necromancer", 120, 0, 3, 2);
//		monsters[3] = new Monster("Кристальный монстр", "cristal", 200, 0, 4);
		monsters[0] = new Monster("Зомби", "zombie2", 50, 0, 1).setAttack(10).setDefence(10);
	}
	
}
