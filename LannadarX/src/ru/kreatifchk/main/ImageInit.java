package ru.kreatifchk.main;

import java.awt.Image;

import javax.swing.ImageIcon;

import ru.kreatifchk.tools.Img;

public class ImageInit {
	
	public static Image buttEd, buttEdEntered, buttEdActivated, createT, openT, saveT, closeT;
	public static Image tileForestI, tileCityI, fillI, deleteI, transitI, monsterI, drawI,
	tilesButtonBord, tilesButtonEnt, tilesButtonAct;
	
	public static Image plUp, plDown, plLeft, plRight;
	
	public ImageInit() {
		initEditorImage();
		initPlayer();
	}
	
	private void initEditorImage() {
		buttEd = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/buttEd.png"));
		buttEdEntered = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/buttEdEntered.png"));
		buttEdActivated = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/buttEdActivated.png"));
		
		createT = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/createT.png"));
		openT = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/openT.png"));
		saveT = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/saveT.png"));
		closeT = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/closeT.png"));
		
		tileForestI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesForest.png"));
		tileCityI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesCity.png"));
		fillI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/fill.png"));
		deleteI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/delete.png"));
		transitI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/transit.png"));
		monsterI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/monster.png"));
		drawI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/shadow.png"));
		
		tilesButtonBord = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonBord.png"));
		tilesButtonEnt = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonEnt.png"));
		tilesButtonAct = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonAct.png"));
	}
	
	private void initPlayer() {
		plUp = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/up.png")).getImage();
		plDown = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/down.png")).getImage();
		plRight = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/right.png")).getImage();
		plLeft = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/player/left.png")).getImage();
	}
	
}
