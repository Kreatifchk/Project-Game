package ru.kreatifchk.editor;

import java.util.ArrayList;

import ru.kreatifchk.game.Monster;

/** КЛасс игровой карты */
public class Map {
	
	public static ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	static PointEditor field[][]; //Поле - массив ячеек редактора которые заполняются редактором карты
	
}
