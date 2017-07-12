package base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PersButton extends JButton{
	
	Image persI = new ImageIcon(getClass().getResource("res/pers.png")).getImage();
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(persI, 0, 0, null);
	}
	
}
