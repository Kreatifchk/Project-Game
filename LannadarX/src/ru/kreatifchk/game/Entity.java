package ru.kreatifchk.game;

import java.awt.Image;
import java.util.Random;

public abstract class Entity {
	
	public Image view; //Изображение существа
	
	private int hpMax, mpMax;
	int hp, mp, level;
	int danger = 1; //Уровень опасности
	
	private String name;
	protected String imageName;
	
	int x, y; //Текущие x и y
	public int startX, startY; //Те на которых он появляется
	int location; //Локация
	
	private Random r = new Random();
	
	public Entity(String name, int hpMax, int mpMax, int level) {
		this.name = name;
		this.hpMax = hpMax;
		this.mpMax = mpMax;
		this.level = level;
	}
	
	public Entity(String name, int hpMax, int mpMax, int level, int danger) {
		this(name, hpMax, mpMax, level);
		this.danger = danger;
	}
	
	//Вызывается по очереди для всех entity, отсюда идет выполнение различных задач
	protected abstract void update();
	
	/** Устанавливает специфичные хар-ки для определенного моба */
	protected void setSpecific(int hpMax, int mpMax, int level) {
		this.hpMax = hpMax;
		this.mpMax = mpMax;
		this.level = level;
	}
	
	/** Устанавливает специфичные хар-ки для определенного моба */
	protected void setSpecific(int hpMax, int mpMax, int level, int danger) {
		setSpecific(hpMax, mpMax, level);
		this.danger = danger;
	}
	
	/** Устанавливаем начальные ккординаты*/
	public Entity setStart(int x, int y) {
		startX = x;
		startY = y;
		return this;
	}
	
	//Движение к определенной точке
	protected void moveTo(int targetX, int targetY) {
		if (targetX > x & targetY > y) {
			if (r.nextBoolean() == false) x++;
			else y++;
		}
		else if (targetX > x & targetY < y) {
			if (r.nextBoolean() == false) x++;
			else y--;
		}
		else if (targetX < x & targetY > y) {
			if (r.nextBoolean() == false) x--;
			else y++;
		}
		else if (targetX < x & targetY < y) {
			if (r.nextBoolean() == false) x--;
			else y--;
		}
		else if (targetX == x & targetY < y) {
			y--;
		}
		else if (targetX == x & targetY > y) {
			y++;
		}
		else if (targetX < x & targetY == y) {
			x--;
		}
		else if (targetX > x & targetY == y) {
			x++;
		}
	}
	
	//Установка местоположениия
	protected void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public int getHpMax() {
		return hpMax;
	}
	
	public int getMpMax() {
		return mpMax;
	}
	
	public int getLevel() {
		return level;
	}
	public int getDanger() {
		return danger;
	}
	public String getName() {
		return name;
	}
	public String getImageName() {
		return imageName;
	}
	
}
