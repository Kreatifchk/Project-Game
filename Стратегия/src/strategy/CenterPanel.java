package strategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CenterPanel extends JLabel implements MouseListener {
	
	static boolean focus;
	static int townId;
	
	Color locked = new Color(220, 255, 120);
	Color active = new Color(255, 255, 10);
	Color notActive = new Color(245, 255, 71);
	
	//Кнопки - переключатели панелей
	CityButton city = new CityButton();
	ArmyButton army = new ArmyButton();
	AgentsButton agents = new AgentsButton();
	
	//Меняющиеся панели с различным содержимым
	JLabel current;
	JLabel armyL = new JLabel();
	JLabel cityL = new JLabel();
	JLabel agentsL = new JLabel();
	
	//Кнопки отрядов
	ArmButton[] arb = new ArmButton[12];
	
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
		
		armyL.setBounds(0, 20, 600, 135);
		armyL.addMouseListener(this);
		cityL.setBounds(0, 20, 600, 135);
		cityL.addMouseListener(this);
		add(cityL);
		agentsL.setBounds(0, 20, 600, 135);
		agentsL.addMouseListener(this);
		
		current = armyL;
	}
	
	protected void armies() {
		for (int i = 0; i < Game.emp.get(0).troop.size(); i++) {
			if (Game.emp.get(0).troop.get(i).id == Game.town.get(townId).idArmy) {
				//Если армия в городе найдена - отобразить ее
				//Проверить с какого элемента заполнять массив
				int size = 0;
				for (int z = 0; z < arb.length; z++) {
					if (arb[z] == null) {
						size = z;
						break;
					}
				}
				//Заполнять arb массив
				int x = 10, y = 7;
				for (int j = 0; j < Game.emp.get(0).troop.get(i).arm.size(); j++) {
					arb[size] = new ArmButton(Game.emp.get(0).troop.get(i).arm.get(j));
					arb[size].setIcon(arb[size].ta.icon);
					arb[size].setBounds(x, y, 100, 100);
					armyL.add(arb[size]);
					size++;
					x += 100;
				}
			}
		}
		repaint();
	}
	
	//Если убрали фокус с города
	protected void locked() {
		city.setColor(locked);
		army.setColor(locked);
		agents.setColor(locked);
		
		remove(current);
		
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
	
	private class ArmButton extends JButton {
		TypeArmy ta;
		public ArmButton(TypeArmy ta) {
			this.ta = ta;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (focus == true) {
			if (e.getSource() == city) {
				city.setColor(active);
				army.setColor(notActive);
				agents.setColor(notActive);
				
				remove(current);
				add(cityL);
				current = cityL;
				
				repaint();
			}
			if (e.getSource() == army) {
				city.setColor(notActive);
				army.setColor(active);
				agents.setColor(notActive);
				
				remove(current);
				add(armyL);
				armies();
				current = armyL;
				
				repaint();
			}
			if (e.getSource() == agents) {
				city.setColor(notActive);
				army.setColor(notActive);
				agents.setColor(active);
				
				remove(current);
				add(agentsL);
				current = agentsL;
				
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
