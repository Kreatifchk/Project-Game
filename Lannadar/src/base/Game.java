package base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.Border;

import heroPanel.HeroPanel;


/**
 * Класс с игровым окном
 */
@SuppressWarnings({ "unused", "serial" })
public class Game extends JFrame implements Runnable {
	
	//git
	
	Image hpI = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	Image icon = new ImageIcon(getClass().getResource("res/Image/icon.png")).getImage();
	ImageIcon inf = new ImageIcon(getClass().getResource("res/inf.png"));
	static ImageIcon inf2 = new ImageIcon(Main.class.getResource("res/inf2.png"));
	ImageIcon end = new ImageIcon(getClass().getResource("res/end.png"));
	static ImageIcon qwI, qwSI;
	static Image player;
	static ImageIcon portalI;
	
	static paint p; //Панель с персонажем
	HpPaint hpp; //Панель с хп игрока
	MpPanel mpP = new MpPanel(); //Панель с маной игрока
	ExpPanel expP = new ExpPanel(); //Панель с опытом игрока
	LevelPanel lP = new LevelPanel(); //Панель с уровнем игрока
	HpMobs hpM = new HpMobs(); //Панель с хп монстров
	LevelMobs lmP = new LevelMobs(); //Панель с уровнем моба
	
	public static Player pl;
	Massiv ms = new Massiv(); //Заполняет тайлы
	TimerListener tl = new TimerListener();
	Timer t = new Timer(40, tl);
	
	Thread movePlayer = new Thread(this);
	Thread recovery = new Thread(new Recovery()); //Восстанавливает хп
	Thread animation = new Thread(new Animat());
	//Thread loadingThread;
	
	static boolean move; //Проверяет, нажал ли игрок кнопку движения
	static boolean hpMB; //Если игрок в бою добавляет панели моба
	static boolean stop; //Не дает двигаться бесконечно при нажатии на кнопку
	static boolean informB = false; //Надо ли закрыть окно с информацией
	
	static int currentLocation = 1; 
	static int oldLocation = 0;
	static int direction; //Направление движения
	static int hpMax, hpThis, hpPoint;
	/*
	 * hpMax - максимально возможное hp
	 * hpThis - hp в данный момент
	 * hpPoint - сколько hp на каждый процент полосы
	 */
	final static int TILE = 48; //Размер тайла
	
	static int[][] map = new int[15][12]; //Массив с данными уровня из файла
	static Tiles[][] mapx = new Tiles[15][12]; //Массив непосредственнно самих тайлов
	static Portals[] portal = new Portals[10]; //Массив хранящий данные о порталах
	static ArrayList<Monsters> monster = new ArrayList<Monsters>();
	static NPC[] npc = new NPC[8]; //Массив с NPC
	public static Qwest[] qwest = new Qwest[11]; //Все квесты
	
	public static int[] takeQwests = new int[10]; //Взятые квесты (номера/id)
	
	//Изменить здесь, в Dead.java и LocationFile.java
	static InputStream f2;
	//static File f2 = new File("res/levels/" + currentLocation + ".txt");
	//static File playerCont = new File("res/Player.txt"); Не используется
	//InputStream portalFile;
	
	JLabel upPanel = new JLabel();
	JLabel downPanel = new JLabel();
	static JLabel inform; //Информация в начале игры
	
	static PersButton menuB = new PersButton(); //Кнопка, открывает меню персонажа
	static HeroPanel pan = new HeroPanel();//Непосредственно меню персонажа
	static QwestGivePanel qGP;
	
	public Game() {
	}
	
	public Game(boolean m, boolean cont) {
		super("Lannadar");
		if (m != true) {
			if (cont == true) {
				//Если пользователь нажал продолжить
				continued();
			} else {
				pl = new Player();
				
				hpMax = pl.durability*2;
				hpThis = hpMax;
				hpPoint = hpMax / 100;
				
				activeQwests();
				QwestList qwl = new QwestList();
				
				/*inform = new JLabel();
				inform.setBounds(-5, 48, inf.getIconWidth(), inf.getIconHeight());
				inform.setIcon(inf);
				add(inform);
				inform.addMouseListener(new NpcListener());*/
				//Информация в начале игры
			}
			
			init();
			
			//Далее идет установка компонентов
			
			setLayout(null);
			
			Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
			Border borderGray = BorderFactory.createLineBorder(Color.GRAY, 2);
			Color BROWN = new Color(185, 122, 87);
			upPanel.setBounds(0, 0, 721, 51);
			upPanel.setBorder(border);
			upPanel.setOpaque(true);
			upPanel.setBackground(BROWN);
			
			downPanel.setBounds(0, 627, 721, 48);
			downPanel.setBorder(border);
			downPanel.setOpaque(true);
			downPanel.setBackground(BROWN);
			
			menuB.setBounds(604, 2, 115, 44);
			menuB.addMouseListener(new NpcListener());
			
			//hpp.setBounds(10, 9, 104, 30);
			//mpP.setBounds(120, 9, 104, 30);
			//expP.setBounds(230, 9, 104, 30);
			//lP.setBounds(340, 9, 30, 30);
			hpp.setBounds(10, 2, 104, 16);//22
			hpp.setBorder(border);
			mpP.setBounds(10, 18, 104, 16);//10, 24, 104, 22
			mpP.setBorder(border);
			expP.setBounds(10, 34, 104, 15);//120, 24, 104, 22
			expP.setBorder(border);
			lP.setBounds(228, 9, 30, 30);
			
			hpM.setBounds(570, 18, 104, 16);
			hpM.setBorder(border);
			hpM.setVisible(hpMB);
			lmP.setBounds(680, 9, 30, 30);
			
			p.setBounds(0, 0, 726, 704);
			
			add(upPanel);
			add(downPanel);
			upPanel.add(hpp);
			upPanel.add(mpP);
			upPanel.add(expP);
			upPanel.add(lP);
			upPanel.add(hpM);
			upPanel.add(lmP);
			downPanel.add(menuB);
			add(p);
			p.setLayout(null);
			addMonster();
			addNPC();
			
			addTile(); //Добавляет тайлы на карту
			p.setFocusable(true);
			addMouseListener(new NpcListener());
			//Music.start("Refl1.mp3", Settings.volume);
		}
	}

	//Инициализация
	private void init() {
		setIconImage(icon);
		//Стартовые установки
		p = new paint();
		hpp = new HpPaint();
		p.addKeyListener(new Listener());
		
		f2 = Game.class.getResourceAsStream("res/levels/" + currentLocation + ".txt");
		
		LocationFile.openFile(null);
		LocationFile.readFile(); //Читает файл с локациями
		
		//Инициализирует изображения букв
		QwestGivePanel qInit = new QwestGivePanel();
		qInit.init();
		qInit = null;
		
		imageInit();
		MonsterList mnl = new MonsterList(); //Массив с мнострами
		NPCList npl = new NPCList(); //Массив с NPC
		ms.massiv(); //Располагает тайлы на фрейме
		Portals.portals();
		Portals.addPortal();
		
		player = Player.playerD[0];
		movePlayer.start();
		Move.battle.start();
		animation.start();
		t.start();
		recovery.start();
	}
	
	//Считывает данные игрока при входе через продолжить
	private void continued() {
		//Считывает данные игрока при входе через продолжить
		try (ObjectInputStream ds = new ObjectInputStream
				(new FileInputStream("res/save/save1.lnd"))) {
			pl = (Player) ds.readObject();
			pl.init();
			//player = Player.playerDown;
			player = Player.playerD[0];
			currentLocation = pl.currentLocation;
			
			for (int i = 0; i <= qwest.length - 1; i++) {
				qwest[i] = (Qwest) ds.readObject();
			}
			for (int i = 0; i <= takeQwests.length - 1; i++) {
				takeQwests[i] = (int) ds.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не удалось загрузить игру");
		}
	}

	private void imageInit() {
		for (int i = 0; i <= 9; i++) {
			qwI = new ImageIcon(getClass().getResource("res/qw.png"));
			qwSI = new ImageIcon(getClass().getResource("res/qs.png"));
		}
		portalI = new ImageIcon(getClass().getResource("res/Image/Tiles/portal.png"));;
	}
	
	//Делает массив взятых квестов пустым
	private void activeQwests() {
		for (int i = 0; i <= takeQwests.length - 1; i++) {
			takeQwests[i] = -1;
		}
	}
	
	public class paint extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(player, pl.x, pl.y, null);
		}
	}
	
	//Панель с хп игрока
	private class HpPaint extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			int x = 2;
			int hpPointThis = hpThis / hpPoint;
			for (int i = 1; i <= hpPointThis; i++) {
				g2d.drawImage(hpI, x, 1, null);
				x++;
			}
			Font smw = Initialize.smw.deriveFont(19F);
			g2d.setFont(smw);
			g2d.setColor(Color.black);
			int pixW = (int) smw.getStringBounds("" + hpThis, new FontRenderContext(null, true, true)).getWidth();
			g2d.drawString(hpThis + "", (getWidth() - pixW) / 2, 15);
		}
	}
	
	// Добавляет на карту монстров
	public void addMonster() {
		boolean exit = false; //Если монстры с текущей лок. кончились, закончить цикл
		for (int i = 0; i <= monster.size()-1; i++) {
			if (monster.get(i).location == currentLocation) {
				monster.get(i).setBounds(monster.get(i).x*Game.TILE, monster.get(i).y*Game.TILE+48, Game.TILE, Game.TILE);
				monster.get(i).setIcon(new ImageIcon(getClass().getResource("res/Image/monsters/" + monster.get(i).icon + ".png")));
				mapx[monster.get(i).x][monster.get(i).y].busy = true;
				add(monster.get(i));
			} else {
				if (exit == true) {
					exit = false;
					break;
				}
			}
		}
	}
	
	public void deleteMonster() {
		boolean exit = false; //Если монстры с текущей лок. кончились, закончить цикл
		for (int i = 0; i <= monster.size()-1; i++) {
			if (monster.get(i).location == oldLocation) {;
				remove(monster.get(i));
			} else {
				if (exit == true) {
					exit = false;
					break;
				}
			}
		}
	}
	
	// Добавляет на карту NPC
	protected void addNPC() {
		boolean exit = false; //Если npc с текущей лок. кончились, закончить цикл
		for (int i = 0; i < npc.length; i++) {
			if (npc[i].location == currentLocation) {
				npc[i].setBounds(npc[i].x*Game.TILE, npc[i].y*Game.TILE+48, Game.TILE, Game.TILE);
				npc[i].setIcon(new ImageIcon(getClass().getResource("res/Image/NPC/" + npc[i].icon + ".png")));
				mapx[npc[i].x][npc[i].y].busy = true;
				add(npc[i]);
				//signQwest(i);
				SignQwest.sign(i);
				
				JLabel name = new JLabel();
				name.setBounds(0, 35, 48, 14);
				name.setText(npc[i].name);
				name.setForeground(Color.BLACK);
				mapx[npc[i].x][npc[i].y-1].add(name);
			} else {
				if (exit == true) {
					exit = false;
					break;
				}
			}
		}
	}
	
	protected void deleteNPC() {
		boolean exit = false; //Если монстры с текущей лок. кончились, закончить цикл
		for (int i = 0; i <= npc.length-1; i++) {
			if (npc[i].location == oldLocation) {;
				remove(npc[i]);
			} else {
				if (exit == true) {
					exit = false;
					break;
				}
			}
		}
	}
	
	//Если игрок вошел в портал
	private void verifyPortal() {
		int nextLevel = 0, nextX = 0, nextY = 0;
		try {
			for (int i = 0; i <= portal.length; i++) {
				if (pl.mX == portal[i].portalX & pl.mY == portal[i].portalY
						& portal[i].dostyp == true) {
					if (currentLocation != 5) {
						nextLevel = portal[i].nextLevel;
						nextX = portal[i].startX;
						nextY = portal[i].startY;
						crossing(nextLevel, nextX, nextY);
					} else {
						for (int z = 0; z <= mapx[0].length - 1; z++) {
							for (int j = 0; j <= mapx.length - 1; j++) {
								remove(mapx[j][z]);
								remove(p);
								remove(npc[7]);
								JLabel endInf = new JLabel();
								endInf.setBounds(-5, 48, end.getIconWidth(), end.getIconHeight());
								endInf.setIcon(end);
								add(endInf);
							}
						}
					}
				}
			}
		} catch (NullPointerException e) {
		}
	}
	
	//Загрузка другой локации
	protected void loadLocation(int location) {
		deleteTile();
		
		f2 = Game.class.getResourceAsStream("res/levels/" + location + ".txt");
		//f2 = new File("res/levels/" + nextLocation + ".txt");
		//LocationFile.openFile(null); //для file
		LocationFile.openFile(f2); //для input stream
		LocationFile.readFile();
		ms.massiv();
		
		deleteMonster();
		deleteNPC();
		Animation.sleep(500);
		addMonster();
		addNPC();
		
		Portals.removePortal();
		Portals.portals();
		Portals.addPortal();
		
		MusicLocation.music(location);
		
		addTile();
	}
	
	//Переход на другую локацию
	public void crossing(int nextLocation, int nextX, int nextY) {
		oldLocation = currentLocation; //Запоминаем локацию с которой ушли
		currentLocation = nextLocation;
		loadLocation(nextLocation);

		pl.mX = nextX;
		pl.mY = nextY;
		pl.x = pl.mX * 48;
		pl.y = pl.mY * 48 + 49;
	}
	
	//Метод добавляющий тайлы на фрейм
	protected void addTile() {
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				add(mapx[j][i]);
			}
		}
	}
	
	//Метод удаляющий все тайлы с фрейма
	protected void deleteTile() {
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				remove(mapx[j][i]);
			}
		}
	}
	
	//Слушатель нажатий клавиатуры
	public static class Listener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				Move.move(1, true);
			}
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				Move.move(2, true);
			}
			if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				Move.move(3, true);
			}
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				Move.move(4, true);
			}
			if (key == KeyEvent.VK_F4) {
				Music.stop();
			}
			if (key == KeyEvent.VK_F5) {
				//Сохранение
				try (ObjectOutputStream sr = new ObjectOutputStream
						(new FileOutputStream("res/save/save1.lnd"))) {
					pl.currentLocation = currentLocation;
					sr.writeObject(pl);
					
					for (int i = 0; i <= qwest.length - 1; i++) {
						sr.writeObject(qwest[i]);
					}
					for (int i = 0; i <= takeQwests.length - 1; i++) {
						sr.writeObject(takeQwests[i]);
					}
					
					JOptionPane.showMessageDialog(null, "Игра сохранена");
				} catch (Exception x) {
					x.printStackTrace();
					JOptionPane.showMessageDialog(null, "Не удалось сохранить");
				}
			}
		}
		
		public void keyReleased(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				Move.move(1, false);
				stop = false;
			}
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				Move.move(2, false);
				stop = false;
			}
			if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				Move.move(3, false);
				stop = false;
			}
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				Move.move(4, false);
				stop = false;
			}
		}
	}
	
	//Осуществляет движение и анимацию
	@Override
	public void run() {
		while (true) {
			Animation.sleep(5);
			if (move == true & direction == 1) {
				pl.mY++;
				Player.moveAnimation(1);
				move = false;
				verifyPortal();
			}
			if (move == true & direction == 2) {
				pl.mY--;
				Player.moveAnimation(2);
				move = false;
				verifyPortal();
			}
			if (move == true & direction == 3) {
				pl.mX--;
				Player.moveAnimation(3);
				move = false;
				verifyPortal();
			}
			if (move == true & direction == 4) {
				pl.mX++;
				Player.moveAnimation(4);
				move = false;
				verifyPortal();
			}
		}
	}

	
	private class TimerListener implements ActionListener {
		String s = getTitle();
		@Override
		public void actionPerformed(ActionEvent arg0) {
			long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
			int op = (int) usedBytes / 1048576;
			setTitle(s +  " " + op + " MB");
			repaint();
		}
	}
	
	public static class NpcListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent a) {
			if (a.getComponent() == inform) {
				if (informB == false) {
					inform.setIcon(inf2);
					informB = true;
				} else {
					Menu.g.remove(inform);
				}
			}
			if (a.getComponent() == menuB) {
				pan.setBounds(0, 0, 726, 701);
				p.add(pan);
			}
			if (a.getComponent() == pan.exit) {
				p.remove(pan);
				p.requestFocus();
			}
			try {
				if (a.getComponent() == qGP.exit) {
					//Кнопка exit в окне с получение квеста
					p.remove(qGP);
					p.requestFocus();
					qGP = null;
				}
				/*if (a.getComponent() == qGP.take) {
					//Кнопка взять квест у NPC
					//Animation.sleep(10);
					p.remove(qGP);
					p.requestFocus();
					qGP = null;
				}*/
			} catch (NullPointerException e) {
				
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		@Override
		public void mousePressed(MouseEvent a) {
			int xClick = a.getX() / 48;
			int yClick = (a.getY() - 70) / 48;
			try {
				for (int i = 0; i <= npc.length-1; i++) {
					if (npc[i].location == currentLocation && npc[i].x == xClick && npc[i].y == yClick) {
						int npcX = npc[i].x, npcY = npc[i].y;
						if ((pl.mX == npcX & pl.mY+1 == npcY) || (pl.mX == npcX & pl.mY-1 == npcY)
							|| (pl.mX+1 == npcX & pl.mY == npcY) || (pl.mX-1 == npcX & pl.mY == npcY)) {
							//Если персонаж рядом с NPC
							int id = npc[i].id;
							QwestGivePanel.nameNPC = npc[i].name;
							qGP = new QwestGivePanel(id); //Панель со списоком квестов
							qGP.setBounds(140, 70, 440, 540); //530
							p.add(qGP);
							break;
						} else {
							JOptionPane.showMessageDialog(null, "Что бы взять задание подойдите вплотную");
						}
					}
				}
			} catch (NullPointerException e) {
			}
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	
	private class Animat implements Runnable {
		@Override
		public void run() {
			int sl = 0; //Подсчитывает сколько сек. прошло
			while(true) {
				//0.2 сек
				if (sl%200 == 0) {
					//Анимация тайлов
					for (int i = 0; i <= 11; i++) {
						for (int j = 0; j <= 14; j++) {
							if (mapx[j][i].anim == true) {
								int number = mapx[j][i].number;
								int colum = Massiv.animIcon[number].length-1;
								if (mapx[j][i].currentFrame < colum) {
									mapx[j][i].currentFrame++;
								} else {
									mapx[j][i].currentFrame = 0;
								}
								mapx[j][i].setIcon(Massiv.animIcon[number][mapx[j][i].currentFrame]);
							}
						}
					}
				}
				
				//0.5 сек.
				if (sl%500 == 0) {
					if (move != true) {
						
					}
				}
				
				Animation.sleep(50); //200
				if (sl == 1050) {
					sl = 0;
				}
				sl += 50;
			}
		}
	}

	/** Добавляет на панель персонажа */
	protected void addComponent(JComponent comp) {
		p.add(comp);
	}
	
	/** Убирает с панели персонажа */
	protected void removeComponent(JComponent comp) {
		p.remove(comp);
	}
}