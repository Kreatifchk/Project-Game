package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelMobs extends JPanel {
	
	//Здесь происходит отрисовка уровня врага
	
	Image base = new ImageIcon(getClass().getResource("res/levelsP/base.png")).getImage();
	
	static int level;
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		if (Battle.battle == true) {
			int levelObject = level;
			if (level < 10) {
				g2d.drawImage(base, 0, 0, null);
				g2d.drawImage(Game.levelImage[levelObject], 10, 5, null);
			} else if (level < 100) {
				int a = level / 10 , b = level % 10;
				g2d.drawImage(base, 0, 0, null);
				g2d.drawImage(Game.levelImage[a], 5, 5, null);
				g2d.drawImage(Game.levelImage[b], 16, 5, null);
			}
		}
	}
	
	
}
