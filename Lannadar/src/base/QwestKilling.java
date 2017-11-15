package base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QwestKilling extends Qwest implements Serializable {
	
	//String nameMonster; //Какого монстра необходимо убивать
	//int count; //Сколько необходоимо убить
	//Остальные в классе Qwest
	
	public QwestKilling(String[] nameMonster, int count[], int id, String name, 
			String textN, String textK, String request, int exp) {
		super(id, name, textN, textK, request, 1, exp);
		this.nameMonster = nameMonster;
		this.count = count;
		this.progress = new int[count.length];
		this.lastId = -1;
		this.idNPC = -1;
	}
	
	public QwestKilling(String[] nameMonster, int count[], int id,
			String name, String textN, String textK, String request,
			int exp, int lastId) {
		super(id, name, textN, textK, request, 1, exp, lastId);
		this.nameMonster = nameMonster;
		this.count = count;
		this.progress = new int[count.length];
		this.idNPC = -1;
	}
	
}
