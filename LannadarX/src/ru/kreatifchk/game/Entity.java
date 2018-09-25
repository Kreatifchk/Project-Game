package ru.kreatifchk.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

import ru.kreatifchk.game.Direction;
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
	
	int x, y; //Текущие x и y в тайловой координатной системе
	public int startX, startY; //Те на которых он появляется
	int realX, realY; //Координаты x и y в пикселах относительно экрана
	protected int targetX = -1, targetY = -1; //Координаты куда необходимо двигаться монстру
	private int stageMove = 0; //Процесс движения
	
	protected Direction dir = Direction.stand; //Направление движения
	
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
		//Если частота цикла - 20мс, то делить на 24, а анимацию обновлять по stageMove % 8
		if (dir == Direction.up) {
			realY -= Tile.SIZE / 12;
			//Обновление анимации
			if (stageMove % 4 == 0) 
			currentView = (currentFrame == 3) ? view[currentFrame = 0][3] : view[currentFrame++][3];
		}
		if (dir == Direction.down) {
			realY += Tile.SIZE / 12;
			if (stageMove % 4 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][0] : view[currentFrame++][0];
		}
		if (dir == Direction.left) {
			realX -= Tile.SIZE / 12;
			if (stageMove % 4 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][1] : view[currentFrame++][1];
		}
		if (dir == Direction.right) {
			realX += Tile.SIZE / 12;
			if (stageMove % 4 == 0)
			currentView = (currentFrame == 3) ? view[currentFrame = 0][2] : view[currentFrame++][2];
		}
		stageMove++;
		
		//Когда дошло до следующей клетки изменить x (y), и показать, что готово к дальнейшему передвижению
		if (stageMove == 12) {
			stageMove = 0;
			if (dir == Direction.up) y--;
			if (dir == Direction.down) y++;
			if (dir == Direction.left) x--;
			if (dir == Direction.right) x++;
			this.dir = Direction.stand;
		}
	}
	
	//Движение к определенной точке, куда двигаться задается из следующего метода
	protected void moveTo() {
		MovePoint[][] pole = new MovePoint[Game.map.length][Game.map[0].length]; //Здесь будет записываться дальность
		for (int i = 0; i < Game.map[0].length; i++) {
			for (int j = 0; j < Game.map.length; j++) {
				pole[j][i] = new MovePoint();
				pole[j][i].x = j;
				pole[j][i].y = i;
				if (Game.map[j][i].solid || Game.map[j][i].busy) {
					pole[j][i].available = false;
				}
			}
		}
		pole[x][y].available = true; //Клетка на которой стоит entity дсотупна иначе не заработает
		
		//Очередь клеток, к оцениванию
		Queue<MovePoint> elements = new LinkedList<MovePoint>();
		
		//Координаты просматриваемых ячеек, с начала устанавливаются на целевой клетке
		int xS = targetX;
		int yS = targetY;
		//Сразу задаем, что путь до целевой клетки (все инвертировано) - 0
		pole[xS][yS].cost = 0;
		//Цикл на прохождение по полю
		label1: while(true) {
			//Просматриваем соседей каждой стороны
			for (int i = 0; i < 4; i++) {
				//Убеждаемся, что просматриваемая клетка существует, не твердая и не оценена ранее
				if (yS - 1 >= 0 && pole[xS][yS-1].available & pole[xS][yS-1].cost == -1) {
					//Просмотр верхней клетки
					pole[xS][yS-1].cost = pole[xS][yS].cost + 1;
					//Дабвляем ее в очередь
					elements.add(pole[xS][yS-1]);
				}
				if (yS + 1 < pole[0].length && pole[xS][yS+1].available & pole[xS][yS+1].cost == -1) {
					//Просмотр нижний клетки
					pole[xS][yS+1].cost = pole[xS][yS].cost + 1;
					elements.add(pole[xS][yS+1]);
				}
				if (xS - 1 >= 0 && pole[xS-1][yS].available & pole[xS-1][yS].cost == -1) {
					//Просмотр влево
					pole[xS-1][yS].cost = pole[xS][yS].cost + 1;
					elements.add(pole[xS-1][yS]);
				}
				if (xS + 1 < pole.length && pole[xS+1][yS].available & pole[xS+1][yS].cost == -1) {
					//Просмотр вправо
					pole[xS+1][yS].cost = pole[xS][yS].cost + 1;
					elements.add(pole[xS+1][yS]);
				}
			}
			//Пока очередь не пуста указываем следующий элемент, который необходимо рассмотреть
			//Так-же остановить просчет если дошел до цели
			if (elements.isEmpty() || (xS == x & yS == y)) {
				break label1;
			} else {
				MovePoint pt = elements.poll();
				xS = pt.x;
				yS = pt.y;
			}
		}
		
		//Заканчиваем все если дошел до цели
		if (x == targetX & y == targetY) {
			targetX = -1;
			targetY = -1;
			dir = Direction.stand;
			return;
		}
		
		//Команда к движению (если цена одной из клеток вокруг меньше на один), при этом изменить направление можно
		//только когда дошел полностью до следующей клетки
		if (dir == Direction.stand) {
			if (y - 1 >= 0 && pole[x][y].cost - 1 == pole[x][y-1].cost) {
				move(Direction.up);
				dir = Direction.up;
			}
			else if (y + 1 < pole[0].length && pole[x][y].cost - 1 == pole[x][y+1].cost) {
				move(Direction.down);
				dir = Direction.down;
			}
			else if (x - 1 >= 0 && pole[x][y].cost - 1 == pole[x-1][y].cost) {
				move(Direction.left);
				dir = Direction.left;
			}
			else if (x + 1 < pole.length && pole[x][y].cost - 1 == pole[x+1][y].cost) {
				move(Direction.right);
				dir = Direction.right;
			}
		} else {
			move(dir);
		}
		
		pole = null;
	}
	
	/** Дает указание двигаться к определенной точке */
	protected void moveTo(int targetX, int targetY) {
		System.out.println(targetX + " " + targetY);
		//Предусмотреть чтоб нельзя было отправить на занятую клетку
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	//Класс поле по которому будет составляться путь к точке
	private class MovePoint {
		int x, y;
		int cost = -1; //Сколько клеток необходимо пройти чтоб добраться до целевой клетки
		boolean available = true; //Можно ли пройти по клетке (нет если она твердая или кто-то на ней стоит)
	}
	
	/** Установка местоположениия по тайлам */
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
