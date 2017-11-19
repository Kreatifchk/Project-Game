package inventory;

import java.awt.Image;

import javax.swing.ImageIcon;

import base.Resize;

public class InitImage {
	
	public static Image money;
	
	static Image h1, m1;
	
	static Image qwIt1;
	
	public InitImage() {
		money = new ImageIcon(getClass().getResource("/base/res/Image/Inventory/money.png")).getImage();
		
		h1 = new ImageIcon(getClass().getResource("/base/res/Image/Inventory/Consumable/h1.png")).getImage();
		m1 = new ImageIcon(getClass().getResource("/base/res/Image/Inventory/Consumable/m1.png")).getImage();
		
		qwIt1 = Resize.resizeA(new ImageIcon(getClass().getResource("/base/res/Image/Item/herbage.png")).getImage(), 40, 38);
	}
	
}
