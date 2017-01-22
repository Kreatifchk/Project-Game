package strategy;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import others.ArrowButton;

/** Окно - объединение армий */
@SuppressWarnings("serial")
public class JoinArmy {
	
	int widthL = 300;
	
	OneLabel ol;
	TwoLabel tl;
	
	ArrowButton ab = new ArrowButton();
	
	public JoinArmy(int idArmy) {
		ol = new OneLabel();
		tl = new TwoLabel();
		
		int x = (tl.getX() - (ol.getX() + ol.getWidth()) - 160)
				/ 2 + ol.getX() + ol.getWidth();
		System.out.println(x);
		ab.setBounds(x, 100, 160, 160);
		
		Game.jlp.add(ol, new Integer(10));
		Game.jlp.add(tl, new Integer(10));
		Game.jlp.add(ab, new Integer(10));
		//Game.jlp.add(new Arrow(2), new Integer(10));
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
