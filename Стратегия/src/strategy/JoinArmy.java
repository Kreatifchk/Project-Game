package strategy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import others.ArrowButton;

/** Окно - объединение армий */
@SuppressWarnings("serial")
public class JoinArmy {
	
	int widthL = 302;
	
	int idArmy;
	int idArmy2;
	
	static boolean pressedShift;
	int one = -1;
	
	OneLabel ol;
	TwoLabel tl;
	
	ArrowButton ab = new ArrowButton(1);
	ArrowButton ab2 = new ArrowButton(2);
	
	JButton[] armyLeft = new JButton[12];
	JButton[] armyRigth = new JButton[12];
	
	ArrayList<Integer> selected = new ArrayList<Integer>();
	
	public JoinArmy(int idArmy, int idArmy2) {
		this.idArmy = idArmy;
		this.idArmy2 = idArmy2;
		
		ol = new OneLabel();
		tl = new TwoLabel();
		
		int x = (tl.getX() - (ol.getX() + ol.getWidth()) - 160)
				/ 2 + ol.getX() + ol.getWidth();
		ab.setBounds(x, 120, 160, 160);
		ab.addMouseListener(new Mouse());
		ab.setColor(Color.ORANGE);
		ab.setFocusable(false);
		ab2.setBounds(x, 220, 160, 160);
		ab2.addMouseListener(new Mouse());
		ab2.setColor(Color.ORANGE);
		ab2.setFocusable(false);
		
		Game.jlp.add(ol, new Integer(6));
		Game.jlp.add(tl, new Integer(6));
		Game.jlp.add(ab, new Integer(6));
		Game.jlp.add(ab2, new Integer(6));
		//Game.jlp.add(new Arrow(2), new Integer(10));
	}
	
	Exit exit = new Exit();
	Exit exit2 = new Exit();
	//Первое окно - первая армия
	private class OneLabel extends JLabel {
		public OneLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2;
			setBounds(x, 30, widthL, 438);
			addButton(-1);
			
			exit2.setBounds(getWidth()-40, 4, 36, 35);
			add(exit2);
			addMouseListener(new Mouse());
			setFocusable(false);
		}
		public void addButton(int razm) {
			if (razm == -1) {
				razm = CenterPanel.selected.size();
			}
			int start = CenterPanel.selected.get(0);
			int xx = 4, y = 42;
			int iter = 0; //Сколко раз прошел цикл
			for (int i = start; i < start + razm; i++) {
				armyLeft[i] = new JButton();
				if (iter == 3 || i == 6 || i == 9) {
					y += 98;
					xx = 4;
				}
				armyLeft[i].setBounds(xx, y, 98, 98);
				armyLeft[i].setIcon(Resize.resizeIcon
						(Game.emp.get(0).troop.get(idArmy).arm.get(i).icon.getImage()
								, 98, 98));
				armyLeft[i].addMouseListener(new Mouse());
				armyLeft[i].setFocusable(false);
				add(armyLeft[i]);
				xx += 98;
				iter++;
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
	
	//После отправки всей армии из города, не нанимаются войска!!
	//Второе окно - вторая армия
	private class TwoLabel extends JLabel {
		public TwoLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2 + 500;
			setBounds(x, 30, widthL, 438);
			addButton();

			exit.setBounds(getWidth()-40, 4, 36, 35);
			add(exit);
			addMouseListener(new Mouse());
			setFocusable(false);
		}
		public void addButton() {
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
				armyRigth[i].addMouseListener(new Mouse());
				armyRigth[i].setFocusable(false);
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
	
	@SuppressWarnings("static-access")
	private void remButton(JButton[] but) {
		try {
			for (int i = 0; i < but.length; i++) {
				but[i].getParent().remove(but[i]);
			}
		} catch (NullPointerException e) {
		}
	}
	
	//Возвращает стандартные рамки кнопкам
	protected void standBorder(JButton[] but) {
		try {
			for (int i = 0; i < but.length; i++) {
				if (but[i] != null) {
					but[i].setBorder(null);
					but[i].setBorder(new JButton().getBorder());
					but[i].repaint();
				}
			}
		} catch (NullPointerException e) {
		}
	}
	
	//Выделение войск
	private void select(JButton[] but, MouseEvent e) {
		for (int i = 0; i < but.length; i++) {
			if (e.getSource() == but[i]) {
				standBorder(armyLeft);
				standBorder(armyRigth);
				selected.clear();
				if (pressedShift == true) {
					if (one == -1) {
						one = i;
						but[i].setBorder(new LineBorder(Color.BLACK, 2));
						selected.add(new Integer(i));
					} else {
						for (int j = one; j <= i; j++) {
							but[j].setBorder(new LineBorder(Color.BLACK, 2));
							selected.add(new Integer(j));
						}
						one = -1;
					}
				} else {
					one = i;
					but[i].setBorder(new LineBorder(Color.BLACK, 2));
					selected.add(new Integer(i));
				}
			}
		}
		ol.repaint();
		tl.repaint();
	}
	
	enum type {LEFT, RIGTH};
	
	//Перемещение войск
	@SuppressWarnings("static-access")
	private void change(type type) {
		if (type == type.LEFT) {
			int sel = selected.get(0);
			for (int i = 0; i < selected.size(); i++) {
				TypeArmy x = Game.emp.get(0).troop.get(idArmy).arm.get(sel);
				Game.emp.get(0).troop.get(idArmy2).arm.add(x);
				Game.emp.get(0).troop.get(idArmy).arm.remove(sel);
			}
			remButton(armyLeft);
			remButton(armyRigth);
			ol.addButton(Game.emp.get(0).troop.get(idArmy).arm.size());
			tl.addButton();
		}
		if (type == type.RIGTH) {
			int sel = selected.get(0);
			for (int i = 0; i < selected.size(); i++) {
				TypeArmy x = Game.emp.get(0).troop.get(idArmy2).arm.get(sel);
				Game.emp.get(0).troop.get(idArmy).arm.add(x);
				Game.emp.get(0).troop.get(idArmy2).arm.remove(sel);
			}
			remButton(armyLeft);
			remButton(armyRigth);
			ol.addButton(Game.emp.get(0).troop.get(idArmy).arm.size());
			tl.addButton();
		}
		
		ol.repaint();
		tl.repaint();
		Game.downCenter.armButtonRemove();
		Game.downCenter.armies();
		Game.downCenter.repaint();
	}
	
	private class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == exit || e.getSource() == exit2) {
				Game.jlp.remove(ol);
				Game.jlp.remove(tl);
				Game.jlp.remove(ab);
				Game.jlp.remove(ab2);
				Game.jlp.repaint();
			}
			else if (e.getComponent().getParent() == ol) {
				select(armyLeft, e);
			}
			else if (e.getComponent().getParent() == tl) {
				select(armyRigth, e);
			}
			else if (e.getComponent() == ab) {
				change(type.LEFT);
				selected.clear();
				standBorder(armyLeft);
				standBorder(armyRigth);
			}
			else if (e.getComponent() == ab2) {
				change(type.RIGTH);
				selected.clear();
				standBorder(armyLeft);
				standBorder(armyRigth);
			}
			else {
				selected.clear();
				standBorder(armyLeft);
				standBorder(armyRigth);
				one = -1;
				ol.repaint();
				tl.repaint();
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
