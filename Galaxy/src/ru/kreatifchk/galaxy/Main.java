package ru.kreatifchk.galaxy;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		/*SquareColor g = new SquareColor();
		g.setSize(800, 600);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setVisible(true);
		g.setResizable(false);
		g.setLocationRelativeTo(null);*/
		
		Game g = new Game();
		g.setSize(1100, 649); //860 660
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setVisible(true);
		g.setResizable(false);
		g.setLocationRelativeTo(null);
	}

}
