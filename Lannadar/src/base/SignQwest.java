package base;

import javax.swing.JLabel;

public class SignQwest {
	
	static boolean d = false; //true - если есть невзятые квесты
	static boolean zav = false; //true - если есть квесты которые можно сдать
	
	static int pl = 0;
	
	static JLabel exclam; //Воскл. знак для квестов
	
	public static void sign(int i) {
		//Аргументы: i - id NPC
			
		if (Game.mapx[Game.npc[i].x][Game.npc[i].y-1].getComponentCount() > 0) {
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].remove(exclam); //Удалить уже имеющиеся значки
		}
		
		//Если NPC может выдавать квесты
		if (Game.npc[i].qwest != null) {
			int lng = Game.npc[i].qwest.length - 1; //Количество выдаваемых квестов
			
			//Пробегает по всем квестам NPC
			cycle(lng, i);
			
			//Отрисовывает знак
			draw(i);
		}
	}
	
	private static void cycle(int lng, int i) {
		for (int j = 0; j <= lng; j++) {
			//Если квест сдавать то знак вопроса
			if (end(i, j) == true) {
				break;
			}
				
			//Если квест получать
			if (begin(i, j) == true) {
				break;
			}
		}
	}
	
	private static boolean end(int i, int j) {
		boolean br = false; //Вместо Break
		
		if (Game.qwest[Game.npc[i].qwest[j]].status == 3) {
			if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
				//Если квест разговорный
				if (Game.qwest[Game.npc[i].qwest[j]].idNPC == Game.npc[i].id) {
					//Если NPC тот кому сдавать квест
					zav = true;
					br = true;
				}
			} else {
				//Если квест не разговорный
				zav = true;
				br = true;
			}
		} else {
			zav = false;
		}
		
		return br;
	}
	
	private static boolean begin(int i, int j) {
		boolean br = false;
		
		if (Game.qwest[Game.npc[i].qwest[j]].status == 1) {
			//Если найден невзятый квест			
			if (Game.qwest[Game.npc[i].qwest[j]].lastId != -1) {
				//Если для открытия квеста надой пройти другой
				int lastId = Game.qwest[Game.npc[i].qwest[j]].lastId;
				if (Game.qwest[lastId].status == 4) {
					if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
						//Если квест разговорный
						if (Game.qwest[Game.npc[i].qwest[j]].idNPC != Game.npc[i].id) {
							//Если NPC не тот который завершает квест
							d = true;
							br = true;
						}
					} else {
						//Если квест не разговорный
						d = true;
						br = true;
					}
				} else {
					d = false;
				}
			} else {
				//Если доступен сразу
				if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
					//Если квест разговорный
					if (Game.qwest[Game.npc[i].qwest[j]].idNPC != Game.npc[i].id) {
						//Если NPC не тот который завершает квест
						d = true;
						br = true;
					}
				} else {
					//Если квест не разговорный
					d = true;
					br = true;
				}
			}
		} else {
			d = false;
		}
		
		return br;
	}
	
	private static void draw(int i) {
		//Если не взятых квестов больше нету, то удалить значок
		if (d == false) {
			try {
				Game.mapx[Game.npc[i].x][Game.npc[i].y-1].remove(exclam);
			} catch (NullPointerException e) {
			}
		}
		//Если не взятые квесты есть, и нет квестов которые сдавать
		if (d == true) {
			exclam = new JLabel();
			exclam.setBounds(10, 0, Game.TILE, Game.TILE);
			exclam.setIcon(Game.qwI);
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].add(exclam);
			//Воскл. знак
		}
		if (zav == true) {
			exclam = new JLabel();
			exclam.setBounds(6, 0, Game.TILE, Game.TILE);
			exclam.setIcon(Game.qwSI);
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].add(exclam);
			//Знак вопроса
		}
	}
	
}
