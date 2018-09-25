package ru.kreatifchk.game;

import java.io.Serializable;
import java.util.Random;

import ru.kreatifchk.game.Direction;

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
	@SuppressWarnings("unused")
	@Override
	protected void update() {
		//Добавить восстановление здоровья и маны (при необходимости)
		
		if (false) {
			//Если битва то сражаться
		} else {
			//Иначе движение
			moving();
		}
	}
	
	protected void setArea(int area) {
		this.area = area;
	}
	
	//Блок кода для движения
	@SuppressWarnings("unused")
	private void moving() {
		if (targetX != -1 & targetY != -1) {
			//Двигаться к точке
			moveTo();
		}
		else if (false) {
			//Если персонаж рядом, то двигаться к нему (указать куда)
		}
		else {
			//Иначе хаотическое движение
			System.out.println("move");
			randomMove();
		}
	}
	
	//Просто хаотичное движение во время простоя
	private void randomMove() {
		if (dir == Direction.stand) {
			//Если монстр уже остановился то дать новую команду двигаться
			while(true) {
				//Ищем подходящую сторону для движения
				int dir = new Random().nextInt(5);
				if (dir == 0 & y - 1 >= 0 && startY - (y-1) <= area & !Game.map[x][y-1].solid & !Game.map[x][y-1].busy) {
					this.dir = Direction.up;
					Game.map[x][y].busy = false; //Освобождаем старое поле
					Game.map[x][y-1].busy = true; //Поле на которое он двигается обьявляем занятым
					break;
				}
				if (dir == 1 & y + 1 < Game.map[0].length && y+1 - startY <= area & !Game.map[x][y+1].solid & !Game.map[x][y+1].busy) {
					this.dir = Direction.down;
					Game.map[x][y].busy = false;
					Game.map[x][y+1].busy = true;
					break;
				}
				if (dir == 2 & x - 1 >= 0 && startX - (x-1) <= area & !Game.map[x-1][y].solid & !Game.map[x-1][y].busy) {
					this.dir = Direction.left;
					Game.map[x][y].busy = false;
					Game.map[x-1][y].busy = true;
					break;
				}
				if (dir == 3 & x + 1 < Game.map.length && x+1 - startX <= area & !Game.map[x+1][y].solid & !Game.map[x+1][y].busy) {
					this.dir = Direction.right;
					Game.map[x][y].busy = false;
					Game.map[x+1][y].busy = true;
					break;
				}
				//Просто стоять
				if (dir >= 4) {
					this.dir = Direction.specialStand;
					break;
				}
			}
		} else {
			move(dir);
		}
	}
	
}
