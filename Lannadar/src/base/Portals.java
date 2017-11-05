package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JLabel;

public class Portals {
	
	int id;
	
	int nextLevel;
	
	boolean dostyp;
	
	int startX, startY, portalX, portalY;
	
	static File portalFile;
	static Scanner sP;
	
	static JLabel port;
	
	public static void portals() {
		//portalFile = Game.class.getResourceAsStream("res/portals/" + currentLocation + ".txt");
		portalFile = new File("res/portals/" + Game.currentLocation + ".txt");
		try {
			sP = new Scanner(portalFile);
			int count = 0;
			while(sP.hasNext()) {
				Game.portal[count] = new Portals();
				Game.portal[count].id = sP.nextInt();
				Game.portal[count].nextLevel = sP.nextInt();
				Game.portal[count].startX = sP.nextInt();
				Game.portal[count].startY = sP.nextInt();
				Game.portal[count].portalX = sP.nextInt();
				Game.portal[count].portalY = sP.nextInt();
				Game.portal[count].dostyp = true;
				count++;
			}
			sP.close();
			closed();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sP.close();
		}
	}
	
	protected static void closed() {
		try {
			Game.portal[0].dostyp = false;
		} catch (NullPointerException e) {
		}
	}
	
	protected static void removePortal() {
		try {
			for (int i = 0; i <= Game.portal.length - 1; i++) {
				Game.portal[i] = null;
			}
		} catch (Exception e) {
			
		}
	}
	
	protected static void addPortal() {
		try {
			for (int i = 0; i < Game.portal.length; i++) {
				if (Game.portal[i] != null && Game.portal[i].dostyp == true) {
					port = new JLabel();
					port.setIcon(Game.portalI);
					port.setBounds(0, 0, 48, 48);
					System.out.println("ff");
					Game.mapx[Game.portal[i].portalX][Game.portal[i].portalY].add(port);
				}
			}
		} catch (Exception e) {
		}
	}
	
}
