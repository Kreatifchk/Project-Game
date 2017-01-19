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
	
	private class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (owner == 0) {
				CenterPanel.idArmy = id;
				Game.downCenter.clickArmy();
			}
		}
	}
	
}
