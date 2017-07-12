package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;
import base.Resize;
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
	}
	
}
