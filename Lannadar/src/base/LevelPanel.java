package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import javax.swing.JPanel;

import initialize.InitFont;

@SuppressWarnings("serial")
public class LevelPanel extends JPanel {
	
	//Image base = new ImageIcon(getClass().getResource("res/Image/Panels/base.png")).getImage();
	
	Font smw = InitFont.smw.deriveFont(36F);//24
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		
		if (Game.pl.level > 9) {
			smw = smw.deriveFont(34F);
		}
		
		int pixW = (int) smw.getStringBounds("" + Game.pl.level, new FontRenderContext(null, true, true)).getWidth();
		
		g2d.setFont(smw);
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, 46, 46);
		g2d.setColor(new Color(128, 128, 128));
		g2d.fillRect(1, 1, 45, 45);
		g2d.setColor(Color.black);

		int x = (getWidth() - pixW) / 2;

		g2d.drawString("" + Game.pl.level, x, 34);
	}
	
}
