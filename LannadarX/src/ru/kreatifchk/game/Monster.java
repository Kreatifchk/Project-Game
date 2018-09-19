package ru.kreatifchk.game;

import java.util.Random;

import javax.swing.ImageIcon;

import ru.kreatifchk.tools.Resize;

public class Monster extends Entity {
	
	private int area = 0; //Область перемещения в состоянии простоя, если 0 стоит на месте
	
	private Random r = new Random();

	public Monster(String name, String imageName, int hpMax, int mpMax, int level) {
		super(name, hpMax, mpMax, level);
		view = new ImageIcon(Game.class.getResource("/ru/kreatifchk/res/image/entity/monster/" + imageName + ".png")).getImage();
		view = Resize.resize(view, Tile.SIZE, Tile.SIZE);
		this.imageName = imageName;
	}
	
	public Monster(String name, String imageName, int hpMax, int mpMax, int level, int danger) {
		super(name, hpMax, mpMax, level, danger);
		view = new ImageIcon(Game.class.getResource("/ru/kreatifchk/res/image/entity/monster/" + imageName + ".png")).getImage();
		view = Resize.resize(view, Tile.SIZE, Tile.SIZE);
		this.imageName = imageName;
	}

	//Вызывается по очереди для всех entity, отсюда идет выполнение различных задач
	@Override
	protected void update() {
		moving();
		//Добавить восстановление здоровья и маны (при необходимости)
	}
	
	protected void setArea(int area) {
		this.area = area;
	}
	
	//Определяет перемещения монстра, либо просто по определенной зоне, либо за персонажем
	private void moving() {
		//Здесь будет блок посвященный преследованию персонажа, включается если рядом персонаж
		
		while(true) {
			int dir = r.nextInt(4);
			if (dir == 0 & y - 1 >= 0 && startY - (y-1) <= area) {
				y--;
				break;
			}
			if (dir == 1 & y + 1 < Game.map[0].length && y+1 - startY <= area) {
				y++;
				break;
			}
			if (dir == 2 & x - 1 >= 0 && startX - (-1) <= area) {
				x--;
				break;
			}
			if (dir == 3 & x + 1 < Game.map.length && x+1 - startX <= area) {
				x++;
				break;
			}
		}
	}
	
}
