package rpg;

import java.util.Random;

public class Battle implements Runnable{
	
	static boolean battle = false;
	boolean zdr = true;
	
	//boolean hpFreeze = true; //�� ���� ������������� �� ���� ���������� �� ����� ���
	
	static int mobAttack, mobMp, mobLevel, mobExp, mobX, mobY;
	static String name;
	
	Dead d = new Dead();
	
	static int id;
	
	@Override
	public void run() {
		while (true) {
			Animation.sleep(10);
			if (battle == true) {
				if (zdr = true) {
					Menu.g.hpM.setVisible(true); //������� ������ � �� ����
					Animation.sleep(300);
					zdr = false;
				}
				mobeAttack(); //����� �������
				playerAttack(); //����� ���������
				if (HpMobs.xpCurrent <= 0) {
					//���� � ���� �������� ������ 0 ��
					battle = false;
					if (Game.pl.level < Player.maxLevel) {
						expPlus();
						LevelUp.levelUp();
					}
					
					Menu.g.removeComponent(Game.monster.get(id));
					Menu.g.remove(Game.monster.get(id)); //������� ������� ������� � �����
					Game.mapx[mobX][mobY].busy = false; //����������� ������ �� �������
					Menu.g.hpM.setVisible(false); //������� ������ � �� ����
					
					HpMobs.xpCurrent = 0;
					HpMobs.xpMax = 0;
					zdr = true;
					
					try {
						qwestTest();
					} catch (NullPointerException e) {
					}
				}
				Animation.sleep(1000);
				d.dead(Game.currentLocation);
			}
		}
	}
	
	private void qwestTest() {
		//�������� �������� � ���������� ������
		for (int i = 0; i <= Game.takeQwests.length-1; i++) {
			if (Game.takeQwests[i] != -1 &&
					Game.qwest[Game.takeQwests[i]].nameMonster.equals(name)) {
				//���� �������� ���� ������� ����� �� ������
				if (Game.qwest[Game.takeQwests[i]].progress < Game.qwest[Game.takeQwests[i]].count) {
					//���� ��� �� ���� ����������, ��������� ����� ������
					Game.qwest[Game.takeQwests[i]].progress++;
					if (Game.qwest[Game.takeQwests[i]].progress ==
							Game.qwest[Game.takeQwests[i]].count) {
						//� ��� ��� ���������� � ���� ��� �������� �� �������� ������
						Game.qwest[Game.takeQwests[i]].status = 3;
						sign(Game.qwest[Game.takeQwests[i]].id);
					}
				}
			}
		}
	}
	
	private void sign(int id) {
		for (int i = 0; i <= Game.npc.length - 1; i++) {
			if (Game.npc[i].qwest != null) {
				for (int j = 0; j <= Game.npc[i].qwest.length - 1; j++) {
					if (Game.qwest[Game.npc[i].qwest[j]].status == 3) {
						SignQwest.sign(i);
						break;
					}
				}
			}
		}
	}
	
	private void playerAttack() {
		//���������� ���� ����� ������
		Random r = new Random();
		int sh = r.nextInt(100);
		int pAttack = Game.pl.playerAttack;
		if (sh >= 0 & sh <= 40) {
			pAttack -= Maths.persentageNumber(pAttack, 3);
			HpMobs.xpCurrent -= pAttack;
		} else if (sh >= 41 & sh <= 60) {
			HpMobs.xpCurrent -= pAttack;
		} else if (sh >= 61 & sh < 80){
			pAttack += Maths.persentageNumber(pAttack, 3);
			HpMobs.xpCurrent -= pAttack;
		} else {
			pAttack += Maths.persentageNumber(pAttack, 100);
			HpMobs.xpCurrent -= pAttack;
		}
	}
	
	private void mobeAttack() {
		Game.hpThis -= mobAttack;
		if (Game.hpThis < 0) {
			Game.hpThis = 0; //� ������ ���� ����� ����� �� ������ ����, ���� �� ���������� ������������� hp
		}
	}
	
	private void expPlus() {
		//���������� ������� ���� ����� �� �������
		if (Game.pl.level <= mobLevel) {
			//���� ������� ������ ������ ��� ����� ������ ������� �� �������� �����
			Game.pl.exp += mobExp;
		} else if (Game.pl.level == mobLevel - 1) {
			Game.pl.exp += Maths.persentageNumber(mobExp, 20);
		} else if (Game.pl.level == mobLevel - 2) {
			Game.pl.exp += Maths.persentageNumber(mobExp, 30);
		} else if (Game.pl.level == mobLevel - 3) {
			Game.pl.exp += Maths.persentageNumber(mobExp, 50);
		}
	}
	
}
