package ru.kreatifchk.main;

import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;

import ru.kreatifchk.editor.Editor;

public class Main {
	
	static LoadingGame load;
	
	/** Переменная - разница размергов объектов, зависящая от разрешения */
	public static float INC;
	
	public static Insets insets; //Размеры рамок окна и строки заголовка
	public static int WIDTH, HEIGHT; //Постоянные размеры окна

	public static void main(String[] args) {
		//System.setProperty("sun.java2d.opengl", "true");
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		if (width >= 1920 & height >= 1080) {
			INC = 1.5F;
		} else {
			INC = 1;
		}
		
		load = new LoadingGame();
		load.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		load.setVisible(true);
		load.setLocationRelativeTo(null);
		load.setResizable(false);
		
		insets = load.getInsets();
		
		WIDTH = (int)(960*Main.INC)+insets.left+insets.left;
		HEIGHT = (int)(672*Main.INC)+insets.top;
		
		load.setVisible(false);
		
		if (args.length > 0) {
			new Editor().setFileParametr(new File(args[0]));
		} else {
			new Menu();
		}
		
		load.dispose();
	}

}
