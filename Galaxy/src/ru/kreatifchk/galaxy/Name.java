package ru.kreatifchk.galaxy;

import java.util.Random;

public class Name {
	
	static Random r = new Random();

	static String pr, name, title;
	
	static int p = 0;
	
	public static String name() {
		String s = "";
		/*int o = r.nextInt(100)+1;
		if (o <= 58) {
			define();
		} else {
			name = "Ани";
		}*/
		define();
		genTitle();
		s = title + name;
		pr = "";
		name = "";
		title = "";
		return s;
	}
	
	public static String names() {
		define();
		String n = name;
		name = "";
		pr = "";
		return n;
	}
	
	private static void define() {
		int k = r.nextInt(2)+3;
		genName(k);
	}
	
	private static void genTitle() {
		int b = r.nextInt(10)+1;
		if (b == 1) {
			title = "Доминион ";
		}
		if (b == 2) {
			title = "Империя ";
		}
		if (b == 3) {
			title = "Федерация ";
		}
		if (b == 4) {
			title = "Конфедерация ";
		}
		if (b == 5) {
			title = "Электорат ";
		}
		if (b == 6) {
			title = "Директорат ";
		}
		if (b == 7) {
			title = "Сенат ";
		}
		if (b == 8) {
			title = "Протекторат ";
		}
		if (b == 9) {
			title = "Альянс ";
		}
		if (b == 10) {
			title = "Республика ";
		}
	}
	
	private static void genName(int k) {
		for (int i = 0; i <= k-1; i++) {
			int w = r.nextInt(20)+1;
			if (w == p) {
				while (true) {
					if (w != p) {
						break;
					} else {
						w = r.nextInt(20)+1;
					}
				}
			}
			p = w;
			if (w == 1) {
				pr = "са";
			}
			if (w == 2) {
				pr = "со";
			}
			if (w == 3) {
				pr = "су";
			}
			if (w == 4) {
				pr = "во";
			}
			if (w == 5) {
				pr = "фа";
			}
			if (w == 6) {
				pr = "па";
			}
			if (w == 7) {
				pr = "по";
			}
			if (w == 8) {
				pr = "но";
			}
			if (w == 9) {
				pr = "ку";
			}
			if (w == 10) {
				pr = "ра";
			}
			if (w == 11) {
				pr = "ро";
			}
			if (w == 12) {
				pr = "ру";
			}
			if (w == 13) {
				pr = "ук";
			}
			if (w == 14) {
				pr = "ит";
			}
			if (w == 15) {
				pr = "ог";
			}
			if (w == 16) {
				pr = "от";
			}
			if (w == 17) {
				pr = "им";
			}
			if (w == 18) {
				pr = "об";
			}
			if (w == 19) {
				pr = "ен";
			}
			if (w == 20) {
				pr = "ан";
			}
			
			if (name == null) {
				name = pr;
			} else {
				name = name + pr;
			}
		}
	}
	
}
