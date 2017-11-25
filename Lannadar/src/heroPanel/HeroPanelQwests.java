package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollBar;

import base.Game;
import base.QwestScrollUI;
import initialize.InitFont;
import inventory.InventList;

//В панели персонажа вкладка с заданиями
@SuppressWarnings("serial")
public class HeroPanelQwests extends MouseAdapter implements ActionListener, AdjustmentListener {
	
	QwestsPanel qp;
	
	//Изображение панелей на которыз будут названия квестов
	Image frameQwestD = new ImageIcon(getClass().getResource("/base/res/others/frameQwest.png")).getImage();
	Image frameQwestA = new ImageIcon(getClass().getResource("/base/res/others/frameQwestA.png")).getImage();
	int frm = -1; //Номер квеста на который нажимают (для подсветки)
	
	String name;
	boolean pt; //Отрисовывать ли название квеста
	
	Font uph = InitFont.uph, uphMini = null;
	Font determ = InitFont.determ.deriveFont(24F);
	
	public HeroPanelQwests() {
		qp = new QwestsPanel();
		uphMini = uph.deriveFont(20F);
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
			jsb.setUI(new QwestScrollUI());
			jsb.setMinimum(0);
			jsb.setMaximum(610);
			jsb.addAdjustmentListener(HeroPanelQwests.this);
			
			jsb2.setBounds(328, 0, 20, 86);
			jsb2.setUI(new QwestScrollUI());
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
		for (int i = 0; i < Game.takeQwests.length; i++) {
			if (Game.takeQwests[i] != -1) {
				qwestsM[st] = new QwestButton(Game.qwest[Game.takeQwests[i]].name);
				qwestsM[st].text = Game.qwest[Game.takeQwests[i]].textN;
				qwestsM[st].request = Game.qwest[Game.takeQwests[i]].request;
				try {
					qwestsM[st].nameMonster = Game.qwest[Game.takeQwests[i]].nameMonster;
					qwestsM[st].idItem = Game.qwest[Game.takeQwests[i]].idItem;
					
					qwestsM[st].progress = Game.qwest[Game.takeQwests[i]].progress;
					qwestsM[st].count = Game.qwest[Game.takeQwests[i]].count;
				} catch (NullPointerException e) {
				}
				
				qwestsM[st].numb = i;
				name = Game.qwest[Game.takeQwests[i]].name; //Название квеста
				qwestsM[st].setBounds(x, y, 283, 45); //270, 30
				qwestsM[st].addActionListener(this);
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
						
				textRequest = new RequestQwest(qwestsM[i].request);
				textRequest.nameMonster = qwestsM[i].nameMonster;
				textRequest.idItem = qwestsM[i].idItem;
				textRequest.progress = qwestsM[i].progress;
				textRequest.count = qwestsM[i].count;
				textRequest.setBounds(0, 0, 330, 400);
				reqBase.add(textRequest);
				reqBase.add(jsb2);
			}
		}
	}
	
	//Выводит текст - описание задания
	private class TextQwest extends JLabel {
		String text;
		public TextQwest(String text) {
			this.text = text;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			int x = 5, y = 25;
			g2d.setFont(determ);
			String[] sub = text.split(" "); //Разделение строки на слова
			for (int i = 0; i < sub.length; i++) {
				int bound = (int) determ.getStringBounds(sub[i],
						new FontRenderContext(null, true, true)).getWidth();
				if (x + bound >= 330) {
					x = 5;
					y += 35;
				}
				g2d.drawString(sub[i], x, y);
				x += bound + 18;
			}
		}
	}
	
	//Выводит текст - требование задания
	private class RequestQwest extends JLabel {
		String request;
		int progress[], count[], idItem[];
		String nameMonster[];
		public RequestQwest(String request) {
			this.request = request;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			int x = 5, y = 25;
			g2d.setFont(determ);
			String[] sub = request.split(" "); //Разделение строки на слова
			for (int i = 0; i < sub.length; i++) {
				int bound = (int) determ.getStringBounds(sub[i],
						new FontRenderContext(null, true, true)).getWidth();
				if (x + bound >= 330) {
					x = 5;
					y += 35;
				}
				g2d.drawString(sub[i], x, y);
				x += bound + 18;
			}
			
			y += 35;
			if (nameMonster != null) {
				g2d.drawString("Убито: ", 5, y);
				y += 35;
				for (int i = 0; i < nameMonster.length; i++) {
					String prog = nameMonster[i] + "  " + progress[i] + " / " + count[i];
					g2d.drawString(prog, 5, y);
					y += 35;
				}
			}
			if (idItem != null) {
				g2d.drawString("Собрано: ", 5, y);
				y += 35;
				for (int i = 0; i < idItem.length; i++) {
					String prog = InventList.inventory.get(idItem[i]).name + "  " + progress[i] + " / " + count[i];
					g2d.drawString(prog, 5, y);
					y += 35;
				}
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
		String nameMonster[];
		int idItem[];
		int progress[], count[];
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
