package rpg;

public class MonsterList {
	
	//����� ���������� ������� ������ ������� � ����� Game
	
	public MonsterList() {
		//��������� Location, x, y, icon, xp, mp, attack, level, difficulty, exp, name
		Game.monster.add(new Monsters(2, 2, 1, "zombie", 40, 0, 4, 1, 1, 6, "�����"));
		Game.monster.add(new Monsters(2, 7, 1, "zombie", 40, 0, 4, 1, 1, 6, "�����"));
		Game.monster.add(new Monsters(2, 9, 0, "zombie", 40, 0, 4, 1, 1, 6, "�����"));
		Game.monster.add(new Monsters(3, 9, 3, "zombie", 50, 0, 6, 2, 1, 8, "�����"));
		Game.monster.add(new Monsters(3, 9, 1, "zombie", 50, 0, 6, 2, 1, 8, "�����"));
		Game.monster.add(new Monsters(4, 3, 5, "necromancer", 105, 10, 12, 4, 1, 16, "���������"));
		Game.monster.add(new Monsters(4, 2, 4, "necromancer", 105, 10, 12, 4, 1, 16, "���������"));
		Game.monster.add(new Monsters(4, 5, 2, "necromancer", 105, 10, 12, 4, 1, 16, "���������"));
		Game.monster.add(new Monsters(4, 6, 1, "necromancer", 105, 10, 12, 4, 1, 16, "���������"));
		Game.monster.add(new Monsters(4, 9, 4, "necromancer", 105, 10, 12, 4, 1, 16, "���������"));
	}
	
}
