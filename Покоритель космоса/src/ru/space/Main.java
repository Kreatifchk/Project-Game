package ru.space;

import javax.swing.JFrame;

public class Main {
	
	static Game g = new Game();
	
	public static void main(String[] args) {
		g.setVisible(true);
		g.setSize(966,701);
		g.setResizable(false);
		g.setLocationRelativeTo(null);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Random r = new Random();
		int x = 0;;
		for (int i = 0; i <= 100; i++) {
			int p = r.nextInt(100) + 1;
			if (p >= 10 & p <= 90) {
				x++;
			}
		}
		System.out.println(x);*/
	}

}
