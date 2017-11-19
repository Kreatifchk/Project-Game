package heroPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import base.Animation;
import base.Player;
import initialize.InitFont;
import inventory.InitImage;
import inventory.InventList;
import inventory.Inventory;

@SuppressWarnings("serial")
public class HeroPanelBag extends JLabel implements MouseMotionListener, MouseListener {
	
	Inventory[] invent = new Inventory[10];
	
	int selX = -1, selY = -1; //Номера выделенной клетки
	int point = -1; //выбранная йчейка (для переноса предметов)
	int startPoint = -1; //Выбранная клетка (для выбрасывания)
	boolean press; //Нажата ли кнопка мыши (перемещение только когда true)
	int objX, objY; //Координаты пермещения предмета
	int oldPoint = -1; //Старая клетка предмета
	int oldPoint2 = -1; //Старая клетка (для всплывающей подсказки)
	boolean move; //Для всплывающих подсказок
	boolean eject; //Выбросить предмет
	boolean taken; //Взят ли предмет (чтоб выбросить)
	
	public HeroPanelBag() {
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	//Получение предмета
	public static boolean take(int id, int stack) {
		int point = -1; //Доступная клетка
		boolean succes = false;
		for (int i = 0; i < Player.bagPlayer.length; i++) {
			if (Player.bagPlayer[i].idInv == -1
					& Player.bagPlayer[i].access == true) {
				point = i;
				break;
			}
		}
		//Если нашло свободнуюю ячейку
		if (point != -1) {
			Player.bagPlayer[point].idInv = id;
			Player.bagPlayer[point].stack = stack;
			succes = true;
		} else {
			//Если инвертарь заполнен
			succes = false;
		}
		return succes;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.drawImage(InitImage.money, 80, 9, null);
		g2d.setFont(InitFont.smw.deriveFont(28F));
		g2d.drawString("- " + Player.money, 150, 43);
		//Вертикальные линии рамки
		g2d.drawLine(50, 74, 50, 446);//0 to 74
		g2d.drawLine(579, 74, 579, 446);
		//Горизонтальные линии рамки
		g2d.drawLine(0, 73, 634, 73);//31
		//g2d.drawLine(0, 410, 634, 410);
		g2d.setColor(Color.ORANGE);
		g2d.setStroke(new BasicStroke(4));
		//Вертикальные линии
		int x = 95, y = 78;//36
		for (int i = 0; i < 11; i++) {
			g2d.drawLine(x, y, x, y+370);
			x += 44;
		}
		//Горизонтальные линии
		x = 55;
		y = 116;//74
		for (int i = 0; i < 8; i++) {
			g2d.drawLine(x, y, x+44*12-8, y);
			y += 42;
		}
		
		//Заполняет серым, недоступные ячейки инвертаря
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(3));
		x = 53;
		y = 76;//34
		for (int i = 0; i < 108; i++) {
			if (Player.bagPlayer[i].access == false) {
				g2d.fillRect(x, y, 40, 38); //Если ячейка еще не доступна, заполняем серым
			}
			x+=44;
			if (i > 0 & (i+1) % 12 == 0) {
				y += 42;
				x = 53;
			}
		}
		//Заполняет клетки предмета и отображает перемещение предметов
		x = 53;
		y = 76;//34
		for (int i = 0; i < 108; i++) {
			//Отображение перемещения предмета
			if (i == oldPoint & press == true & (objX != 0 & objY !=0)) {
				int id = Player.bagPlayer[i].idInv;
				g2d.drawImage(InventList.inventory.get(id).icon, objX, objY, null);
			}
			
			//Отображение предмета в клетке
			if (Player.bagPlayer[i].idInv != -1 & i != oldPoint){
				int id = Player.bagPlayer[i].idInv;//Ид предмета,лежашего в инвертаре
				g2d.drawImage(InventList.inventory.get(id).icon, x, y, null);
			}
			
			x+=44;
			if (i > 0 & (i+1) % 12 == 0) {
				y += 42;
				x = 53;
			}
		}
		
		//Подсвечивает выделенную ячейку
		if (selX != -1 & selY != -1) {
			g2d.setColor(new Color(50, 34, 22, 56));
			g2d.fillRect(53+44*selX, 76+42*selY, 40, 38);//34 to 76
		}
	}

	@Override
	public void mouseDragged(MouseEvent a) {
		if (press == true & a.getModifiers() == InputEvent.BUTTON1_MASK) {
			//Если зажата правая кнопка мыши
			if (a.getX() >= 55 & a.getX() < 578
					& a.getY() >= 76) {
				//a.getY() <= 406
				selX = (a.getX() - 7) / 44 - 1;
				selY = (a.getY() - 76) / 42;//34 to 76
				point = 11*selY+selX+selY;
			} else {
				//Выбросить предмет
				eject = true;
			}
			objX = a.getX() - 15;
			objY = a.getY() - 15;
		}
	}
	
	boolean sW; //Не даст SwingWorker заработать больше одного раза
	
	@Override
	public void mouseMoved(MouseEvent a) {
		//Собирает координаты ячейки на которую указывает мышь и ее номер
		if (a.getX() >= 55 & a.getX() < 578
				& a.getY() >= 76) {
			//a.getY() <= 406
			selX = (a.getX() - 7) / 44 - 1;
			selY = (a.getY() - 76) / 42;//34 to 76
			point = 11*selY+selX+selY;
			//Если увели курсор с ячейки
			if (point != oldPoint2) {
				move = false;
				oldPoint2 = point;
				//Остановить счетчик
			} else {
				move = true; //Запустить счетчик до всплывающей подсказки
			}
		} else {
			selX = -1;
			selY = -1;
		}
		
		//Всплывающая подсказка
		if (sW == false & move == true) {
			new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() throws Exception {
					sW = true;
					boolean z = true;
					for (int i = 0; i <= 25; i++) {
						if (move == true) {
							Animation.sleep(25);
						} else {
							z = false;
							break;
						}
					}
					
					if (z == true) {
						if (Player.bagPlayer[point].idInv != -1) {
							String name =  InventList.inventory.get(Player.bagPlayer[point].idInv).name;
							Tip inf = new Tip(name);
							add(inf);
							int width = (int) inf.getFont().getStringBounds(name, new FontRenderContext(null, true, true)).getWidth();
							inf.setBounds((selX+1)*44+47, selY*42+44, width + 8, 18);
							while(move == true) {
								Animation.sleep(1);
							}
							remove(inf);
						}
					}
					sW = false;
					return null;
				}
			}.execute();
		}
	}

	@Override
	public void mouseClicked(MouseEvent a) {
	}
	@Override
	public void mouseEntered(MouseEvent a) {	
	}
	@Override
	public void mouseExited(MouseEvent a) {
		sW = false;
		move = false;
		oldPoint2 = -1;
	}
	@Override
	public void mousePressed(MouseEvent a) {
		try {
			if (Player.bagPlayer[point].idInv != -1
					& a.getModifiers() == InputEvent.BUTTON1_MASK) {
				//Если щелчек по не пустой ячейке (для пермещения предметов)
				press = true;
				oldPoint = point;
				taken = true;
				startPoint = point;
			}
		} catch (Exception e) {
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		press = false;
		try {
			if (oldPoint != point & Player.bagPlayer[point].access == true) {
				int idInv = Player.bagPlayer[oldPoint].idInv; //id перемещаемого предмета
				if (Player.bagPlayer[point].idInv == -1) {
					//Если ячейка в которую перемещают - пустая
					Player.bagPlayer[oldPoint].idInv = -1;
					Player.bagPlayer[point].idInv = idInv;
				} else {
					//Если занята
					int idZam = Player.bagPlayer[point].idInv; //Предмет из новой ячейки
					Player.bagPlayer[oldPoint].idInv = idZam;
					Player.bagPlayer[point].idInv = idInv;
				}
				oldPoint = -1;
			} else {
				oldPoint = -1;
			}
		} catch (Exception e) {
		}
		
		//Выбрасывание предмета
		if (eject == true & taken == true) {
			if (JOptionPane.showConfirmDialog(this, "Выбросить?", "Потверждение", 0) == 0) {
				Player.bagPlayer[startPoint].idInv = -1;
			}
			eject = false;
		}
		taken = false;
		
		objX = 0;
		objY = 0;
		point = -1;
		startPoint = -1;
	}
	
}
