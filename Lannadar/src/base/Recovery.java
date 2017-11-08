package base;

public class Recovery implements Runnable {

	@Override
	public void run() {
		while(true) {
			Animation.sleep(5);
			if (Game.pl.hpThis < Game.pl.hpMax) {
				if (Battle.battle != true) {
					Game.pl.hpThis += Game.pl.regeneration;
					if (Game.pl.hpThis > Game.pl.hpMax) {
						Game.pl.hpThis = Game.pl.hpMax;
					}
					Animation.sleep(2000);
				}
			} else {
				Animation.sleep(1000);
			}
		}
	}

}
