package base;

import java.io.InputStream;

import javax.swing.JOptionPane;

import menu.Menu;

public class Dead {
	
	//File f2;
	InputStream f2;
	
	Massiv ms = new Massiv();
	
	Game gm = new Game();//true, false
	
	//respawnLoc - локация на которой умер
	public Dead(int respawnLoc) {
		Battle.battle = false;
		Music.stop();
		if (Game.currentLocation == respawnLoc) {
			JOptionPane.showMessageDialog(null, "Вы умерли");
			Game.pl.mX = 1;
			Game.pl.mY = 6;
			Game.pl.x = Game.pl.mX * 48;
			Game.pl.y = Game.pl.mY * 48 + 49;
			Game.pl.hpThis = Game.pl.hpMax;
			hidePanels();
		} else {
			JOptionPane.showMessageDialog(null, "Вы умерли");
			Game.oldLocation = Game.currentLocation;
			Game.currentLocation = respawnLoc;
			Game.pl.hpThis = Game.pl.hpMax;
			Menu.g.loadLocation(2);
			
			Game.pl.mX = 1;
			Game.pl.mY = 6;
			Game.pl.x = Game.pl.mX * 48;
			Game.pl.y = Game.pl.mY * 48 + 49;
			
			hidePanels();
		}
	}
	
	private void hidePanels() {
		Menu.g.upPanel.nameM.setVisible(false);
		Menu.g.upPanel.hpM.setVisible(false);
		Menu.g.upPanel.mpM.setVisible(false);
		Menu.g.upPanel.lvlM.setVisible(false);
	}
	
}
