package strategy;

import javax.swing.ImageIcon;

/** Типы воинов, на основе этого класса делаются наследники */
public abstract class TypeArmy {
	
	@SuppressWarnings("unused")
	private int count; //Количество юнитов в отряде
	
	protected int cost; //Стоимость юнита
	protected int force; //Сила
	protected int speedAttack; //Скорость атаки
	protected int lineNumber; //Место в очереди наема
	
	protected int speed; //Сколько может за ход пройти
	
	protected String name; //Имя юнита
	
	protected ImageIcon icon; //Иконка воина
	
	protected void setCount(int count) {
		this.count = count;
	}
	
	protected void setLineNumber(int l) {
		lineNumber = l;
	}
	
}
