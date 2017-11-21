package base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QwestTalk extends Qwest implements Serializable {
	
	public QwestTalk(int idNPC, int id, int exp) {
		super(id, name(id), textN(id), textK(id), request(id), 1, exp);
		this.idNPC = idNPC;
		this.lastId = -1;
	}
	
	public QwestTalk(int idNPC, int id, int exp, int lastId) {
		super(id, name(id), textN(id), textK(id), request(id), 1, exp, lastId);
		this.idNPC = idNPC;
	}
	
}
