package base;

public class GameLoop implements Runnable {
	
	int sl = 0; //Счетчик милисекунд
	
	@Override
	public void run() {
		while(true) {
			if (sl % 2000 == 0) {
				recoveryPlayer(); //Регенирируем игроку здоровье
			}
			
			Animation.sleep(20);
			if (sl == 2020) {
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

}
