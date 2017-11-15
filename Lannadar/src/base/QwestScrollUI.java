package base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

@SuppressWarnings("serial")
public class QwestScrollUI extends MetalScrollBarUI {
	
	Image jsbI = new ImageIcon(getClass().getResource("res/others/jsb.png")).getImage();
	Image jsbI2 = new ImageIcon(getClass().getResource("res/others/jsb1.png")).getImage();
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(jsbI, 0, 0, null);
	}
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    	g.translate(thumbBounds.x, thumbBounds.y);
        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/jsbI2.getWidth(null),(double)thumbBounds.height/jsbI2.getHeight(null));
        ((Graphics2D)g).drawImage(jsbI2, transform, null);
        g.translate( -thumbBounds.x, -thumbBounds.y );
    }
    //Верхняя
    @Override
    protected JButton createDecreaseButton(int orientation) {
    	DecButton but = new DecButton();
    	Dimension zeroDim = new Dimension(14,14);
    	but.setPreferredSize(zeroDim);
    	but.setMinimumSize(zeroDim);
    	but.setMaximumSize(zeroDim);
    	return but;
    }
    //Нижняя
    @Override
    protected JButton createIncreaseButton(int orientation) {
    	IncButton but = new IncButton();
    	Dimension zeroDim = new Dimension(14,14);
    	but.setPreferredSize(zeroDim);
    	but.setMinimumSize(zeroDim);
    	but.setMaximumSize(zeroDim);
    	return but;
    }
    
	private class IncButton extends JButton {
		Image jsbD = new ImageIcon(getClass().getResource("res/others/jsbD.png")).getImage();
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(jsbD, 0, 0, null);
		}
		@Override
		public void paintBorder(Graphics g) {
			
		}
	}
	
	private class DecButton extends JButton {
		Image jsbU = new ImageIcon(getClass().getResource("res/others/jsbU.png")).getImage();
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(jsbU, 0, 0, null);
		}
		@Override
		public void paintBorder(Graphics g) {
			
		}
	}
	
}
