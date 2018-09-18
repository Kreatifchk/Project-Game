package ru.kreatifchk.game;

import java.util.ArrayList;

public class MonsterList {
	
	public static ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	public MonsterList() {
		monsters.add(new Monster("Зомби", "zombie", 50, 0, 1));
		monsters.add(new Monster("Скелет", "skelet", 75, 0, 2));
		monsters.add(new Monster("Некромант", "necromancer", 120, 0, 3, 2));
		monsters.add(new Monster("Кристальный монстр", "cristal", 200, 0, 4));
	}
	
}
