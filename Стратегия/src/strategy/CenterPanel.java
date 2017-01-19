package strategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class CenterPanel extends JLabel implements MouseListener {
	
	static boolean focus;
	static int townId = - 1;
	static int idArmy; //id армии на панеле
	static boolean pressedShift; //Если нажата shift
	static boolean selection; //Выделены ли войска
	
	int one = -1; //На какого юнита нажал в начале (для выделения)
	
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
	static ArmButton[] arb = new ArmButton[12];
	static ArrayList<Integer> selected = new ArrayList<Integer>();
	
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
		
		addKeyListener(new Key());
	}
	
	protected void addPanel() {
		armyL.setBounds(0, 20, 600, 135);//135
		armyL.addMouseListener(this);
		cityL.setBounds(0, 20, 600, 135);
		cityL.addMouseListener(this);
		add(cityL);
		agentsL.setBounds(0, 20, 600, 135);
		agentsL.addMouseListener(this);
		
		current = cityL;
	}
	
	protected void removePanel() {
		try {
			remove(current);
			current = null;
		} catch (NullPointerException e) {
		}
	}
	
	//Удаляет кнопки - отряды, чтобы потом поставить их заново
	protected void armButtonRemove() {
		for (int i = 0; i < arb.length; i++) {
			try {
				armyL.remove(arb[i]);
				repaint();
			} catch (NullPointerException e) {
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			arb[i] = null;
		}
	}
	
	//Устанавливает войска на панели
	protected void armies() {
		for (int i = 0; i < Game.emp.get(0).troop.size(); i++) {
			if (Game.emp.get(0).troop.get(i).id == Game.town.get(townId).idArmy) {
				//Если армия в городе найдена - отобразить ее
				//Заполнять arb массив (отриосвка в нижней панели войск)
				int sizeT = 68; //Размеры изображения и кнопок
				int x = (getWidth() - sizeT * 6) / 2, y = 0;
				for (int j = 0; j < Game.emp.get(0).troop.get(i).arm.size(); j++) {
					if (j == 6) {
						y += sizeT;
						x = (getWidth() - sizeT * 6) / 2;
					}
					if (arb[j] == null) {
						arb[j] = new ArmButton(Game.emp.get(0).troop.get(i).arm.get(j));
					}
					arb[j].setIcon(Resize.resizeIcon(arb[j].ta.icon.getImage(), sizeT, sizeT));
					arb[j].setBounds(x, y, sizeT, sizeT);
					arb[j].setFocusable(false);
					armyL.add(arb[j]);
					x += sizeT;
				}
				idArmy = i;
				break;
			}
		}
		repaint();
	}
	
	//Если убрали фокус с города
	protected void locked() {
		city.setColor(locked);
		army.setColor(locked);
		agents.setColor(locked);
		
		try {
			remove(current);
		} catch (NullPointerException e) {
		}
		
		repaint();
	}
	
	//Возвращает стандартные рамки кнопкам
	protected void standBorder() {
		try {
			for (int i = 0; i < arb.length; i++) {
				arb[i].setBorder(new JButton().getBorder());
			}
		} catch (NullPointerException e) {
		}
	}
	
	//Выход армии из города
	protected static void outputArmy(int xR, int yR) {
		//Аргументы: x и y координата где размещать войско
		int size = Game.emp.get(0).troop.size();
		//Создаем новую армию
		Game.emp.get(0).troop.add(new Army().setTown(-1).setId(size).setOwner(0));
		Game.emp.get(0).troop.get(size).setBounds(xR-18, yR-18, 36, 36);
		//Определяем на каком тайле находится город и ставим там армию
		int xTile = Game.town.get(townId).x, yTile = Game.town.get(townId).y;
		Game.bcTiles[xTile][yTile].add(Game.emp.get(0).troop.get(size));
		
		int sel = selected.get(0); //Удалять всегда будем 1 элемент
		//потому что размер массива с каждым удалением будет скоращаться
		//и элементы сдвигаться влево
		for (int i = 0; i < selected.size(); i++) {
			TypeArmy x = Game.emp.get(0).troop.get(idArmy).arm.get(sel);
			//Добавляем отряды в новую армию
			Game.emp.get(0).troop.get(size).arm.add(x);
			//Убираем из старой
			Game.emp.get(0).troop.get(idArmy).arm.remove(sel);
		}
		//Если отрядов больше не осталось - удалить армию
		if (Game.emp.get(0).troop.get(idArmy).arm.size() == 0) {
			Game.emp.get(0).troop.remove(idArmy);
		}
		Game.downCenter.armButtonRemove();
		Game.downCenter.armies();
		Game.downCenter.removeBord();
		Game.downCenter.repaint();
	}
	
	//Происходит при клике на армию вне города
	protected void clickArmy() {
		if (focus == true) {
			focus = false;
			city.setColor(locked);
			agents.setColor(locked);
			
			changeArmy();
			
			repaint();
		} else {
			add(armyL);
			current = armyL;
			army.setColor(active);
			changeArmy();
			repaint();
		}
	}
	
	//Меняет армию на панеле с городской на внегородскую или отрисовывает с нуля
	private void changeArmy() {
		armButtonRemove();
		int sizeT = 68;
		int x = (getWidth() - sizeT * 6) / 2, y = 0;
		for (int i = 0; i < Game.emp.get(0).troop.get(idArmy).arm.size(); i++) {
			if (i == 6) {
				y += sizeT;
				x = (getWidth() - sizeT * 6) / 2;
			}
			if (arb[i] == null) {
				arb[i] = new ArmButton(Game.emp.get(0).troop.get(idArmy).arm.get(i));
			}
			arb[i].setIcon(Resize.resizeIcon(arb[i].ta.icon.getImage(), sizeT, sizeT));
			arb[i].setBounds(x, y, sizeT, sizeT);
			arb[i].setFocusable(false);
			armyL.add(arb[i]);
			x += sizeT;
		}
	}
	
	//Если нажали на город
	protected void unlocked() {
		city.setColor(notActive);
		city.setEnabled(true);
		army.setColor(notActive);
		agents.setColor(notActive);
		agents.setEnabled(true);
		repaint();
	}
	
	static JLabel bord;
	//Показывает где можно разместить войска
	private void bord() {
		int x = Game.town.get(townId).getX(), y = Game.town.get(townId).getY();
		int xT = Game.town.get(townId).x, yT = Game.town.get(townId).x;
		if (bord == null) {
			bord = new JLabel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D) g;
					g2d.setColor(new Color(32, 32, 128, 128));
					g2d.fillRect(0, 0, 102, 102);
				}
			};
			bord.setBounds(x-30, y-30, 102, 102);
			Game.bcTiles[xT][yT].add(bord);
			Game.bcTiles[xT][yT].repaint();
		}
	}
	
	//Убирает рамку размещения войска
	protected void removeBord() {
		if (bord != null) {
			JComponent x = (JComponent) bord.getParent();
			x.remove(bord);
			x.repaint();
			bord = null;
		}
	}
	
	protected class CityButton extends JLabel {
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
			addMouseListener(CenterPanel.this);
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
				
				selection = false;
				selected.clear();
				removeBord();
				
				repaint();
			}
			else if (e.getSource() == army) {
				city.setColor(notActive);
				army.setColor(active);
				agents.setColor(notActive);
				
				remove(current);
				add(armyL);
				armies();
				current = armyL;
				
				selection = false;
				selected.clear();
				removeBord();
				
				repaint();
			}
			else if (e.getSource() == agents) {
				city.setColor(notActive);
				army.setColor(notActive);
				agents.setColor(active);
				
				remove(current);
				add(agentsL);
				current = agentsL;
				
				selection = false;
				selected.clear();
				removeBord();
				
				repaint();
			} else {
				standBorder();
				selection = false;
				selected.clear();
				removeBord();
			}
			for (int i = 0; i < arb.length; i++) {
				if (e.getComponent() == arb[i]) {
					if (pressedShift == true) {
						standBorder();
						if (one == - 1) {
							arb[i].setBorder(new LineBorder(Color.BLACK, 2));
							one = i;
						} else {
							for (int z = one; z <= i; z++) {
								arb[z].setBorder(new LineBorder(Color.BLACK, 2));
								selected.add(new Integer(z));
							}
							one = - 1;
						}
					} else {
						one = i;
						standBorder();
						arb[i].setBorder(new LineBorder(Color.BLACK, 2));
						selected.add(new Integer(i));
					}
					bord();
					selection = true;
					break;
				}
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
