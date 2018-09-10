package ru.kreatifchk.game;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import ru.kreatifchk.main.Main;
import ru.kreatifchk.main.Menu;

@SuppressWarnings("serial")
public class Game extends JFrame {
	
	protected static JLayeredPane mainPane = new JLayeredPane();
	protected static JLabel tilePanel = new JLabel();
	
	static Player pl;//Класс игрока
	
	static MapPoint[][] map; //Массив клеток игровой карты
	
	int tm; //Для таймера и отрисовки анимаций
	
	public Game(boolean load) {
		setTitle("Lannadar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.WIDTH-Main.insets.left-Main.insets.right, Main.HEIGHT);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);	
		setIconImage(Menu.icon);
		setFocusable(true);
		addKeyListener(new Keyboard());
		
		//Если load =  true то игра загружается из сохранения, иначе то начинается снова
		if (load) {
			
		} else {
			pl = new Player();
		}
		
		arragement();
		
		//Запуск таймера для ОЗУ
		Timer t = new Timer(16, (e) -> {
			//long memory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
			//setTitle("lannadar " + memory);
			//repaint();
			updateGraphics();
		});
		t.start();
		
		pl.startMoveThread();
	}
	
	//Первичная компоновка
	private void arragement() {
		mainPane.setBounds(0, 0, getWidth(), getHeight());
		mainPane.setDoubleBuffered(true);
		add(mainPane);
		
		//Панель на которой будет расположено игровое поле
		tilePanel.setBounds(0, 0, mainPane.getWidth(), (int)(624*Main.INC));
		mainPane.add(tilePanel, new Integer(0));
		
		//Панель со здоровььем маной и.т.д игрока
		StatusBar sb = new StatusBar();
		sb.setLocation(0, 0);
		mainPane.add(sb, new Integer(2));
		
		//Добавляем игровое поле
		new LoadMap(pl.currentLocation + "");
		initMap();
		
		//Добавляем на карту игрока
		mainPane.add(pl, new Integer(1));
		
		guk = new JLabel();
		guk.setOpaque(true);
		guk.setBackground(Color.WHITE);
		guk.setBounds(300, 200, 70, 30);
		guk.setText(pl.xMap + " " + pl.yMap);
		mainPane.add(guk, new Integer(10));
	}
	static JLabel guk;
	
	//Отрисовывает карту
	private void initMap() {
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
				if (Main.INC == 1) {
					//Из какой-то хрени с размерами
					map[j][i].setLocation(x, y - 10);
				} else {
					map[j][i].setLocation(x, y);
				}
				
				
				tilePanel.add(map[j][i], new Integer(0));
				
				//Указать местонахождение персонажа
				if (i == pl.yMap & j == pl.xMap) {
					pl.xFrame = j;
					pl.yFrame = i;
					pl.setBounds(x, y, (int)(48*Main.INC), (int)(48*Main.INC));
				}
				
				x += Tile.SIZE;
			}
			x = xPrimal;
			y += Tile.SIZE;
		}
	}
	
	//Метод в таймере, реализует анимацию
	private void updateGraphics() {
		tm += 16;
		if (tm > 960) {
			tm = 0;
		}
		
		if (pl.localDir != Player.Direction.stand & tm % 128 == 0) {
			//pl.changeFrame(Keyboard.dir);
			pl.changeFrame(pl.localDir);
		}
		repaint();
	}
	
	protected static class MapPoint extends JLabel {
		boolean solid;
		public MapPoint() {
			//setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}
	}
	
}
