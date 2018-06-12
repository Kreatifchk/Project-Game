package ru.kreatifchk.strategy2;

import java.util.Random;

public class Events {
	
	/** Аргументы - атакующая клетка, количество атакующих солдат, защищающая клетка, id атакующей стороны */
	public static void attackEvent(PointMap attack, int army1, PointMap defence, Entity entity) {
		int army2 = defence.army;
		
		int x;
		while (army1 > 0 & army2 > 0) {
			x = new Random().nextInt(100);
			if (x > 50) {
				army1--;
			} else {
				army2--;
			}
		}
		
		attack.army += army1; //Вернуть в нападающую клетку всех выживших
		defence.army = army2;
		if (defence.army <= 0) {
			defence.owner = entity.id;
			defence.mainColor = entity.cl;
			GameFrame.clearColor();
		}
	}
	
}
