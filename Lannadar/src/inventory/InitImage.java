package inventory;

import java.awt.Image;

import javax.swing.ImageIcon;

public class InitImage {
	
	static Image h1;
	
	public InitImage() {
		h1 = new ImageIcon(getClass().getResource("/base/res/Image/Inventory/Consumable/h1.png")).getImage();
	}
	
}
