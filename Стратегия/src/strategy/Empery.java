package strategy;

import java.util.ArrayList;

public class Empery {
	
	String name;
	
	int id;
	
	int town; //���������� �������
	
	ArrayList<Integer> towns = new ArrayList<Integer>(); //������ ������� 
	ArrayList<Army> troop = new ArrayList<Army>(); //������
	
	int capital; //����� - �������
	
	int gold = 1000; //������ �������
	
	public Empery(int id) {
		this.id = id;
	}
	
	public Empery setName(String name) {
		this.name = name;
		return this;
	}
	
}
