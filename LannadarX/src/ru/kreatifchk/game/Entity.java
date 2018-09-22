package ru.kreatifchk.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

import ru.kreatifchk.game.Player.Direction;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Resize;

public abstract class Entity {
	
	private ImageIcon[][] view; //Изображение существа, полное со всеми анимациями
	public transient ImageIcon currentView; //Текущий кадр
	private int currentFrame;
	
	private int hpMax, mpMax;
	int hp, mp, level;
	int danger = 1; //Уровень опасности
	
	private String name;
	protected String imageName;
	
	int x, y; //Текущие x и y
	public int startX, startY; //Те на которых он появляется
	int realX, realY; //Координаты x и y в пикселах относительно экрана
	int location; //Локация
	private int stageMove = 0; //Процесс движения
	
	protected Direction dir = Direction.stand; //Направление движения
	
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
	
	public Entity() {
	}
	
	//Вызывается по очереди для всех entity, отсюда идет выполнение различных задач
	protected abstract void update();
	
	/** Устанавливает специфичные хар-ки для определенного моба */
	public void setSpecific(int hpMax, int mpMax, int level) {
		this.hpMax = hpMax;
		this.mpMax = mpMax;
		this.level = level;
	}
	
	/** Устанавливает специфичные хар-ки для определенного моба */
	public void setSpecific(int hpMax, int mpMax, int level, int danger) {
		setSpecific(hpMax, mpMax, level);
		this.danger = danger;
	}
	
	/** Устанавливаем начальные ккординаты*/
	public Entity setStart(int x, int y) {
		startX = x;
		startY = y;
		return this;
	}
	
	/** Устанавливает изображения для разных ситуаций entity */
	protected void setIcon(String imageName) {
		//Получаем общее изображение и растягиваем его при необходимости
		Image img = new ImageIcon(Game.class.getResource("/ru/kreatifchk/res/image/entity/monster/" + imageName + ".png")).getImage();
		img = Resize.resize(img, (int)(img.getWidth(null)*Main.INC), (int)(img.getHeight(null)*Main.INC));
		
		//Создаем буффер
		BufferedImage bf = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bf.getGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		
		//Заполняем массив кадрами
		view = new ImageIcon[bf.getWidth() / Tile.SIZE][bf.getHeight() / Tile.SIZE];
		for (int i = 0; i < bf.getWidth() / Tile.SIZE; i++) {
			for (int j = 0; j < bf.getHeight() / Tile.SIZE; j++) {
				view[i][j] = new ImageIcon(bf.getSubimage(i*Tile.SIZE, j*Tile.SIZE, Tile.SIZE, Tile.SIZE).getScaledInstance(Tile.SIZE, Tile.SIZE, Image.SCALE_SMOOTH));
			}
		}
		bf = null;
		
		currentView = view[1][0];
	}
	
	/** Движение на одну клетку (постепенное) в определенном направлении */
	protected void move(Direction dir) {
		if (dir == Direction.up) {
			realY -= Tile.SIZE / 24;
			//Обновление анимации
			if (stageMove % 8 == 0) 
			currentView = (currentFrame == 3) ? view[currentFrame = 0][3] : view[currentFrame++][3];
		}
		if (dir == Direction.down) {
			realY += Tile.SIZE / 24;
			if (stageMove % 8 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][0] : view[currentFrame++][0];
		}
		if (dir == Direction.left) {
			realX -= Tile.SIZE / 24;
			if (stageMove % 8 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][1] : view[currentFrame++][1];
		}
		if (dir == Direction.right) {
			realX += Tile.SIZE / 24;
			if (stageMove % 8 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][2] : view[currentFrame++][2];
		}
		stageMove++;
		
		//Когда дошло до следующей клетки изменить x (y), и показать, что готово к дальнейшему передвижению
		if (stageMove == 24) {
			stageMove = 0;
			if (dir == Direction.up) y--;
			if (dir == Direction.down) y++;
			if (dir == Direction.left) x--;
			if (dir == Direction.right) x++;
			this.dir = Direction.stand;
		}
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
	/** Установка текущего кадра */
	public void setCurrentView(int x, int y) {
		currentView = view[x][y];
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
