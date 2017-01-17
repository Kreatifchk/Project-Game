package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ExpPanel extends JPanel {
	
	Image expI = new ImageIcon(getClass().getResource("res/exp.png")).getImage();
	
	int persent = 0;
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		int x = 2;
		persent = Maths.whatPersentage(Game.pl.exp, Game.pl.maxExp);
		for (int i = 1; i <= persent; i++) {
			g2d.drawImage(expI, x, 1, null);
			x++;
		}
	}
	
}
