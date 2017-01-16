package strategy;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BcTile extends JLabel {
	
	private int x, y;
	
	public BcTile(int x, int y) {
		this.x = x;
		this.y = y;
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
	
}
