package ru.kreatifchk.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LannadarSlider extends JComponent implements MouseListener, MouseMotionListener {
	
	private int min, max; //Минимальное и максимальное значение
	private int majorSpacing;// minorSpacing; //Растояние между большими (маленькими) делениями чисел
	private final int BORDER_SIZE = 3; //Размер рамки
	private int countMajor; //countMinor; //Количество больших и маленьких делений
	
	private Rectangle track; //Дорожка по которой будет ездить слайдер
	private Rectangle slider; //Сам слайдер
	
	private boolean pressed; //true если конпка зажата на слайдере
	
	public LannadarSlider(int min, int max) {
		this.min = min;
		this.max = max;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public LannadarSlider(int min, int max, int value) {
		this.min = min;
		this.max = max;
		//this.value = value;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		
		track = new Rectangle(0, 0, getWidth(), getHeight() / 2);
		slider = new Rectangle(BORDER_SIZE, BORDER_SIZE, getWidth() / 100 * 5, track.height - BORDER_SIZE*2);
	}
	
	public void setMajorTickSpacing(int value) {
		majorSpacing = value;
		
		countMajor = (max - min) / majorSpacing;
	}
	
	public void setMinorTickSpacing(int value) {
		//minorSpacing = value;
		
		//countMinor = (max - min) / minorSpacing;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		//Рисуем полосу
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, track.width, track.height);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(BORDER_SIZE));
		g2d.drawRect(1, 1, track.width - BORDER_SIZE, track.height - BORDER_SIZE);
		
		//Рисуем ездилку
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.RED);
		g2d.fillRect(slider.x, slider.y, slider.width, slider.height);
		g2d.setColor(Color.YELLOW);
		g2d.drawRect(slider.x+1, slider.y+1, slider.width - 2, slider.height - 2);
		
		//Рисуем длинные полоски
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(BORDER_SIZE));
		int x = BORDER_SIZE + slider.width / 2;
		int hh = track.width - (((BORDER_SIZE + slider.width / 2)*2) + track.width / countMajor * (countMajor-1));
		//Сколько осталось до конца полосы без границ
		hh /= 10;
		
		for (int i = 0; i < countMajor; i++) {
			g2d.drawLine(x, track.height, x, track.height*2);
			x += track.width / countMajor + hh;
		}
	}

	@Override
	public void mouseDragged(MouseEvent a) {
		if (pressed == true) {
			if (a.getX() > BORDER_SIZE & a.getX() < track.width - slider.width - BORDER_SIZE) {
				slider.x = a.getX();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {	
	}
	@Override
	public void mousePressed(MouseEvent a) {
		Rectangle r = new Rectangle(a.getX(), a.getY(), 1, 1);
		if (r.intersects(slider)) {
			pressed = true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent a) {
		pressed = false;
	}
	
}
