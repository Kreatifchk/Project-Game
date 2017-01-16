package strategy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Resize {
	
	public static Image resize(Image img, int width, int height) {
		java.awt.Image im;
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, null);
		im = bi.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return im;
	}
	
	public static ImageIcon resizeIcon(Image img, int width, int height) {
		Image im;
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, null);
		im = bi.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ii = new ImageIcon(im);
		return ii;
	}
	
}
