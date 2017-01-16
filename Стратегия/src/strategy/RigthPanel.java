package strategy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class RigthPanel extends JLabel implements MouseListener {
	
	static String name;
	static int townId;
	
	InfLabel inform = new InfLabel();
	JLabel building = new JLabel();
	JLabel arms = new JLabel();
	JLabel empery = new JLabel();
	JLabel end = new JLabel(); //Кнопка - конец хода
	
	ArmyTownHiring ath;
	
	public RigthPanel() {
		super();
		building.setBounds(4, 4, 46, 76);
		building.setOpaque(true);
		building.setBackground(Color.ORANGE);
		building.addMouseListener(this);
		add(building);
		
		arms.setBounds(4, 80, 46, 88);
		arms.setOpaque(true);
		arms.setBackground(Color.MAGENTA);
		arms.addMouseListener(this);
		add(arms);
		
		empery.setBounds(50, 104, 72, 51);
		empery.setOpaque(true);
		empery.setBackground(Color.CYAN);
		empery.addMouseListener(this);
		add(empery);
		
		end.setBounds(122, 104, 78, 51);
		end.setOpaque(true);
		end.setBackground(Color.GREEN);
		end.addMouseListener(this);
		add(end);
		
		inform.setBounds(50, 4, 145, 100);
		inform.setOpaque(true);
		inform.setBackground(Color.PINK);
		add(inform);
	}

	@Override
	public void mouseClicked(MouseEvent a) {
		if (a.getSource() == arms & CenterPanel.focus == true) {
			ath = new ArmyTownHiring();
			ath.name = name;
			ath.townId = townId;
			Game.jlp.add(ath, new Integer(11));
		}
		if (a.getSource() == end) {
			new EndTurn();
			inform.repaint();
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	protected class InfLabel extends JLabel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.PINK);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setFont(new Font("Garamond", Font.BOLD, 17));
			g2d.setColor(Color.BLACK);
			if (name != null) {
				if (name.length() <= 3) {
					g2d.drawString(name, 55, 20);
				} else if (name.length() <= 5) {
					g2d.drawString(name, 45, 20);
				} else if (name.length() <= 8) {
					g2d.drawString(name, 35, 20);
				} else if (name.length() <= 10) {
					g2d.drawString(name, 25, 20);
				} else {
					g2d.drawString(name, 15, 20);
				}
			}
			g2d.drawString("Золото: " + Game.emp.get(0).gold, 10, 50);
			g2d.drawString("Ход: " + Game.turn, 10, 80);
		}
	}

}
