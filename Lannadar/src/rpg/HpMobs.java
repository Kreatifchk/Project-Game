package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HpMobs extends JPanel {
	
	//����� ��� ��������� xp ����
	
	Image hpI = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	
	static int xpMax, xpCurrent, xpPercent;
	/*
	 * xpMax - ������������ xp ����
	 * xpCurrent - ������� xp ���� � ���
	 * xpPercent ������� xp � ���������, ��� ��������� �������
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		int x = 2;
		
		//��������� �������
		double nb = (double) xpMax / 100;
		xpPercent = (int) (xpCurrent / nb);
		
		for (int i = 1; i <= xpPercent; i++) {
			g2d.drawImage(hpI, x, 1, null);
			x++;
		}
		
		//��������� ����
		try {
			String a = "" + xpCurrent;
			int xx = 25;
			int n = 0;
			for (int i = 0; i <= a.length()-1; i++) {
				if (a.charAt(i) != '-') {
					n = Integer.parseInt("" + a.charAt(i));
				}
				if (Battle.battle == true) {
					g2d.drawImage(Game.hpNumberImage[n], xx, 6, null);
				}
				xx += 15;
			}
		} catch (NullPointerException e) {
			
		}
	}
	
}
