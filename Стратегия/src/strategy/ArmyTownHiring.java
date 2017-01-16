package strategy;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ArmyTownHiring extends JLabel {
	
	String name;
	int townId;
	
	public ArmyTownHiring() {
		setBounds(100, 50, 400, 500);
		setOpaque(true);
		setBackground(new Color(150, 75, 0));
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
	}

}
