package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import base.Player;
import inventory.InventList;
import inventory.Inventory;

@SuppressWarnings("serial")
public class HeroPanelBag extends JLabel {
	
	Inventory[] invent = new Inventory[10];
	
	public HeroPanelBag() {
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		//Вертикальные линии рамки
		g2d.drawLine(50, 0, 50, 446);
		g2d.drawLine(579, 0, 579, 446);
		//Горизонтальные линии рамки
		g2d.drawLine(0, 31, 634, 31);//18
		g2d.drawLine(0, 410, 634, 410);//427
		g2d.setColor(Color.ORANGE);
		g2d.setStroke(new BasicStroke(4));
		//Вертикальные линии
		int x = 95, y = 36;
		for (int i = 0; i < 11; i++) {
			g2d.drawLine(x, y, x, y+370);
			x += 44;
		}
		//Горизонтальные линии
		x = 55;
		y = 74;
		for (int i = 0; i < 8; i++) {
			g2d.drawLine(x, y, x+44*12-8, y);
			y += 42;
		}
		
		//Заполняет серым, недоступные ячейки инвертаря
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(3));
		x = 53;
		y = 34;
		for (int i = 0; i < 108; i++) {
			if (Player.bagPlayer[i].access == false) {
				g2d.fillRect(x, y, 40, 38);
			} else if (Player.bagPlayer[i].idInv != -1){
				int id = Player.bagPlayer[i].idInv; //Ид предмета из инвертаря
				g2d.drawImage(InventList.inventory.get(id).icon, x, y, null);
			}
			x+=44;
			if (i > 0 & (i+1) % 12 == 0) {
				y += 42;
				x = 53;
			}
		}
	}
	
}
