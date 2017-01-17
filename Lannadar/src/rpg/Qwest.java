package rpg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Qwest implements Serializable {
	
	String name, textN, textK, request;
	/*
	 * name - название
	 * textN - описание в начале
	 * textK - описание при сдаче
	 * request - требование задания
	 */
	
	int id, lastId, status, exp;
	/*
	 * id - номер квеста (с нуля)
	 * lastId - какой квест необходимо выполнить чтоб открылся этот
	 * status - 0 (не доступен), 1 (не взят), 2 (взят, не пройден),
	 * 3 (пройден, не сдан), 4 (пройден и сдан)
	 * exp - кол-во опыта
	 */
	
	String nameMonster;
	int count, progress; //Сколько необходимо убить и сколько уже убито
	int idNPC; //С каким NPC необходимо поговорить
	
	public Qwest(int id, String name, String textN, String textK, String request, 
			int status, int exp) {
		this.id = id;
		this.name = name;
		this.textN = textN;
		this.textK = textK;
		this.request = request;
		this.status = status;
		this.exp = exp;
	}
	
	public Qwest(int id, String name, String textN, String textK, String request, 
			int status, int exp, int lastId) {
		this.id = id;
		this.name = name;
		this.textN = textN;
		this.textK = textK;
		this.request = request;
		this.lastId = lastId;
		this.status = status;
		this.exp = exp;
	}
	
}
