package heroPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class HeroPanelButton extends MouseAdapter {
	
	static Image buttonUT;
	static Image buttonT;
	static Image button1, button2, button3, button4, button5;
	
	static Image name1, name2, name3, name4, name5;
	
	HeroButton hb = new HeroButton();
	QwestsButton qb = new QwestsButton();
	BagButton bb = new BagButton();
	SkillsButton sb = new SkillsButton();
	MapButton mb = new MapButton();
	
	public HeroPanelButton() {
		buttonUT = new ImageIcon(getClass().getResource("/base/res/others/button.png")).getImage();
		buttonT = new ImageIcon(getClass().getResource("/base/res/others/buttonT.png")).getImage();
		
		name1 = new ImageIcon(getClass().getResource("/base/res/others/hero.png")).getImage();
		name2 = new ImageIcon(getClass().getResource("/base/res/others/qwests.png")).getImage();
		name3 = new ImageIcon(getClass().getResource("/base/res/others/bag.png")).getImage();
		name4 = new ImageIcon(getClass().getResource("/base/res/others/skills.png")).getImage();
		name5 = new ImageIcon(getClass().getResource("/base/res/others/map.png")).getImage();
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getComponent() == hb) {
			button1 = buttonT;
		}
		if (e.getComponent() == qb) {
			button2 = buttonT;
		}
		if (e.getComponent() == bb) {
			button3 = buttonT;
		}
		if (e.getComponent() == sb) {
			button4 = buttonT;
		}
		if (e.getComponent() == mb) {
			button5 = buttonT;
		}
	}
	public void mouseReleased(MouseEvent e) {
		if (e.getComponent() == hb) {
			button1 = buttonUT;
		}
		if (e.getComponent() == qb) {
			button2 = buttonUT;
		}
		if (e.getComponent() == bb) {
			button3 = buttonUT;
		}
		if (e.getComponent() == sb) {
			button4 = buttonUT;
		}
		if (e.getComponent() == mb) {
			button5 = buttonUT;
		}
	}
	
	//Кнопка - персонаж
	public class HeroButton extends JButton {
		public HeroButton() {
			button1 = buttonUT;
			setBorder(null);
			addMouseListener(HeroPanelButton.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button1, 0, 0, null);
			g2d.drawImage(name1, 0, 0, null);
		}
	}
	
	//Кнопка - квесты
	public class QwestsButton extends JButton {
		public QwestsButton() {
			button2 = buttonUT;
			setBorder(null);
			addMouseListener(HeroPanelButton.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button2, 0, 0, null);
			g2d.drawImage(name2, 0, 0, null);
		}
	}
	
	//Кнопка - сумка
	public class BagButton extends JButton {
		public BagButton() {
			button3 = buttonUT;
			setBorder(null);
			addMouseListener(HeroPanelButton.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button3, 0, 0, null);
			g2d.drawImage(name3, 0, 0, null);
		}
	}
	
	//Кнопка - способности
	public class SkillsButton extends JButton {
		public SkillsButton() {
			button4 = buttonUT;
			setBorder(null);
			addMouseListener(HeroPanelButton.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button4, 0, 0, null);
			g2d.drawImage(name4, 0, 0, null);
		}
	}
	
	//Кнопка - карта
	public class MapButton extends JButton {
		public MapButton() {
			button5 = buttonUT;
			setBorder(null);
			addMouseListener(HeroPanelButton.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button5, 0, 0, null);
			g2d.drawImage(name5, 0, 0, null);
		}
	}
}
