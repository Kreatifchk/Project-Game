package ru.kreatifchk.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Img;

/** Кнопки для наборов тайлов и инструментов */
@SuppressWarnings("serial")
public class TileButton extends JButton {
	
	Image tileForestI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesForest.png"));
	Image tileCityI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesCity.png"));
	Image fillI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/fill.png"));
	Image deleteI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/delete.png"));
	
	Image tilesButtonBord = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonBord.png"));
	Image tilesButtonEnt = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonEnt.png"));
	Image tilesButtonAct = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/tilesButtonAct.png"));
	
	boolean selected, pressed;
	int number; //Номер набора тайлов
	Ellipse2D circle;
	
	public TileButton(int number) {
		setSize((int)(48*Main.INC), (int)(48*Main.INC));
		setContentAreaFilled(false);
		setBorderPainted(false);
		this.number = number;
		
		circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
	}
	@Override
	public boolean contains(int x, int y) {
		Shape shape;
		shape = circle;
		return shape.contains(x, y);
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (number == 0) {
			g2d.drawImage(tileForestI, 0, 0, null);
		}
		else if (number == 1) {
			g2d.drawImage(tileCityI, 0, 0, null);
		}
		else if (number == 998) {
			g2d.drawImage(deleteI, 0, 0, null);
		}else if (number == 999) {
			g2d.drawImage(fillI, 0, 0, null);
		}
		
		
		if (pressed == true) {
			g2d.drawImage(tilesButtonAct, 0, 0, null);
		}
		else if (selected == true) {
			g2d.drawImage(tilesButtonEnt, 0, 0, null);
		} else {
			g2d.drawImage(tilesButtonBord, 0, 0, null);
		}
	}
	
}
