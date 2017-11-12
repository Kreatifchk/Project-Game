package base;

import javax.swing.JLabel;

@SuppressWarnings({ "serial" })
public class Monsters extends JLabel {
	
	int idType, xp, mp, attack, level, difficulty, exp, location;
	/*
	 * xp - кол-во xp
	 * mp - кол-во mp
	 * attack - сила атаки
	 * level - уровень
	 * difficulty - сложность
	 * exp - опыт получаемый за его убийство
	 * location - на какой локации
	 */
	
	String icon, name;
	/*
	 * icon - название изображения
	 * name - название монстра (для квестов)
	 */
	
	int x, y;
	
	boolean alive; //Жив ли монстр (респавнится со временем)
	
	/**
	 * 
	 * @param idType - ID типа монстра
	 * @param location - ID локации
	 * @param x - координата x тайла
	 * @param y - координата y тайла
	 * @param icon - изображение
	 * @param xp - здоровье
	 * @param mp - мана
	 * @param attack - сила атаки
	 * @param level - уровень
	 * @param exp - количество опыта получаемое за убийство моба
	 * @param name - имя
	 */
	public Monsters(int idType, int location, int x, int y, String icon, int xp, 
			int mp, int attack, int level, int exp, String name) {
		this.idType = idType;
		this.x = x;
		this.y = y;
		this.mp = mp;
		this.icon = icon;
		this.xp = xp;
		this.mp = mp;
		this.attack = attack;
		this.level = level;
		difficulty = 1;
		this.exp = exp;
		this.location = location;
		this.name = name;
		alive = true;
	}
	
}
