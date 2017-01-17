package rpg;

public class Recovery implements Runnable {

	@Override
	public void run() {
		while(true) {
			Animation.sleep(5);
			if (Game.hpThis < Game.hpMax) {
				if (Battle.battle != true) {
					Game.hpThis += Player.speedRecovery;
					if (Game.hpThis > Game.hpMax) {
						Game.hpThis = Game.hpMax;
					}
					Animation.sleep(2000);
				}
			} else {
				Animation.sleep(1000);
			}
		}
	}

}
