package base;

@SuppressWarnings("serial")
public class QwestCollect extends Qwest {
	
	/**
	 * @param idItem - id предметов которые надо собирать
	 * @param count - количество для каждого предмета
	 * @param id - id квеста
	 * @param exp - кол-во опыта
	 */
	public QwestCollect(int idItem[], int[] count, int id, int exp) {
		super(id, name(id), textN(id), textK(id), request(id), 1, exp);
		this.idItem = idItem;
		this.count = count;
		this.progress = new int[count.length];
		this.lastId = -1;
		this.idNPC = -1;
	}
	
	/**
	 * @param idItem - id предметов которые надо собирать
	 * @param count - количество для каждого предмета
	 * @param id - id квеста
	 * @param exp - кол-во опыта
	 * @param lastId - квест который надо пройти чтоб открыть этот
	 */
	public QwestCollect(int idItem[], int[] count, int id, int exp, int lastId) {
		super(id, name(id), textN(id), textK(id), request(id), 1, exp, lastId);
		this.idItem = idItem;
		this.count = count;
		this.progress = new int[count.length];
		this.idNPC = -1;
	}
	
	//Очищает инвертарь от предметов по квесту после его прохождения
	protected static void clearBag(int idInvent[]) {
		for (int i = 0; i < idInvent.length; i++) {
			for (int j = 0; j < Player.bagPlayer.length; j++) {
				if (Player.bagPlayer[j].idInv == idInvent[i]) {
					Player.bagPlayer[j].idInv = -1;
					Player.bagPlayer[j].stack = 0;
				}
			}
		}
	}

}
