package rpg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QwestKilling extends Qwest implements Serializable {
	
	//String nameMonster; //Какого монстра необходимо убивать
	//int number; //Сколько необходоимо убить
	//Остальные в классе Qwest
	
	public QwestKilling(String nameMonster, int number, int id, String name, 
			String textN, String textK, String request, int status, int exp) {
		super(id, name, textN, textK, request, status, exp);
		this.nameMonster = nameMonster;
		this.count = number;
		this.progress = 0;
		this.lastId = -1;
		this.idNPC = -1;
	}
	
	public QwestKilling(String nameMonster, int number, int id,
			String name, String textN, String textK, String request, int status,
			int exp, int lastId) {
		super(id, name, textN, textK, request, status, exp, lastId);
		this.nameMonster = nameMonster;
		this.count = number;
		this.progress = 0;
		this.idNPC = -1;
	}
	
}
