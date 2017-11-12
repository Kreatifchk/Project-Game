package heroPanel;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/** Всплывающие подсказки */
@SuppressWarnings("serial")
public class Tip extends JLabel {
	
	public Tip(String text) {
		super(text);
		setOpaque(true);
		setBackground(new Color(234, 202, 77));
		setBorder(BorderFactory.createLineBorder(new Color(77, 54, 21), 2));
	}

}
