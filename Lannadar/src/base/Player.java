package base;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Player implements Serializable {
	
	public static Image playerR[] = new Image[3];
	public static Image playerD[] = new Image[3];
	public static Image playerL[] = new Image[3];
	public static Image playerU[] = new Image[3];
	
	public int force = 2, durability = 50, intellect, armor = 0, regeneration;
		
	int mX = 2, mY = 7;
	
	int x = mX * 48, y = 48 * mY + 48;//, y = 0;
	
	int playerAttack = force * 3 + 2;
	
	int level = 1;

	static int maxLevel = 20;

	int exp = 0;

	int maxExp;
	
	static int speedRecovery = 5;
	
	int currentLocation;
	
	Scanner scn;
	File expF;
	//InputStream expF;
	
	static int[] expTable = new int[maxLevel];
	
	public Player() {
		expTable();
		maxExp = expTable[level-1];
		
		regeneration = speedRecovery;
		
		playerR[0] = new ImageIcon(getClass().getResource("res/Image/Player/r0.png")).getImage();
		playerR[1] = new ImageIcon(getClass().getResource("res/Image/Player/r1.png")).getImage();
		playerR[2] = new ImageIcon(getClass().getResource("res/Image/Player/r2.png")).getImage();
		
		playerD[0] = new ImageIcon(getClass().getResource("res/Image/Player/d0.png")).getImage();
		playerD[1] = new ImageIcon(getClass().getResource("res/Image/Player/d1.png")).getImage();
		playerD[2] = new ImageIcon(getClass().getResource("res/Image/Player/d2.png")).getImage();
		
		playerL[0] = new ImageIcon(getClass().getResource("res/Image/Player/l0.png")).getImage();
		playerL[1] = new ImageIcon(getClass().getResource("res/Image/Player/l1.png")).getImage();
		playerL[2] = new ImageIcon(getClass().getResource("res/Image/Player/l2.png")).getImage();
		
		playerU[0] = new ImageIcon(getClass().getResource("res/Image/Player/u0.png")).getImage();
		playerU[1] = new ImageIcon(getClass().getResource("res/Image/Player/u1.png")).getImage();
		playerU[2] = new ImageIcon(getClass().getResource("res/Image/Player/u2.png")).getImage();
	}
	
	public void init() {
		playerR[0] = new ImageIcon(getClass().getResource("res/Image/Player/r0.png")).getImage();
		playerR[1] = new ImageIcon(getClass().getResource("res/Image/Player/r1.png")).getImage();
		playerR[2] = new ImageIcon(getClass().getResource("res/Image/Player/r2.png")).getImage();
		
		playerD[0] = new ImageIcon(getClass().getResource("res/Image/Player/d0.png")).getImage();
		playerD[1] = new ImageIcon(getClass().getResource("res/Image/Player/d1.png")).getImage();
		playerD[2] = new ImageIcon(getClass().getResource("res/Image/Player/d2.png")).getImage();
		
		playerL[0] = new ImageIcon(getClass().getResource("res/Image/Player/l0.png")).getImage();
		playerL[1] = new ImageIcon(getClass().getResource("res/Image/Player/l1.png")).getImage();
		playerL[2] = new ImageIcon(getClass().getResource("res/Image/Player/l2.png")).getImage();
		
		playerU[0] = new ImageIcon(getClass().getResource("res/Image/Player/u0.png")).getImage();
		playerU[1] = new ImageIcon(getClass().getResource("res/Image/Player/u1.png")).getImage();
		playerU[2] = new ImageIcon(getClass().getResource("res/Image/Player/u2.png")).getImage();
		expTable();
		maxExp = expTable[level-1];
	}
	
	protected static void moveAnimation(int direction) {
		if (direction == 1) {
			int j = 0;
			for (int i = 1; i <= 8; i++) {
				Game.player = Player.playerD[j];
				j++;
				if (j == 3) {
					j = 0;
				}
				Game.pl.y = Game.pl.y + 6;
				Animation.sleep(50);
			}
			Game.player = Player.playerD[0];
		}
		if (direction == 2) {
			int j = 0;
			for (int i = 1; i <= 8; i++) {
				Game.player = Player.playerU[j];
				j++;
				if (j == 3) {
					j = 0;
				}
				Game.pl.y = Game.pl.y - 6;
				Animation.sleep(50);
			}
			Game.player = Player.playerU[0];
		}
		if (direction == 3) {
			int j = 0;
			for (int i = 1; i <= 8; i++) {
				Game.player = Player.playerL[j];
				j++;
				if (j == 3) {
					j = 0;
				}
				Game.pl.x = Game.pl.x - 6;
				Animation.sleep(50);
			}
			Game.player = Player.playerL[0];
		}
		if (direction == 4) {
			int j = 0;
			for (int i = 1; i <= 8; i++) {
				Game.player = Player.playerR[j];
				j++;
				if (j == 3) {
					j = 0;
				}
				Game.pl.x = Game.pl.x + 6;
				Animation.sleep(50);
			}
			Game.player = Player.playerR[0];
		}
	}
	
	//Необходимое кол-во exp для перехода на новый уровень
	private void expTable() {
		expTable[0] = 40;
		expTable[1] = 100;
		expTable[2] = 200;
		expTable[3] = 400;
		expTable[4] = 600;
	}
	
}
