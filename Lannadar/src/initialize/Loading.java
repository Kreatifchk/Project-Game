package initialize;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import base.Game;

@SuppressWarnings("serial")
public class Loading extends JFrame {
	
	JProgressBar jpb = new JProgressBar();
	
	JLabel logo = new JLabel();
	
	ImageIcon logoI = new ImageIcon(Game.class.getResource("res/Image/logo.png"));
	
	public Loading() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 561);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//getContentPane().setLayout(null);
		
		logo.setIcon(logoI);
		logo.setBounds((getWidth()-352) / 2, (getHeight() - 107) / 2 - 50, 352, 107);
		
		jpb.setIndeterminate(true);
		jpb.setBounds(200, 336, 200, 29);
		
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(logo);
		getContentPane().add(jpb);
		
		new Initialize(); //Инициализация всего
		dispose();
	}
	
}
