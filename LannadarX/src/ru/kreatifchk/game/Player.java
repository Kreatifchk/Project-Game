package ru.kreatifchk.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import ru.kreatifchk.main.ImageInit;
import ru.kreatifchk.tools.Sleep;

@SuppressWarnings("serial")
public class Player extends JLabel {
	
	int hpMax = 300, mpMax = 100, ep = 0; //Очки здоровья, маны и опыта (максимальные)
	int hp = 200, mp = 90;
	int level = 999; //Уровень персонажа
	int type = 0; //Класс персонажа (на данный момент недоступно)
	
	int currentLocation = 0; //Текущая локация
	
	int xMap = 12, yMap = 10; //Положение на локации (в тайлах)
	int xFrame, yFrame; //Положение на экране
	
	private Image act;
	private int actFrame = 0;
	
	protected Direction localDir;
	
	Rectangle rect; //Квадрат размер в треть тайла, когда персонаж на него наступает считается, что он перешел в другой тайл
	
	/** Направление движения */
	protected static enum Direction {up, down, left, right, stand};
	
	
	public Player() {
		this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		changeFrame(Direction.down);
	}
	
	protected void startMoveThread() {
		new Thread(() -> move()).start(); //Поток обеспечивающий движение персонажа
	}
	
	/** Движение персонажа */
	protected void move() {
		while (true) {
			Sleep.sleep(1);
			try {
			//Проверяем, что нажата соответствующая кнопка, а так-же, что следующий тайл не твердый,
			//но только если игрок уже полностью стоит на текущем
			if (Keyboard.dir == Direction.up & yMap - 1 >= 0 &&
					!(Game.map[xMap][yMap-1].solid == true & Game.map[xMap][yMap].getY() == getY())) {
				//Если персонаж наступил на новый тайл то будет считаться, что он уже на нем
				//if (getBounds().y == Game.map[xMap][yMap-1].getY()) {
					//yMap--;
				//}
				
				System.out.println(yFrame);
				//Если верхний ряд за экраном, а персонаж в центре двигать поле вниз
				if (Game.map[0][0].getY() < 0 & yFrame == 10) {
					movedMap(Keyboard.dir, 0, Tile.SIZE);
				} else {
					//Иначе двигать персонажа
					movedPlayer(Keyboard.dir, 0, -Tile.SIZE);
				}
			}
			
			else if (Keyboard.dir == Direction.down & yMap + 1 < Game.map.length &&
					!(Game.map[xMap][yMap+1].solid == true & Game.map[xMap][yMap].getY() == getY())) {
				//if (getBounds().y == Game.map[xMap][yMap+1].getY()) {
					//yMap++;
				//}
				
				System.out.println(yFrame);
				if (Game.map[0][Game.map[0].length-1].getY() > Game.tilePanel.getHeight() - Tile.SIZE & yFrame == 10) {
					movedMap(Keyboard.dir, 0, -Tile.SIZE);
				} else {
					movedPlayer(Keyboard.dir, 0, Tile.SIZE);
				}
			}
			
			else if (Keyboard.dir == Direction.left & xMap - 1 >= 0 &&
					!(Game.map[xMap-1][yMap].solid == true & Game.map[xMap][yMap].getX() == getX())) {
				//if (getBounds().x == Game.map[xMap-1][yMap].getX()) {
					//xMap--;
				//}
				
				if (Game.map[0][0].getX() < 0 & xFrame == 12) {
					movedMap(Keyboard.dir, Tile.SIZE, 0);
				} else {
					movedPlayer(Keyboard.dir, -Tile.SIZE, 0);
				}
			}
			
			else if (Keyboard.dir == Direction.right & xMap + 1 < Game.map.length &&
					!(Game.map[xMap+1][yMap].solid == true & Game.map[xMap][yMap].getX() == getX())) {
				//if (getBounds().x == Game.map[xMap+1][yMap].getX()) {
					//xMap++;
				//}
				
				if (Game.map[Game.map.length-1][0].getX() > Game.mainPane.getWidth() - Tile.SIZE & xFrame == 12) {
					movedMap(Keyboard.dir, -Tile.SIZE, 0);
				} else {
					movedPlayer(Keyboard.dir, Tile.SIZE, 0);
				}
			}
			} catch (Exception e) {}
		}
	}
	
	//Двигает игрока (когда он близко к краю)
	private void movedPlayer(Direction dir, int x, int y) {
		x /= 24;
		y /= 24;
		
		for (int i = 0; i < 24; i++) {
			localDir = dir;
			setLocation(getX() + x, getY() + y);
			Sleep.sleep(16);
		}
		localDir = Direction.stand;
		
		//System.out.println("dd");
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
		
		//Позволяет знать на каком тайле находится персонаж
		/*if (dir == Player.Direction.down) {
			countY++;
		} else if (dir == Player.Direction.up) {
			countY--;
		} else if (dir == Player.Direction.left) {
			countX--;
		} else if (dir == Player.Direction.right) {
			countX++;
		}
		
		//Если было сделано 24 шагов значит персонаж перешел на другой тайл
		if (countY == 24) {
			countY = 0;
			//yMap++;
			//Изменяем так-же положение на окне
			if (yFrame < 12) {
				yFrame++;
			}
		}
		if (countY == -24) {
			countY = 0;
			//yMap--;
			if (yFrame > 0) {
				yFrame--;
			}
		}
		if (countX == 24) {
			countX = 0;
			//xMap++;
			if (xFrame < 20) {
				xFrame++;
			}
		}
		if (countX == -24) {
			countX = 0;
			//xMap--;
			if (xFrame > 0) {
				xFrame--;
			}
		}*/
		
		Game.guk.setText(xMap + " " + yMap);
		
		//Sleep.sleep(16);
	}
	
	private int countX = 0, countY = 0;
	//Двигает поле при движении персонажа (если персонаж в центре карты)
	private void movedMap(Direction dir, int x, int y) {
		int del = 24;
		
		y /= del;
		x /= del;
		
		for (int z = 0; z < 24; z++) {
			localDir = dir;
		for (int i = 0; i < Game.map[0].length; i++) {
			for (int j = 0; j < Game.map.length; j++) {
				Game.map[j][i].setLocation(Game.map[j][i].getX() + x, Game.map[j][i].getY() + y);
			}
		}
		Sleep.sleep(16);
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
		
		//Позволяет знать на каком тайле находится персонаж
		/*if (dir == Player.Direction.down) {
			countY++;
		} else if (dir == Player.Direction.up) {
			countY--;
		} else if (dir == Player.Direction.left) {
			countX--;
		} else if (dir == Player.Direction.right) {
			countX++;
		}
		
		//Если было сделано 12 шагов значит персонаж перешел на другой тайл
		/*if (countY == del) {
			countY = 0;
			yMap++;
		}
		if (countY == -del) {
			countY = 0;
			yMap--;
		}
		if (countX == del) {
			countX = 0;
			xMap++;
		}
		if (countX == -del) {
			countX = 0;
			xMap--;
		}*/
		
		Game.guk.setText(xMap + " " + yMap);
		
		//Sleep.sleep(16);
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
	}
	
}
