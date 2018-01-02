package base;

import java.awt.Point;

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
	
	int tab; //Категория тайла
	
	String name;
	
	ImageIcon ic;
	String main;
	
	int number; //номер анимации ид (для анимации)
	
	int currentFrame = 0; //текущий кадр (для анимации)
	
	int idKit = -1; //Номер набора тайла (для мультитайловых структур)
	Point point;
	
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
	
	//Именованные тайлы для редактора
	public Tiles(String name, boolean solid, ImageIcon ic) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
		tab = 0;
	}
	public Tiles(String name, boolean solid, ImageIcon ic, boolean anim, int number) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
		this.anim = anim;
		this.number = number;
		tab = 0;
	}
	
	//Именованные тайлы для редактора с номером вклдаки
	public Tiles(String name, boolean solid, ImageIcon ic, int tab) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
		this.tab = tab;
	}
	public Tiles(String name, boolean solid, ImageIcon ic, boolean anim, int number, int tab) {
		this.name = name;
		this.solid = solid;
		this.ic = ic;
		this.anim = anim;
		this.number = number;
		this.tab = tab;
	}
	
	//Установка координат (для мультитайловых объектов) а также установка общей иконки для всей структуры
	public Tiles setPoint(int idKit, Point point, String main) {
		this.idKit = idKit;
		this.point = point;
		this.main = main;
		return this;
	}
	public Tiles setPoint(int idKit, Point point) {
		this.idKit = idKit;
		this.point = point;
		return this;
	}
}
