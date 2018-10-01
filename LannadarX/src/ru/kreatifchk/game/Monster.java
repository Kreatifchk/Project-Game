package ru.kreatifchk.game;

import java.io.Serializable;
import java.util.Random;

import ru.kreatifchk.game.Direction;

@SuppressWarnings("serial")
public class Monster extends Entity implements Serializable, Cloneable {
	
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
		//Добавить восстановление здоровья и маны (при необходимости)
		if (state == State.battle & isPlayerNear(true)) {
			//Если бой то сражаться
			battle();
		}
		else if (state == State.battle & !isPlayerNear(true)) {
			state = State.normal;
		}
		else if (state == State.normal & isPlayerNear(true)) {
			//Если монстр стоит вплотную к игроку
			turn(); //Разворачиваем монстра к игроку
			state = State.battle; //Устанавливаем бой
		} else {
			//Иначе движение
			moving();
		}
	}
	
	protected void setArea(int area) {
		this.area = area;
	}
	
	//Блок кода для движения
	private void moving() {
		if (targetX != -1 & targetY != -1) {
			//Двигаться к точке
			moveTo();
		}
		else if (isPlayerNear(false)) {
			//Если персонаж рядом, то двигаться к нему
			if (y - 1 >= 0 && !Game.map[Game.pl.xMap][Game.pl.yMap-1].solid & !Game.map[Game.pl.xMap][Game.pl.yMap-1].busy) {
				//Если сверху от персонажа свободно то двигаться на верхнюю клетку от персонажа
				moveTo(Game.pl.xMap, Game.pl.yMap-1);
			}
			else if (x - 1 >= 0 && !Game.map[Game.pl.xMap-1][Game.pl.yMap].solid & !Game.map[Game.pl.xMap-1][Game.pl.yMap].busy) {
				//Если слева свободно
				moveTo(Game.pl.xMap-1, Game.pl.yMap);
			}
			else if (y + 1 < Game.map[0].length && !Game.map[Game.pl.xMap][Game.pl.yMap+1].solid & !Game.map[Game.pl.xMap][Game.pl.yMap+1].busy) {
				//Если снизу свободно
				moveTo(Game.pl.xMap, Game.pl.yMap+1);
			}
			else if (x + 1 < Game.map.length && !Game.map[Game.pl.xMap+1][Game.pl.yMap].solid & !Game.map[Game.pl.xMap+1][Game.pl.yMap].busy) {
				//Если справа свободно
				moveTo(Game.pl.xMap+1, Game.pl.yMap);
			}
			else {
				//Если к персонажу не подобраться то хаотичное движение
				randomMove();
			}
		}
		else if (!isPlayerNear(false) & (Math.abs(startX - x) > area || Math.abs(startY - y) > area)) {
			//Если игрок ушел далеко, и монстр тожед алеко от своих начальных координат то отправить его к ним
			moveTo(startX, startY);
		}
		else {
			//Иначе хаотическое движение
			randomMove();
		}
	}
	
	//Определяет близко ли игрок, устанавливать в true для проверки того что монстр стоит вплотную (для боя)
	private boolean isPlayerNear(boolean battle) {
		int xDiff = Math.abs(Player.getPlayer().xMap - x);
		int yDiff = Math.abs(Player.getPlayer().yMap - y);
		if (!battle) {
			//Для преследования проверяем, что игрок находится в зоне движения моба
			if (xDiff <= area & yDiff <= area) {
				return true;
			} else {
				return false;
			}
		} else {
			//Для битвы проверяем, что моб стоит вплотную к игроку
			if ((xDiff == 0 & yDiff == 1) || (xDiff == 1 & yDiff == 0)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	//Просто хаотичное движение во время простоя
	private void randomMove() {
		if (dir == Direction.stand) {
			//Если монстр уже остановился то дать новую команду двигаться
			while(true) {
				//Ищем подходящую сторону для движения
				int dir = new Random().nextInt(7);
				if (dir == 0 & y - 1 >= 0 && startY - (y-1) <= area & !Game.map[x][y-1].solid & !Game.map[x][y-1].busy) {
					this.dir = Direction.up;
					break;
				}
				if (dir == 1 & y + 1 < Game.map[0].length && y+1 - startY <= area & !Game.map[x][y+1].solid & !Game.map[x][y+1].busy) {
					this.dir = Direction.down;
					break;
				}
				if (dir == 2 & x - 1 >= 0 && startX - (x-1) <= area & !Game.map[x-1][y].solid & !Game.map[x-1][y].busy) {
					this.dir = Direction.left;
					break;
				}
				if (dir == 3 & x + 1 < Game.map.length && x+1 - startX <= area & !Game.map[x+1][y].solid & !Game.map[x+1][y].busy) {
					this.dir = Direction.right;
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
	
	transient int count = 25; //Чтоб удар происходил раз в секунду
	private void battle() {
		if (count == 25) {
			count = 0;
			Player.getPlayer().hp -= attack;
		}
		count++;
		
		//Если монстр проиграл
		if (hp <= 0) {
			state = State.dead;
			hp = hpMax;
			mp = mpMax;
		}
	}
	
	//Разворот к игроку лицом
	private void turn() {
		if (Game.pl.yMap > y) {
			setCurrentView(1, 0);
		}
		if (Game.pl.yMap < y) {
			setCurrentView(1, 3);
		}
		if (Game.pl.xMap > x) {
			setCurrentView(1, 2);
		}
		if (Game.pl.xMap < x) {
			setCurrentView(1, 1);
		}
	}
	
	@Override
	public Monster clone() {
		try {
			return (Monster) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
