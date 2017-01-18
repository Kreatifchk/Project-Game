package ru.space;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Player extends JLabel {
	
	static int x = 9, y = 6; //Координаты в квадрате
	static int xA = 9, yA = 6; //Координаты общие
	static int xS = 9, yS = 6; //Координаты в квадрате (обнуляются при заходе в другой квадрат)
	
	static boolean stop;
	
	static ImageIcon pUp, pDown, pLeft, pRight;
	
	public Player() {
		pUp = new ImageIcon(getClass().getResource("res/PlayerUp.png"));
		pDown = new ImageIcon(getClass().getResource("res/PlayerDown.png"));
		pLeft = new ImageIcon(getClass().getResource("res/PlayerLeft.png"));
		pRight = new ImageIcon(getClass().getResource("res/PlayerRight.png"));
	}
	
	public void setXP(int x) {
		Player.x = x;
	}
	
	public void setYP(int y) {
		Player.y = y;
	}
	
	/** Получает x - координаты в квадрате */
	public static int getXP() {
		return x;
	}
	
	/** Получает y - координаты в квадрате */
	public static int getYP() {
		return y;
	}
	
	public static class PlayerListener extends KeyAdapter {
		//int currentX = getXP(), currentY = getYP();
		public void keyPressed(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			if (key == KeyEvent.VK_DOWN & stop == false) {
				Game.move(1);
				stop = true;
			}
			if (key == KeyEvent.VK_UP & stop == false) {
				Game.move(2);
				stop = true;
			}
			if (key == KeyEvent.VK_LEFT & stop == false) {
				Game.move(3);
				stop = true;
			}
			if (key == KeyEvent.VK_RIGHT & stop == false) {
				Game.move(4);
				stop = true;
			}
			if (key == KeyEvent.VK_0) {
				//System.out.println("Общие: X: " + xA + " Y: " + yA);
				System.out.println("В квадрате для прочих целей: X: " + xS + " Y: " + yS);
				System.out.println("Номер квадрата по x: " + Game.xSquare + " По y: " + Game.ySquare);
			}
		}
		public void keyReleased(KeyEvent e) {
			stop = false;
			//int key = e.getExtendedKeyCode();
			
		}
	}
	
}
