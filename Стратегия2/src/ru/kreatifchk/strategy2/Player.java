package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.util.Random;

public class Player extends Entity {
	
	Random r = new Random();
	
	public Player(int id) {
		cl = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		this.id = id;
		money = 100;
		count = 1;
		pointAction = 1;
	}
	
}
