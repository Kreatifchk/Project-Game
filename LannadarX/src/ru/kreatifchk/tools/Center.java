package ru.kreatifchk.tools;

import java.awt.Component;

import javax.swing.JComponent;

public class Center {
	
	/** Нахождение точки центра для компонента, аргументы: vel - размер окна, velObj - величина объекта */
	public static int cnt(int vel, int velObj) {
		return Math.round((vel - velObj) / 2);
	}
	
	/** Нахождение и расположение по центру компонента, аргументы: vel - размер окна, velObj - величина объекта */
	public static void cnt(JComponent comp, Component cont) {
		int x = Math.round((cont.getWidth() - comp.getWidth()) / 2);
		int y = Math.round((cont.getHeight() - comp.getHeight()) / 2);
		comp.setLocation(x, y-15);
	}
	
	/** Нахождение и расположение по центру по x компонента, аргументы: vel - размер окна, velObj - величина объекта */
	public static void cnt(JComponent comp, Component cont, int height) {
		int x = Math.round((cont.getWidth() - comp.getWidth()) / 2);
		comp.setLocation(x, height);
	}
	
}
