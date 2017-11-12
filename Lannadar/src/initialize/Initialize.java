package initialize;

import inventory.InventList;

public class Initialize {
	
	public Initialize() {
		new InitImage();
		new InitFont();
		new InitFunc();

		initFunc();
	}
	
	private void initFunc() {
		new InventList();
	}
	
}
