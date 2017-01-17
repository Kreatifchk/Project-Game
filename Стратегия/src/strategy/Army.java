package strategy;

import java.util.ArrayList;

import javax.swing.JLabel;

/** Класс для армии на карте */
@SuppressWarnings("serial")
public class Army extends JLabel {
	
	int id;
	int owner; //Владелец
	
	int town; //В каком городе находится, если вне города то -1
	int tile; //На каком тайле
	
	ArrayList<TypeArmy> arm = new ArrayList<TypeArmy>(); //Типы отрядов
	
	public Army() {
	}
	
	public Army setTown(int town) {
		this.town = town;
		return this;
	}
	
	public Army setId(int id) {
		this.id = id;
		return this;
	}
	
}
