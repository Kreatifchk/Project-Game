package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class GUICenter extends JLabel {
	
	String nameWind; //Имя окна
	
	public GUICenter() {
		
		int widthInf = Main.windWidth / 100 * 58;
		int heightInf = Main.windHeight / 100 * 85;
		
		setBounds((Main.windWidth - widthInf) / 2,
				(Main.windHeight - heightInf) /2, widthInf, heightInf);
		
		setBorder(BorderFactory.createLineBorder(new Color(174, 31, 182), 4));
		addMouseListener(Main.gf);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(247, 234, 159));
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
