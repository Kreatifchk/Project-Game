package ru.kreatifchk.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class GradientProgressBar extends JProgressBar {
	
	private int borderSize = 3;
	private int div = 5;
	private boolean indeterminate;
	
	int tm, pos;
	boolean inc = true;
	
	public GradientProgressBar() {
		setBorder(BorderFactory.createLineBorder(Color.black, 2));
	}
	public void setDivision(int div) {
		this.div = div;
	}
	@Override
	public void setIndeterminate(boolean indeterminate) {
		this.indeterminate = indeterminate;
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gp = new GradientPaint(0, 10, Color.LIGHT_GRAY, getWidth() + (int)(50*1.5), 0, Color.RED, false);
		g2d.setPaint(gp);
		if (!indeterminate) {
			g2d.clearRect(0, 0, getWidth(), getHeight());
			//Отрисовка полосы
			g2d.setStroke(new BasicStroke(1));
			
			int b = getWidth() / getMaximum();
			g2d.fillRect(0, 0, b*getValue(), getHeight());
			
			//Отрисовка делений
			g2d.setStroke(new BasicStroke(borderSize));
			g2d.setColor(Color.BLACK);
			
			int d = getWidth() / div;
			for (int i = 0; i < d; i++) {
				g2d.drawLine(d*i, 0, d*i, getHeight());
			}
			
			//Отрисовка значения
			int del = (getWidth() / div * 3) - (getWidth() / div * 2); //Ширина одного деления
			float width = (float) getFont().getStringBounds(getValue() + "", new FontRenderContext(null, false, true)).getWidth();
			float mest = (del - width) / 2;
			g2d.drawString(getValue() + "", (getWidth() / 5) * 2 + mest, getHeight() / 2 + 3);
		} else {
			g2d.clearRect(0, 0, getWidth(), getHeight());
			tm++;
			if (tm == 15) {
				tm = 0;
				if (inc) {
					pos++;
					if (pos == div-1) {
						inc = false;
					}
				} else {
					pos--;
					if (pos == 0) {
						inc = true;
					}
				}
			}
			int del = (getWidth() / div * 3) - (getWidth() / div * 2); //Ширина одного деления
			g2d.fillRect((getWidth() / div) * pos, 0, del, getHeight());
		}
	}
	
}
