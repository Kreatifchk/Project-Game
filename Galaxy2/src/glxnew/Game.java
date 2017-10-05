package glxnew;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, Runnable, MouseListener {
	
	static JLayeredPane jlp = new JLayeredPane();
	
	static boolean paused;
	
	static boolean xs;
	
	//513 полей всего
	
	/*Сделать разные специализации стран: коррупционная (деньги идут в столицу),
	 * военная (деньги идут в спорные регионы) и.т.д
	 */
	
	//int xI, yI;
	
	/*final int MAX_X = 1000; //+ 200
	final int MAX_Y = 550; // + 100
	final int MIN_POINT = 20;*/
	
	static Sector[][] pole = new Sector[19][27];
	static ArrayList<Empery> emp = new ArrayList<Empery>();
	
	JButton pauseB;
	
	Timer t = new Timer(20, this);
	static Random r = new Random();
	
	Thread gameLoop = new Thread(this);
	
	public Game() {
		super("Galaxy");
		
		setLayout(null);
		
		int width = pole[0].length * 30;
		jlp.setBounds(0, 0, width + 16, 640);
		add(jlp);
		setSize(width + 16, 640);
		
		pauseB = new JButton();
		pauseB.setBounds(60, 570, 40, 40);
		pauseB.addMouseListener(this);
		jlp.add(pauseB, new Integer(0));
		
		preGen();
		gen(); //Генерация первых империй
		
		t.start();
		gameLoop.start();
	}
	
	private void preGen() {
		int x = 0, y = 0;
		for (int i = 0; i < pole.length; i++) {
			for (int j = 0; j < pole[0].length; j++) {
				pole[i][j] = new Sector();
				pole[i][j].setBackground(Color.white);
				pole[i][j].setOpaque(true);
				pole[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				pole[i][j].setBounds(x, y, 30, 30);
				pole[i][j].addMouseListener(this);
				jlp.add(pole[i][j], new Integer(1));
				x += 30;
			}
			x = 0;
			y += 30;
		}
	}
	
	private void gen() {
		int countEmp = r.nextInt(7) + 5;
		for (int i = 0; i < countEmp; i++) {
			emp.add(new Empery(emp.size(), createColor()));
			while (true) {
				int x = r.nextInt(pole[0].length-1), y = r.nextInt(pole.length-1);
				if (pole[y][x].busy == false) {
					pole[y][x].setBackground(emp.get(i).cl);
					pole[y][x].owner = i;
					pole[y][x].busy = true;
					pole[y][x].capital = true;
					emp.get(i).capital = new Point(y, x);
					emp.get(i).money = 1000;
					emp.get(i).id = i;
					emp.get(i).count++;
					break;
				}
			}
		}
	}
	
	public void run() {
		//Главный цикл игры
		Sleep.sleep(500);
		while(true) {
			//System.out.println("size: " + emp.size());
			for (int i = 0; i < emp.size(); i++) {
				if (paused != true) {
					new Internal(i); //Внутренние дела (технологии, распределение бюджета)
					new Problem(i); //Коррупция, восстания и прочие проблемы
					new War(i); //Война
					new Clear(i); //Очистка империй, потерявших все клетки
				}
			}
			Sleep.sleep(50);//180
		}
	}
	
	protected static Color createColor() {
		return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent a) {
		for (int i = 0; i < pole.length; i++) {
			for (int j = 0; j < pole[0].length; j++) {
				if (a.getSource() == pole[i][j]) {
					System.out.println("Деньги клетки: " + pole[i][j].money);
					System.out.println("Деньги империи: " + emp.get(pole[i][j].owner).money);
					System.out.println("Армия клетки: " + pole[i][j].army);
					System.out.println("Коррупция: " + emp.get(pole[i][j].owner).corruption);
					System.out.println("id: " + emp.get(pole[i][j].owner).id);
					System.out.println("im: " + emp.get(pole[i][j].owner).immort);
				}
			}
		}
		
		if (a.getSource() == pauseB) {
			if (paused == true) {
				paused = false;
			} else
				paused = true;
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
