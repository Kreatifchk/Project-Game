package rpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelMobs extends JPanel {
	
	//«десь происходит отрисовка уровн¤ врага
	
	Image base = new ImageIcon(getClass().getResource("res/Image/Panels/base.png")).getImage();
	
	static int level;
	
	Font smw = Initialize.smw.deriveFont(24F);
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		if (Battle.battle == true) {
			int pixW = (int) smw.getStringBounds("" + level, new FontRenderContext(null, true, true)).getWidth();
			g2d.setFont(smw);
			g2d.setColor(Color.black);
			g2d.drawImage(base, 0, 0, null);
			if (Game.pl.level > 9) {
				smw = smw.deriveFont(22F);
			}
			g2d.drawString("" + level, 
					(int) Math.ceil((double)(30 - pixW) / 2), 23);
		}
	}
	
	
}
