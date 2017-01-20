package strategy;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/** Окно - объединение армий */
@SuppressWarnings("serial")
public class JoinArmy {
	
	int widthL = 300;
	
	public JoinArmy(int idArmy) {
		OneLabel ol = new OneLabel();
		TwoLabel tl = new TwoLabel();
		Game.jlp.add(ol, new Integer(10));
		Game.jlp.add(tl, new Integer(10));
	}
	
	//Первое окно - первая армия
	private class OneLabel extends JLabel {
		public OneLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2;
			setBounds(x, 50, widthL, 400);
		}
	}
	
	//Второе окно - вторая армия
	private class TwoLabel extends JLabel {
		public TwoLabel() {
			setOpaque(true);
			setBackground(new Color(150, 75, 0));
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4));
			int x = (Menu.g.getWidth()/2 - widthL) /2 + 500;
			setBounds(x, 50, widthL, 400);
		}
	}
	
}
