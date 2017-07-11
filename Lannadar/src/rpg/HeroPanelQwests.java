package rpg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.plaf.metal.MetalScrollBarUI;

//В панели персонажа вкладка с заданиями
@SuppressWarnings("serial")
public class HeroPanelQwests extends MouseAdapter implements ActionListener, AdjustmentListener {
	
	QwestsPanel qp;
	
	//Изображение панелей на которыз будут названия квестов
	Image frameQwestD = new ImageIcon(getClass().getResource("res/others/frameQwest.png")).getImage();
	Image frameQwestA = new ImageIcon(getClass().getResource("res/others/frameQwestA.png")).getImage();
	int frm = -1; //Номер квеста на который нажимают (для подсветки)
	
	String name;
	boolean pt; //Отрисовывать ли название квеста
	
	Font uph = null, uphMini = null;
	
	public HeroPanelQwests() {
		qp = new QwestsPanel();
		try {
			InputStream s = getClass().getResourceAsStream("res/Font/UpheavalPro.ttf");
			uph = Font.createFont(Font.TRUETYPE_FONT, s).deriveFont(22F);
			uphMini = uph.deriveFont(20F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Общий контейнер для всего с квестами
	protected class QwestsPanel extends JLabel {
		public QwestsPanel() {
			qp = this;
			setBounds(42, 119, 636, 455);
			
			textQwest.remove(jsb);
			reqBase.remove(jsb2);
			
			massiv();
			textQwest.setBounds(288, 33, 348, 330); //293, 33, 350, 350
			add(textQwest);
			reqBase.setBounds(288, 368, 348, 86);
			add(reqBase);

			jsb.setBounds(328, 0, 20, 330); //330, 0, 20, 350
			jsb.setUI(new BarUI());
			jsb.setMinimum(0);
			jsb.setMaximum(610);
			jsb.addAdjustmentListener(HeroPanelQwests.this);
			
			jsb2.setBounds(328, 0, 20, 86);
			jsb2.setUI(new BarUI());
			jsb2.setMinimum(0);
			jsb2.setMaximum(315);
			jsb2.addAdjustmentListener(HeroPanelQwests.this);
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g2d.drawLine(285, 4, 285, 480); //Вертикальная линия, перегородка
			g2d.drawLine(0, 3, 641, 3); //Горизонтальная линия, отделяющая от кнопок
			g2d.setColor(Color.BLACK);
			g2d.drawLine(290, 27, 640, 27); //Перегородка названия и текста
			g2d.drawLine(290, 365, 640, 365); //Перегородка текста от требования
			if (pt == true) {
				g2d.setFont(uphMini);
				g2d.drawString(name, 350, 21);
			}
		}
	}
	
	QwestButton[] qwestsM; //Кнопки квесты
	//Распологает кнопки - квесты
	private void massiv() {
		qwestsM = new QwestButton[10];
		//Обнуляем массив
		for (int i = 0; i < qwestsM.length; i++) {
			qwestsM[i] = null;
		}
		
		int st = 0; //Номер строки - квеста на панели
		int x = 0, y = 6;//10, 10
		/*for (int i = 0; i < Game.takeQwests.length; i++) {
			Game.takeQwests[i] = 0;
		}*/
		for (int i = 0; i < Game.takeQwests.length; i++) {
			if (Game.takeQwests[i] != -1) {
				qwestsM[st] = new QwestButton(Game.qwest[Game.takeQwests[i]].name);
				qwestsM[st].text = Game.qwest[Game.takeQwests[i]].textN;
				qwestsM[st].request = Game.qwest[Game.takeQwests[i]].request;
				qwestsM[st].progress = Game.qwest[Game.takeQwests[i]].progress;
				qwestsM[st].max = Game.qwest[Game.takeQwests[i]].count;
				qwestsM[st].numb = i;
				name = Game.qwest[Game.takeQwests[i]].name; //Название квеста
				qwestsM[st].setBounds(x, y, 283, 45); //270, 30
				qwestsM[st].addActionListener(this);
				//qwestsM[st].setOpaque(true);
				//qwestsM[st].setBackground(Color.PINK);
				qp.add(qwestsM[st]);
				st++;
				y += 45;
			}
		}
	}
	
	JLabel textQwest = new JLabel(); //Контейнер в который будет вложен контейнер с текстом
	TextQwest textQwestDop; //Вложенный контейнер с текст квеста
	JLabel reqBase = new JLabel();
	RequestQwest textRequest;
	
	JScrollBar jsb = new JScrollBar();
	JScrollBar jsb2 = new JScrollBar();
	
	//Нажатие на кнопку - квест
	public void actionPerformed(ActionEvent a) {
		//Если нажали на кнопку - квест
		for (int i = 0; i < qwestsM.length; i++) {
			if (a.getSource() == qwestsM[i]) {
				if (textQwestDop != null & textRequest != null) {
					textQwest.remove(textQwestDop); //Убирает предыдущий квест с экрана
					reqBase.remove(textRequest);
				}
				pt = true;
				
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
	
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < qwestsM.length; i++) {
			if (e.getSource() == qwestsM[i]) {
				frm = i;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		for (int i = 0; i < qwestsM.length; i++) {
			if (e.getSource() == qwestsM[i]) {
				frm = -1;
			}
		}
	}
	
	//Кнопка квест
	public class QwestButton extends JButton {
		String name, text, request;
		int progress, max;
		int numb;
		public QwestButton(String name) {
			this.name = name;
			setOpaque(false);
			setBorder(null);
			addMouseListener(HeroPanelQwests.this);
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			if (numb == frm) {
				g2d.drawImage(frameQwestA, 0, 0, null);
			} else {
				g2d.drawImage(frameQwestD, 0, 0, null);
			}

			g2d.setFont(uph);
			g2d.setColor(Color.BLACK);
			
			int pix = (int) uph.getStringBounds(name, new FontRenderContext(null, true, true)).getWidth();
			//Узнать длину строки в пикселях
			int x = (283 - pix) / 2; //Расчитываем центр для установки туда названия
			
			g2d.drawString(name, x, 30); //25 30
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
