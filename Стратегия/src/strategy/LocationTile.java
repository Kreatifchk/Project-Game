package strategy;

public class LocationTile {
	
	private int x, y;
	
	public LocationTile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getY() {
		return y;
	}

	public LocationTile setY(int y) {
		this.y = y;
		return this;
	}

	public int getX() {
		return x;
	}

	public LocationTile setX(int x) {
		this.x = x;
		return this;
	}
	
}
