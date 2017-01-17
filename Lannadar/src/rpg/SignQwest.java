package rpg;

import javax.swing.JLabel;

public class SignQwest {
	
	static boolean d = false; //true - ���� ���� �������� ������
	static boolean zav = false; //true - ���� ���� ������ ������� ����� �����
	
	static int pl = 0;
	
	static JLabel exclam; //�����. ���� ��� �������
	
	public static void sign(int i) {
		//���������: i - id NPC
			
		if (Game.mapx[Game.npc[i].x][Game.npc[i].y-1].getComponentCount() > 0) {
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].remove(exclam); //������� ��� ��������� ������
		}
		
		//���� NPC ����� �������� ������
		if (Game.npc[i].qwest != null) {
			int lng = Game.npc[i].qwest.length - 1; //���������� ���������� �������
			
			//��������� �� ���� ������� NPC
			cycle(lng, i);
			
			//������������ ����
			draw(i);
		}
	}
	
	private static void cycle(int lng, int i) {
		for (int j = 0; j <= lng; j++) {
			//���� ����� ������� �� ���� �������
			if (end(i, j) == true) {
				break;
			}
				
			//���� ����� ��������
			if (begin(i, j) == true) {
				break;
			}
		}
	}
	
	private static boolean end(int i, int j) {
		boolean br = false; //������ Break
		
		if (Game.qwest[Game.npc[i].qwest[j]].status == 3) {
			if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
				//���� ����� �����������
				if (Game.qwest[Game.npc[i].qwest[j]].idNPC == Game.npc[i].id) {
					//���� NPC ��� ���� ������� �����
					zav = true;
					br = true;
				}
			} else {
				//���� ����� �� �����������
				zav = true;
				br = true;
			}
		} else {
			zav = false;
		}
		
		return br;
	}
	
	private static boolean begin(int i, int j) {
		boolean br = false;
		
		if (Game.qwest[Game.npc[i].qwest[j]].status == 1) {
			//���� ������ �������� �����			
			if (Game.qwest[Game.npc[i].qwest[j]].lastId != -1) {
				//���� ��� �������� ������ ����� ������ ������
				int lastId = Game.qwest[Game.npc[i].qwest[j]].lastId;
				if (Game.qwest[lastId].status == 4) {
					if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
						//���� ����� �����������
						if (Game.qwest[Game.npc[i].qwest[j]].idNPC != Game.npc[i].id) {
							//���� NPC �� ��� ������� ��������� �����
							d = true;
							br = true;
						}
					} else {
						//���� ����� �� �����������
						d = true;
						br = true;
					}
				} else {
					d = false;
				}
			} else {
				//���� �������� �����
				if (Game.qwest[Game.npc[i].qwest[j]].idNPC != -1) {
					//���� ����� �����������
					if (Game.qwest[Game.npc[i].qwest[j]].idNPC != Game.npc[i].id) {
						//���� NPC �� ��� ������� ��������� �����
						d = true;
						br = true;
					}
				} else {
					//���� ����� �� �����������
					d = true;
					br = true;
				}
			}
		} else {
			d = false;
		}
		
		return br;
	}
	
	private static void draw(int i) {
		//���� �� ������ ������� ������ ����, �� ������� ������
		if (d == false) {
			try {
				Game.mapx[Game.npc[i].x][Game.npc[i].y-1].remove(exclam);
			} catch (NullPointerException e) {
			}
		}
		//���� �� ������ ������ ����, � ��� ������� ������� �������
		if (d == true) {
			exclam = new JLabel();
			exclam.setBounds(10, 0, Game.TILE, Game.TILE);
			exclam.setIcon(Game.qwI);
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].add(exclam);
			//�����. ����
		}
		if (zav == true) {
			exclam = new JLabel();
			exclam.setBounds(6, 0, Game.TILE, Game.TILE);
			exclam.setIcon(Game.qwSI);
			Game.mapx[Game.npc[i].x][Game.npc[i].y-1].add(exclam);
			//���� �������
		}
	}
	
}
