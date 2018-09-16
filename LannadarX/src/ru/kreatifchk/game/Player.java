package ru.kreatifchk.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import ru.kreatifchk.main.ImageInit;
import ru.kreatifchk.tools.Sleep;

@SuppressWarnings("serial")
public class Player extends JComponent {
	
	protected int hpMax = 300, mpMax = 100, ep = 0; //Очки здоровья, маны и опыта (максимальные)
	protected int hp = 200, mp = 90;
	protected int level = 999; //Уровень персонажа
	int type = 0; //Класс персонажа (на данный момент недоступно)
	
	protected String currentLocation = "15"; //Текущая локация
	
	int xMap = 12, yMap = 11; //Положение на локации (в тайлах)
	int xFrame, yFrame; //Положение на экране
	
	private Image act;
	private int actFrame = 0;
	
	protected Direction localDir = Direction.stand; //Куда движется персонаж (независимая от нажатия переменная для анимации)
	
	/** Направление движения */
	public static enum Direction {up, down, left, right, stand};
	
	public Player() {
		//this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		changeFrame(Direction.down);
	}
	
	protected void startMoveThread() {
		new Thread(() -> move()).start(); //Поток обеспечивающий движение персонажа
		
		yFrame = Game.map[0][yMap].getY() / Tile.SIZE;
		xFrame = Game.map[xMap][0].getX() / Tile.SIZE;
	}
	
	/** Движение персонажа */
	protected void move() {
		boolean stop = false;
		while (true) {
			Sleep.sleep(2);
			try {
			//Проверяем в начале, что игрок не заходит в портал, если зашел то дальнейшие действия не делаем
			stop = false;
			if (transfer()) {
				stop = true;
			}
			//Проверяем, что нажата соответствующая кнопка, а так-же, что следующий тайл не твердый,
			//но только если игрок уже полностью стоит на текущем
			if (Keyboard.dir == Direction.up & yMap - 1 >= 0 && Game.map[xMap][yMap-1].solid == false & !stop) {
				//Если верхний ряд за экраном, а персонаж в центре двигать поле вниз
				if (Game.map[0][0].getY() < 0 & yFrame == 6) {
					movedMap(Keyboard.dir, 0, Tile.SIZE);
				} else {
					//Иначе двигать персонажа
					movedPlayer(Keyboard.dir, 0, -Tile.SIZE);
				}
			}
			
			if (Keyboard.dir == Direction.down & yMap + 1 < Game.map.length && Game.map[xMap][yMap+1].solid == false & !stop) {
				if (Game.map[0][Game.map[0].length-1].getY() > Game.tilePanel.getHeight() - Tile.SIZE & yFrame == 6) {
					movedMap(Keyboard.dir, 0, -Tile.SIZE);
				} else {
					movedPlayer(Keyboard.dir, 0, Tile.SIZE);
				}
			}
			
			if (Keyboard.dir == Direction.left & xMap - 1 >= 0 && Game.map[xMap-1][yMap].solid == false & !stop) {
				if (Game.map[0][0].getX() < 0 & xFrame == 8) {
					movedMap(Keyboard.dir, Tile.SIZE, 0);
				} else {
					movedPlayer(Keyboard.dir, -Tile.SIZE, 0);
				}
			}
			
			if (Keyboard.dir == Direction.right & xMap + 1 < Game.map.length && Game.map[xMap+1][yMap].solid == false & !stop) {
				if (Game.map[Game.map.length-1][0].getX() > Game.mainPane.getWidth() - Tile.SIZE & xFrame == 8) {
					movedMap(Keyboard.dir, -Tile.SIZE, 0);
				} else {
					movedPlayer(Keyboard.dir, Tile.SIZE, 0);
				}
			}
			localDir = Keyboard.dir;
			} catch (Exception e) {}
		}
	}
	
	//Проверка попытки перехода на другую локацию
	private boolean transfer() {
		if (Keyboard.dir == Game.map[xMap][yMap].transfer & Game.map[xMap][yMap].transfer != Direction.stand) {
			currentLocation = Game.map[xMap][yMap].newLocation;
			xMap = Game.map[xMap][yMap].xTrans;
			yMap = Game.map[xMap][yMap].yTrans;
			Game.changeFrame();
			return true;
		} else {
			return false;
		}
	}
	
	//Двигает игрока (когда он близко к краю)
	private void movedPlayer(Direction dir, int x, int y) {
		x /= 24;
		y /= 24;
		
		localDir = dir;
		for (int i = 0; i < 24; i++) {
			setLocation(getX() + x, getY() + y);
			Sleep.sleep(16);
		}
		localDir = Direction.stand;
		
		if (dir == Player.Direction.down) {
			yMap++;
			if (yFrame < 12) {
				yFrame++;
			}
		} else if (dir == Player.Direction.up) {
			yMap--;
			if (yFrame > 0) {
				yFrame--;
			}
		} else if (dir == Player.Direction.left) {
			xMap--;
			if (xFrame < 20) {
				xFrame++;
			}
		} else if (dir == Player.Direction.right) {
			xMap++;
			if (xFrame > 0) {
				xFrame--;
			}
		}
	}
	
	//Двигает поле при движении персонажа (если персонаж в центре карты)
	private void movedMap(Direction dir, int x, int y) {
		y /= 24;
		x /= 24;
		
		localDir = dir;
		for (int z = 0; z < 24; z++) {
			for (int i = 0; i < Game.map[0].length; i++) {
				for (int j = 0; j < Game.map.length; j++) {
					final int i2 = i, j2= j, x2 = x, y2 = y;
					SwingUtilities.invokeLater(() -> {
						Game.map[j2][i2].setLocation(Game.map[j2][i2].getX() + x2, Game.map[j2][i2].getY() + y2);
					});
				}
			}
			Sleep.sleep(17);
		}
		
		localDir = Direction.stand;
		if (dir == Player.Direction.down) {
			yMap++;
		} else if (dir == Player.Direction.up) {
			yMap--;
		} else if (dir == Player.Direction.left) {
			xMap--;
		} else if (dir == Player.Direction.right) {
			xMap++;
		}
	}
	
	//Смена кадра анимации
	protected void changeFrame(Direction dir) {
		BufferedImage bi = new BufferedImage(144, 96, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		
		if (dir == Direction.down) {
			g.drawImage(ImageInit.plDown, 0, 0, null);
		} else if (dir == Direction.up) {
			g.drawImage(ImageInit.plUp, 0, 0, null);
		} else if (dir == Direction.left) {
			g.drawImage(ImageInit.plLeft, 0, 0, null);
		} else if (dir == Direction.right) {
			g.drawImage(ImageInit.plRight, 0, 0, null);
		}
		
		actFrame++;
		if (actFrame >= 3) {
			actFrame = 0;
		}
		
		BufferedImage bi2 = new BufferedImage(Tile.SIZE, Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
		bi2 = bi.getSubimage(actFrame*48, 0, 48, 48);
		
		g.dispose();
		bi = null;
		act = bi2.getScaledInstance(Tile.SIZE, Tile.SIZE, Image.SCALE_DEFAULT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(act, 0, 0, null);
		g2d.dispose();
	}
	
}
