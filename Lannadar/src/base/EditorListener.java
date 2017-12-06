package base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;

import menu.Menu;

public class EditorListener implements MouseListener, MouseMotionListener {
	
	boolean openedList;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	@Override
	public void mousePressed(MouseEvent a) {
		boolean v = false;
		for (int i = 0; i < Editor.allTiles.length; i++) {
			if (a.getComponent() == Editor.allTiles[i]) {
				Editor.block = i;
				v = true;
				//Устанавливает инструмент для заливки
				Editor.lastTool = (ImageIcon) Editor.allTiles[i].getIcon();
				Editor.lastToolN = i;
				//Закрывает окно
				openedList = false;
				Menu.ed.basicPane.remove(Editor.tilesList);
				Editor.tilesList = null;
				Editor.inList = null;
			}
		}
		
		if (v != true) {
			if (a.getComponent() == Editor.fill) {
				Editor.block = 96;
			}
			else if (a.getComponent() == Editor.empty) {
				Editor.block = 97;
			}
			else if (a.getComponent() == Editor.save) {
				Editor.saveLevel();
			}
			else if (a.getComponent() == Editor.open) {
				Editor.eo.fillMap();
			}
			else if (a.getComponent() == Editor.define) {
				Editor.block = 99;
			}
			else if (a.getComponent() == Editor.tilesB) {
				//Если лист тайлов не открыт - открыть, иначе закрыть
				if (openedList != true) {
					Editor.tilesList();
					new EditorTile();
					openedList = true;
				} else {
					openedList = false;
					Menu.ed.basicPane.remove(Editor.tilesList);
					Editor.tilesList = null;
					Editor.inList = null;
				}
			}
			else {
				if (openedList == true) {
					openedList = false;
					Menu.ed.basicPane.remove(Editor.tilesList);
					Editor.tilesList = null;
					Editor.inList = null;
				}
				Editor.xClick = a.getX();
				Editor.yClick = a.getY();
				Editor.click(Editor.xClick, Editor.yClick);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	//Вызывается когда мышь была нажата и передвинута
	@Override
	public void mouseDragged(MouseEvent a) {
		int x = 0, y = 0;
		if (a.getX() <= 48) {
			x = 0;
		} else {
			x = a.getX() / 48;
		}
		
		if (a.getY() <= 48) {
			y = 0;
		} else {
			y = a.getY() / 48;
		}
		
		try {
			Editor.map[x][y].setIcon(Editor.lastTool);
			Editor.mapFile[x][y] = Editor.lastToolN;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	//Вызывается когда мышь не была нажата и передвинута
	@Override
	public void mouseMoved(MouseEvent a) {
	}

}
