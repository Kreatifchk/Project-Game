package rpg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QwestTalk extends Qwest implements Serializable {
	
	public QwestTalk(int idNPC, int id, String name, String textN,
			String textK, String request, int status, int exp) {
		super(id, name, textN, textK, request, status, exp);
		this.idNPC = idNPC;
		this.lastId = -1;
	}
	
	public QwestTalk(int idNPC, int id, String name, String textN,
			String textK, String request, int status, int exp, int lastId) {
		super(id, name, textN, textK, request, status, exp, lastId);
		this.idNPC = idNPC;
	}
	
}
