package glxnew;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Sector extends JLabel {
	
	boolean busy; //Занята ли, для первичного размещения
	/** Находится ли у границы */
	boolean border; //Находится ли у границы
	boolean capital;
	boolean newP; //Если только захватили
	boolean immort; //бессмертие (временное, для империй повстанцев)
	
	/** Владелец */
	int owner = -1; //Владелец
	
	int defense = 0; //Уровень защиты
	
	/** Количество солдат */
	int army = 0; //Количество солдат
	
	int money = 0; //Количество денег
	
	
	public Sector() {
		
	}

}
