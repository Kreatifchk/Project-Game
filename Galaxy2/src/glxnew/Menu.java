package glxnew;

import javax.swing.JFrame;

public class Menu {
	
	//git
	
	static Game g;
	
	int x, y;
	
	public Menu() {
		//super("Galaxy2");
		//x = Integer.parseInt(JOptionPane.showInputDialog("Введите ширину"));
		//y = Integer.parseInt(JOptionPane.showInputDialog("Введите длинну"));
		start();
	}
	
	private void start() {
		g = new Game();
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setLocationRelativeTo(null);
		g.setResizable(false);
		g.setVisible(true);
	}
	
}
