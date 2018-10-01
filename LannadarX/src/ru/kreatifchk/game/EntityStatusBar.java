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
public class EntityStatusBar extends JLabel {
	
	private Font dt;
	
	private int alpha = 255;
	private int stroke;
	
	private int hpMpPix;
	
	private String name;
	private int hp = 0, mp = 0, level = 1;
	private int hpMax = 100, mpMax = 100;
	
	public EntityStatusBar() {
		stroke = (int)(2*Main.INC);
		
		setSize(Tile.SIZE * 3, Tile.SIZE);
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, alpha), stroke));
		
		//Уменьшаем шрифт для уровней если цифр несколько
		if (level < 10) {
			setFontSize(32 * Main.INC);
		}
		else if (level < 100) {
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
	
	protected void setParametrs(String name, int hp, int hpMax, int mp, int mpMax, int level) {
		this.name = name;
		this.hp = hp;
		this.mp = mp;
		this.hpMax = hpMax;
		this.mpMax = mpMax;
		this.level = level;
	}
	
	//Изменение hp во время боя
	protected void changeHp(int hp) {
		this.hp = hp;
	}
	
	//Изменение mp во время боя
	protected void changeMp(int mp) {
		this.mp = mp;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(218, 218, 218, alpha));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		//Окно разделяется на сегменты
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawLine((int)(Tile.SIZE / 1.5), Tile.SIZE / 3, (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 3);
		g2d.drawLine(0, Tile.SIZE / 3, getWidth(), Tile.SIZE / 3);
		g2d.drawLine((int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 2, getWidth(), Tile.SIZE / 3 * 2);
		
		//Вывод имени моба
		g2d.setFont(Fonts.digitalThin.deriveFont(22F));
		int widthText = (int) Fonts.digitalThin.deriveFont(22F).getStringBounds(name,new FontRenderContext(null, true, true)).getWidth();
		g2d.setColor(new Color(36, 186, 29, alpha));
		g2d.drawString(name, (getWidth() - widthText) / 2, Tile.SIZE / 3 - 2);
		
		g2d.setFont(dt);
		//Выводится уровень персонажа
		g2d.setColor(new Color(36, 186, 29, alpha));
		int heightText = (int) dt.getStringBounds(level + "",new FontRenderContext(null, true, true)).getY();
		widthText = (int) dt.getStringBounds(level + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(level + "", ((int)(Tile.SIZE / 1.5) - widthText) / 2, ((Tile.SIZE / 3 * 4)-(heightText)) / 2);
		
		//Вывод здоровья (графически)
		int hpPoint = (int)(hp / ((float)hpMax / 100)); //Получаем в процентах здоровье персонажа
		hpPoint = (int)((float)hpMpPix / 100 * hpPoint); //Подсчитываем сколько это в пикселях
		g2d.setColor(new Color(255, 0, 0, alpha));
		g2d.fillRect((int)(Tile.SIZE / 1.5) + stroke-1, Tile.SIZE / 3 + stroke - 1, hpPoint, Tile.SIZE / 3 - stroke);
		
		//Вывод маны персонажа (графически)
		int mpPoint = (int)(mp / ((float)mpMax / 100)); //Получаем в процентах ману персонажа
		mpPoint = (int)((float)hpMpPix / 100 * mpPoint); //Подсчитываем сколько это в пикселях
		g2d.setColor(new Color(64, 0, 255, alpha));
		g2d.fillRect((int)(Tile.SIZE / 1.5) + stroke-1, Tile.SIZE / 3 * 2 + stroke - 1, mpPoint, Tile.SIZE / 3 - stroke);
		
		g2d.setFont(dt.deriveFont(16*Main.INC));
		g2d.setColor(new Color(0, 0, 0, alpha));
		
		//Вывод здоровья (текстом)
		widthText = (int) dt.getStringBounds(hp + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(hp + "", (hpMpPix - widthText) / 2 + (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 2 - 2);
		
		//Вывод маны (текстом)
		widthText = (int) dt.getStringBounds(mp + "",new FontRenderContext(null, true, true)).getWidth();
		g2d.drawString(mp + "", (hpMpPix - widthText) / 2 + (int)(Tile.SIZE / 1.5), Tile.SIZE / 3 * 3 - 3);
	}
	
}
