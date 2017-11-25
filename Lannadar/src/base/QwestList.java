package base;

/** класс - список квестов */
public class QwestList {
	
	//Перед добавлением измени размер массива в классе Game
	
	public QwestList() {
		//Аргументы: id, name, textN, textK, request, exp, lastId(опционально)
		//Для убийства: nameMonster, number и общие в том же порядке
		//Для разговора idNPC и далее общие
		Game.qwest[0] = new QwestTalk(1, 0, 1);
		Game.qwest[1] = new QwestTalk(2, 1, 10, 0);
		Game.qwest[2] = new QwestTalk(1, 2, 10, 1);
		Game.qwest[3] = new QwestKilling(new String[] {"Зомби"}, new int[] {3}, 3, 20, 2);
		Game.qwest[4] = new QwestTalk(3, 4, 14, 3);
		Game.qwest[5] = new QwestCollect(new int[] {100}, new int[] {3}, 5, 20, 4);
		Game.qwest[6] = new QwestKilling(new String[] {"Зомби"}, new int[] {2}, 6, 25, 4);
		Game.qwest[7] = new QwestKilling(new String[] {"Скелет"}, new int[] {3}, 7, 35, 5);
		Game.qwest[8] = new QwestTalk(5, 8, 40, 7);
		Game.qwest[9] = new QwestKilling(new String[] {"Некромант"}, new int[] {5}, 9, 60, 8);
		Game.qwest[10] = new QwestTalk(7, 10, 40, 9);
		Game.qwest[11] = new QwestTalk(999, 11, 999, 10);
	}
}
