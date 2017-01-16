package strategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
	
	JButton start = new JButton("Начать игру");
	
	static Game g;
	
	public Menu() {
		super("Стратегия");
		setLayout(null);
		
		start.setBounds(215, 50, 170, 30);
		start.addActionListener(this);
		
		add(start);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			dispose();
			g = new Game();
			g.setSize(1000, 700);
			g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			g.setLocationRelativeTo(null);
			g.setResizable(false);
			g.setVisible(true);
			//g.setLocation(g.getX() + 1000, g.getY());
		}
	}
	
}
