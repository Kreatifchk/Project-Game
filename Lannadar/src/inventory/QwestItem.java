package inventory;

import java.awt.Image;

public class QwestItem extends Inventory {
	
	/**
	 * 
	 * @param id - ид предмета (общий для всех предметов)
	 * @param stack - максимальное кло-во вещей в стеке
	 * @param name - название предмета
	 * @param icon - изображение
	 */
	public QwestItem(int id, int stack, String name, Image icon) {
		super(id, 4, stack, name, icon);
	}
	
}
