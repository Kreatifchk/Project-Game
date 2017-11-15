package base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QwestTalk extends Qwest implements Serializable {
	
	public QwestTalk(int idNPC, int id, String name, String textN,
			String textK, String request, int exp) {
		super(id, name, textN, textK, request, 1, exp);
		this.idNPC = idNPC;
		this.lastId = -1;
	}
	
	public QwestTalk(int idNPC, int id, String name, String textN,
			String textK, String request, int exp, int lastId) {
		super(id, name, textN, textK, request, 1, exp, lastId);
		this.idNPC = idNPC;
	}
	
}
