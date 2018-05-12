package ru.kreatifchk.strategy2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PointMap extends JLabel implements ActionListener {
	
	private Image capitalI = new ImageIcon(getClass().getResource("res/star.png")).getImage();
	
	int number;
	int xMap, yMap;
	
	int owner = -1; //��������
	boolean busy; //������ �� ��� ������ (��� ���������)
	boolean capital = false; //���� ��� ������ �������
	
	int army = 500;
	
	public PointMap(int number, int x, int y) {
		this.number = number;
		xMap = x;
		yMap = y;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//g2d.drawString(xMap + " " + yMap, 7, 14);
		if (capital == true) {
			g2d.drawImage(capitalI,
					(GameFrame.sizePoint - capitalI.getWidth(null)) / 2,
					(GameFrame.sizePoint - capitalI.getHeight(null)) / 2, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
}
