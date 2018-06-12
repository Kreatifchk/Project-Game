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
	 * Заблокировать движения окна пока открыто GUI (+)
	 * Для атаки - учитывать развитие технологий, типы юнитов
	 * Сделать чтоб игроки не генерировались рядом
	 * Сделать чтоб оставшиеся войска после атаки переходили в новую локацию
	 * Уменьшать очки действий
	 * Запретить атаковать когда армия - 0
	 * Исправить проблему с заменой цвета конкурентов (при атаке)
	 * Переименовать info (boolean) в GuiOpened и использовать для проверки открытости любого GUI (+)
	 * Если не хватает очков действий не давать ничего сделать
	 */
	
	static PointMap[][] pm;
	static PointInfo pInf; //Панель с информацией о клетке
	static Head head; //Верхняя панель
	static int sizePoint;
	
	static JLayeredPane mainPane = new JLayeredPane();
	static boolean GUIOpened; //Открыта ли панель информации о клетке, или атака на клетку
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
		//Генерация врагов
		int ct = r.nextInt(14) + 5;
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
	
	/** Возвращение своих цветов клеткам окрашеным для выделения диапазона нападения */
	protected static void clearColor() {
		for (int j = 0; j < GameFrame.pm[0].length; j++) {
			for (int i = 0; i < GameFrame.pm.length; i++) {
				if (!GameFrame.pm[i][j].getBackground().equals(GameFrame.pm[i][j].mainColor)) {
					GameFrame.pm[i][j].setBackground(GameFrame.pm[i][j].mainColor);
				}
			}
		}
	}
	
	/** Закрывает любое GUI открытое по центру экрана */
	private void closeGUI(MouseEvent a) {
		if (GUIOpened == true) {
			for (int i = 3; i >= 2; i--) {
				for (int j = 0; j < mainPane.getComponentCountInLayer(i); j++) {
					if (!(a.getComponent() instanceof GUICenter)) {
						mainPane.remove(mainPane.getComponentsInLayer(i)[j]);
						GUIOpened = false;
					}
				}
			}
		} else {
			GUIOpened = true;
		}
	}
	
	//MouseMovedListener - движение поля мышью
	int startX, startY;
	@Override
	public void mouseDragged(MouseEvent a) {
		if (GUIOpened != true) {
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
	}
	@Override
	public void mouseMoved(MouseEvent a) {
	}
	//MouseListener
	@Override
	public void mouseClicked(MouseEvent a) {
		PointMap pm = (PointMap) mainPane.getComponentAt(a.getX(), a.getY());
		//Клик по клетке
		if (attack != true) {
			selectPoint = pm; //
			//Если не атакуешь
			if (GUIOpened == false) {
				pInf = new PointInfo(pm);
				mainPane.add(pInf, new Integer(2));
			}
		} else {
			//Если нажата атака - то есть идет выбор клетки для нападения
			if (GUIOpened == false) {
				pm = (PointMap) mainPane.getComponentAt(a.getX(), a.getY()); //На какую клетку нажали
				AttackGUI agui = new AttackGUI(pm.xMap, pm.yMap, selectPoint.xMap, selectPoint.yMap);
				//Нажата кнопка атака и выбрана клетка для атаки, идет проверка что нажали куда следует
				if (((pm.xMap == selectPoint.xMap + 1 || pm.xMap == selectPoint.xMap - 1) & pm.yMap == selectPoint.yMap) ||
						((pm.yMap == selectPoint.yMap + 1 || pm.yMap == selectPoint.yMap - 1) & pm.xMap == selectPoint.xMap)) {
					//После открывается GUI длля атаки
					mainPane.add(agui, new Integer(3));
					clearColor();
				} else { return; }
			} else {
				attack = false;
			}
		}
		
		closeGUI(a);
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
