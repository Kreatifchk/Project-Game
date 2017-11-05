package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;
import base.Resize;
import base.Game;
import base.Initialize;
import base.Player;

@SuppressWarnings("serial")
public class HeroPanelInfo extends JLabel {
	
	Image plD = Player.playerD[0];
	
	public HeroPanelInfo() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(153, 238, 157));
		g2d.fillRect(165, 0, 293, 302);
		g2d.drawImage(Resize.resizeA(plD, 300, 300), 168, 0, null);
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.black);
		g2d.drawLine(165, 0, 165, 302);
		g2d.drawLine(165, 302, 458, 302);
		g2d.drawLine(458, 0, 458, 302);
		
		g2d.drawLine(0, 112, 160, 112);
		g2d.drawLine(0, 224, 160, 224);
		g2d.drawLine(0, 336, 160, 336);
		g2d.drawLine(458, 112, 652, 112);
		g2d.drawLine(458, 224, 652, 224);
		g2d.drawLine(458, 336, 652, 336);
		
		g2d.drawLine(165, 302, 165, 448);
		g2d.drawLine(458, 302, 458, 448);
		
		g2d.setFont(Initialize.uph.deriveFont(26F));
		g2d.drawString("Уровень:", 170, 325);
		g2d.drawString(Game.pl.level + "", 380, 325);
		g2d.drawString("Сила:", 170, 348);
		g2d.drawString(Game.pl.force + "", 380, 348);
		g2d.drawString("Интеллект:", 170, 371);
		g2d.drawString(Game.pl.intellect + "", 380, 371);
		g2d.drawString("Выносливость:", 170, 394);
		g2d.drawString(Game.pl.endurance + "", 380, 394);
		g2d.drawString("Защита:", 170, 417);
		g2d.drawString(Game.pl.armor + "", 380, 417);
		g2d.drawString("Регенерация:", 170, 440);
		g2d.drawString(Game.pl.regeneration + "", 380, 440);
	}
	
}
