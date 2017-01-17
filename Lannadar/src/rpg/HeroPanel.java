package rpg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.plaf.metal.MetalScrollBarUI;

/**
 * 
 * Класс панель содержащая инвертарь, квесты, навыки и информацию о персонаже.
 *
 */
@SuppressWarnings("serial")
public class HeroPanel extends JPanel implements ActionListener, AdjustmentListener {
	
	static boolean qw;
	
	Image bc = new ImageIcon(getClass().getResource("res/others/heroPanel.png")).getImage();
	Image exitI = new ImageIcon(getClass().getResource("res/others/exit.png")).getImage();
	Image hp = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	
	JButton exit = new exitButton();
	JButton hero = new TabButton("hero");
	JButton qwests = new TabButton("qwests");
	JButton bag = new TabButton("bag");
	JButton skills = new TabButton("skills");
	
	QwestButton[] qwestsM; //Кнопки квесты
	
	JLabel x;
	JLabel infoP = new infoPanel();
	JLabel qwestsP = new QwestsPanel(); //Общий контейнер для всего с квестами
	JLabel textQwest = new JLabel(); //Контейнер в который будет вложен контейнер с текстом
	TextQwest textQwestDop; //Вложенный контейнер с текст квеста
	JLabel reqBase = new JLabel();
	RequestQwest textRequest;
	
	JScrollBar jsb = new JScrollBar();
	JScrollBar jsb2 = new JScrollBar();
	
	int nQwest; //Количество взятых квестов
	//String nameQ;
	
	public HeroPanel() {
		exit.setBounds(645, 88, 30, 30);
		exit.setBorderPainted(false);
		exit.setOpaque(false);
		exit.addActionListener(this);
		hero.setBounds(42, 87, 120, 31);
		hero.setOpaque(false);
		hero.addActionListener(this);
		qwests.setBounds(162, 87, 120, 31);
		qwests.setOpaque(false);
		qwests.addActionListener(this);
		bag.setBounds(282, 87, 120, 31);
		bag.setOpaque(false);
		skills.setBounds(402, 87, 120, 31);
		skills.setOpaque(false);
		add(exit);
		add(hero);
		add(qwests);
		add(bag);
		add(skills);
		
		infoP.setBounds(42, 119, 636, 455);
		add(infoP);
		qwestsP.setBounds(35, 90, 645, 490); //макс x - 726
		exit.addMouseListener(new Game.NpcListener());
		
		x = infoP;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bc, 35, 80, null);
	}

	//Распологает кнопки - квесты
	private void massiv() {
		int st = 0;
		qwestsM = new QwestButton[10];
		//Определяет с какого элемента надо заполнять массив
		/*for (int i = 0; i <= qwestsM.length - 1; i++) {
			if (qwestsM[i] == null) {
				st = i;
				break;
			}
		}*/
		for (int i = 0; i <= qwestsM.length - 1; i++) {
			if (qwestsM[i] != null) {
				qwestsM[i] = null;
			}
		}
		
		int x = 15, y = 40;
		for (int i = 0; i <= Game.takeQwests.length - 1; i++) {
			if (Game.takeQwests[i] != -1) {
				qwestsM[st] = new QwestButton(Game.qwest[Game.takeQwests[i]].name);
				qwestsM[st].text = Game.qwest[Game.takeQwests[i]].textN;
				qwestsM[st].request = Game.qwest[Game.takeQwests[i]].request;
				qwestsM[st].progress = Game.qwest[Game.takeQwests[i]].progress;
				qwestsM[st].max = Game.qwest[Game.takeQwests[i]].count;
				qwestsM[st].setBounds(x, y, 269, 30);
				qwestsM[st].setOpaque(false);
				qwestsM[st].addActionListener(this);
				qwestsP.add(qwestsM[st]);
				st++;
				y += 50;
			}
		}
	}
	
	public class QwestButton extends JButton {
		String name, text, request;
		int progress, max;
		public QwestButton(String name) {
			this.name = name;
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setFont(new Font("Times new Roman", Font.BOLD, 20));
			g2d.setColor(Color.BLACK);
			g2d.drawString(name, 5, 20);
		}
		public void paintBorder(Graphics g) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == qwests) {
			remove(x);
			textQwest.remove(jsb);
			reqBase.remove(jsb2);
			add(qwestsP);
			x = qwestsP;
			massiv();
			
			textQwest.setBounds(293, 33, 350, 350);
			qwestsP.add(textQwest);
			
			reqBase.setBounds(293, 388, 350, 95);
			qwestsP.add(reqBase);
			
			jsb.setBounds(330, 0, 20, 350);
			jsb.setUI(new BarUI());
			jsb.setMinimum(0);
			jsb.setMaximum(610);
			jsb.addAdjustmentListener(this);
			
			jsb2.setBounds(330, 0, 20, 95);
			jsb2.setUI(new BarUI());
			jsb2.setMinimum(0);
			jsb2.setMaximum(315);
			jsb2.addAdjustmentListener(this);
		}
		
		if (a.getSource() == hero) {
			remove(x);
			textQwest.remove(jsb);
			textQwest.remove(jsb2);
			add(infoP);
			x = infoP;
		}
		
		//Если нажали на кнопку - квест
		for (int i = 0; i <= qwestsM.length - 1; i++) {
			if (a.getSource() == qwestsM[i]) {
				if (textQwestDop != null & textRequest != null) {
					textQwest.remove(textQwestDop); //Убирает предыдущий квест с экрана
					reqBase.remove(textRequest);
				}
				textQwestDop = new TextQwest(qwestsM[i].text);
				textQwestDop.setBounds(0, 0, 330, 1050);
				textQwest.add(textQwestDop);
				textQwest.add(jsb);
				
				//System.out.println(qwestsM[i].progress);
				textRequest = new RequestQwest(qwestsM[i].request);
				textRequest.progress = qwestsM[i].progress;
				textRequest.max = qwestsM[i].max;
				textRequest.setBounds(0, 0, 330, 400);
				reqBase.add(textRequest);
				reqBase.add(jsb2);
				
				//System.out.println(qwestsM[i].text);
			}
		}
		
		if (a.getSource() == exit) {
			remove(x);
			textQwest.remove(jsb);
			textQwest.remove(jsb2);
			if (textRequest != null & textQwestDop != null) {
				textQwest.remove(textQwestDop);
				reqBase.remove(textRequest);
			}
			add(infoP);
			x = infoP;
			
			//Стереть кнопки квесты
			for (int i = 0; i <= qwestsM.length - 1; i++) {
				if (qwestsM[i] != null) {
					qwestsP.remove(qwestsM[i]);
				}
			}
		}
	}
	
	
	//Кнопка exit
	private class exitButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(exitI, 0, 0, null);
		}
		public void paintBorder(Graphics g) {
		}
	}
	
	//Кнопки вкладок
	private class TabButton extends JButton {
		Image nameTab, button;
		private TabButton(String name) {
			button = new ImageIcon(getClass().getResource("res/others/button.png")).getImage();
			nameTab = new ImageIcon(getClass().getResource("res/others/" + name + ".png")).getImage();
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(button, 0, 0, null);
			g2d.drawImage(nameTab, 0, 0, null);
		}
		public void paintBorder(Graphics g) {
		}
	}
	
	private class infoPanel extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			/*Graphics2D g2d = (Graphics2D)g;
			((Graphics2D) g).setStroke(new BasicStroke(7));
			g2d.drawLine(10, 10, 42, 40);*/
		}
	}
	
	private class QwestsPanel extends JLabel {
		public QwestsPanel() {
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g2d.drawLine(290, 30, 290, 480);
			g2d.drawLine(8, 30, 641, 30);
			g2d.setColor(Color.BLACK);
			g2d.drawLine(295, 385, 640, 385);
		}
	}
	
	//Выводит текст - описание задания
	private class TextQwest extends JLabel {
		String text;
		public TextQwest(String text) {
			//System.out.println(text);
			this.text = text;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			int length = text.length();
			int y = 0, x = 5;
			char l = 0;
			for (int i = 0; i <= length - 1; i++) {	
				l = text.charAt(i);
				int y2 = 0;
				boolean prob = false;
				if (l == ',') {
					//Немного опустить запятую
					y2 = y + 3;
				} else if (l == 'щ') {
					y2 = y + 5;
				} else if (l == ' ') {
					prob = true;
				} else {
					y2 = y;
				}
				g2d.drawImage(QwestGivePanel.letters(l), x, y2, null);
				
				if (l == '.') {
					//Меньше расстояние после точки
					x -= 12;
				}
				
				if (prob == true) {
					//Меньше размер пробелов
					x -= 10;
					prob = false;
				}
				
				//Добавляет переносы строки
				int xx = x;
				if (l == ' ') {
					int c = i + 1; //устанавливает с какого символа пробегать (слово)
					char l2 = 0;
					try {
						while (l2 != ' ') {
							l2 = text.charAt(c);
							xx += 21;
							c++;
							if (xx > 330) {
								y += 21;
								x = -21 + 5;
								break;
							}
						}
					} catch (StringIndexOutOfBoundsException e) {}
				}
				x += 21;
			}
		}
	}
	
	//Выводит текст - требование задания
	private class RequestQwest extends JLabel {
		String request;
		int progress, max;
		public RequestQwest(String request) {
			this.request = request;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			int length = request.length();
			int y = 0, x = 5;
			char l = 0;
			for (int i = 0; i <= length - 1; i++) {	
				l = request.charAt(i);
				int y2 = 0;
				boolean prob = false;
				if (l == ',') {
					//Немного опустить запятую
					y2 = y + 3;
				} else if (l == 'щ') {
					y2 = y + 5;
				} else if (l == ' ') {
					prob = true;
				} else {
					y2 = y;
				}
				g2d.drawImage(QwestGivePanel.letters(l), x, y2, null);
				if (l == '.') {
					//Меньше расстояние после точки
					x -= 12;
				}
				if (prob == true) {
					//Меньше размер пробелов
					x -= 10;
					prob = false;
				}
				//Добавляет переносы строки
				int xx = x;
				if (l == ' ') {
					int c = i + 1; //устанавливает с какого символа пробегать (слово)
					char l2 = 0;
					try {
						while (l2 != ' ') {
							l2 = request.charAt(c);
							xx += 21;
							c++;
							if (xx > 320) {
								y += 21;
								x = -21 + 5;
								break;
							}
						}
					} catch (StringIndexOutOfBoundsException e) {}
				}
				x += 21;
			}
			
			x = 5;
			y += 41;
			String prog = progress + "/" + max;
			String dop = "Выполнено: " + prog;
			char sb = 0;
			for (int i = 0; i <= dop.length() - 1; i++) {
				sb = dop.charAt(i);
				g2d.drawImage(QwestGivePanel.letters(sb), x, y, null);
				x += 21;
			}
		}
	}
	
	//Для скролл бара
	private class BarUI extends MetalScrollBarUI {
		Image jsbI = new ImageIcon(getClass().getResource("res/others/jsb.png")).getImage();
		Image jsbI2 = new ImageIcon(getClass().getResource("res/others/jsb1.png")).getImage();
		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(jsbI, 0, 0, null);
		}
	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	    	g.translate(thumbBounds.x, thumbBounds.y);
	        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/jsbI2.getWidth(null),(double)thumbBounds.height/jsbI2.getHeight(null));
	        ((Graphics2D)g).drawImage(jsbI2, transform, null);
	        g.translate( -thumbBounds.x, -thumbBounds.y );
	    }
	    //Верхняя
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	    	DecButton but = new DecButton();
	    	Dimension zeroDim = new Dimension(14,14);
	    	but.setPreferredSize(zeroDim);
	    	but.setMinimumSize(zeroDim);
	    	but.setMaximumSize(zeroDim);
	    	return but;
	    }
	    //Нижняя
	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	    	IncButton but = new IncButton();
	    	Dimension zeroDim = new Dimension(14,14);
	    	but.setPreferredSize(zeroDim);
	    	but.setMinimumSize(zeroDim);
	    	but.setMaximumSize(zeroDim);
	    	return but;
	    }
	}
	
	private class IncButton extends JButton {
		Image jsbD = new ImageIcon(getClass().getResource("res/others/jsbD.png")).getImage();
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(jsbD, 0, 0, null);
		}
		@Override
		public void paintBorder(Graphics g) {
			
		}
	}
	
	private class DecButton extends JButton {
		Image jsbU = new ImageIcon(getClass().getResource("res/others/jsbU.png")).getImage();
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(jsbU, 0, 0, null);
		}
		@Override
		public void paintBorder(Graphics g) {
			
		}
	}
	
	//Скрол движение
	@Override
	public void adjustmentValueChanged(AdjustmentEvent a) {
		if (a.getSource() == jsb) {
			textQwestDop.setLocation(0, textQwestDop.getX() - a.getValue());
		}
		if (a.getSource() == jsb2) {
			textRequest.setLocation(0, textRequest.getX() - a.getValue());
		}
	}
	
}
