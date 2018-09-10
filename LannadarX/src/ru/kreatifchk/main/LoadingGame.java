package ru.kreatifchk.main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Img;

@SuppressWarnings("serial")
public class LoadingGame extends JFrame {
	
	ImageIcon logoI;
	JLabel logo = new JLabel();
	JProgressBar jpb = new JProgressBar();
	
	public LoadingGame() {
		setTitle("Lannadar");
		setSize((int)(960*Main.INC), (int)(672*Main.INC));
		setLayout(null);
		
		//Можно такой вариант "../res/image/logo.png" , но он работает только в IDE
		logoI = Img.ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/logo.png"));
		
		logo.setSize(logoI.getIconWidth(), logoI.getIconHeight());
		Center.cnt(logo, this);
		logo.setIcon(logoI);
		
		jpb.setIndeterminate(true);
		jpb.setSize(logo.getWidth() * 1, (int)(logo.getHeight() / 8));
		jpb.setLocation(logo.getX(), (int)(430 * Main.INC));
		
		getContentPane().setBackground(Color.black);
		add(logo);
		add(jpb);
		
		new Initialization();
	}
	
}
