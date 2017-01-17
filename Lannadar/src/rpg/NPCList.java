package rpg;

public class NPCList {
	
	//ѕеред добавление увеличь размер массива в класе Game
	
	public NPCList() {
		//јргументы ID, location, x, y, icon, xp, mp, attack, level, name, int массив с квестами
		Game.npc[0] = new NPC(0, 1, 7, 4, "man", 50, 0, 0, 1, "Ѕраун", new int[] {0});
		Game.npc[1] = new NPC(1, 2, 1, 8, "commander", 50, 0, 0, 10, " рон", new int[] {0, 1, 2, 3, 4});
		Game.npc[2] = new NPC(2, 2, 8, 6, "armourer", 50, 0, 0, 10, "√енгор", new int[] {1, 2});
		Game.npc[3] = new NPC(3, 3, 4, 7, "warrior", 50, 0, 0, 10, "Ёллан", new int[] {4, 5, 6, 7});
		Game.npc[4] = new NPC(4, 3, 2, 7, "warrior2", 50, 0, 0, 10, "’арен");
		Game.npc[5] = new NPC(5, 4, 6, 11, "commander", 50, 0, 0, 10, " рон", new int[] {7, 8, 9});
		Game.npc[6] = new NPC(6, 4, 5, 10, "warriorRight", 50, 0, 0, 10, "Ёллан");
		Game.npc[7] = new NPC(7, 5, 5, 5, "Mage", 50, 25, 0, 10, "–иден", new int[] {9, 10});
	}
	
}
