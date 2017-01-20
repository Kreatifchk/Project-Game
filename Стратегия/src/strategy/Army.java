package strategy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** Класс для армии на карте */
@SuppressWarnings("serial")
public class Army extends JLabel {
	
	int id;
	int owner = -1; //Владелец
	
	int town; //В каком городе находится, если вне города то -1
	int tile; //На каком тайле
	
	ImageIcon icon = new ImageIcon(getClass().getResource("res/pers.png"));
	
	ArrayList<TypeArmy> arm = new ArrayList<TypeArmy>(); //Типы отрядов
	
	public Army() {
		setIcon(icon);
		addMouseListener(new Mouse());
	}
	
	public Army setTown(int town) {
		this.town = town;
		return this;
	}
	
	public Army setId(int id) {
		this.id = id;
		return this;
	}
	
	public Army setOwner(int owner) {
		this.owner = owner;
		return this;
	}
	
	//Слияние отрядов в случае если их общее кол-во не больше 12
	private void merge() {
		int sel = CenterPanel.selected.get(0);
		for (int i = 0; i < CenterPanel.selected.size(); i++) {
			TypeArmy x = Game.emp.get(0).troop.get(CenterPanel.idArmy).arm.get(sel);
			arm.add(x);
			Game.emp.get(0).troop.get(CenterPanel.idArmy).arm.remove(sel);
		}
		Game.downCenter.armButtonRemove();
		Game.downCenter.armies();
		Game.downCenter.removeBord();
		Game.downCenter.repaint();
	}
	
	private class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//Клик мышью по армии
			if (owner == 0) {
				if (CenterPanel.bordB != true) {
					//Если мы при этом не размещаем войско
					CenterPanel.idArmy = id;
					Game.downCenter.clickArmy();
				} else {
					//Если размещаем
					if (arm.size() + CenterPanel.selected.size() <= 12) {
						//Если общее кол-во отрядов меньше или равно 12
						merge();
					} else {
						//Иначе создаем окно с выбором кого перенести, кого оставить
						new JoinArmy(id);
					}
				}
			}
		}
	}
	
}
