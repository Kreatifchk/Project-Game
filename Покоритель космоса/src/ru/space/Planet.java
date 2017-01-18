package ru.space;

import java.util.Random;

import javax.swing.ImageIcon;

public class Planet {
	
	static ImageIcon stone, grass, water, sand, three, three2;
	
	static Random r = new Random();
	
	static int typeP;//��� �������
	
	/*���� ������
	 *  5 - ������, 2 - �����, 3 - ����, 4 - ����� 
	 */
	
	public static void genPlanet() {
		//typeP = r.nextInt(50) + 1;
		typeP = 22;
	}
	
	//0 - ��������, 1 - ������, 2 - �������, 3 - �����, 4 - ������
	public static int pole(int square) {
		int rez = 0;
		if (typeP >= 21) {
			//������� �������
			/*int type = r.nextInt(120) + 1;
			if (type >= 111 & type <= 120) {
				rez = 5;
			}
			if (type >= 0 & type <= 80) {
				rez = 2;
			}
			if (type >= 81 & type <= 100) {
				rez = 3;
			}
			if (type >= 101 & type <= 110) {
				rez = 4;
			}*/
			GeneratePlanet.generate(1, square);
		}
		
		else if (typeP >= 0 & typeP <= 5) {
			//�������� �������
			rez = 5;
		}
		
		else if (typeP >= 11 & typeP <= 20) {
			//������� �������� �������
			int type = r.nextInt(2) + 1;
			if (type == 1) {
				rez = 5;
			}
			if (type == 2) {
				rez = 4;
			}
		}
		
		else if (typeP >= 6 & typeP <= 10) {
			//������ �������
			rez = 3;
		}
		return rez;
	}

	//����������� � ������������� �����
	public void iconTile(int type, int i, int j) {
		init();
		if (type == 2) {
			Game.tile[i][j].setIcon(grass);
			Game.tile[i][j].type = 2;
			Game.tile[i][j].solid = false;
		}
		if (type == 3) {
			Game.tile[i][j].setIcon(water);
			Game.tile[i][j].type = 3;
			Game.tile[i][j].solid = true;
		}
		if (type == 4) {
			Game.tile[i][j].setIcon(sand);
			Game.tile[i][j].type = 4;
			Game.tile[i][j].solid = false;
		}
		if (type == 5) {
			Game.tile[i][j].setIcon(stone);
			Game.tile[i][j].type = 5;
			Game.tile[i][j].solid = false;
		}
		if (type == 6) {
			Game.tile[i][j].setIcon(three);
			Game.tile[i][j].type = 6;
			Game.tile[i][j].solid = true;
		}
		if (type == 7) {
			Game.tile[i][j].setIcon(three2);
			Game.tile[i][j].type = 7;
			Game.tile[i][j].solid = true;
		}
	}
	
	private void init() {
		stone = new ImageIcon(getClass().getResource("res/stone.png"));
		grass = new ImageIcon(getClass().getResource("res/grass.png"));
		water = new ImageIcon(getClass().getResource("res/water.png"));
		sand = new ImageIcon(getClass().getResource("res/sand.png"));
		three = new ImageIcon(getClass().getResource("res/three.png"));
		three2 = new ImageIcon(getClass().getResource("res/three2.png"));
	}
	
	public static void tile(int j, int i, int type) {
		if (type == 5) {
			Game.tile[j][i].setIcon(stone);
		}
		if (type == 2) {
			Game.tile[j][i].setIcon(grass);
		}
		if (type == 3) {
			Game.tile[j][i].setIcon(water);
		}
		if (type == 4) {
			Game.tile[j][i].setIcon(sand);
		}
	}
	
}
