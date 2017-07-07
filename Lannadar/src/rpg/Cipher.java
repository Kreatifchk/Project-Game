package rpg;

import java.util.Random;

/**
 * Класс для шифровки
 */
public class Cipher {
	
	static char[] b = new char[26];
	static Random r = new Random();
	
	/**
	 * Зашифровывает числовую строку
	 * @param String s
	 * @return String rez
	 */
	public static String cipher(String s) {
		String rez = "", prom;
		//В начале
		for (int i = 0; i <= 2; i++) {
			rez = rez + randomSymbol();
		}
		int length = s.length();
		for (int i = 0; i <= length - 1; i++) {
			int c = Character.getNumericValue(s.charAt(i));
			prom = "" + transf(c);
			rez = rez + prom;
		}
		
		//В конце
		for (int i = 0; i <= 2; i++) {
			rez = rez + randomSymbol();
		}
		randomLetter();
		return rez;
	}
	
	//Расшифровывает строку
	public static String decryptor(String s) {
		String rez = "", prom = "";
		int a = s.length();
		for (int i = 3; i <= a - 4; i+= 3) {
			prom = "" + s.charAt(i);
			rez = rez + returnTransf(prom);
		}
		return rez;
	}
	
	private static String randomSymbol() {
		int x = r.nextInt(9)+1;
		String ch = "";
		if (x == 1) {
			ch = "#";
		}
		if (x == 2) {
			ch = "&";
		}
		if (x == 3) {
			ch = "!";
		}
		if (x == 4) {
			ch = "@";
		}
		if (x == 5) {
			ch = "?";
		}
		if (x == 6) {
			ch = "$";
		}
		if (x == 7) {
			ch = "^";
		}
		if (x == 8) {
			ch = "[";
		}
		if (x == 9) {
			ch = "/";
		}
		return ch;
	}
	
	private static String transf(int c) {
		String s = "";
		if (c == 0) {
			s = "u";
		}
		else if (c == 1) {
			s = "I";
		}
		else if (c == 2) {
			s = "T";
		}
		else if (c == 3) {
			s = "r";
		}
		else if (c == 4) {
			s = "M";
		}
		else if (c == 5) {
			s = "U";
		}
		else if (c == 6) {
			s = "t";
		}
		else if (c == 7) {
			s = "x";
		}
		else if (c == 8) {
			s = "E";
		}
		else if (c == 9) {
			s = "e";
		}
		s = s + randomLetter() + randomSymbol();
		return s;
	}
	
	//Генерирует рандомную букву
	private static String randomLetter() {
		int count = 0, gen;
		String s = "";
		for (char j = 'a'; j <= 'z'; j++) {
			b[count] = j;
			count++;
		}
		gen = r.nextInt(26);
		s = "" + b[gen];
		return s;
	}
	
	private static String returnTransf(String c) {
		String x = "";
		if (c.equals("u")) {
			x = "0";
		}
		else if (c.equals("I")) {
			x = "1";
		}
		else if (c.equals("T")) {
			x = "2";
		}
		else if (c.equals("r")) {
			x = "3";
		}
		else if (c.equals("M")) {
			x = "4";
		}
		else if (c.equals("U")) {
			x = "5";
		}
		else if (c.equals("t")) {
			x = "6";
		}
		else if (c.equals("x")) {
			x = "7";
		}
		else if (c.equals("E")) {
			x = "8";
		}
		else if (c.equals("e")) {
			x = "9";
		}
		return x;
	}
	
}
