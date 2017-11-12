package base;

public class GameLoop implements Runnable {
	
	int sl = 0; //Счетчик милисекунд
	
	@Override
	public void run() {
		while(true) {
			if (sl % 2000 == 0) {
				recoveryPlayer(); //Регенирируем игроку здоровье
			}
			
			if (sl == 30000) {
				//respawnMobs();
			}
			
			Animation.sleep(20);
			if (sl == 30020) {
				sl = 0;
			}
			sl += 20;
		}
	}
	
	private void recoveryPlayer() {
		if (Game.pl.hpThis < Game.pl.hpMax) {
			if (Battle.battle != true) {
				Game.pl.hpThis += Game.pl.regeneration;
				if (Game.pl.hpThis > Game.pl.hpMax) {
					Game.pl.hpThis = Game.pl.hpMax;
				}
			}
		}
	}
	
	Monsters mns;
	private void respawnMobs() {
		System.out.println("work");
		for (int i = 0; i < Game.monster.size(); i++) {
			//System.out.println(i);
			if (Game.monster.get(i).alive == false) {
				//System.out.println("in");
				mns = Game.monster.get(i);
				Game.monster.get(i).alive = true;
				Game.monster.get(i).setVisible(true);
				Game.mapx[mns.x][mns.y].busy = true;
			}
		}
	}

}
