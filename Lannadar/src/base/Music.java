package base;

import java.io.BufferedInputStream;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Music {
	
	public static BasicPlayer player = new BasicPlayer();
	static boolean izm = false;
	
	public static void start(String name, double value) {
		try {
			player.open(new BufferedInputStream(Game.class.getResourceAsStream("res/music/" + name)));
			player.play();
			if (izm == false) {
				player.setGain(value);
			}
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	public static void stop() {
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	public static void pStop() {
		double x = 0.07;
		try {
			for (int i = 0; i <= 6; i++) {
				player.setGain(x);
				Animation.sleep(200);
				x = x - 0.01;
			}
			Animation.sleep(200);
			player.stop();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
}
