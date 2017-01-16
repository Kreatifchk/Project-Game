package strategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CenterPanel extends JLabel implements MouseListener {
	
	static boolean focus;
	
	Color locked = new Color(220, 255, 120);
	Color active = new Color(255, 255, 10);
	Color notActive = new Color(245, 255, 71);
	
	CityButton city = new CityButton();
	ArmyButton army = new ArmyButton();
	AgentsButton agents = new AgentsButton();
	
	public CenterPanel() {
		city.setBounds(0, 0, 200, 20);
		city.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		add(city);
		army.setBounds(200, 0, 200, 20);
		army.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		add(army);
		agents.setBounds(400, 0, 200, 20);
		agents.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		add(agents);
	}
	
	//Если убрали фокус с города
	protected void locked() {
		city.setColor(locked);
		army.setColor(locked);
		agents.setColor(locked);
		repaint();
	}
	
	//Если нажали на город
	protected void unlocked() {
		city.setColor(notActive);
		army.setColor(notActive);
		agents.setColor(notActive);
		repaint();
	}
	
	private class CityButton extends JLabel {
		Color cl = locked;
		public CityButton() {
			addMouseListener(CenterPanel.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(cl);
			g2d.fillRect(0, 0, 200, 20);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Город", 80, 14);
		}
		public void setColor(Color cl) {
			this.cl = cl;
		}
	}
	
	private class ArmyButton extends JLabel {
		Color cl = locked;
		public ArmyButton() {
			addMouseListener(CenterPanel.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(cl);
			g2d.fillRect(0, 0, 200, 20);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Армия", 80, 14);
		}
		public void setColor(Color cl) {
			this.cl = cl;
		}
	}
	
	private class AgentsButton extends JLabel {
		Color cl = locked;
		public AgentsButton() {
			addMouseListener(CenterPanel.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(cl);
			g2d.fillRect(0, 0, 200, 20);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Агенты", 80, 14);
		}
		public void setColor(Color cl) {
			this.cl = cl;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (focus == true) {
			if (e.getSource() == city) {
				city.setColor(active);
				army.setColor(notActive);
				agents.setColor(notActive);
				repaint();
			}
			if (e.getSource() == army) {
				city.setColor(notActive);
				army.setColor(active);
				agents.setColor(notActive);
				repaint();
			}
			if (e.getSource() == agents) {
				city.setColor(notActive);
				army.setColor(notActive);
				agents.setColor(active);
				repaint();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
