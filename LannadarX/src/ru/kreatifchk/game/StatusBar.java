package ru.kreatifchk.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import ru.kreatifchk.main.Fonts;
import ru.kreatifchk.main.Main;

@SuppressWarnings("serial")
public class StatusBar extends JLabel {
	
	private Font dt;
	
	private int alpha = 255;
	private int stroke;
	
	private int hpMpPix;
	
	public StatusBar() {
		stroke = (int)(2*Main.INC);
		
		setSize(Tile.SIZE * 3, Tile.SIZE);
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, alpha), stroke));
		
		//Уменьшаем шрифт для уровней если цифр несколько
		if (Game.pl.level < 10) {
			setFontSize(32 * Main.INC);
		}
		else if (Game.pl.level < 100) {
			setFontSize(22 * Main.INC);
		}
		else {
			setFontSize(14 * Main.INC);
		}
		
		hpMpPix = (getWidth() - stroke + 1) - (int)(Tile.SIZE / 1.5);
	}
	
	private void setFontSize(float size) {
		dt = Fonts.digitalThin.deriveFont(size);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(218, 218, 218, alpha));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setFont(dt);
		
		//Окно разделяется на сегменты
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawLine((int)(Tile.SIZE / 1.5), 0, (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 2);
		g2d.drawLine((int)(Tile.SIZE / 1.5), Tile.SIZE / 3, getWidth(), Tile.SIZE / 3);
		g2d.drawLine(0, Tile.SIZE / 3 * 2, getWidth(), Tile.SIZE / 3 * 2);
		
		//Выводится уровень персонажа
		g2d.setColor(new Color(36, 186, 29, alpha));
		int heightText = (int) dt.getStringBounds(Game.pl.level + "",new FontRenderContext(null, true, true)).getY();
		int widthText = (int) dt.getStringBounds(Game.pl.level + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(Game.pl.level + "", ((int)(Tile.SIZE / 1.5) - widthText) / 2, ((Tile.SIZE / 3 * 2)-(heightText)) / 2);
		
		//Вывод здоровья персонажа (графически)
		int hpPoint = Game.pl.hp / (Game.pl.hpMax / 100); //Получаем в процентах здоровье персонажа
		hpPoint = (int)((float)hpMpPix / 100 * hpPoint); //Подсчитываем сколько это в пикселях
		g2d.setColor(new Color(255, 0, 0, alpha));
		g2d.fillRect((int)(Tile.SIZE / 1.5) + stroke-1, stroke, hpPoint, Tile.SIZE / 3 - stroke - 1);
		
		//Вывод маны персонажа (графически)
		int mpPoint = Game.pl.mp / (Game.pl.mpMax / 100); //Получаем в процентах ману персонажа
		mpPoint = (int)((float)hpMpPix / 100 * mpPoint); //Подсчитываем сколько это в пикселях
		g2d.setColor(new Color(64, 0, 255, alpha));
		g2d.fillRect((int)(Tile.SIZE / 1.5) + stroke-1, Tile.SIZE / 3 + stroke - 1, mpPoint, Tile.SIZE / 3 - stroke);
		
		//Вывод опыта персонажа (графически)
		g2d.setColor(new Color(129, 207, 233, alpha));
		g2d.fillRect(0, Tile.SIZE / 3 * 2 + stroke - 1, getWidth() - stroke+1, Tile.SIZE / 3);
		
		g2d.setFont(dt.deriveFont(16*Main.INC));
		g2d.setColor(new Color(0, 0, 0, alpha));
		//Вывод здоровья (текстом)
		widthText = (int) dt.getStringBounds(Game.pl.hp + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(Game.pl.hp + "", (hpMpPix - widthText) / 2 + (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 - 2);
		
		//Вывод маны (текстом)
		widthText = (int) dt.getStringBounds(Game.pl.mp + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(Game.pl.mp + "", (hpMpPix - widthText) / 2 + (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 2 - 2);
	}
	
}
