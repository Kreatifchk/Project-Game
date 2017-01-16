package strategy;

import java.util.ArrayList;

import javax.swing.JLabel;

/** Класс для армии на карте */
@SuppressWarnings("serial")
public class Army extends JLabel {
	
	int id;
	int owner; //Владелец
	
	ArrayList<TypeArmy> arm = new ArrayList<TypeArmy>(); //Типы отрядов
	
	public Army() {
	}
	
}
