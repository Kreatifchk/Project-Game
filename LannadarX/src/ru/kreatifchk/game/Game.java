package ru.kreatifchk.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import ru.kreatifchk.editor.Map;
import ru.kreatifchk.game.Entity.State;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.main.Menu;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Sleep;

@SuppressWarnings("serial")
public class Game extends JFrame {
	
	protected static JLayeredPane mainPane = new JLayeredPane();
	protected static TileLabel tilePanel = new TileLabel();
	
	static Player pl;//Класс игрока
	
	static MapPoint[][] map; //Массив клеток игровой карты
	
	static EntityStatusBar esb;
	
	int tm; //Для таймера и отрисовки анимаций
	
	Thread game = new Thread(new GameCycle()); //Игровой цикл
	
	static int fps;
	
	public Game(boolean load) {
		setTitle("Lannadar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.WIDTH-Main.insets.left-Main.insets.right, Main.HEIGHT);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setLocation(Menu.loc);
		setIconImage(Menu.icon);
		setFocusable(true);
		addKeyListener(new Keyboard());
		
		//Если load =  true то игра загружается из сохранения, иначе начинается с начала
		if (load) {
			
		} else {
			pl = Player.getPlayer();
		}
		
		arragement();
		
		//Запуск таймера для ОЗУ и перерисовки
		Timer t = new Timer(16, (e) -> {
			long memory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
			setTitle("Lannadar  ОЗУ: " + memory + " MB  FPS: " + fps);
			updateGraphics();
		});
		t.start();
		
		pl.startMoveThread();
		game.start();
		
		//Поток демон для большей точности времени сна
		Thread dm = new Thread(() -> {Sleep.sleep(Integer.MAX_VALUE);});
		dm.setDaemon(true);
		dm.start();
	}
	
	//Первичная компоновка
	private void arragement() {
		mainPane.setBounds(0, 0, getWidth(), getHeight());
		//mainPane.setDoubleBuffered(true);
		mainPane.setOpaque(true);
		add(mainPane);
		
		//Панель на которой будет расположено игровое поле
		tilePanel.setBounds(0, 0, mainPane.getWidth(), (int)(624*Main.INC));
		tilePanel.setOpaque(true);
		tilePanel.addMouseListener(new MapListener());
		mainPane.add(tilePanel, new Integer(0));
		
		//Панель со здоровььем маной и.т.д игрока
		StatusBar sb = new StatusBar();
		sb.setLocation(0, 0);
		mainPane.add(sb, new Integer(2));
		
		//Аналогичная панель для монстров (изначально скрытая)
		esb = new EntityStatusBar();
		esb.setLocation((mainPane.getWidth() - esb.getWidth()) - (int)(4*Main.INC), 0);
		esb.setVisible(false);
		mainPane.add(esb, new Integer(2));
		
		//Добавляем игровое поле
		new LoadMap(pl.currentLocation + "");
		initMap();
		
		//Добавляем на карту игрока
		mainPane.add(pl, new Integer(1));
		Game.map[pl.xMap][pl.yMap].busy = true;
		
		//Инициализируем мобов
		initMonsters();
	}
	
	//Отрисовывает карту
	protected static void initMap() {
		int xPrimal = 0, yPrimal = 0;
		//Проверяем на какой позиции находится игрок, если слишком близко к краям то рисовать его не в центре
		if (pl.xMap <= 8) {
			xPrimal = 0;
		}
		else if (pl.xMap + 11 >= map.length) {
			xPrimal = -((map.length - 20) * Tile.SIZE);
		} else {
			//Иначе делаем позицию игрока в центре окна (или если карта минимального размера ничего не двигаем)
			if (map.length == 20) {
				xPrimal = 0;
			} else {
				xPrimal = -((pl.xMap - 8) * Tile.SIZE);
			}
		}
		
		if (pl.yMap < 7) {
			yPrimal = 0;
		}
		else if (pl.yMap + 7 >= map[0].length) {
			yPrimal = -((map[0].length - 13) * Tile.SIZE);
		} else {
			if (map[0].length == 13) {
				yPrimal = 0;
			} else {
				yPrimal = -((pl.yMap - 6) * Tile.SIZE);
			}
		}
		
		int x = xPrimal, y = yPrimal;
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[j][i].setLocation(x, y);
				
				//Указать местонахождение персонажа
				if (i == pl.yMap & j == pl.xMap) {
					pl.setBounds(x, y, (int)(48*Main.INC), (int)(48*Main.INC));
				}
				
				x += Tile.SIZE;
			}
			x = xPrimal;
			y += Tile.SIZE;
		}
	}
	
	//Загрузка новой карты
	protected static void changeMap() {
		//Создаем прогресс бар
		GradientProgressBar gpb = new GradientProgressBar();
		gpb.setSize((int)(350*Main.INC), (int)(18*Main.INC));
		gpb.setIndeterminate(true);
		Center.cnt(gpb, tilePanel);
		tilePanel.add(gpb);
		
		//Загружаем карту
		new LoadMap(pl.currentLocation + "");
		tilePanel.drawBuffer();
		initMap();
		
		//Удаляем прогресс бар
		tilePanel.remove(gpb);
	}
	
	public static int count;
	//Метод в таймере, реализует анимацию
	private void updateGraphics() {
		tm += 15;
		if (tm > 990) {
			tm = 0;
		}
		
		if (pl.localDir != Direction.stand & tm % 150 == 0) {
			pl.changeFrame(pl.localDir);
		}
		mainPane.repaint();
		
		//Подсчитывает fps
		count++;
	}
	
	//Инициализация монстров
	private void initMonsters() {
		for (Monster i: Map.monsters) {
			i.setCurrentView(1, 0);
			i.x = i.startX;
			i.y = i.startY;
			i.realX = map[i.x][i.y].getX();
			i.realY = map[i.x][i.y].getY();
			map[i.x][i.y].busy = true;
			
			//Удалить позже
			i.hp = i.hpMax;
			i.mp = i.mpMax;
		}
	}
	
	//Класс панели на которой рисуется карта
	protected static class TileLabel extends JLabel {
		BufferedImage img;
		//Отрисовываем карту на буфере
		private void drawBuffer() {
			int width = map.length * Tile.SIZE;
			int height = map[0].length * Tile.SIZE;
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			
			int x = 0, y = 0;
			for (int i = 0; i < map[0].length; i++) {
				for (int j = 0; j < map.length; j++) {
					g2d.drawImage(TilesList.tiles[map[j][i].number].icon.getImage(), x, y, null);
					//g2d.drawRect(x, y, Tile.SIZE, Tile.SIZE);
					x += Tile.SIZE;
				}
				x = 0;
				y += Tile.SIZE;
			}
			
			g2d.dispose();
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if (img == null) {
				drawBuffer();
			}
			g2d.drawImage(img, map[0][0].getX(), map[0][0].getY(), null);
			
			//Отрисовка монстров, будет в отдельном буфере
			for (Monster i: Map.monsters) {
				if (i.state != State.dead)
				g2d.drawImage(i.currentView.getImage(), i.realX, i.realY, null);
			}
		}
	}
	
}
