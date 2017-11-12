package base;

import java.util.Random;

import menu.Menu;

public class Battle implements Runnable{
	
	static boolean battle = false;
	
	static int mobAttack, mobMp, mobLevel, mobExp, mobX, mobY;
	static int hpMax, hpCurrent;
	static String name;
	
	Dead d = new Dead();
	
	static int id; //id монстра
	
	@Override
	public void run() {
		while (true) {
			Animation.sleep(10);
			if (battle == true) {
				//Показывает панели с хп, манной, именем моба
				Menu.g.upPanel.nameM.setVisible(true);
				Menu.g.upPanel.hpM.setVisible(true);
				Menu.g.upPanel.mpM.setVisible(true);
				Menu.g.upPanel.lvlM.setVisible(true);
				
				Animation.sleep(300);
					
				mobeAttack(); //Атака монстра
				playerAttack(); //Атака персонажа
				if (hpCurrent <= 0) {
					//Если у моба осталось меньше 0 хп
					battle = false;
					if (Game.pl.level < Player.maxLevel) {
						expPlus();
						LevelUp.levelUp();
					}
					
					Game.monster.get(id).alive = false;
					Game.monster.get(id).setVisible(false); //Убирает убитого монстра с карты
					Game.mapx[mobX][mobY].busy = false; //Освобождает клетку от монстра
					//Респаун мобов
					new Thread(new Runnable() {
						@Override
						public void run() {
							int id = Battle.id;
							Animation.sleep(30000);
							Monsters mns = Game.monster.get(id);
							mns.alive = true;
							mns.setVisible(true);
							Game.mapx[mns.x][mns.y].busy = true;
						}
					}).start();
					//Скрывает панели моба
					Menu.g.upPanel.nameM.setVisible(false);
					Menu.g.upPanel.hpM.setVisible(false);
					Menu.g.upPanel.mpM.setVisible(false);
					Menu.g.upPanel.lvlM.setVisible(false);
					
					hpCurrent = 0;
					hpMax = 0;
					
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
			hpCurrent -= pAttack;
		} else if (sh >= 41 & sh <= 60) {
			hpCurrent -= pAttack;
		} else if (sh >= 61 & sh < 80){
			pAttack += Maths.persentageNumber(pAttack, 3);
			hpCurrent -= pAttack;
		} else {
			pAttack += Maths.persentageNumber(pAttack, 100);
			hpCurrent -= pAttack;
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
