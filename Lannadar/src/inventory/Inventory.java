package inventory;

import java.awt.Image;

/** Базовый класс, для различных типов вещей */
public abstract class Inventory {
	
	/* Классы вещей:
	 * 0) Экипировка
	 * 1) Расходники (зелья, свитки)
	 * 2) Улучшающие экипировку
	 * 3) Бесполезные вещи
	 * 4) Вещи для заданий
	 * 5) Многоразовые вещи (расходники и подобные)
	 */
	
	public int id, type, stack;
	public String name;
	public Image icon;
	
	public Inventory(int id, int type, int stack, String name, Image icon) {
		this.id = id;
		this.type = type;
		this.stack = stack;
		this.name = name;
		this.icon = icon;
	}
	
	public void use() {
	}
}
