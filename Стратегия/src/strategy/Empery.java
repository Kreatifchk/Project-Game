package strategy;

import java.util.ArrayList;

public class Empery {
	
	String name;
	
	int id;
	
	int town; //Количество городов
	
	ArrayList<Integer> towns = new ArrayList<Integer>(); //Номера городов 
	ArrayList<Army> troop = new ArrayList<Army>(); //Армии
	
	int capital; //Город - столица
	
	int gold = 1000; //Деньги империи
	
	public Empery(int id) {
		this.id = id;
	}
	
	public Empery setName(String name) {
		this.name = name;
		return this;
	}
	
}
