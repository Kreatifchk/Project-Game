package strategy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import others.ArrowButton;

/** Окно - объединение армий */
@SuppressWarnings("serial")
public class JoinArmy {
	
	int widthL = 302;
	
	int idArmy2;
	
	OneLabel ol;
	TwoLabel tl;
	
	ArrowButton ab = new ArrowButton(1);
	ArrowButton ab2 = new ArrowButton(2);
	
	JButton[] armyLeft = new JButton[12];
	JButton[] armyRigth = new JButton[12];
	
	public JoinArmy(int idArmy) {
		idArmy2 = idArmy;
		
		ol = new OneLabel();
		tl = new TwoLabel();
		
		int x = (tl.getX() - (ol.getX() + ol.getWidth()) - 160)
				/ 2 + ol.getX() + ol.getWidth();
		ab.setBounds(x, 120, 160, 160);
		ab.setColor(Color.ORANGE);
		ab2.setBounds(x, 220, 160, 160);
		ab2.setColor(Color.ORANGE);
		
		Game.jlp.add(ol, new Integer(6));
		Game.jlp.add(tl, new Integer(6));
		Game.jlp.add(ab, new Integer(6));
		Game.jlp.add(ab2, new Integer(6));
		//Game.jlp.add(new Arrow(2), new Integer(10));
	}
	
	Exit exit = new Exit();
	//Первое окно - первая армия
	private class OneLabel extends JLabel {
		public OneLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2;
			setBounds(x, 30, widthL, 440);
			exit.setBounds(getWidth()-40, 4, 36, 35);
			add(exit);
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(4));
			g2d.drawLine(0, 40, getWidth(), 40);
		}
	}
	
	//После отправки всей армии из города, не нанимаются войска!!
	//Второе окно - вторая армия
	private class TwoLabel extends JLabel {
		public TwoLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2 + 500;
			setBounds(x, 30, widthL, 438);
			int razm = Game.emp.get(0).troop.get(idArmy2).arm.size();
			int xx = 4, y = 42;
			for (int i = 0; i < razm; i++) {
				armyRigth[i] = new JButton();
				if (i == 3 || i == 6 || i == 9) {
					y += 98;
					xx = 4;
				}
				armyRigth[i].setBounds(xx, y, 98, 98);
				armyRigth[i].setIcon(Resize.resizeIcon
						(Game.emp.get(0).troop.get(idArmy2).arm.get(i).icon.getImage()
								, 98, 98));
				add(armyRigth[i]);
				xx += 98;
			}
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(4));
			g2d.drawLine(0, 40, getWidth(), 40);
		}
	}
	
	private class Exit extends JLabel {
		boolean ent;
		public Exit() {
			addMouseListener(new Mouse());
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			if (ent == true) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawLine(0, 0, getWidth(), getHeight());
			g2d.drawLine(0, getHeight(), getWidth(), 0);
		}
	}
	
	private class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == exit) {
				Game.jlp.remove(ol);
				Game.jlp.remove(tl);
				Game.jlp.remove(ab);
				Game.jlp.remove(ab2);
				Game.jlp.repaint();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == exit) {
				exit.ent = true;
				exit.repaint();
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == exit) {
				exit.ent = false;
				exit.repaint();
			}
		}
	}
	
}
