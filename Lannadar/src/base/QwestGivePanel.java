package base;

import java.awt.BasicStroke;
import java.awt.Dimension;
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
import javax.swing.JScrollBar;
import javax.swing.plaf.metal.MetalScrollBarUI;

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
		add(listQwests);
		
		nameN = new NameNPC(nameNPC);
		//Где расположить имя нпс
		int xName = 0, lName = nameNPC.length();
		if (lName <= 4) {
			xName = 150;
		} else if (lName <= 8) {
			xName = 120;
		} else if (lName <= 11) {
			xName = 80;
		} else if (lName <= 14) {
			xName = 50;
		}
		nameN.setBounds(xName, 5, 500, 40);
		
		add(exit);
		add(nameN);
		exit.addMouseListener(new Game.NpcListener());
		
		init();
		
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
	
	//Инициализация изображений - букв
	public void init() {
		for (int i = 1; i <= 33; i++) {
			lettersI[i] =  new ImageIcon(getClass().getResource("res/letters/" + i + ".png")).getImage();
		}
		lettersI[34] = new ImageIcon(getClass().getResource("res/letters/space.png")).getImage();
		//пробел
		lettersI[35] = new ImageIcon(getClass().getResource("res/letters/t.png")).getImage();
		//точка
		lettersI[36] = new ImageIcon(getClass().getResource("res/letters/v.png")).getImage();
		//восклицательный знак
		lettersI[37] = new ImageIcon(getClass().getResource("res/letters/z.png")).getImage();
		//запятая
		lettersI[38] = new ImageIcon(getClass().getResource("res/letters/vp.png")).getImage();
		//Двоеточие
		lettersI[39] = new ImageIcon(getClass().getResource("res/letters/dv.png")).getImage();
		//Слеш
		lettersI[40] = new ImageIcon(getClass().getResource("res/letters/sl.png")).getImage();
		//Тире
		lettersI[41] = new ImageIcon(getClass().getResource("res/letters/tr.png")).getImage();
		int x = 42;
		for (int i = 0; i <= 9; i++) {
			lettersI[x] =  new ImageIcon(getClass().getResource("res/letters/0" + i + ".png")).getImage();
			x++;
		}
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
				qwests[count].id = Game.qwest[number].id;
				qwests[count].status = Game.qwest[number].status;
			} else {
				if (Game.qwest[number].idNPC != npcId) {
					QwestGivePanel.name = name;
					qwests[count] = new qwestButton();
					qwests[count].setBounds(5, y, 400, 24);
					listQwests.add(qwests[count]); //Добавляет на сам JLabel (фон)
					qwests[count].setOpaque(false);
					qwests[count].addActionListener(this);
					
					qwests[count].textN = Game.qwest[number].textN;
					qwests[count].request = Game.qwest[number].request;
					qwests[count].id = Game.qwest[number].id;
					qwests[count].status = Game.qwest[number].status;
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
				qwests[count].id = Game.qwest[number].id;
				qwests[count].status = Game.qwest[number].status;
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
					qwests[count].id = Game.qwest[number].id;
					qwests[count].status = Game.qwest[number].status;
				}
			}
		}
	}
	
	//Отрисовка фона и линии сверху
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bc, 0, 0, null);
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
				//Game.signQwest(Game.npc[i].id);
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
		int l = qwests.length - 1;
		for (int i = 0; i <= l; i++) {
			if (a.getSource() == qwests[i]) {
				//кнопка - квест
				listQwests.setVisible(false);
				
				id = qwests[i].id;
				if (qwests[i].status == 1) {
					textN = qwests[i].textN;
					status = qwests[i].status;
				}
				if (qwests[i].status == 3) {
					textK = qwests[i].textK;
					status = qwests[i].status;
				}
				reques = qwests[i].request;
				
				fDescribe.setBounds(6, 47, 430, 388); //y2 = 460
				add(fDescribe);
				
				describe = new QwestTextLabel();
				describe.setBounds(0, 0, 453, 1527); //x2 = 436
				//describe.setBorder(border);
				fDescribe.add(describe);
				
				fRequest.setBounds(2, 435, 430, 70);
				//fRequest.setBorder(border2);
				add(fRequest);
				
				request = new RequestLabel();
				request.setBounds(0, 5, 460, 580); //y1 = 405, y2 = 58
				//request.setBorder(border);
				fRequest.add(request);
				
				//JScroll бары
				jsb.setBounds(400, 5, 20, 380);
				jsb.addAdjustmentListener(this);
				jsb.setMinimum(0);
				jsb.setMaximum(1058);
				jsb.setUI(new BarUI());
				fDescribe.add(jsb);
				revalidate();
				
				jsb2.setBounds(403, 6, 20, 56);
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
					//take.setText("Сдать");
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
	
	//Буквы
	public static Image letters(char l) {
		String lt = "" + l;
		if (lt.equals("а")) {
			letter = lettersI[1];
		}
		else if (lt.equals("б") || (lt.equals("Б"))) {
			letter = lettersI[2];
		}
		else if (lt.equals("в") || (lt.equals("В"))) {
			letter = lettersI[3];
		}
		else if (lt.equals("г") || (lt.equals("Г"))) {
			letter = lettersI[4];
		}
		else if (lt.equals("д") || (lt.equals("Д"))) {
			letter = lettersI[5];
		}
		else if (lt.equals("е") || (lt.equals("Е"))) {
			letter = lettersI[6];
		}
		else if (lt.equals("ё")) {
			letter = lettersI[7];
		}
		else if (lt.equals("ж")) {
			letter = lettersI[8];
		}
		else if (lt.equals("з") || (lt.equals("З"))) {
			letter = lettersI[9];
		}
		else if (lt.equals("и") || lt.equals("И")) {
			letter = lettersI[10];
		}
		else if (lt.equals("й")) {
			letter = lettersI[11];
		}
		else if (lt.equals("к") || lt.equals("К")) {
			letter = lettersI[12];
		}
		else if (lt.equals("л") || lt.equals("Л")) {
			letter = lettersI[13];
		}
		else if (lt.equals("м") || lt.equals("М")) {
			letter = lettersI[14];
		}
		else if (lt.equals("н") || (lt.equals("Н"))) {
			letter = lettersI[15];
		}
		else if (lt.equals("о") || lt.equals("О")) {
			letter = lettersI[16];
		}
		else if (lt.equals("п") || lt.equals("П")) {
			letter = lettersI[17];
		}
		else if (lt.equals("р") || lt.equals("Р")) {
			letter = lettersI[18];
		}
		else if (lt.equals("с") || lt.equals("С")) {
			letter = lettersI[19];
		}
		else if (lt.equals("т") || lt.equals("Т")) {
			letter = lettersI[20];
		}
		else if (lt.equals("у") || lt.equals("У")) {
			letter = lettersI[21];
		}
		else if (lt.equals("ф")) {
			letter = lettersI[22];
		}
		else if (lt.equals("х") || (lt.equals("Х"))) {
			letter = lettersI[23];
		}
		else if (lt.equals("ц")) {
			letter = lettersI[24];
		}
		else if (lt.equals("ч") || (lt.equals("Ч"))) {
			letter = lettersI[25];
		}
		else if (lt.equals("ш")) {
			letter = lettersI[26];
		}
		else if (lt.equals("щ")) {
			letter = lettersI[27];
		}
		else if (lt.equals("ъ")) {
			letter = lettersI[28];
		}
		else if (lt.equals("ы")) {
			letter = lettersI[29];
		}
		else if (lt.equals("ь")) {
			letter = lettersI[30];
		}
		else if (lt.equals("э") || (lt.equals("Э"))) {
			letter = lettersI[31];
		}
		else if (lt.equals("ю")) {
			letter = lettersI[32];
		}
		else if (lt.equals("я") || lt.equals("Я")) {
			letter = lettersI[33];
		}
		else if (lt.equals(" ")) {
			letter = lettersI[34];
		}
		else if (lt.equals(".")) {
			letter = lettersI[35];
		}
		else if (lt.equals("!")) {
			letter = lettersI[36];
		}
		else if (lt.equals(",")) {
			letter = lettersI[37];
		}
		else if (lt.equals("?")) {
			letter = lettersI[38];
		}
		else if (lt.equals(":")) {
			letter = lettersI[39];
		}
		else if (lt.equals("/")) {
			letter = lettersI[40];
		}
		else if (lt.equals("-")) {
			letter = lettersI[41];
		}
		else if (lt.equals("0")) {
			letter = lettersI[42];
		}
		else if (lt.equals("1")) {
			letter = lettersI[43];
		}
		else if (lt.equals("2")) {
			letter = lettersI[44];
		}
		else if (lt.equals("3")) {
			letter = lettersI[45];
		}
		else if (lt.equals("4")) {
			letter = lettersI[46];
		}
		else if (lt.equals("5")) {
			letter = lettersI[47];
		}
		else if (lt.equals("6")) {
			letter = lettersI[48];
		}
		else if (lt.equals("7")) {
			letter = lettersI[49];
		}
		else if (lt.equals("8")) {
			letter = lettersI[50];
		}
		else if (lt.equals("9")) {
			letter = lettersI[51];
		}
		return letter;
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
			length = name.length();
			int x = 0, y = 0;
			for (int i = 0; i <= length - 1; i++) {
				char l = name.charAt(i);
				g2d.drawImage(letters(l), x, y, null);
				if (l == ' ') {
					//Меньше размер пробелов
					x -= 10;
				}
				x += 24;
			}
		}
		public void paintBorder(Graphics g) {
		}
	}
	
	private class NameNPC extends JLabel {
		//Имя NPC
		String nameNPC;
		
		public NameNPC(String name) {
			nameNPC = name;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			int length = nameNPC.length();
			int x = 0; y = 0;
			for (int i = 0; i <= length - 1; i++) {
				char l = nameNPC.charAt(i);
				g2d.drawImage(letters(l), x, y, null);
				x += 24;
			}
		}
	}

	//Label с текстом квеста
	private class QwestTextLabel extends JLabel{
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			if (status == 1) {
				length = textN.length();
			}
			if (status == 3) {
				length = textK.length();
			}
			int x = 5; y = 0;
			/*g2d.setFont(new Font("Times new Roman", Font.BOLD, 20));
			g2d.drawString(text, 10, 30);*/
			char l = 0;
			for (int i = 0; i <= length - 1; i++) {
				if (status == 1) {
					l = textN.charAt(i);
				}
				if (status == 3) {
					l = textK.charAt(i);
				}
				
				int y2 = 0;
				boolean prob = false;
				if (l == ',') {
					//Немного опустить запятую
					y2 = y + 3;
				} else if (l == 'щ') {
					y2 = y + 1;
				} else if (l == ' ') {
					prob = true;
				} else {
					y2 = y;
				}
				g2d.drawImage(letters(l), x, y2, null);
				
				if (prob == true) {
					x -= 4;
					prob = false;
				}
				
				//Добавляет переносы строки
				int xx = x;
				if (l == ' ') {
					int c = i + 1; //устанавливает с какого символа пробегать (слово)
					char l2 = 0;
					try {
						while (l2 != ' ') {
							if (status == 1) {
								l2 = textN.charAt(c);
							}
							if (status == 3) {
								l2 = textK.charAt(c);
							}
							xx += 21; //21
							c++;
							if (xx > 410) {
								y += 21; //21
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
	
	//Label в который встроено описание и требование квеста
	private class FRequestLabel extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			((Graphics2D) g).setStroke(new BasicStroke(7));
			g2d.drawLine(8, 2, 427, 2);
			g2d.drawLine(8, 65, 425, 65); //455
		}
	}
	
	//Label с требованием квеста
	private class RequestLabel extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			
			int lengthR = reques.length();
			int x = 5, y = 0;
			for (int i = 0; i <= lengthR - 1; i++) {
				char l = reques.charAt(i);
				
				int y2 = 0;
				if (l == 'щ') {
					y2 = y + 1;
				} else {
					y2 = y;
				}
						
				g2d.drawImage(letters(l), x, y2, null);
				
				int xx = x;
				//Далее проверяется побуквенно, влезает ли слово в строку, если нет - перенос на новую
				if (l == ' ') {
					int c = i + 1; //устанавливает с какого символа пробегать (слово)
					char l2 = 0;
					try {
						while (l2 != ' ') {
							l2 = reques.charAt(c);
							xx += 21;
							c++;
							if (xx > 250) {
								y += 30;
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
