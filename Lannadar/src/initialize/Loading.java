package initialize;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class Loading extends JFrame {
	
	JProgressBar jpb = new JProgressBar();
	
	public Loading() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 561);
		setVisible(true);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(null);
		
		jpb.setIndeterminate(true);
		jpb.setBounds(200, 226, 200, 29);
		
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(jpb, BorderLayout.CENTER);
		
		new Initialize(); //Инициализация всего
		dispose();
	}
	
}
