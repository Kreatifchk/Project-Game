package rpg;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EditorListener implements MouseListener, MouseMotionListener {

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
		if (a.getComponent() == Editor.grass) {
			Editor.block = 2;
		}
		else if (a.getComponent() == Editor.path) {
			Editor.block = 1;
		}
		else if (a.getComponent() == Editor.three) {
			Editor.block = 4;
		}
		else if (a.getComponent() == Editor.water) {
			Editor.block = 5;
		}
		else if (a.getComponent() == Editor.mount) {
			Editor.block = 6;
		}
		else if (a.getComponent() == Editor.home01) {
			Editor.block = 7;
		}
		else if (a.getComponent() == Editor.brickPath) {
			Editor.block = 8;
		}
		else if (a.getComponent() == Editor.brickWallP) {
			Editor.block = 9;
		}
		else if (a.getComponent() == Editor.brickWall) {
			Editor.block = 10;
		}
		else if (a.getComponent() == Editor.brickPathG) {
			Editor.block = 11;
		}
		else if (a.getComponent() == Editor.bridge) {
			Editor.block = 12;
		}
		else if (a.getComponent() == Editor.three2) {
			Editor.block = 13;
		}
		else if (a.getComponent() == Editor.water1) {
			Editor.block = 14;
		}
		else if (a.getComponent() == Editor.home02) {
			Editor.block = 15;
		}
		else if (a.getComponent() == Editor.home03) {
			Editor.block = 16;
		}
		else if (a.getComponent() == Editor.homeFire){
			Editor.block = 17;
		}
		else if (a.getComponent() == Editor.flooring){
			Editor.block = 18;
		}
		else if (a.getComponent() == Editor.homeWall){
			Editor.block = 19;
		}
		else if (a.getComponent() == Editor.homeWallRigth){
			Editor.block = 20;
		}
		else if (a.getComponent() == Editor.flooringRigth){
			Editor.block = 21;
		}
		else if (a.getComponent() == Editor.homeWallLeft){
			Editor.block = 22;
		}
		else if (a.getComponent() == Editor.flooringLeft){
			Editor.block = 23;
		}
		else if (a.getComponent() == Editor.voidT){
			Editor.block = 24;
		}
		else if (a.getComponent() == Editor.window){
			Editor.block = 25;
		}
		else if (a.getComponent() == Editor.grassDark){
			Editor.block = 26;
		}
		else if (a.getComponent() == Editor.threeDark){
			Editor.block = 27;
		}
		else if (a.getComponent() == Editor.threeDark2){
			Editor.block = 28;
		}
		else if (a.getComponent() == Editor.grassFire){
			Editor.block = 29;
		}
		else if (a.getComponent() == Editor.well) {
			Editor.block = 30;
		}
		
		
		else if (a.getComponent() == Editor.fill) {
			Editor.block = 96;
		}
		else if (a.getComponent() == Editor.empty) {
			Editor.block = 97;
		}
		else if (a.getComponent() == Editor.portal) {
			Editor.block = 98;
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
		else {
			Editor.xClick = a.getX();
			Editor.yClick = a.getY();
			Editor.click(Editor.xClick, Editor.yClick);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	//MouseMotionListener
	
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
		
		Editor.map[x][y].setIcon(Editor.lastTool);
		Editor.mapFile[x][y] = Editor.lastToolN;
	}
	
	//Вызывается когда мышь не была нажата и передвинута
	@Override
	public void mouseMoved(MouseEvent a) {
	}

}
