package base;

public class MonsterList {
	
	//Перед добавление увеличь размер массива в класе Game
	
	public MonsterList() {
		//Аргументы IDtype, location, x, y, icon, xp, mp, attack, level, exp, name
		Game.monster.add(new Monsters(0, 2, 2, 1, "zombie", 40, 0, 4, 1, 6, "Зомби"));
		Game.monster.add(new Monsters(0, 2, 7, 1, "zombie", 40, 0, 4, 1, 6, "Зомби"));
		Game.monster.add(new Monsters(0, 2, 9, 0, "zombie", 40, 0, 4, 1, 6, "Зомби"));
		Game.monster.add(new Monsters(1, 3, 9, 3, "zombie", 50, 0, 6, 2, 8, "Зомби"));
		Game.monster.add(new Monsters(1, 3, 9, 1, "zombie", 50, 0, 6, 2, 8, "Зомби"));
		Game.monster.add(new Monsters(2, 4, 3, 5, "necromancer", 105, 10, 12, 4, 16, "Некромант"));
		Game.monster.add(new Monsters(2, 4, 2, 4, "necromancer", 105, 10, 12, 4, 16, "Некромант"));
		Game.monster.add(new Monsters(2, 4, 5, 2, "necromancer", 105, 10, 12, 4, 16, "Некромант"));
		Game.monster.add(new Monsters(2, 4, 6, 1, "necromancer", 105, 10, 12, 4, 16, "Некромант"));
		Game.monster.add(new Monsters(2, 4, 9, 4, "necromancer", 105, 10, 12, 4, 16, "Некромант"));
	}
	
}
