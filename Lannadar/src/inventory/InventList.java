package inventory;

import java.util.ArrayList;

public class InventList {
	
	public static ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	
	public InventList() {
		consumable();
	}
	
	private void consumable() {
		inventory.add(new Consumable(0, 10, "Малое зелье жизни", InitImage.h1, 0));
		inventory.add(new Consumable(1, 10, "Малое зелье маны", InitImage.m1, 0));
	}
	
}
