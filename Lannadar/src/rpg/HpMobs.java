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
public class HpMobs extends JPanel {
	
	// ласс дл¤ отрисовки xp моба
	
	Image hpI = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	
	static int xpMax, xpCurrent, xpPercent;
	/*
	 * xpMax - максимальное xp моба
	 * xpCurrent - текущее xp моба в бою
	 * xpPercent текущее xp в процентах, дл¤ отрисовки полоски
	 */
	
	Font smw = Initialize.smw.deriveFont(19F);
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		int x = 2;
		
		//Отрисовка полоски
		double nb = (double) xpMax / 100;
		xpPercent = (int) (xpCurrent / nb);
		
		for (int i = 1; i <= xpPercent; i++) {
			g2d.drawImage(hpI, x, 1, null);
			x++;
		}
		
		//Отрисовка цифр
		try {
			if (Battle.battle == true) {
				g2d.setFont(smw);
				g2d.setColor(Color.black);
				int pixW = (int) smw.getStringBounds("" + xpCurrent, new FontRenderContext(null, true, true)).getWidth();
				g2d.drawString(xpCurrent + "", (getWidth() - pixW) / 2, 15);
			}
		} catch (NullPointerException e) {
			
		}
	}
	
}
