package glxnew;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Empery {
	
	//git
	
	Color cl;
	
	static Random r = new Random();
	
	boolean immort; //Бессмертие
	
	int id;
	int oldId; //Чтоб после удадения империй, изменить у клеток id владельца
	
	int money = 0;
	int level = 1; //Уровень технолог. развития (влияет на лимит)
	
	/** Количество территорий */
	int count;
	
	Point capital; //Столица (для режима захват столицы)
	
	int corruption; //Уровень коррупции (понадобится в дальнейшем)
	int incCorr; //Увеличение уровня коррупции за каждый ход
	/** Недовольство людей */
	int disapproval;
	
	ArrayList<Party> parties = new ArrayList<Party>();
	
	public Empery() {
	}
	public Empery(int id) {
		this.id = id;
	}
	public Empery(int id, Color cl) {
		this.id = id;
		this.cl = cl;
	}
	
	//Генерирует партии (в начале игры)
	protected void parties() {
		int countP = 3;
		for (int i = 0; i < countP; i++) {
			genParty();
		}
		//Далее - распределние влияния (случайное)
		//Определяется влияние первой сгенерированной партии
		int vl = r.nextInt(69) + 1; //Не может быть быльше 70
		parties.get(0).influence = vl;
		int ost = 100 - vl;
		vl = r.nextInt(ost) + 1;
		parties.get(1).influence = vl;
		parties.get(2).influence = ost - vl;
		
		/*for (int i = 0; i < parties.size(); i++) {
			System.out.println(parties.get(i).name + " " + parties.get(i).influence);
		}
		System.out.println();*/
	}
	
	//Генерирует партию (одну) (для общего использования)
	protected void genParty() {
		parties.add(new Party(GenNamesParties.genName()));
	}
	
}
