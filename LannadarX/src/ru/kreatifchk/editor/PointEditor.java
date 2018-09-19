package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ru.kreatifchk.game.Monster;
import ru.kreatifchk.game.Player;
import ru.kreatifchk.game.Player.Direction;
import ru.kreatifchk.game.Tile;
import ru.kreatifchk.game.TilesList;

//Ячейка, содержащие необходимые свойства
public class PointEditor extends JLabel implements Serializable {
	
	//transient запрещает сериализовать перменную/объект
	private static final long serialVersionUID = 31082007L;
	public int number = -1; //Номер тайла на указанной клетке
	transient ImageIcon oldIcon;
	transient boolean izm; //Изменяли ли иконку (для предпросмотра тайлов
	boolean anim; //Анимированный ли тайл
	public String transition = ""; //На какую локацию можно перейти из этого тайла
	public Player.Direction dirTrans = null; //В какую сторону необходимо двигаться для перехода
	public int xTrans = -1, yTrans = -1; //Местоположение перемещения
	
	private int i, j;
	
	public PointEditor(int j, int i) {
		//Подпись номеров боковых клеток
		if (i == 0) {
			setText(j + "");
		} else if (j == 0) {
			setText(i + "");
		}
		
		this.i = i;
		this.j = j;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (getIcon() != null) {
			if (oldIcon == null) {
				g2d.drawImage(((ImageIcon) getIcon()).getImage(), 0, 0, null);
			} else {
				g2d.drawImage(TilesList.tiles[Editor.actTile].icon.getImage(), 0, 0, null);
			}
		} else {
			g2d.setColor(new Color(255, 255, 255));
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
		
		//Данный код может тормозить UI поток, исправить
		try {
			Monster mr = Map.monsters.stream().filter((e) -> e.startX == j & e.startY == i).findFirst().get();
			g2d.drawImage(mr.view, 0, 0, null);
		} catch (Exception e) {}
		
		if (dirTrans == Direction.stand) {
			g2d.setColor(Color.YELLOW);
			g2d.fillOval((getWidth() - Tile.SIZE / 4) / 2, (getHeight() - Tile.SIZE / 4) / 2, Tile.SIZE / 4, Tile.SIZE / 4);
		}
	}
	
}
