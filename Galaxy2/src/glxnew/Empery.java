package glxnew;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Empery {
	
	Color cl;
	
	static Random r = new Random();
	
	boolean immort;
	
	int id;
	int oldId; //Чтоб после удадения империй, изменить у клеток id владельца
	
	int money = 0;
	int level = 1; //Уровень технолог. развития (влияет на лимит)
	
	/** Количество территорий */
	int count;
	
	Point capital; //Столица (для режима захват столицы)
	
	int corruption; //Уровень коррупции (понадобится в дальнейшем)
	int incCorr; //Увеличение уровня коррупции за каждый ход
	/** Недовольство людей */
	int disapproval;
	
	boolean[][] map = new boolean[Game.pole.length][Game.pole[0].length];
	
	public Empery() {
	}
	public Empery(int id) {
		this.id = id;
	}
	public Empery(int id, Color cl) {
		this.id = id;
		this.cl = cl;
	}
	
}
