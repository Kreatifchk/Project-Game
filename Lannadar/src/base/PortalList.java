package base;

public class PortalList {
	
	public PortalList() {
		Game.portal[0] = new Portal(0, 1, 2, 13, 10, 0, 7, false);
		Game.portal[1] = new Portal(1, 2, 3, 16, 5, 0, 5, false);
		Game.portal[2] = new Portal(2, 3, 4, 10, 0, 10, 11, false);
		Game.portal[3] = new Portal(3, 4, 5, 0, 3, 16, 3, false);
		Game.portal[4] = new Portal(4, 5, 6, 5, 7, 7, 0, false);
	}
	
}
