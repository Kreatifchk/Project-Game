package ru.kreatifchk.galaxy;

import java.awt.Color;
import java.util.Random;

public class Empery {
	
	int id;
	
	int stars = 1; //Количество звезд во владении
	
	int money = 0; //Деньги империи
	
	int moneyV = 0; //Деньги на войну
	int moneyM = 0; //Деньги на мирные
	
	int varC = 0; //Количество клеток, находящихся в войне
	
	//int covenant = 100; // С кем заключен союз
	
	Color cl;
	
	int[][] controlMap = new int[Game.x+1][Game.y+1];
	
	int riot; //Процент бунта
	
	boolean alive; //Все еще жива :D Portal
	
	String name;
	
	Party pt[] = new Party[3];
	
	public Empery() {
		party();
	}
	
	public Empery(int red, int green, int blue) {
		Color cl = new Color(red, green, blue);
		this.cl = cl;
		riot = 0;
		party();
	}
	
	private void party() {
		Random r = new Random();
		int proc = 100;
		for (int i = 0; i < pt.length; i++) {
			pt[i] = new Party();
			pt[i].setName("Партия " + Name.names());
			if (i == 0) {
				int gen = r.nextInt(50) + 1;
				proc -= gen;
				pt[i].setTrust(gen);
			} else if (i == 1) {
				int gen = r.nextInt(proc - 10) + 1;
				proc -= gen;
				pt[i].setTrust(gen);
			} else {
				pt[i].setTrust(proc);
			}
		}
	}
	
}
