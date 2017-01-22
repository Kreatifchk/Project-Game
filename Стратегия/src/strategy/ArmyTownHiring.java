package strategy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import warriors.Archer;
import warriors.Cavalry;
import warriors.Footman;

/** Окно наема армии */
@SuppressWarnings("serial")
public class ArmyTownHiring extends JLabel implements MouseListener, ActionListener {
	
	String name;
	int townId;
	
	boolean ent;
	
	ExitButton exit;
	
	ArmButton[] arm = new ArmButton[3];
	
	public ArmyTownHiring() {
		setSize(498, 470);
		
		exit = new ExitButton();
		
		setOpaque(true);
		setBackground(new Color(150, 75, 0));
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
		setLocation((1000 - getWidth()) / 2, 25);
		
		add(exit);
		armAdd();
	}
	
	private void armAdd() {
		arm[0] = new ArmButton(new Footman(0));
		arm[1] = new ArmButton(new Archer(0));
		arm[2] = new ArmButton(new Cavalry(0));
		
		int x = 4, y = 49;
		int size = 98;
		for (int i = 0; i < arm.length; i++) {
			//arm[i] = new ArmButton(new Footman(0));
			arm[i].setBounds(x, y, size, size);
			arm[i].addActionListener(this);
			
			arm[i].setIcon(Resize.resizeIcon(arm[i].ta.icon.getImage(), size, size));
			add(arm[i]);
			x += size;
		}
	}
	
	private void armDel() {
		for (int i = 0; i < arm.length; i++) {
			remove(arm[i]);
			arm[i] = null;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < arm.length; i++) {
			if (e.getSource() == arm[i]) {
				if (Game.town.get(townId).line.size() < 12) {
					if (Game.town.get(townId).army == true) {
						//Если армия в городе уже есть
						int och = Game.town.get(townId).line.size() + 1;
						int numb = Game.emp.get(0).troop.get(Game.town.get(townId).idArmy).arm.size();
						if (och + numb <= 12) {
							//Если поставив в очередь новых юнитов их не станет больше лимита
							Game.town.get(townId).line.add(arm[i].ta);
						}
					} else {
						Game.town.get(townId).line.add(arm[i].ta);
					}
				}
				repaint();
				armDel();
				armAdd();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent a) {
		if (a.getSource() == exit) {
			Game.jlp.remove(this);
			Game.jlp.repaint();
		}
	}
	@Override
	public void mouseEntered(MouseEvent a) {
		if (a.getSource() == exit) {
			ent = true;
			exit.repaint();
		}
	}
	@Override
	public void mouseExited(MouseEvent a) {
		if (a.getSource() == exit) {
			ent = false;
			exit.repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent a) {
	}
	@Override
	public void mouseReleased(MouseEvent a) {
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(255, 255, 128));
		setFont(new Font("Garamond", Font.BOLD, 25));
		g2d.drawString(name, (getWidth() - name.length()*25) / 2, 30);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5));
		int y = 46;
		g2d.drawLine(4, y, getWidth()-4, y);
		y = 345;
		g2d.drawLine(4, y, getWidth()-4, y);
		
		//int x = 4;
		int x = (getWidth() - 8 - 360) / 2;
		y = 348;
		for (int i = 0; i < Game.town.get(townId).line.size(); i++) {
			if (i == 6) {
				y += 60;
				x = (getWidth() - 8 - 360) / 2;
			}
			Image img = Game.town.get(townId).line.get(i).icon.getImage();
			img = Resize.resize(img, 60, 60);
			g2d.drawImage(img, x, y, null);
			
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x + 20, y + 20, 20, 20);
			g2d.fillOval(x + 20, y + 20, 20, 20);
			g2d.setColor(Color.RED);
			setFont(new Font("Garamond", Font.BOLD, 20));
			g2d.drawString("" + (i+1), x + 24, y + 36);
			
			x += 60;
		}
	}
	
	private class ArmButton extends JButton {
		TypeArmy ta;
		public ArmButton(TypeArmy ta) {
			this.ta = ta;
		}
	}
	
	public class ExitButton extends JButton {
		public ExitButton() {
			setBounds(ArmyTownHiring.this.getWidth()-44, 4, 40, 40);
			setOpaque(false);
			setBorder(null);
			addMouseListener(ArmyTownHiring.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.GRAY);
			if (ent == true) {
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
			g2d.setColor(Color.WHITE);
			g2d.drawLine(0, 0, 40, 40);
			g2d.drawLine(0, 40, 40, 0);
		}
	}

}
