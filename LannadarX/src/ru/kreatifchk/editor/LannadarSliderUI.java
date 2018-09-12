package ru.kreatifchk.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;

import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Resize;

public class LannadarSliderUI extends MetalSliderUI {
	
	Image track = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/trackSlider.png")).getImage();
	Image trackFull = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/trackFullSlider.png")).getImage();
	Image thumb = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/thumbSlider.png")).getImage();
	
	JSlider js;
	
	Stroke s;
	
	int borderSize;
	
	public LannadarSliderUI(JSlider js) {
		js.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent a) {
				js.repaint();
			}
		});
		
		this.js = js;
		
		borderSize = (int)(2*Main.INC);
		
		track = Resize.resizeA(track, js.getWidth() - (int)(2*Main.INC), (int)(track.getHeight(null)*Main.INC));
		thumb = Resize.resizeA(thumb, (int)(thumb.getWidth(null)*Main.INC)+1, (int)(track.getHeight(null))-borderSize);
	}
	
	@Override
	protected Dimension getThumbSize() {
		return new Dimension(thumb.getWidth(null), thumb.getHeight(null));
	}
	
	@Override
	public void paintTrack(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Color col = new Color(253, 244, 227);
		g2d.setColor(col);
		g2d.fillRect(0, 0, js.getWidth(), js.getHeight());
		
		if (js.getValue() == 120) {
			trackFull = Resize.resizeA(trackFull, track.getWidth(null)-borderSize,
					track.getHeight(null)-borderSize);
		} else {
			trackFull = Resize.resizeA(trackFull, thumbRect.x, track.getHeight(null)-borderSize);
		}
		
		g2d.drawImage(track, 2, 0, null);
		if (js.getValue() != 12) {
			g2d.drawImage(trackFull, borderSize, borderSize, null);
		}
		
		s = g2d.getStroke();
		
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(borderSize));
		g2d.drawRect(1, 1, js.getWidth() - (int)(2*Main.INC), track.getHeight(null));
		
		g2d.setStroke(s);
	}
	
	@Override
	public void paintThumb(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		trackRect.y = 0;
		
		int x = thumbRect.x;
		int y = trackRect.y + borderSize;
		
		g2d.drawImage(thumb, x, y, null);
	}
	
	@Override
	public void paintHorizontalLabel(Graphics g, int value, Component comp) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font f = new Font(g2d.getFont().getFontName(), Font.BOLD, (int)(9*Main.INC));
		g2d.setFont(f);
		g2d.setColor(Color.black);
		
		if (Main.INC == 1) {
			g2d.drawString(value + "", xPositionForValue(value) - (int)(6*Main.INC), (int)(13*Main.INC));
		} else {
			g2d.drawString(value + "", xPositionForValue(value) - (int)(6*Main.INC), (int)(12*Main.INC));
		}
	}
	
	@Override
	public void paintMajorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x)  {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLUE);
		if (Main.INC == 1) {
			g2d.drawLine(x, (int)(8*Main.INC), x, (int)(14*Main.INC));
		} else {
			g2d.drawLine(x, (int)(3*Main.INC), x, (int)(10*Main.INC));
		}
	}
	
	@Override
	public void paintMinorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLUE);
		if (Main.INC == 1) {
			g2d.drawLine(x, (int)(8*Main.INC), x, (int)(11*Main.INC));
		} else {
			g2d.drawLine(x, (int)(3*Main.INC), x, (int)(7*Main.INC));
		}
	}
	
}
