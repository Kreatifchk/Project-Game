package ru.space;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class World extends JLabel {
	
	int x, y;

	boolean solid;
	
	int type;
	
	public World(int x, int y, boolean solid) {
		this.x = x;
		this.y = y;
		this.solid = solid;
	}
	
	public void setXTile(int x) {
		this.x = x;
	}
	
	public void setYTile(int y) {
		this.y = y;
	}
	
	public int getXtile() {
		return x;
	}

	public int getYtile() {
		return y;
	}
	
}
