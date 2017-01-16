package strategy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Resize {
	
	public static java.awt.Image resize(java.awt.Image img, int width, int height) {
		java.awt.Image im;
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, null);
		im = bi.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return im;
	}
	
}
