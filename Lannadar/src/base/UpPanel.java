package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import initialize.InitFont;

@SuppressWarnings("serial")
public class UpPanel extends JLabel {
	
	Image hpI = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	
	HpPaint hpp = new HpPaint();; //Панель с хп игрока
	MpPanel mpP = new MpPanel(); //Панель с маной игрока
	ExpPanel expP = new ExpPanel(); //Панель с опытом игрока
	LevelPanel lP = new LevelPanel(); //Панель с уровнем игрока
	HpMobs hpM = new HpMobs(); //Панель с хп монстров
	LevelMobs lmP = new LevelMobs(); //Панель с уровнем моба
	
	static boolean hpMB; //Если игрок в бою добавляет панели моба
	
	public UpPanel() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		Color BROWN = new Color(185, 122, 87);
		
		this.setBorder(border);
		this.setOpaque(true);
		this.setBackground(BROWN);
		
		hpp.setBounds(10, 2, 104, 16);//22
		hpp.setBorder(border);
		mpP.setBounds(10, 18, 104, 16);//10, 24, 104, 22
		mpP.setBorder(border);
		expP.setBounds(10, 34, 104, 15);//120, 24, 104, 22
		expP.setBorder(border);
		lP.setBounds(114, 2, 47, 47);//228, 9, 30, 30
		
		hpM.setBounds(570, 18, 104, 16);
		hpM.setBorder(border);
		hpM.setVisible(hpMB);
		lmP.setBounds(680, 9, 30, 30);
		
		this.add(hpp);
		this.add(mpP);
		this.add(expP);
		this.add(lP);
		this.add(hpM);
		this.add(lmP);
		
		Game.pl.hpMax = Game.pl.endurance*2;
		Game.pl.hpThis = Game.pl.hpMax;
	}
	
	//Панель с хп игрока
	private class HpPaint extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			int x = 2;
			int hpPointThis = Game.pl.hpThis / (Game.pl.hpMax / 100);
			for (int i = 1; i <= hpPointThis; i++) {
				g2d.drawImage(hpI, x, 1, null);
				x++;
			}
			Font smw = InitFont.smw.deriveFont(19F);
			g2d.setFont(smw);
			g2d.setColor(Color.black);
			int pixW = (int) smw.getStringBounds("" + Game.pl.hpThis, new FontRenderContext(null, true, true)).getWidth();
			g2d.drawString(Game.pl.hpThis + "", (getWidth() - pixW) / 2, 15);
		}
	}
	
}
