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
	
	HpPaint hp = new HpPaint();; //Панель с хп игрока
	MpPaint mp = new MpPaint(); //Панель с маной игрока
	ExpPanel exp = new ExpPanel(); //Панель с опытом игрока
	LevelPaint lvl = new LevelPaint(); //Панель с уровнем игрока
	
	NameMob nameM = new NameMob(); //Панель с хп монстров
	MobHp hpM = new MobHp(); //Панель с хп моба
	MobMp mpM = new MobMp(); //Панель с манной моба
	MobLevel lvlM = new MobLevel();
	
	Font smw = InitFont.smw.deriveFont(19F);
	
	public UpPanel() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		Color BROWN = new Color(185, 122, 87);
		
		this.setBorder(border);
		this.setOpaque(true);
		this.setBackground(BROWN);
		
		//Собственные показатели
		hp.setBounds(10, 2, 104, 16);//22
		hp.setBorder(border);
		mp.setBounds(10, 18, 104, 16);//10, 24, 104, 22
		mp.setBorder(border);
		exp.setBounds(10, 34, 104, 15);//120, 24, 104, 22
		exp.setBorder(border);
		lvl.setBounds(114, 2, 47, 47);//228, 9, 30, 30
		
		//Показатели мобов
		nameM.setBounds(656, 2, 151, 16);//560
		nameM.setBorder(border);
		nameM.setVisible(false);
		hpM.setBounds(703, 18, 104, 16);//607
		hpM.setBorder(border);
		hpM.setVisible(false);
		mpM.setBounds(703, 34, 104, 16);//607
		mpM.setBorder(border);
		mpM.setVisible(false);
		lvlM.setBounds(656, 18, 47, 31);//560
		lvlM.setVisible(false);
		
		this.add(hp);
		this.add(mp);
		this.add(exp);
		this.add(lvl);
		
		this.add(nameM);
		this.add(hpM);
		this.add(mpM);
		this.add(lvlM);
		
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
	
	private class MpPaint extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			//Graphics2D g2d = (Graphics2D)g;
		}
	}
	
	private class LevelPaint extends JLabel {
		Font smw = InitFont.smw.deriveFont(36F);//24
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			if (Game.pl.level > 9) {
				smw = smw.deriveFont(34F);
			}
			int pixW = (int) smw.getStringBounds("" + Game.pl.level, new FontRenderContext(null, true, true)).getWidth();
			
			g2d.setFont(smw);
			g2d.setColor(Color.black);
			g2d.drawRect(0, 0, 46, 46);
			g2d.setColor(new Color(128, 128, 128));
			g2d.fillRect(1, 1, 45, 45);
			g2d.setColor(Color.black);

			int x = (getWidth() - pixW) / 2;
			g2d.drawString("" + Game.pl.level, x, 34);
		}
	}
	
	class MobHp extends JLabel {
		int hpPercent;
		public void paintComponent(Graphics g) {
			//super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			int x = 2;
			//Отрисовка полоски
			double nb = (double) Battle.hpMax / 100;
			hpPercent = (int) (Battle.hpCurrent / nb);
			for (int i = 1; i <= hpPercent; i++) {
				g2d.drawImage(hpI, x, 1, null);
				x++;
			}
			//Отрисовка цифр
			try {
				if (Battle.battle == true) {
					g2d.setFont(smw);
					g2d.setColor(Color.black);
					int pixW = (int) smw.getStringBounds("" + Battle.hpCurrent, new FontRenderContext(null, true, true)).getWidth();
					g2d.drawString(Battle.hpCurrent + "", (getWidth() - pixW) / 2, 15);
				}
			} catch (NullPointerException e) {
			}
		}
	}
	
	class NameMob extends JLabel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.lightGray);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.black);
			g2d.setFont(smw.deriveFont(18F));
			int pixW = (int) smw.getStringBounds(Battle.name, new FontRenderContext(null, true, true)).getWidth();
			g2d.drawString(Battle.name, (getWidth() - pixW) / 2, 15);
		}
	}
	
	class MobMp extends JLabel {
	}
	
	class MobLevel extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			if (Battle.mobLevel > 9) {
				smw = smw.deriveFont(28F);
			}
			int pixW = (int) smw.getStringBounds("" + Battle.mobLevel, new FontRenderContext(null, true, true)).getWidth();

			g2d.setFont(smw.deriveFont(30F));
			g2d.setColor(Color.black);
			g2d.drawRect(0, 0, 46, 46);
			g2d.setColor(new Color(128, 128, 128));
			g2d.fillRect(1, 1, 45, 45);
			g2d.setColor(Color.black);
			
			int x = (getWidth() - pixW) / 2;
			g2d.drawString("" + Battle.mobLevel, x, 26);
		}
	}
	
}
