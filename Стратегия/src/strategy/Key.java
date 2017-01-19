package strategy;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Key extends KeyAdapter {
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			Menu.g.setLocation(Menu.g.getX() + 70, Menu.g.getY());
		}
		if (key == KeyEvent.VK_LEFT) {
			Menu.g.setLocation(Menu.g.getX() - 70, Menu.g.getY());
		}
		if (key == KeyEvent.VK_DELETE) {
			System.exit(0);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			escape();
		}
		if (key == KeyEvent.VK_SHIFT) {
			CenterPanel.pressedShift = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		if (key == KeyEvent.VK_SHIFT) {
			CenterPanel.pressedShift = false;
		}
	}
	
	//Потеря фокуса нижней центральной панели
	public static void escape() {
		Game.downCenter.locked();
		CenterPanel.focus = false;
		Game.downCenter.removePanel();
		CenterPanel.townId = -1;
		CenterPanel.selection = false;
		CenterPanel.selected.clear();
		
		RigthPanel.name = null;
		Game.downInf.inform.repaint();
	}
	
}
