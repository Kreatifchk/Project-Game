package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
	 * id - номер квеста (с нуля)
	 * lastId - какой квест необходимо выполнить чтоб открылсz этот
	 * status - 0 (не доступен), 1 (не взят), 2 (взят, не пройден),
	 * 3 (пройден, не сдан), 4 (пройден и сдан)
	 * exp - кол-во опыта
	 */
	
	public String[] nameMonster;//Каких мобов надо убивать (квесты на сражения с мобами)
	public int[] idItem; //Какие предметы надо собрать (собирательные квестов)
	public int idNPC; //С каким NPC необходимо поговорить (разговорные квесты)
	
	public int count[], progress[]; //Сколько необходимо убить (собрать) и сколько 
	//уже убито (собрано)
	
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
	
	protected static String name(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String name = "";
		try {
			sc = new Scanner(f);
			name = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
		}
		return name;
	}
	
	protected static String textN(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String textN = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			textN = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
		}
		return textN;
	}
	
	protected static String textK(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String textK = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			sc.nextLine();
			textK = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
		}
		return textK;
	}
	
	protected static String request(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String request = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			sc.nextLine();
			sc.nextLine();
			request = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
		}
		return request;
	}
	
}
