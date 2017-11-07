package inventory;

import java.awt.Image;

public class Consumable extends Inventory {
	
	int subType;
	
	/** Предмет - расходник
	 * @param id - ид предмета (общий для всех предметов)
	 * @param stack - максимальное кло-во вещей в стеке
	 * @param name - название предмета
	 * @param icon - изображение
	 * @param subType - сабтип (не помню зачем)
	 */
	public Consumable(int id, int stack, String name, Image icon, int subType) {
		super(id, 1, stack, name, icon);
		this.subType = subType;
	}
	
	@Override
	public void use() {
		
	}
	
}
