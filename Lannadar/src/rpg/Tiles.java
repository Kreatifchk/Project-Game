package rpg;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tiles extends JLabel {
	
	int id;
	
	boolean solid;
	
	boolean portal;
	
	boolean busy; //занят
	
	boolean anim = false;
	
	int number; //номер анимационного ид (для анимации)
	
	int currentFrame = 0; //текущий кадр (для анимации)
	
	public Tiles(int id,boolean solid) {
		this.id = id;
		this.solid = solid;
	}
	
	public Tiles(int id, boolean solid, boolean anim, int number) {
		this.id = id;
		this.solid = solid;
		this.anim = anim;
		this.number = number;
	}
	
}
