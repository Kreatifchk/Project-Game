package ru.kreatifchk.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ru.kreatifchk.editor.Map;

public class Keyboard implements KeyListener {
	
	static Direction dir = Direction.stand;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dir = Direction.down;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dir = Direction.up;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dir = Direction.left;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dir = Direction.right;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Map.monsters.stream().forEach((u) -> {u.moveTo(Game.pl.xMap, Game.pl.yMap-1); });
		}
		if (e.getKeyCode() == KeyEvent.VK_0) {
			System.out.println(Game.pl.xMap + " " + Game.pl.yMap);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dir = Direction.stand;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
