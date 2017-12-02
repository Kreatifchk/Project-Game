package base;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import heroPanel.HeroPanel;
import heroPanel.PersButton;
import menu.Menu;


/**
 * Класс с игровым окном
 */
@SuppressWarnings({ "unused", "serial" })
public class Game extends JFrame implements Runnable {
	
	public static JLayeredPane mainPane = new JLayeredPane();
	
	Image hpI = new ImageIcon(getClass().getResource("res/hp.png")).getImage();
	Image icon = new ImageIcon(getClass().getResource("res/Image/icon.png")).getImage();
	ImageIcon inf = new ImageIcon(getClass().getResource("res/inf.png"));
	static ImageIcon inf2 = new ImageIcon(Main.class.getResource("res/inf2.png"));
	static ImageIcon qwI, qwSI;
	static Image player;
	
	public static paint p; //Панель с персонажем
	
	public static Player pl;
	Massiv ms = new Massiv(); //Заполняет тайлы
	
	TimerListener tl = new TimerListener(); //Показывает ОЗУ, перерисовывает экран
	Timer t = new Timer(40, tl);
	
	Thread movePlayer = new Thread(this);
	Thread gameLoop = new Thread(new GameLoop()); //Восстанавливает хп
	Thread animation = new Thread(new Animat());
	
	static boolean move; //Проверяет, нажал ли игрок кнопку движения
	static boolean stop; //Не дает двигаться бесконечно при нажатии на кнопку
	static boolean informB = false; //Надо ли закрыть окно с информацией
	
	static int currentLocation = 1; 
	static int oldLocation = 0;
	static int direction; //Направление движения

	final static int TILE = 48; //Размер тайла
	
	static Tiles[][] mapx = new Tiles[17][12]; //Массив непосредственнно самих тайлов
	static int[][] map = new int[mapx.length][mapx[0].length];
	//Массив с данными уровня из файла
	static Portal[] portal = new Portal[5]; //Массив хранящий данные о порталах
	static ArrayList<Monsters> monster = new ArrayList<Monsters>();
	public static ArrayList<Item> item = new ArrayList<Item>();
	static NPC[] npc = new NPC[8]; //Массив с NPC
	public static Qwest[] qwest = new Qwest[12]; //Все квесты
	public static int[] takeQwests = new int[10]; //Взятые квесты (номера/id)
	
	//Изменить здесь, в Dead.java и LocationFile.java
	static InputStream f2;
	//static File f2 = new File("res/levels/" + currentLocation + ".txt");
	//static File playerCont = new File("res/Player.txt"); Не используется
	//InputStream portalFile;
	
	UpPanel upPanel;
	JLabel downPanel = new JLabel();
	static JLabel inform; //Информация в начале игры
	
	static PersButton menuB = new PersButton(); //Кнопка, открывает меню персонажа
	static QwestGivePanel qGP;
	
	Random r = new Random();
	
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
			
			upPanel = new UpPanel();
			upPanel.setBounds(0, 0, 817, 51);//721, 51
			
			downPanel.setBounds(0, 627, 817, 48);//721, 51
			downPanel.setBorder(border);
			downPanel.setOpaque(true);
			downPanel.setBackground(BROWN);
			
			menuB.setBounds(700, 2, 115, 44);//604
			menuB.addMouseListener(new NpcListener());
			
			p.setBounds(0, 0, 822, 704);//726, 704
			p.setLayout(null);
			p.setFocusable(true);
			
			mainPane.add(upPanel, new Integer(0));
			mainPane.add(downPanel, new Integer(0));
			downPanel.add(menuB);
			mainPane.add(p, new Integer(1));
			
			addMonster();
			addNPC();
			addItem();
			
			addTile(); //Добавляет тайлы на карту
			addMouseListener(new NpcListener());
		}
	}

	//Инициализация
	private void init() {
		mainPane.setBounds(0, 0, 822, 704);//726, 704
		add(mainPane);
		
		setIconImage(icon);
		//Стартовые установки
		p = new paint();
		p.addKeyListener(new Listener());
		
		f2 = Game.class.getResourceAsStream("res/levels/" + currentLocation + ".txt");
		
		LocationFile.openFile(null);
		LocationFile.readFile(); //Читает файл с локациями
		
		imageInit();
		ms.massiv(); //Располагает тайлы на фрейме
		Portal.addPortal(currentLocation);
		
		player = Player.playerD[0];
		
		movePlayer.start();
		Move.battle.start();
		animation.start();
		t.start();
		gameLoop.start();
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
	
	// Добавляет на карту монстров
	public void addMonster() {
		boolean exit = false; //Если монстры с текущей лок. кончились, закончить цикл
		for (int i = 0; i < monster.size(); i++) {
			if (monster.get(i).location == currentLocation) {
				monster.get(i).setBounds(monster.get(i).x*Game.TILE, monster.get(i).y*Game.TILE+48, Game.TILE, Game.TILE);
				monster.get(i).setIcon(new ImageIcon(getClass().getResource("res/Image/monsters/" + monster.get(i).icon + ".png")));
				mapx[monster.get(i).x][monster.get(i).y].busy = true;
				mainPane.add(monster.get(i), new Integer(3));
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
		for (int i = 0; i < monster.size(); i++) {
			if (monster.get(i).location == oldLocation) {
				mainPane.remove(monster.get(i));
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
		for (int i = 0; i < npc.length; i++) {
			if (npc[i].location == currentLocation) {
				npc[i].setBounds(npc[i].x*Game.TILE, npc[i].y*Game.TILE+48, Game.TILE, Game.TILE);
				npc[i].setIcon(new ImageIcon(getClass().getResource("res/Image/NPC/" + npc[i].icon + ".png")));
				mapx[npc[i].x][npc[i].y].busy = true;
				mainPane.add(npc[i], new Integer(2));
				SignQwest.sign(i);
				
				JLabel name = new JLabel();
				name.setBounds(0, 35, 48, 14);
				name.setText(npc[i].name);
				name.setForeground(Color.BLACK);
				mapx[npc[i].x][npc[i].y-1].add(name);
			}
		}
	}
	
	protected void deleteNPC() {
		for (int i = 0; i < npc.length; i++) {
			if (npc[i].location != currentLocation) {
				mainPane.remove(npc[i]);
			}
		}
	}
	
	Item[] itemM;
	protected void addItem() {
		//Массив создает аналогичные объекты, так как один и тот же объект не добавляется
		int x = 0, y = 0; //координаты тайла на котором появится предмет
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).location == currentLocation) {
				itemM = new Item[item.get(i).count];
				Item itemX = item.get(i); //Для сокращения кода
				for (int j = 0; j < itemX.count; j++) {
					//Сгенерировать столько раз сколько прописано
					x = r.nextInt(map.length);
					y = r.nextInt(map[0].length);
					if (mapx[x][y].busy == true || mapx[x][y].item == true) {
						//Если тайл занят не генерировать
						j--;
					} else if (map[x][y] == 24) {
						//Если тайл трава
						//Цикл для массива idInvent делается так как иначе
						//он будет во всех экземплярах один и тот же
						int a[] = new int[itemX.idInvent.length];
						for (int z = 0; z < itemX.idInvent.length; z++) {
							a[z] = itemX.idInvent[z];
						}
						
 						itemM[j] = new Item(itemX.id, itemX.location, itemX.count, itemX.icon, a);
						a = null;
						itemM[j].setBounds(16, 16, 16, 16);
						itemM[j].setIcon(new ImageIcon(getClass().getResource("res/Image/Item/" + item.get(i).icon + ".png")));
						itemM[j].mX = x;
						itemM[j].mY = y;
						mapx[x][y].item = true;
						mapx[x][y].add(itemM[j]);
					} else {
						j--;
					}
				}
			}
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
		Animation.sleep(400);
		addMonster();
		addNPC();
		addItem();
		
		Portal.addPortal(currentLocation);
		
		MusicLocation.music(location);
		
		addTile();
	}
	
	//Переход на другую локацию
	protected void crossing(int nextLocation, int nextX, int nextY) {
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
		for (int i = 0; i < mapx[0].length; i++) {
			for (int j = 0; j < mapx.length; j++) {
				mainPane.add(mapx[j][i], new Integer(0));
			}
		}
	}
	
	//Метод удаляющий все тайлы с фрейма
	protected void deleteTile() {
		for (int i = 0; i < mapx[0].length; i++) {
			for (int j = 0; j < mapx.length; j++) {
				mapx[j][i].busy = false;
				mapx[j][i].item = false;
				mainPane.remove(mapx[j][i]);
			}
		}
	}
	
	static boolean lock; //Блокировка движения
	//Слушатель нажатий клавиатуры
	public static class Listener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (lock != true) {
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
					}
				}
			});
			if (key == KeyEvent.VK_F4) {
				Music.stop();
			}
			if (key == KeyEvent.VK_F9) {
				pl.force += 100;
				pl.endurance += 100;
				pl.regeneration += 100;
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
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (lock != true) {
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
			});
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
				Portal.verifyPortal();
			}
			if (move == true & direction == 2) {
				pl.mY--;
				Player.moveAnimation(2);
				move = false;
				Portal.verifyPortal();
			}
			if (move == true & direction == 3) {
				pl.mX--;
				Player.moveAnimation(3);
				move = false;
				Portal.verifyPortal();
			}
			if (move == true & direction == 4) {
				pl.mX++;
				Player.moveAnimation(4);
				move = false;
				Portal.verifyPortal();
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
				mainPane.add(new HeroPanel(), new Integer(20));
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
							qGP.setBounds((Menu.g.getWidth()-440)/2, 70, 440, 540); //140, 70
							mainPane.add(qGP, new Integer(10));
							lock = true;
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
					for (int i = 0; i < mapx[0].length; i++) {
						for (int j = 0; j < mapx.length; j++) {
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
}