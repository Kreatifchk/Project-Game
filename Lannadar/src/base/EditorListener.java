package base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;

import menu.Menu;

public class EditorListener implements MouseListener, MouseMotionListener,
MouseWheelListener {
	
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
				oldX = -1;
				oldY = -1;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	//MouseMovedListener
	
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
		oldX = -1;
		oldY = -1;
	}
	
	int oldX = -1, oldY = -1;
	ImageIcon oldIcon;
	//Вызывается когда мышь не была нажата и передвинута
	@Override
	public void mouseMoved(MouseEvent a) {
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
		
		//Показывает на клетке с курсором выбранный тайл в данный момент
		if (Editor.block < TileList.tiles.length) {
			if (oldX != -1 & oldY != -1) {
				if (x != oldX || y != oldY) {
					Editor.map[oldX][oldY].setIcon(oldIcon);
					oldIcon = (ImageIcon) Editor.map[x][y].getIcon();
					Editor.map[x][y].setIcon(TileList.tiles[Editor.block].ic);
				}
			} else {
				oldIcon = (ImageIcon) Editor.map[x][y].getIcon();
				Editor.map[x][y].setIcon(TileList.tiles[Editor.block].ic);
			}
			oldX = x;
			oldY = y;
		}
	}
	
	//MouseWheelListener
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent a) {
		int rot = a.getWheelRotation();
		if (openedList == true) {
			if (rot < 0) {
				Editor.scroll.setValue(Editor.scroll.getValue() - a.getScrollAmount() * 6);
			} else {
				Editor.scroll.setValue(Editor.scroll.getValue() + a.getScrollAmount() * 6);
			}
		}
	}

}
