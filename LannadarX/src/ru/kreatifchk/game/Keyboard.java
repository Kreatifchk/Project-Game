package ru.kreatifchk.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	static Player.Direction dir = Player.Direction.stand;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dir = Player.Direction.down;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dir = Player.Direction.up;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dir = Player.Direction.left;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dir = Player.Direction.right;
		}
		if (e.getKeyCode() == KeyEvent.VK_0) {
			System.out.println(Game.pl.xMap + " " + Game.pl.yMap);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dir = Player.Direction.stand;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
