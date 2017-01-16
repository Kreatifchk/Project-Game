package strategy;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		Menu m = new Menu();
		m.setSize(600, 600);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setLocationRelativeTo(null);
		m.setResizable(false);
		m.setVisible(true);
	}
	
}
