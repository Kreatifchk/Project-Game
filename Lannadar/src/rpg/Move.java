package rpg;

public class Move {
	
	static Thread battle = new Thread(new Battle());
	
	public static void move(int direction, boolean move) {
		try {
			if (direction == 1 && move == true) {
				//Вниз
				if (Game.mapx[Game.pl.mX][Game.pl.mY+1].solid == false) {
					if (Game.mapx[Game.pl.mX][Game.pl.mY+1].busy == false) {
						Game.direction = 1;
						Game.move = true;
						//Game.player = Player.playerDown;
					} else {
						mob(Game.pl.mX, Game.pl.mY+1); //Если игрок столкнулся с мобом
					}
				} 
			}
			if (direction == 2 && move == true) {
				//Вверх
				if (Game.mapx[Game.pl.mX][Game.pl.mY-1].solid == false) {
					if (Game.mapx[Game.pl.mX][Game.pl.mY-1].busy == false) {
						Game.direction = 2;
						Game.move = true;
						//Game.player = Player.playerUp;
					} else {
						mob(Game.pl.mX, Game.pl.mY-1);
					}
				}
			}
			if (direction == 3 && move == true) {
				//Влево
				if (Game.mapx[Game.pl.mX-1][Game.pl.mY].solid == false) {
					if (Game.mapx[Game.pl.mX-1][Game.pl.mY].busy == false) {
						Game.direction = 3;
						Game.move = true;
						//Game.player = Player.playerLeft;
					} else {
						mob(Game.pl.mX-1, Game.pl.mY);
					}
				}
			}
			if (direction == 4 && move == true) {
				//Вправо
				if (Game.mapx[Game.pl.mX+1][Game.pl.mY].solid == false) {
					if (Game.mapx[Game.pl.mX+1][Game.pl.mY].busy == false) {
						Game.direction = 4;
						Game.move = true;
						//Game.player = Player.playerRight;
					} else {
						mob(Game.pl.mX+1, Game.pl.mY);
					}
				}
			}
			if (move == false) {
				Game.move = false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	private static void mob(int x, int y) {
		if (Battle.battle != true) {
			for (int i = 0; i <= Game.monster.size()-1; i++) {
				if (Game.monster.get(i).x == x && Game.monster.get(i).y == y) {
					Battle.battle = true;
					LevelMobs.level = Game.monster.get(i).level;
					Battle.mobLevel = Game.monster.get(i).level;
					Battle.mobExp = Game.monster.get(i).exp;
					Battle.mobAttack = Game.monster.get(i).attack;
					Battle.name = Game.monster.get(i).name;
					
					Battle.id = i;
					Battle.mobX = Game.monster.get(i).x;
					Battle.mobY = Game.monster.get(i).y;
					
					HpMobs.xpMax = Game.monster.get(i).xp;
					HpMobs.xpCurrent = Game.monster.get(i).xp;
				}
			}
		}
	}
	
}
