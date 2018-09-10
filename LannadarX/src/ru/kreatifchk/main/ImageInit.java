package ru.kreatifchk.main;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageInit {
	
	public static Image plUp, plDown, plLeft, plRight;
	
	public ImageInit() {
		initPlayer();
	}
	
	private void initPlayer() {
		plUp = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/up.png")).getImage();
		plDown = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/down.png")).getImage();
		plRight = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/right.png")).getImage();
		plLeft = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/left.png")).getImage();
	}
	
}
