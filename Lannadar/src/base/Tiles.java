package base;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tiles extends JLabel {
	
	int id;
	
	boolean solid;
	boolean portal;
	boolean busy; //занят
	boolean item; //Размещен ли предмет
	boolean anim = false;
	
	String name;
	
	ImageIcon ic;
	
	int number; //номер анимации ид (для анимации)
	
	int currentFrame = 0; //текущий кадр (для анимации)
	
	public Tiles(int id, boolean solid) {
		this.id = id;
		this.solid = solid;
	}
	public Tiles(int id, boolean solid, boolean anim, int number) {
		this.id = id;
		this.solid = solid;
		this.anim = anim;
		this.number = number;
	}
	
	
	public Tiles(String name, boolean solid, ImageIcon ic) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
	}
	public Tiles(String name, boolean solid, ImageIcon ic, boolean anim, int number) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
		this.anim = anim;
		this.number = number;
	}
	
}
