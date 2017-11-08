package base;

import java.util.Random;

import menu.Menu;

public class Battle implements Runnable{
	
	static boolean battle = false;
	boolean zdr = true;
	
	//boolean hpFreeze = true; //Не дает максимальному хп моба изменяться во время боя
	
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
					Menu.g.upPanel.hpM.setVisible(true); //Создает панель с хп моба
					Animation.sleep(300);
					zdr = false;
				}
				mobeAttack(); //Атака монстра
				playerAttack(); //Атака персонажа
				if (HpMobs.xpCurrent <= 0) {
					//Если у моба осталось меньше 0 хп
					battle = false;
					if (Game.pl.level < Player.maxLevel) {
						expPlus();
						LevelUp.levelUp();
					}
					
					//Menu.g.removeComponent(Game.monster.get(id));
					Game.mainPane.remove(Game.monster.get(id)); //Убирает убитого монстра с карты
					Game.mapx[mobX][mobY].busy = false; //Освобождает клетку от монстра
					Menu.g.upPanel.hpM.setVisible(false); //Убирает панель с хп моба
					
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
		//Повышает прогресс в выполнении квеста
		for (int i = 0; i <= Game.takeQwests.length-1; i++) {
			if (Game.takeQwests[i] != -1 &&
					Game.qwest[Game.takeQwests[i]].nameMonster.equals(name)) {
				//Если убиваешь моба который нужен по квесту
				if (Game.qwest[Game.takeQwests[i]].progress < Game.qwest[Game.takeQwests[i]].count) {
					//Если еще не убил достаточно, прибавить число убитых
					Game.qwest[Game.takeQwests[i]].progress++;
					if (Game.qwest[Game.takeQwests[i]].progress ==
							Game.qwest[Game.takeQwests[i]].count) {
						//И еще раз проверяешь и если это максимум то поменять статус
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
		//Определяет силу атаки игрока
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
		Game.pl.hpThis -= mobAttack;
		if (Game.pl.hpThis < 0) {
			Game.pl.hpThis = 0; //В случае если после атаки хп меньше нуля, чтоб не показывало отрицательное hp
		}
	}
	
	private void expPlus() {
		//Определяет сколько дать опыта за монстра
		if (Game.pl.level <= mobLevel) {
			//Если уровень игрока меньше или равен уровню монстра то максимум опыта
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
