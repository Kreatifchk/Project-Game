package rpg;

import javax.swing.JLabel;

@SuppressWarnings({ "serial" })
public class Monsters extends JLabel {
	
	int xp, mp, attack, level, difficulty, exp, location;
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
	
	public Monsters(int location, int x, int y, String icon, int xp, 
			int mp, int attack, int level, int difficulty, int exp, String name) {
		this.x = x;
		this.y = y;
		this.mp = mp;
		this.icon = icon;
		this.xp = xp;
		this.mp = mp;
		this.attack = attack;
		this.level = level;
		this.difficulty = difficulty;
		this.exp = exp;
		this.location = location;
		this.name = name;
	}
	
}
