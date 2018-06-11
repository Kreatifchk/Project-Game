package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class PointInfo extends GUICenter implements ActionListener {
	
	int xMap, yMap;
	
	FontRenderContext frc = new FontRenderContext(null, true, true);
	
	JButton attack, recruit; //Конпка атака и найм войск
	
	public PointInfo(int x, int y) {
		xMap = x;
		yMap = y;
		
		attack = new JButton("Атака");
		attack.setBounds(GUIManager.getCenter(110, 2, getWidth()), 400, 110, 50);
		attack.addActionListener(this);
		add(attack);
		
		recruit = new JButton("Найм");
		recruit.setBounds(GUIManager.getPosition(110, 2, 2, getWidth()), 400, 110, 50);
		recruit.addActionListener(this);
		add(recruit);
		
		//Если клетка не твоя, то убрать кнопку атаки и прочие
		if (GameFrame.pm[xMap][yMap].owner != GameFrame.idPlayer) {
			attack.setVisible(false);
			recruit.setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == attack) {
			GameFrame.mainPane.remove(this);
			GameFrame.info = false;
			GameFrame.attack = true;
			//GameFrame.selectPoint = new Point(xMap, yMap);
			
			Color cl = GameFrame.pm[xMap+1][yMap].getBackground();
			GameFrame.pm[xMap+1][yMap].setBackground(new Color(cl.getRed(),
					cl.getBlue(), cl.getGreen(), 30));
			cl = GameFrame.pm[xMap-1][yMap].getBackground();
			GameFrame.pm[xMap-1][yMap].setBackground(new Color(cl.getRed(),
					cl.getBlue(), cl.getGreen(), 30));
			cl = GameFrame.pm[xMap][yMap+1].getBackground();
			GameFrame.pm[xMap][yMap+1].setBackground(new Color(cl.getRed(),
					cl.getBlue(), cl.getGreen(), 30));
			cl = GameFrame.pm[xMap][yMap-1].getBackground();
			GameFrame.pm[xMap][yMap-1].setBackground(new Color(cl.getRed(),
					cl.getBlue(), cl.getGreen(), 30));
		} else if (a.getSource() == recruit) {
			//GameFrame.mainPane.remove(this);
			add(new GUIRecruit(getWidth(), getHeight()));
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		/*g2d.setColor(new Color(247, 234, 159));
		g2d.fillRect(0, 0, getWidth(), getHeight());*/
		
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Calibri", Font.BOLD, 32));
		
		String str = "Поле: x - " + xMap + " y - " + yMap;
		int strWidth = (int) g2d.getFont().getStringBounds(str, frc).getWidth();
		g2d.drawString(str, (getWidth() - strWidth) / 2, 30);
		if (GameFrame.pm[xMap][yMap].owner == -1) {
			g2d.drawString("Владельца нет", 30, 90);
		} else if (GameFrame.pm[xMap][yMap].owner == GameFrame.idPlayer) {
			g2d.drawString("Владелец: вы", 30, 90);
		} else {
			g2d.drawString("Владелец: игрок №" + GameFrame.pm[xMap][yMap].owner,
					30, 90);
		}
		
		g2d.drawString("Армия: " + GameFrame.pm[xMap][yMap].army, 30, 150);
	}
	
}
