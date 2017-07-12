package base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Qwest implements Serializable {
	
	public String name, textN, textK, request;
	/*
	 * name - название
	 * textN - описание в начале
	 * textK - описание при сдаче
	 * request - требование задани¤
	 */
	
	public int id, lastId, status, exp;
	/*
	 * id - номер квеста (с нул¤)
	 * lastId - какой квест необходимо выполнить чтоб открылс¤ этот
	 * status - 0 (не доступен), 1 (не вз¤т), 2 (вз¤т, не пройден),
	 * 3 (пройден, не сдан), 4 (пройден и сдан)
	 * exp - кол-во опыта
	 */
	
	String nameMonster;
	public int count, progress; //—колько необходимо убить и сколько уже убито
	public int idNPC; //— каким NPC необходимо поговорить
	
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
