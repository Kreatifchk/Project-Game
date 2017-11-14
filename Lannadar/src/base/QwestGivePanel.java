package base;

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
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.plaf.metal.MetalScrollBarUI;

import initialize.InitFont;

/**
 * Класс - Label, с окном для получения/сдачи квестов
 */
@SuppressWarnings("serial")
public class QwestGivePanel extends JLabel implements ActionListener, AdjustmentListener{
	
	Image bc = new ImageIcon(getClass().getResource("res/others/qwestGivePanel.png")).getImage();
	Image exitI = new ImageIcon(getClass().getResource("res/others/exit.png")).getImage();
	ImageIcon takeI = new ImageIcon(getClass().getResource("res/takeQwest.png"));
	ImageIcon endI = new ImageIcon(getClass().getResource("res/endQwest.png"));
	static Image letter; //Определяет и показывает текущую букву (для циклов и отрисовки)
	static Image lettersI[] = new Image[52]; //Массив изображений букв
	
	JButton exit = new exitButton(); //Кнопка для закрытия окна
	JButton take = new JButton(takeI);
	JLabel nameN; //Имя NPC
	JLabel listQwests = new JLabel(); //Первое окно - список квестов
	JLabel fDescribe = new JLabel(); //Куда будет помещено описание квеста
	JLabel describe; //Label с описанием квеста
	JLabel fRequest = new FRequestLabel(); //Куда булет помещено требование квеста
	JLabel request; //Label с требованием квеста
	/*Сделана по 2, потому что размер внутреннего Label больше
	так как скрол бар его передвигает*/
	
	qwestButton[] qwests; //Массив кнопок - квестов
	
	static String name, textN, textK, reques;
	int length, id, status, npcId;
	int x, y; //Координаты кнопок
	static String nameNPC;
	
	Font determ = InitFont.determ.deriveFont(24F);
	
	
	boolean bx = false;
	boolean kon = false; //не дает сразу завершить квест, если он разговорный
	JScrollBar jsb = new JScrollBar();
	JScrollBar jsb2 = new JScrollBar();
	
	public QwestGivePanel() {
	}
	
	public QwestGivePanel(int id) {
		npcId = id;
		
		exit.setBounds(405, 6, 30, 30);
		exit.setOpaque(false);
		exit.addActionListener(this);
		
		listQwests.setBounds(6, 47, 430, 480);
		//listQwests.setOpaque(true);
		//listQwests.setBackground(Color.pink);
		add(listQwests);
		
		nameN = new NameNPC(nameNPC);
		nameN.setBounds(7, 5, 425, 33);
		
		add(exit);
		add(nameN);
		exit.addMouseListener(new Game.NpcListener());
		
		//Устанавливает размер массива по количеству квестов у NPC
		qwests = new qwestButton[Game.npc[id].qwest.length];
		
		//Вызывает метод для добавления квестов на панель
		int y = 5;
		for (int i = 0; i <= Game.npc[id].qwest.length-1; i++) {
			int number = Game.npc[id].qwest[i]; //id квеста
			if (Game.qwest[number].lastId != -1) {
				if (Game.qwest[Game.qwest[number].lastId].status == 4) {
					//Если предыд. квест пройден
					addButton(Game.qwest[number].name, i, y, number);
				}
			} else {
				addButton(Game.qwest[number].name, i, y, number);
			}
			
			if (Game.qwest[Game.npc[id].qwest[i]].status == 1
					|| Game.qwest[Game.npc[id].qwest[i]].status == 3) {
				//Если квест еще не взят (или окончен) то следующий опустить вниз
				y += 40;
			}
		}
		y = 5;
	}
	
	//Добавляет кнопки - квесты
	private void addButton(String name, int count, int y, int number) {
		//Сделать чтоб если квест взят, но не выполнен, на него нельзя нажать
		if (Game.qwest[number].status == 1) {
			if (Game.qwest[number].idNPC == -1) {
				/*Если этому NPC надо сдавать квест,
				 * а он еще не получен, то не показывать
				 */
				QwestGivePanel.name = name;
				qwests[count] = new qwestButton();
				qwests[count].setBounds(5, y, 400, 24);
				listQwests.add(qwests[count]); //Добавляет на сам JLabel (фон)
				qwests[count].setOpaque(false);
				qwests[count].addActionListener(this);
				
				qwests[count].textN = Game.qwest[number].textN;
				qwests[count].request = Game.qwest[number].request;
				qwests[count].status = Game.qwest[number].status;
				qwests[count].id = Game.qwest[number].id;
			} else {
				if (Game.qwest[number].idNPC != npcId) {
					QwestGivePanel.name = name;
					qwests[count] = new qwestButton();
					qwests[count].setBounds(0, y, 400, 24);
					listQwests.add(qwests[count]); //Добавляет на сам JLabel (фон)
					qwests[count].setOpaque(false);
					qwests[count].addActionListener(this);
					
					qwests[count].textN = Game.qwest[number].textN;
					qwests[count].request = Game.qwest[number].request;
					qwests[count].status = Game.qwest[number].status;
					qwests[count].id = Game.qwest[number].id;
				}
			}
		}
		if (Game.qwest[number].status == 3) {
			if (Game.qwest[number].idNPC == -1) {
				//Если квест не разговорный
				QwestGivePanel.name = name;
				qwests[count] = new qwestButton();
				qwests[count].setBounds(5, y, 400, 24);
				listQwests.add(qwests[count]); //Добавляет на сам JLabel (фон)
				qwests[count].setOpaque(false);
				qwests[count].addActionListener(this);
				
				qwests[count].textK = Game.qwest[number].textK;
				qwests[count].request = Game.qwest[number].request;
				qwests[count].status = Game.qwest[number].status;
				qwests[count].id = Game.qwest[number].id;
			} else {
				//Если квест разговорный
				if (Game.qwest[number].idNPC == npcId) {
					QwestGivePanel.name = name;
					qwests[count] = new qwestButton();
					qwests[count].setBounds(5, y, 400, 24);
					listQwests.add(qwests[count]); //Добавляет на сам JLabel (фон)
					qwests[count].setOpaque(false);
					qwests[count].addActionListener(this);
					
					qwests[count].textK = Game.qwest[number].textK;
					qwests[count].request = Game.qwest[number].request;
					qwests[count].status = Game.qwest[number].status;
					qwests[count].id = Game.qwest[number].id;
				}
			}
		}
	}
	
	//Отрисовка фона и линии сверху
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bc, 0, 0, null);
		g2d.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(10));
		g2d.drawLine(11, 44, 429, 44);
	}
	
	//Что происходит при нажатии кнопки - взять
	private void takeQwests() {
		//Взять квест
		if (Game.qwest[id].status == 1) {
			QwestEvent.giveEvent(Game.qwest[id].id);
			//Если квест разговорный то статус сразу 3
			if (Game.qwest[id].idNPC != -1) {
				Game.qwest[id].status = 3;
				kon = true;
			} else {
				Game.qwest[id].status = 2;
			}
			
			//Находит свободный элемент в массиве взятых игроком квестов
			int pr = 0;
			for (int i = 0; i <= Game.takeQwests.length - 1; i++) {
				if (Game.takeQwests[i] == -1) {
					pr = i;
					break;
				}
			}
			
			Game.takeQwests[pr] = id;
		}
		//Сдать квест
		else if (Game.qwest[id].status == 3) {
			QwestEvent.passEvent(Game.qwest[id].id);
			if (kon != true) {
				Game.qwest[id].status = 4;
				Game.pl.exp += Game.qwest[id].exp;
				LevelUp.levelUp();
			}
			for (int i = 0; i <= Game.takeQwests.length - 1; i++) {
				if (Game.takeQwests[i] == id) {
					Game.takeQwests[i] = -1;
					break;
				}
			}
			kon = false;
		}
		
		updateSign();
	}
	
	//Обновляет значки всех NPC на локации
	private void updateSign() {
		int zId = npcId;
		SignQwest.sign(zId);
		//Сначала обрабатывает NPC общающегося с игроком, далее всех остальных
		
		boolean stop = false; //Служит для остановки цикла когда NPC с текущей локации кончились
		for (int i = 0; i <= Game.npc.length - 1; i++) {
			if (Game.npc[i].location == Game.currentLocation && i != zId) {
				SignQwest.sign(Game.npc[i].id);
				stop = true;
			} else {
				if (stop == true) {
					break;
				}
			}
		}
	}
	
	//Слушатель кнопкок - квестов
	@Override
	public void actionPerformed(ActionEvent a) {
		int l = qwests.length;
		for (int i = 0; i < l; i++) {
			if (a.getSource() == qwests[i]) {
				//кнопка - квест
				listQwests.setVisible(false); //Убирает ссылки - названия квестов
				id = qwests[i].id;
				
				textN = qwests[i].textN;
				status = qwests[i].status;
				textK = qwests[i].textK;
				
				reques = qwests[i].request;
				
				fDescribe.setBounds(6, 47, 430, 353); //y = 47, height = 388
				add(fDescribe);
				describe = new QwestTextLabel();
				describe.setBounds(0, 0, 453, 1527); //x2 = 436
				fDescribe.add(describe);
				
				fRequest.setBounds(2, 400, 432, 105);//y1 = 435 height = 70
				add(fRequest);
				request = new RequestLabel();
				request.setBounds(0, 5, 460, 600);//y1 = 5 height = 580
				fRequest.add(request);
				
				//JScroll бары
				jsb.setBounds(408, 2, 20, 351);//y2 = 380
				jsb.addAdjustmentListener(this);
				jsb.setMinimum(0);
				jsb.setMaximum(1058);
				jsb.setUI(new BarUI());
				fDescribe.add(jsb);
				revalidate();
				
				jsb2.setBounds(412, 6, 20, 91);
				jsb2.addAdjustmentListener(this);
				jsb2.setMinimum(0);
				jsb2.setMaximum(100);
				jsb2.setUI(new BarUI());
				fRequest.add(jsb2);
				revalidate();
				
				//Кнопка взять квест
				take.setBounds(160, 503, 120, 30);
				take.setBorderPainted(false);
				take.addActionListener(this);
				take.addMouseListener(new Game.NpcListener());
				if (status == 3) {
					take.setIcon(endI);
				}
				add(take);
				break;
			}
			if (a.getSource() == take) {
				//кнопка взять квест
				remove(fDescribe);
				remove(fRequest);
				Game.mainPane.remove(Game.qGP);
				Game.p.requestFocus();
				Game.qGP = null;
				takeQwests();
				break;
			}
			if (a.getSource() == exit) {
				Game.mainPane.remove(this);
				Game.qGP = null;
				Game.p.requestFocus();
			}
		}
	}
	
	//Крест в верхнем правом углу
	private class exitButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(exitI, 0, 0, null);
		}
		public void paintBorder(Graphics g) {
		}
	}
	
	//Кнопки - квесты Label
	private class qwestButton extends JButton {
		//Название квеста
		String name = QwestGivePanel.name;
		String textN, textK, request;
		int id, status;
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.black);
			g2d.setFont(determ);
			int bound = (int) determ.getStringBounds(name,
					new FontRenderContext(null, true, true)).getWidth();
			int x = (getWidth() - bound) / 2; y = 24;
			g2d.drawString(name, x, 20);
			
			/*length = name.length();
			int x = 0, y = 0;
			for (int i = 0; i <= length - 1; i++) {
				char l = name.charAt(i);
				//g2d.drawImage(letters(l), x, y, null);
				if (l == ' ') {
					//Меньше размер пробелов
					x -= 10;
				}
				x += 24;
			}*/
		}
		public void paintBorder(Graphics g) {
		}
	}
	
	//Имя NPC
	private class NameNPC extends JLabel {
		String nameNPC;
		public NameNPC(String name) {
			nameNPC = name;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.black);
			g2d.setFont(InitFont.determ.deriveFont(24F));
			int bound = (int) InitFont.determ.deriveFont(24F).getStringBounds(nameNPC,
					new FontRenderContext(null, true, true)).getWidth();
			int x = (getWidth() - bound) / 2; y = 24;
			g2d.drawString(nameNPC, x, y);
		}
	}

	//Label с текстом квеста
	private class QwestTextLabel extends JLabel{
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			String text = null;
			if (status == 1) {
				length = textN.length();
				text = textN;
			}
			if (status == 3) {
				length = textK.length();
				text = textK;
			}
			g2d.setColor(Color.black);
			int x = 5; y = 25;
			g2d.setFont(determ);
			String[] sub = text.split(" "); //Разделение строки на слова
			for (int i = 0; i < sub.length; i++) {
				int bound = (int) determ.getStringBounds(sub[i],
						new FontRenderContext(null, true, true)).getWidth();
				if (x + bound >= 400) {
					x = 5;
					y += 35;
				}
				g2d.drawString(sub[i], x, y);
				x += bound + 18;
			}
		}
	}
	
	//Label в который встроено описание и требование квеста
	private class FRequestLabel extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			((Graphics2D) g).setStroke(new BasicStroke(7));
			g2d.setColor(Color.black);
			g2d.drawLine(8, 2, 432, 2);
			g2d.drawLine(8, 100, 430, 100);
		}
	}
	
	//Label с требованием квеста
	private class RequestLabel extends JLabel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setColor(Color.black);
			String text = reques;
			int x = 5, y = 25;
			g2d.setFont(determ);
			String[] sub = text.split(" "); //Разделение строки на слова
			for (int i = 0; i < sub.length; i++) {
				int bound = (int) determ.getStringBounds(sub[i],
						new FontRenderContext(null, true, true)).getWidth();
				if (x + bound >= 400) {
					x = 5;
					y += 35;
				}
				g2d.drawString(sub[i], x, y);
				x += bound + 18;
			}
		}
	}

	//Скрол
	@Override
	public void adjustmentValueChanged(AdjustmentEvent a) {
		if (a.getSource() == jsb) {
			describe.setLocation(0, describe.getX() - a.getValue());
		}
		if (a.getSource() == jsb2) {
			request.setLocation(0, request.getX() - a.getValue());
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
}
