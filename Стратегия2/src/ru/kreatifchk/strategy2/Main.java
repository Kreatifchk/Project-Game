package ru.kreatifchk.strategy2;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	
	static GameFrame gf;
	
	static double height, width;
	static int windHeight, windWidth;
	
	public static void main(String[] args) {
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		windHeight = (int)(height / 100 * 85);
		windWidth = (int)(width / 100 *  85);
		
		gf = new GameFrame(25, 25);
		gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gf.setSize(windWidth, (int) (windHeight + height / 100 * 5));
		gf.setVisible(true);
		gf.setResizable(false);
		gf.setLocationRelativeTo(null);
	}
	
}
