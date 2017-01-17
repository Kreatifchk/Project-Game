package rpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class About extends JFrame {
	
	Image bc = new ImageIcon(getClass().getResource("res/bc.png")).getImage();
	Image icon = new ImageIcon(getClass().getResource("res/icon.png")).getImage();
	
	MenuPanel background = new MenuPanel();
	JLabel author = new JLabel("Создатель: Kreatifchk");
	JLabel infMusic = new JLabel("Использована музыка с audionautix.com");
	
	JButton back = new JButton("Назад в меню");
	
	public About() {
		super("Lannadar");
		setLayout(null);
		setIconImage(icon);
		background.setBounds(0, 0, 600, 541);
		background.setLayout(null);
		
		Font font = new Font("Verdana", Font.BOLD, 20);
		
		author.setBounds(160, 10, 400, 30);
		author.setFont(font);
		author.setForeground(new Color(0, 0, 255));
		
		infMusic.setBounds(60, 50, 480, 30);
		infMusic.setFont(font);
		infMusic.setForeground(Color.BLUE);
		
		back.setBounds(225, 470, 140, 30);
		back.addActionListener(new MenuListener());
		
		add(background);
		background.add(author);
		background.add(infMusic);
		background.add(back);
	}
	
	private class MenuPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(bc, 0, 0, null);
		}
	}
	
	public class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == back) {
				dispose();
				Menu m = new Menu();
				m.setVisible(true);
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setSize(600, 561); //541
				m.setResizable(false);
				m.setLocationRelativeTo(null);
			}
		}
	}
	
}
