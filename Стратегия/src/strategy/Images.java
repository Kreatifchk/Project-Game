package strategy;

import javax.swing.ImageIcon;

public class Images {
	
	static ImageIcon grass, town;
	public static ImageIcon footman, archer;
	
	public Images() {
		grass = new ImageIcon(getClass().getResource("res/grass.jpg"));
		town = new ImageIcon(getClass().getResource("res/town.png"));
		
		footman = new ImageIcon(getClass().getResource("res/footman2.png"));
		archer = new ImageIcon(getClass().getResource("res/archer.png"));
	}
	
}
