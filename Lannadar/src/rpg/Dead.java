package rpg;

import java.io.InputStream;

import javax.swing.JOptionPane;

public class Dead {
	
	//File f2;
	InputStream f2;
	
	Massiv ms = new Massiv();
	
	Game gm = new Game(true, false);
	
	int oldLocation;
	
	//respawnLoc - локация на которой умер
	public void dead(int respawnLoc) {
		if (Game.hpThis <= 0) {
			Battle.battle = false;
			Music.stop();
			if (respawnLoc == 2) {
				JOptionPane.showMessageDialog(null, "Вы умерли");
				Game.pl.mX = 1;
				Game.pl.mY = 6;
				Game.pl.x = Game.pl.mX * 48;
				Game.pl.y = Game.pl.mY * 48 + 49;
				Game.hpThis = Game.hpMax;
				Menu.g.hpM.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Вы умерли");
				
				oldLocation = Game.currentLocation;
				
				Game.currentLocation = 2;
				Game.hpThis = Game.hpMax;
				
				Game.oldLocation = oldLocation;
				Menu.g.loadLocation(2);
				Menu.g.hpM.setVisible(false);
				
				Game.pl.mX = 1;
				Game.pl.mY = 6;
				Game.pl.x = Game.pl.mX * 48;
				Game.pl.y = Game.pl.mY * 48 + 49;
			}
		}
	}
	
	
}
