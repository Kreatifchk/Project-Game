package inventory;

import java.util.ArrayList;

public class InventList {
	
	public static ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	
	public InventList() {
		consumable();
		qwestItem();
	}
	
	private void consumable() {
		inventory.add(new Consumable(0, 10, "Малое зелье жизни", InitImage.h1, 0));
		inventory.add(new Consumable(1, 10, "Малое зелье маны", InitImage.m1, 1));
		for (int i = 2; i < 100; i++) {
			inventory.add(null);
		}
	}
	
	private void qwestItem() {
		inventory.add(new QwestItem(100, 20, "Целебные травы", InitImage.qwIt1));
	}
	
}
