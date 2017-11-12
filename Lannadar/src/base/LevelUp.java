package base;

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
			Game.pl.endurance += 3;
			Game.pl.hpMax = Game.pl.endurance * 2;
			
			Game.pl.force += 1;
			Game.pl.playerAttack = Game.pl.force * 3 + 2;
			
			Game.pl.regeneration++;
		}
	}
	
}
