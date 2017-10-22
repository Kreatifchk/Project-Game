package glxnew;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Information extends JLabel {
	
	int x, y;
	Empery emp;
	
	public Information(int x, int y) {
		this.x = x;
		this.y = y;
		emp = Game.emp.get(Game.pole[x][y].owner);
		
		int width = 380, height = 450;
		setBounds((Menu.g.getWidth() - width) / 2, (Menu.g.getHeight() - height) / 2,
				width, height);
		setBackground(new Color(234, 247, 164));
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(new Color(145, 82, 30), 4));
		addMouseListener(Menu.g);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		emp = Game.emp.get(Game.pole[x][y].owner);
		g2d.setFont(Menu.calibri);
		g2d.setColor(Color.black);
		g2d.drawString("Empery: " + emp.id, 130, 20);
		g2d.drawString("Деньги поля: " + Game.pole[x][y].money, 15, 60);
		g2d.drawString("Армия: " + Game.pole[x][y].army, 15, 100);
		g2d.setStroke(new BasicStroke(7));
		g2d.drawLine(10, 120, 370, 120);
		g2d.drawString("Партии", 130, 140);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(15, 175, 320, 175);
		g2d.drawString("Партия", 50, 170);
		g2d.drawString("Влияние (%)", 200, 170);
		int y = 195;
		for (int i = 0; i < emp.parties.size(); i++) {
			if (emp.parties.get(i).nameD == 2) {
				//Если слов в названии больше двух то -> делать перенос
				int ct = 0; //Количество символов до пробела
				while (true) {
					if (emp.parties.get(i).name.charAt(ct) == ' ') {
						break;
					} else {
						ct++;
					}
				}
				String one = emp.parties.get(i).name.substring(0, ct);
				String two = emp.parties.get(i).name.substring(ct+1);
				g2d.drawString(one, 15, y);
				g2d.drawString(two, 15, y + 15);
				g2d.drawString(emp.parties.get(i).influence + "", 200, y + 7);
				y += 45;
			} else {
				g2d.drawString(emp.parties.get(i).name, 15, y);
				g2d.drawString(emp.parties.get(i).influence + "", 200, y);
				y += 30;
			}
		}
	}
	
}
