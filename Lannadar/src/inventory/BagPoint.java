package inventory;

/** Класс - ячейка инвертаря */
public class BagPoint {
	
	public int idInv, stack;
	public boolean access;
	
	//108 ячеек в инвертаре
	//Аргументы: idInv - предмет, access - доступ к ячейке, stack - кол-во предметов
	//в стаке
	public BagPoint(int idInv, boolean access, int stack) {
		this.idInv = idInv;
		this.access = access;
		this.stack = stack;
	}
	
}
