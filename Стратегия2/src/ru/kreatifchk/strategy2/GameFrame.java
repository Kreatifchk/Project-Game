package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
	
	/* Комментарии по разработке
	 * 1 - уменьшить чувствительность, чтоб лучше реагировало на нажатия
	 * Заблокировать движения окна пока открыто GUI
	 * Для атаки - учитывать развитие технологий, типы юнитов
	 * Сделать чтоб игроки не генерировались рядом
	 * Сделать чтоб оставшиеся войска после атаки переходили в новую локацию
	 * Уменьшать очки действий
	 * Запретить атаковать когда армия - 0
	 * Исправить проблему с заменой цвета конкурентов (при атаке)
	 */
	
	static PointMap[][] pm;
	PointInfo pInf; //Панель с информацией о клетке
	static Head head; //Верхняя панель
	static int sizePoint;
	
	static JLayeredPane mainPane = new JLayeredPane();
	JLabel back = new JLabel();
	static boolean info; //Открыта ли панель информации о клетке, или атака на клетку
	static boolean attack; //Включен ли режим атаки
	static PointMap selectPoint; //Выбранная клетка
	
	private Timer update = new Timer(20, this);
	Random r = new Random();
	
	static Player pl;
	static ArrayList<Entity> entity = new ArrayList<Entity>();
	
	static int turn = 1; //Номер хода
	static int idPlayer;
	
	public GameFrame(int x, int y) {
		super("Стратегия");
		genPole(x, y);
		init();
		
		update.start();
	}
	
	JLabel num = new JLabel();
	
	private void genPole(int x, int y) {
		setLayout(null);
		mainPane.setBounds(0, (int) (Main.height / 100 * 5), Main.windWidth, Main.windHeight);
		mainPane.addMouseMotionListener(this);
		mainPane.addMouseListener(this);
		add(mainPane);
		
		head = new Head();
		head.setBounds(0, 0, Main.windWidth-5, (int) (Main.height / 100 * 5));
		add(head);
		
		pm = new PointMap[x][y];
		sizePoint = (int)(Main.windWidth / 100 * 7); //Размер клетки
		
		x = 0;
		y = 0;
		int n = 0;
		for (int j = 0; j < pm[0].length; j++) {
			for (int i = 0; i < pm.length; i++) {
				pm[i][j] = new PointMap(n, i, j);
				pm[i][j].setBounds(x, y, sizePoint, sizePoint);
				pm[i][j].setOpaque(true);
				pm[i][j].setBackground(Color.lightGray);
				pm[i][j].mainColor = Color.LIGHT_GRAY;
				pm[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 2));
				mainPane.add(pm[i][j], new Integer(1));
				x += sizePoint;
				n++;
			}
			x = 0;
			y += sizePoint;
		}
		
		//Если клеток больше чем поле сдвигать их
		//Сдвиг по ширине
		if (pm.length * sizePoint > Main.windWidth) {
			double z = pm.length * sizePoint - Main.windWidth; //Сколько пикселей за экраном
			z = Math.round(z / 2);
			int s = (int) z;
			
			for (int j = 0; j < pm[0].length; j++) {
				for (int i = 0; i < pm.length; i++) {
					pm[i][j].setLocation(pm[i][j].getX() - s, pm[i][j].getY());
				}
			}
		}
		//Сдвиг по высоте
		if (pm[0].length * sizePoint > Main.windHeight) {
			double z = pm[0].length * sizePoint - Main.windHeight; //Сколько пикселей за экраном
			z = Math.round(z / 2);
			int s = (int) z;
			
			for (int j = 0; j < pm[0].length; j++) {
				for (int i = 0; i < pm.length; i++) {
					pm[i][j].setLocation(pm[i][j].getX(), pm[i][j].getY() - s);
				}
			}
		}
	}
	
	private void init() {
		//Предел врагов - 1000 , так как id игрока 1001
		//Генерация врагов
		int ct = r.nextInt(9) + 5;
		for (int i = 0; i < ct; i++) {
			entity.add(new Enemy(i));
		}
		
		//Генерация игрока
		idPlayer = entity.size();
		pl = new Player(idPlayer);
		entity.add(pl);
		pl.capital = pm[pm.length/2][pm[0].length/2];
		pm[pl.capital.xMap][pl.capital.yMap].setBackground(pl.cl);
		pm[pl.capital.xMap][pl.capital.yMap].mainColor = pl.cl;
		pm[pl.capital.xMap][pl.capital.yMap].busy = true;
		pm[pl.capital.xMap][pl.capital.yMap].owner = idPlayer;
		pm[pl.capital.xMap][pl.capital.yMap].capital = true;
	}
	
	protected static void clearColor() {
		//Возвращение своих цветов клеткам окрашеным для выделения диапазона нападения
		for (int j = 0; j < GameFrame.pm[0].length; j++) {
			for (int i = 0; i < GameFrame.pm.length; i++) {
				if (!GameFrame.pm[i][j].getBackground().equals(GameFrame.pm[i][j].mainColor)) {
					GameFrame.pm[i][j].setBackground(GameFrame.pm[i][j].mainColor);
				}
			}
		}
	}
	
	//MouseMovedListener - движение поля мышью
	int startX, startY;
	@Override
	public void mouseDragged(MouseEvent a) {
		if (a.getSource() == mainPane) {
			int incX = 0, incY = 0;
			if (a.getX() - startX > 1 & pm[0][0].getX() < 0) {
				//Поле вправо
				incX = 9;
			} else if (a.getX() - startX < -1
					& pm[pm.length-1][0].getX() > Main.windWidth - sizePoint) {
				//Поле влево
				incX = - 9;
			}
			
			if (a.getY() - startY > 1 & pm[0][0].getY() < 0) {
				//Поле вниз
				incY = 9;
			} else if (a.getY() - startY < -1
					& pm[0][pm[0].length-1].getY() > Main.windHeight-sizePoint-25) {
				//Поле вверх
				incY = -9;
			}
			
			for (int j = 0; j < pm[0].length; j++) {
				for (int i = 0; i < pm.length; i++) {
					pm[i][j].setLocation(pm[i][j].getX() + incX,
							pm[i][j].getY() + incY);
				}
			}
			
			startX = a.getX();
			startY = a.getY();
		}
	}
	@Override
	public void mouseMoved(MouseEvent a) {
	}
	//MouseListener
	@Override
	public void mouseClicked(MouseEvent a) {
		try {
			PointMap pm = (PointMap) mainPane.getComponentAt(a.getX(), a.getY());
			//Клик по клетке
			if (attack != true) {
				selectPoint = pm;
				//Если не атакуешь
				if (info == false) {
					pInf = new PointInfo(pm.xMap, pm.yMap);
					mainPane.add(pInf, new Integer(2));
					info = true;
				} else {
					mainPane.remove(pInf); //mainPane.getComponentsInLayer(2)[0]
					info = false;
					selectPoint = null;
					pInf = null;
				}
			} else {
				//Если нажата атака
				if (info == false) {
					pm = (PointMap) mainPane.getComponentAt(a.getX(), a.getY()); //На какую клетку нажали
					AttackGUI agui = new AttackGUI(pm.xMap, pm.yMap, selectPoint.xMap, selectPoint.yMap);
					//Нажата кнопка атака и выбрана клетка для атаки, идет проверкаЮ что нажали куда следует
					if (pm.xMap == selectPoint.xMap + 1 & pm.yMap == selectPoint.yMap) {
						//Rigth
						mainPane.add(agui, new Integer(3));
						clearColor();
						info = true;
					}
					else if (pm.xMap == selectPoint.xMap - 1 & pm.yMap == selectPoint.yMap) {
						//Left
						mainPane.add(agui, new Integer(3));
						clearColor();
						info = true;
					}
					else if (pm.yMap == selectPoint.yMap + 1 & pm.xMap == selectPoint.xMap) {
						//Down
						mainPane.add(agui, new Integer(3));
						clearColor();
						info = true;
					}
					else if (pm.yMap == selectPoint.yMap - 1 & pm.xMap == selectPoint.xMap) {
						//Up
						mainPane.add(agui, new Integer(3));
						clearColor();
						info = true;
					}
				} else {
					mainPane.remove(mainPane.getComponentsInLayer(3)[0]);
					info = false;
					attack = false;
				}
			}
		} catch (Exception e) {}
	}
	@Override
	public void mouseEntered(MouseEvent a) {
	}
	@Override
	public void mouseExited(MouseEvent a) {
	}
	@Override
	public void mousePressed(MouseEvent a) {
		startX = a.getX();
		startY = a.getY();
	}
	@Override
	public void mouseReleased(MouseEvent a) {
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		repaint();
	}
	
}
