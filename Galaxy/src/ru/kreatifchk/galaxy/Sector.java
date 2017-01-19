package ru.kreatifchk.galaxy;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Sector extends JLabel {
	
	int idControl;
	
	int army = 0;
	
	int money; //Кол-во денег в клетке
	
	boolean var; //В войне ли находится клетка
	
	public Sector(String text) {
		super(text);
	}
	
	public Sector() {
		
	}
	
}
