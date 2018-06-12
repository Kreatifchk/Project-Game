package ru.kreatifchk.strategy2;

import java.awt.Color;

public abstract class Entity {
	
	Color cl;
	/** Столица */
	PointMap capital;
	int id;
	/** Деньги империи */
	int money;
	/** Количество захваченных ячеек */
	int count;
	/** Очки действий */
	int pointAction = 2;
	int PointActionMax = 2;
	
}
