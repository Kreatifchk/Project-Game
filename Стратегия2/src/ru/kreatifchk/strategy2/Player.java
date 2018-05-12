package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Player {
	
	Random r = new Random();
	
	Color cl;
	Point capital;
	int id = 1001;
	int money = 100;
	int count; //Количество захваченных ячеек
	int pointAction = 1;
	
	public Player() {
		cl = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
	
}
