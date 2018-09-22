package ru.kreatifchk.game;

import java.io.Serializable;
import java.util.Random;

import ru.kreatifchk.game.Player.Direction;

@SuppressWarnings("serial")
public class Monster extends Entity implements Serializable {
	
	private int area = 2; //Область перемещения в состоянии простоя, если 0 стоит на месте

	public Monster(String name, String imageName, int hpMax, int mpMax, int level) {
		super(name, hpMax, mpMax, level);
		setIcon(imageName);
		this.imageName = imageName;
	}
	
	public Monster(String name, String imageName, int hpMax, int mpMax, int level, int danger) {
		super(name, hpMax, mpMax, level, danger);
		setIcon(imageName);
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
		
		//Просто хаотичное движение во время простоя
		/*P.S изменить, каждый монстр делает небольшое движение в определеноом направлении, изменяет кадр анимации и передает управление
		следующему, потом продолжает это двжиение в том же направлении*/
		if (dir == Direction.stand) {
			while(true) {
				//Ищем подходящую сторону для движения
				int dir = new Random().nextInt(8);
				if (dir == 0 & y - 1 >= 0 && startY - (y-1) <= area) {
					this.dir = Direction.up;
					break;
				}
				if (dir == 1 & y + 1 < Game.map[0].length && y+1 - startY <= area) {
					this.dir = Direction.down;
					break;
				}
				if (dir == 2 & x - 1 >= 0 && startX - (x-1) <= area) {
					this.dir = Direction.left;
					break;
				}
				if (dir == 3 & x + 1 < Game.map.length && x+1 - startX <= area) {
					this.dir = Direction.right;
					break;
				}
				//Просто стоять, пока должным образом не работает
				if (dir >= 4) {
					break;
				}
			}
		} else {
			move(dir);
		}
	}
	
}
