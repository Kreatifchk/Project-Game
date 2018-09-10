package ru.kreatifchk.game;

import java.awt.Image;

import javax.swing.ImageIcon;

import ru.kreatifchk.editor.AnimatedIcon;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Img;

/** Класс - тайл (для создания объектов */
public class Tile {
	
	/** Размер тайла с учетом разрешения экрана */
	public static int SIZE;
	
	boolean solid; //Твердость тайла
	public boolean animate; //Анимированный ли тайл
	
	public int numberKit; //Номер набора к которому он принадлежит
	public int width = -1, height = -1; //Если объект состоит из нескольких тайлов
	
	public String name; //Название тайла
	public String nameFile; //Название файла с изображением тайла
	
	public ImageIcon icon; //Изображение тайла
	public Image icon2; //Изображение для анимационнных тайлов
	
	public Tile(boolean solid, String name, String nameFile, int numberKit) {
		this.solid = solid;
		this.name = name;
		this.nameFile = nameFile;
		this.numberKit = numberKit;
		this.animate = false;
		
		setup();
	}
	
	public Tile(boolean solid, String name, String nameFile, int numberKit, int width, int height) {
		this.solid = solid;
		this.name = name;
		this.nameFile = nameFile;
		this.numberKit = numberKit;
		this.width = width;
		this.height = height;
		this.animate = false;
		
		setup();
	}
	
	public Tile(boolean solid, boolean animate, String name, String nameFile, int numberKit) {
		this.solid = solid;
		this.name = name;
		this.nameFile = nameFile;
		this.numberKit = numberKit;
		this.animate = true;
		
		setup();
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	private void setup() {
		SIZE = (int)(48*Main.INC);
		if (animate == true) {
			icon2 = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/tiles/" + nameFile + ".png")).getImage();
			icon = AnimatedIcon.crop(Main.class.getResource("/ru/kreatifchk/res/image/tiles/" + nameFile + ".png"));
		} else {
			icon = Img.ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/tiles/" + nameFile + ".png"));
		}
	}
	
}
