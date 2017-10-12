﻿package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import base.Game;

/**
 * 
 * Класс панель содержащая инвертарь, квесты, навыки и информацию о персонаже.
 *
 */
@SuppressWarnings("serial")
public class HeroPanel extends JPanel implements ActionListener {
	
	static boolean qw;
	
	Image bc = new ImageIcon(getClass().getResource("/base/res/others/heroPanel.png")).getImage();
	Image exitI = new ImageIcon(getClass().getResource("/base/res/others/exit.png")).getImage();
	
	public JButton exit = new exitButton();

	HeroPanelButton.HeroButton hero;
	HeroPanelButton.QwestsButton qwests;
	HeroPanelButton.BagButton bag;
	HeroPanelButton.SkillsButton skills;
	
	//QwestButton[] qwestsM; //Кнопки квесты
	
	JLabel x; //Текующая панель
	
	JLabel infoP; //Панель с информацией о персонаже
	JLabel qwestsP; //Панель с квестами
	
	JLabel textQwest = new JLabel(); //Контейнер в который будет вложен контейнер с текстом
	JLabel reqBase = new JLabel();
	
	JScrollBar jsb = new JScrollBar();
	JScrollBar jsb2 = new JScrollBar();
	
	int nQwest; //Количество взятых квестов
	//String nameQ;
	
	public HeroPanel() {
		new HeroPanelButton();
		
		hero = new HeroPanelButton().hb;
		qwests = new HeroPanelButton().qb;
		bag = new HeroPanelButton().bb;
		skills = new HeroPanelButton().sb;
		
		exit.setBounds(645, 88, 30, 30);
		exit.setBorderPainted(false);
		exit.setOpaque(false);
		exit.addActionListener(this);
		hero.setBounds(42, 87, 120, 31);
		hero.setOpaque(false);
		hero.addActionListener(this);
		qwests.setBounds(162, 87, 120, 31);
		qwests.setOpaque(false);
		qwests.addActionListener(this);
		bag.setBounds(282, 87, 120, 31);
		bag.setOpaque(false);
		skills.setBounds(402, 87, 120, 31);
		skills.setOpaque(false);
		add(exit);
		add(hero);
		add(qwests);
		add(bag);
		add(skills);
		
		infoP = new HeroPanelInfo();
		infoP.setBounds(42, 123, 636, 451);
		add(infoP);
		
		x = infoP;
		exit.addMouseListener(new Game.NpcListener());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bc, 35, 80, null);
		
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawLine(44, 120, 676, 120);
	}
	
	public class QwestButton extends JButton {
		String name, text, request;
		int progress, max;
		public QwestButton(String name) {
			this.name = name;
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setFont(new Font("Times new Roman", Font.BOLD, 20));
			g2d.setColor(Color.BLACK);
			g2d.drawString(name, 5, 20);
		}
		public void paintBorder(Graphics g) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == qwests) {
			remove(x);
			qwestsP = new HeroPanelQwests().qp;
			x = qwestsP;
			add(x);
		}
		
		if (a.getSource() == hero) {
			remove(x);
			textQwest.remove(jsb);
			textQwest.remove(jsb2);
			add(infoP);
			x = infoP;
			
			qwestsP = null;
		}
		
		if (a.getSource() == exit) {
			remove(x);
			x = infoP;
			add(infoP);
			
			qwestsP = null;
			/*textQwest.remove(jsb);
			textQwest.remove(jsb2);
			if (textRequest != null & textQwestDop != null) {
				textQwest.remove(textQwestDop);
				reqBase.remove(textRequest);
			}*/
			
			//Стереть кнопки квесты
			/*for (int i = 0; i <= qwestsM.length - 1; i++) {
				if (qwestsM[i] != null) {
					qwestsP.remove(qwestsM[i]);
				}
			}*/
		}
	}
	
	
	//Кнопка exit
	private class exitButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(exitI, 0, 0, null);
		}
		public void paintBorder(Graphics g) {
		}
	}
	
}