package rpg;

public class MusicLocation {
	
	public static void music(int location) {
		if (Music.player.getStatus() != 0) {
			if (location == 2) {
				Music.start("Refl1.mp3", Settings.volume);
			}
			if (location == 4) {
				Music.start("OverTime.mp3", Settings.volume);
			}
		}
	}
	
}
