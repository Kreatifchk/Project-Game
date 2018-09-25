package ru.kreatifchk.game;

import ru.kreatifchk.game.Direction;

/** Класс игрового тайла  */
public class MapPoint {
	
	boolean solid; //твердый ли тайл
	boolean busy = false; //Занят ли тайл кем-то (если занят, то пройти по нему нельзя)
	
	Direction transfer; //Сторона с которой будет происходить переход
	
	int number; //Номер тайла в массиве тайлов
	int xTrans, yTrans; //На какой клетке появится персонаж в новой локации
	private int x, y;
	
	String newLocation; //Название файла с целевой локацией
	
	public MapPoint() {
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
