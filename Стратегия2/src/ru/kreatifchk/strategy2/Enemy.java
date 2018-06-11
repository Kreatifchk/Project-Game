package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.util.Random;

public class Enemy extends Entity {
	
	Random r = new Random();
	
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
				GameFrame.pm[x][y].mainColor = cl;
				GameFrame.pm[x][y].capital = true;
				capital = GameFrame.pm[x][y];
				break;
			}
		}
		
		money = 100;
		count = 1;
		pointAction = 1;
	}
	
}
