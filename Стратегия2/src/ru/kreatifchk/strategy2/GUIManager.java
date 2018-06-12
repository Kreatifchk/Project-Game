package ru.kreatifchk.strategy2;

import javax.swing.JComponent;

public abstract class GUIManager {
	
	/** Получить центр для одного объекта. Аргументы: размер объекта, размер окна */
	public static int getCenter(int widthObj, int widthWind) {
		return (widthWind - widthObj) / 2;
	}
	
	/** Получить центр для нескольких объектов одного размера. Аргументы: размер объекта, количество объектов размер окна */
	public static int getCenter(int widthObj, int count, int widthWind) {
		//Вычитаем размер всех объектов, затем вычитаем дистанцию между ними, равную половине объектов
		return ((widthWind - widthObj * count) - widthObj / 2) / 2;
	}
	
	public static int getPosition(int widthObj, int count, int number, int widthWind) {
		//Получаем позицию для первого объекта, прибавляем дистанцию и размер объекта помноженную на количество объектов
		int pos = getCenter(widthObj, count, widthWind);
		return (pos + widthObj + widthObj / 2) * (count - 1);
	}
	
	/** Установка размеров для компонента и расположения в процентах. Если расположение -1 то устанавливается по центру */
	public static void setBoundsComponent(JComponent component, int x, int y, int width, int height) {
		component.setSize(component.getParent().getWidth() / 100 * width, component.getParent().getHeight() / 100 * height);
		if (x == -1 & y == -1) {
			component.setLocation(getCenter(component.getWidth(), component.getParent().getWidth()),
					getCenter(component.getHeight(), component.getParent().getHeight()));
		}
	}
	
}
