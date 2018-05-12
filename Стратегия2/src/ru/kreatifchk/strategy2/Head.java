package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Head extends JLabel {
	
	FontRenderContext frc = new FontRenderContext(null, true, true);
	
	public Head() {
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setFont(new Font("Calibri", Font.BOLD, 25));
		g2d.setColor(new Color(179, 247, 241));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);
		
		String str = "Ход: " + GameFrame.turn;
		int strWidth = (int) g2d.getFont().getStringBounds(str, frc).getWidth();
		g2d.drawString(str, (GameFrame.head.getWidth() - strWidth) / 2, 25);
		
		g2d.drawString("Деньги: " + GameFrame.pl.money, 15, 25);
		
		str = "Очки действий: " + GameFrame.pl.pointAction;
		strWidth = (int) g2d.getFont().getStringBounds(str, frc).getWidth();
		g2d.drawString(str, GameFrame.head.getWidth() - strWidth - 15, 25);
	}
	
}
