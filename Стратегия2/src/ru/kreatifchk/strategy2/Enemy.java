package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Enemy {
	
	Random r = new Random();
	
	Color cl;
	Point capital;
	int id;
	int money = 100;
	int count;
	int pointAction = 1;
	
	public Enemy(int id) {
		this.id = id;
		cl = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		while (true) {
			int x = r.nextInt(GameFrame.pm.length);
			int y = r.nextInt(GameFrame.pm[0].length);
			if (GameFrame.pm[x][y].busy != true) {
				GameFrame.pm[x][y].busy = true;
				GameFrame.pm[x][y].owner = id;
				GameFrame.pm[x][y].setBackground(cl);
				GameFrame.pm[x][y].capital = true;
				capital = new Point(x, y);
				break;
			}
		}
	}
	
}
