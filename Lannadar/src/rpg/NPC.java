package rpg;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class NPC extends JLabel {
	
	int id;
	int location;
	int x, y;
	int xp, mp;
	int level;
	int attack;
	String icon;
	String name;
	int[] qwest;
	
	public NPC(int id, int location, int x, int y, String icon, int xp, int mp, int attack,
			int level, String name, int[] qwest) {
		this.id = id;
		this.location = location;
		this.x = x;
		this.y = y;
		this.xp = xp;
		this.mp = mp;
		this.level = level;
		this.attack = attack;
		this.icon = icon;
		this.name = name;
		
		this.qwest = new int[qwest.length]; //Устанавливает размер массива
		for (int i = 0; i <= qwest.length - 1; i++) {
			this.qwest[i] = qwest[i]; //Получает массив с id квестов выдаваемых этим NPC
		}
	}
	
	public NPC(int id, int location, int x, int y, String icon, int xp, int mp, int attack,
			int level, String name) {
		this.id = id;
		this.location = location;
		this.x = x;
		this.y = y;
		this.xp = xp;
		this.mp = mp;
		this.level = level;
		this.attack = attack;
		this.icon = icon;
		this.name = name;
	}
	
}
