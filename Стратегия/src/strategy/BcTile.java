package strategy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BcTile extends JLabel {
	
	private int x, y;
	
	public BcTile(int x, int y) {
		this.x = x;
		this.y = y;
		addMouseListener(new Mouse());
	}

	public int getXTile() {
		return x;
	}
	public void setXTile(int x) {
		this.x = x;
	}

	public int getYTile() {
		return y;
	}
	public void setYTile(int y) {
		this.y = y;
	}
	
	public class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				int x = Game.town.get(CenterPanel.townId).x;
				int y = Game.town.get(CenterPanel.townId).y;
				if (BcTile.this.x == x & BcTile.this.y == y) {
					if (CenterPanel.selection == true) {
						CenterPanel.outputArmy();
						BcTile.this.repaint();
					}
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
	}
	
}
