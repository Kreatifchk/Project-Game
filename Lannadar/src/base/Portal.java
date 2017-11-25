package base;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import menu.Menu;

@SuppressWarnings("serial")
public class Portal extends JLabel {
	
	int id, location, newLoc;
	
	boolean access;
	
	int startX, startY, mX, mY;
	
	static ImageIcon end = new ImageIcon(Game.class.getResource("res/end.png"));
	static ImageIcon portalI = new ImageIcon(Game.class.getResource("res/Image/Tiles/portal.png"));
	
	/**
	 * @param id
	 * @param location - локация портала
	 * @param newLoc - локация куда ведет портал
	 * @param mX - номер тайла по x
	 * @param mY - номер тайла по y
	 * @param startX - номер тайла по x где появится игрок
	 * @param startY - номер тайла по y где появится игрок
	 * @param access - доступен ли портал изначально
	 */
	public Portal(int id, int location, int newLoc, int mX, int mY,
			int startX, int startY, boolean access) {
		this.id = id;
		this.location = location;
		this.newLoc = newLoc;
		this.mX = mX;
		this.mY = mY;
		this.startX = startX;
		this.startY = startY;
		this.access = access;
	}
	
	//Закрывает портал
	protected static void closed(int id) {
		Game.portal[id].access = false;
		Game.mapx[Game.portal[id].mX][Game.portal[id].mY].remove(Game.portal[id]);
	}
	
	//Открывает портал
	protected static void open(int id) {
		Game.portal[id].access = true;
		Game.mapx[Game.portal[id].mX][Game.portal[id].mY].add(Game.portal[id]);
	}
	
	protected static void addPortal(int location) {
		for (int i = 0; i < Game.portal.length; i++) {
			Game.portal[i].setIcon(portalI);
			Game.portal[i].setBounds(0, 0, 48, 48);
			if (Game.portal[i].access == true & Game.portal[i].location == location) {
				Game.mapx[Game.portal[i].mX][Game.portal[i].mY].add(Game.portal[i]);
			}
		}
	}
	
	//Проверяет на вхождение в портал игрока
	protected static void verifyPortal() {
		for (int i = 0; i < Game.portal.length; i++) {
			Portal port = Game.portal[i];
			int portX = Game.portal[i].mX, portY = Game.portal[i].mY;
			if (Game.pl.mX == portX & Game.pl.mY == portY & Game.portal[i].access == true) {
				if (Game.currentLocation != 5) {
					Menu.g.crossing(port.newLoc, port.startX, port.startY);
				} else {
					Menu.g.deleteTile();
					Menu.g.deleteMonster();
					Menu.g.deleteNPC();
					Menu.g.remove(Game.p);
					JLabel endInf = new JLabel();
					endInf.setBounds(-5, 48, end.getIconWidth(), end.getIconHeight());
					endInf.setIcon(end);
					Game.mainPane.add(endInf, new Integer(400));
				}
			}
		}
	}
	
}
