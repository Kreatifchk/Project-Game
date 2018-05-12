package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Point;
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
	
	static PointMap[][] pm;
	PointInfo pInf; //Панель с информацией о клетке
	static Head head; //Верхняя панель
	static int sizePoint;
	
	static JLayeredPane mainPane = new JLayeredPane();
	JLabel back = new JLabel();
	static boolean info; //Открыта ли панель информации о клетке
	static boolean attack; //Включен ли режим атаки
	
	private Timer update = new Timer(20, this);
	Random r = new Random();
	
	static Player pl;
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	
	static int turn = 1; //Номер хода 
	
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
		sizePoint = (int)(Main.width / 100 * 7); //Размер клетки
		
		x = 0;
		y = 0;
		int n = 0;
		for (int j = 0; j < pm[0].length; j++) {
			for (int i = 0; i < pm.length; i++) {
				pm[i][j] = new PointMap(n, i, j);
				pm[i][j].setBounds(x, y, sizePoint, sizePoint);
				pm[i][j].setOpaque(true);
				pm[i][j].setBackground(Color.lightGray);
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
		//Генерация игрока
		pl = new Player();
		pl.capital = new Point(pm.length / 2, pm[0].length / 2);
		pm[pl.capital.x][pl.capital.y].setBackground(pl.cl);
		pm[pl.capital.x][pl.capital.y].busy = true;
		pm[pl.capital.x][pl.capital.y].owner = 1001;
		pm[pl.capital.x][pl.capital.y].capital = true;
		
		//Предел врагов - 1000 , так как id игрока 1001
		//Генерация врагов
		int ct = r.nextInt(9) + 5;
		for (int i = 0; i < ct; i++) {
			enemy.add(new Enemy(i));
		}
	}
	
	//MouseMovedListener
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
			pInf = new PointInfo(pm.xMap, pm.yMap);
			if (attack != true) {
				if (info == false) {
					mainPane.add(pInf, new Integer(2));
					info = true;
				} else {
					mainPane.remove(mainPane.getComponentsInLayer(2)[0]);
					info = false;
				}
			} else {
				
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
