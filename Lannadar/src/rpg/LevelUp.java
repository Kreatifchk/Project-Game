package rpg;

public class LevelUp {
	
	public static void levelUp() {
		if (Game.pl.exp >= Game.pl.maxExp) {
			int dopExp = Game.pl.exp - Game.pl.maxExp; //Лишнее exp
			Game.pl.level++;
			Game.pl.exp = 0 + dopExp;
			Game.pl.maxExp = Player.expTable[Game.pl.level-1];
			paramUp();
		}
	}
	
	//Повышает характеристики героя
	private static void paramUp() {
		if (Game.pl.level == 2 || Game.pl.level == 3) {
			Game.hpMax += 5;
			Game.hpPoint = Game.hpMax / 100;
			Game.pl.playerAttack += 2;
		}
	}
	
}
