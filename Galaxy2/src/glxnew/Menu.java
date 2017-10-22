package glxnew;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

public class Menu {
	
	static Game g;
	
	int x, y;
	
	static Font calibri;
	
	public Menu() {
		//super("Galaxy2");
		//x = Integer.parseInt(JOptionPane.showInputDialog("Введите ширину"));
		//y = Integer.parseInt(JOptionPane.showInputDialog("Введите длинну"));
		start();
	}
	
	private void start() {
		initial();
		
		g = new Game();
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setLocationRelativeTo(null);
		g.setResizable(false);
		g.setVisible(true);
	}
	
	private void initial() {
		try {
			InputStream s = getClass().getResourceAsStream("res/calibri.ttf");
			calibri = Font.createFont(Font.TRUETYPE_FONT, s).deriveFont(24F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
