package ru.kreatifchk.editor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

import ru.kreatifchk.game.Tile;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Resize;

/** Анимированная иконка для тайлов */
@SuppressWarnings("serial")
public class AnimatedIcon extends ImageIcon {
	
	private int currentFrame; //Текущий кадр
	int speed; //Скорость смены кадров
	
	private Image[] frames;
	
	public AnimatedIcon(Image img) {
		//Определение кол-ва кадров и создание массива
		int height = img.getHeight(null);
		img = Resize.resize(img, Tile.SIZE, (int)(img.getHeight(null) * Main.INC));
		
		frames = new Image[height / Tile.SIZE];
		
		initFrame(img);
	}
	
	public AnimatedIcon(URL location) {
		Image img = new ImageIcon(location).getImage();
		img = Resize.resize(img, Tile.SIZE, (int)(img.getHeight(null) * Main.INC));
		
		int height = img.getHeight(null);
		frames = new Image[height / Tile.SIZE];
		
		initFrame(img);
	}
	
	//Разбивает изображение на кадры и закидывает их в массив
	private void initFrame(Image img) {
		BufferedImage bf;
		int y = 0;
		Graphics2D g;
		
		for (int i = 0; i < frames.length; i++) {
			bf = new BufferedImage(Tile.SIZE, img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			g = bf.createGraphics();
			g.drawImage(img, 0, 0, null);
			
			//Получаем куски изображения и преобразовываем в Image
			frames[i] = bf.getSubimage(0, y, Tile.SIZE, Tile.SIZE).getScaledInstance(Tile.SIZE, Tile.SIZE, Image.SCALE_DEFAULT);
			
			y += Tile.SIZE;
		}
		
		//Устанавливаем иконку на случайный кадр
		currentFrame = new Random().nextInt(frames.length);
		setImage(frames[currentFrame]);
	}
	
	/** Меняет кадр на следующий */
	public void frameChange() {
		currentFrame++;
		if (currentFrame == frames.length) {
			currentFrame = 0;
		}
		setImage(frames[currentFrame]);
	}
	
	/** Обрезает изображение до размера тайла */
	public static ImageIcon crop(URL location) {
		Image img = new ImageIcon(location).getImage();
		img = Resize.resize(img, Tile.SIZE, (int)(img.getHeight(null) * Main.INC));
		
		BufferedImage bf = new BufferedImage(Tile.SIZE, img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bf.createGraphics();
		g.drawImage(img, 0, 0, null);
		
		return new ImageIcon(bf.getSubimage(0, 0, Tile.SIZE, Tile.SIZE).getScaledInstance(Tile.SIZE, Tile.SIZE, Image.SCALE_DEFAULT));
	}
	
}
