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
		//Выбор тайла в меню выбора тайлов
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
				Menu.ed.basicPane.remove(Editor.mainB);
				Menu.ed.basicPane.remove(Editor.builds);
				Editor.tilesList = null;
				Editor.inList = null;
			}
		}
		
		if (v != true) {
			if (a.getComponent() == Editor.fill) {
				Editor.block = 996;
			}
			else if (a.getComponent() == Editor.empty) {
				Editor.block = 997;
			}
			else if (a.getComponent() == Editor.save) {
				Editor.saveLevel();
			}
			else if (a.getComponent() == Editor.open) {
				Editor.eo.fillMap();
			}
			else if (a.getComponent() == Editor.define) {
				Editor.block = 999;
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
					Menu.ed.basicPane.remove(Editor.mainB);
					Menu.ed.basicPane.remove(Editor.builds);
					Editor.tilesList = null;
					Editor.inList = null;
				}
			}
			//Установка тайла на карту (одиночная) или закрытия меню тайлов
			else {
				if (openedList == true) {
					openedList = false;
					Menu.ed.basicPane.remove(Editor.tilesList);
					Menu.ed.basicPane.remove(Editor.mainB);
					Menu.ed.basicPane.remove(Editor.builds);
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
	
	//MouseMovedListener
	
	//Вызывается когда мышь была нажата и передвинута
	//Множественная установка тайлов
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
			Editor.map[x][y].oldIcon = Editor.lastTool;
			Editor.mapFile[x][y] = Editor.lastToolN;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
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
			//Сначала возвращает всем клеткам их иконку
			for (int i = 0; i < Editor.map.length; i++) {
				for (int j = 0; j < Editor.map[0].length; j++) {
					Editor.map[i][j].returnIcon();
				}
			}
			//Затем той на которой стоит мышь иконку активного тайла
			if (TileList.tiles[Editor.block].point != null) {
				//Показ мультитайлового объекта
				try {
				int idKit = TileList.tiles[Editor.block].idKit;
				//Номер объекта
				int tl = Editor.block;
				while (TileList.tiles[tl].idKit == idKit) {
					//Пока тайлы в массиве относятся к данному объекту
					int xTile = TileList.tiles[tl].point.x, yTile = TileList.tiles[tl].point.y;
					Editor.map[x+xTile][y+yTile].setIcon(TileList.tiles[tl].ic);
					tl++;
				}
				} catch (Exception e) {}
			} else {
				//Если объект на один тайл
				Editor.map[x][y].setIcon(TileList.tiles[Editor.block].ic);
			}
		}
	}
	
	//MouseWheelListener
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent a) {
		int rot = a.getWheelRotation();
		if (openedList == true) {
			if (rot < 0) {
				Editor.scroll.setValue(Editor.scroll.getValue() - a.getScrollAmount() * 7);
			} else {
				Editor.scroll.setValue(Editor.scroll.getValue() + a.getScrollAmount() * 7);
			}
		}
	}

}
