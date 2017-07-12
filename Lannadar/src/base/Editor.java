package base;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener {

	static ImageIcon whiteOpaque, delete, fillI, saveImage, portalI,
	openI, defineI;
	static ImageIcon lastTool;
	
	Image icon = new ImageIcon(getClass().getResource("res/icon.png")).getImage();
	
	static File portalNumber; //Файл с порталами и уровнями
	static PrintWriter pw, portalW;
	static Scanner s;
	EditorListener m = new EditorListener();
	static EditorOpen eo = new EditorOpen();
	
	static JPanel p = new JPanel();
	
	static int block; //
	static int xClick, yClick;
	//Координаты клика
	static int xFill, yFill; //Номер тайла который будет заменен/установлен
	static int idPortal = 0;
	static int levelNumber; //Номер уровня для портала
	static int length = 0; //Подсчитывает количество заполненных строк в файле с порталами
	static int lastToolN; //Определяет последний инструмент для заливки
	
	static boolean portalClick; //Определяет: поставил ли игрок портал, прежде чем вызвать окна конфигурации
	static boolean warning; //Определяет выдавалось ли предупрежддение о порталах
	
	static String numberLevel; //В нее записывается номер уровня при сохранении
	static String[] portalConf = new String[32];
	
	static JLabel[][] map = new JLabel[15][12]; //Массив для отрисовки
	static int[][] mapFile = new int[15][12]; //Массив для сохранения данных
	
	static JPanel menu = new JPanel();
	static JButton back = new JButton("Обратно в меню");
	
	JLabel tiles = new JLabel();
	JLabel tiles2 = new JLabel();
	JLabel tiles3 = new JLabel();
	JLabel paths = new JLabel();
	JLabel buildings = new JLabel();
	JLabel tools = new JLabel();
	JLabel extra = new JLabel();
	
	static JButton grass = new JButton("");
	static JButton grassFire = new JButton("");
	static JButton three = new JButton("");
	static JButton water = new JButton("");
	static JButton mount = new JButton("");
	static JButton brickWallP = new JButton();
	static JButton brickWall = new JButton();
	static JButton three2 = new JButton();
	static JButton water1 = new JButton();
	static JButton flooring = new JButton();
	static JButton homeWall = new JButton();
	static JButton homeWallRigth = new JButton();
	static JButton flooringRigth = new JButton();
	static JButton homeWallLeft = new JButton();
	static JButton flooringLeft = new JButton();
	static JButton voidT = new JButton();
	static JButton window = new JButton();
	static JButton grassDark = new JButton();
	static JButton threeDark = new JButton();
	static JButton threeDark2 = new JButton();
	static JButton well = new JButton();
	
	static JButton brickPath = new JButton();
	static JButton brickPathG = new JButton();
	static JButton bridge = new JButton();
	static JButton path = new JButton("");
	
	static JButton home01 = new JButton();
	static JButton home02 = new JButton();
	static JButton home03 = new JButton();
	static JButton homeFire = new JButton();
	
	static JButton fill = new JButton("");
	static JButton save = new JButton("");
	static JButton empty  = new JButton("");
	static JButton open = new JButton();
	static JButton define = new JButton();
	
	static JButton portal  = new JButton();
	
	static JTabbedPane jtp;
	
	Timer t = new Timer(20, this);
	
	public Editor() {
		super("RPG");
		setLayout(null);
		setIconImage(icon);
		p.setBounds(0, 0, 726, 721);
		p.setLayout(null);
		add(p);
		addKeyListener(new Keypad());
		setFocusable(true);
		
		@SuppressWarnings("unused")
		TilesImage ti = new TilesImage();
		
		delete = new ImageIcon(getClass().getResource("res/Editor/delete.png"));
		fillI  = new ImageIcon(getClass().getResource("res/Editor/fill.png"));
		saveImage = new ImageIcon(getClass().getResource("res/Editor/levelSave.png"));
		whiteOpaque = new ImageIcon(getClass().getResource("res/Editor/whiteOpaque.png"));
		openI = new ImageIcon(getClass().getResource("res/Editor/levelOpen.png"));
		defineI = new ImageIcon(getClass().getResource("res/Editor/define.png"));
		
		portalI = new ImageIcon(getClass().getResource("res/Tiles/portal.png"));
		
		massiv();
		t.start();
		
		Border b = BorderFactory.createLineBorder(Color.BLUE, 3);
		menu.setBounds(180, 60, 330, 450);
		menu.setOpaque(true);
		menu.setBackground(Color.CYAN);
		menu.setBorder(b);
		menu.addMouseListener(new MenuMouse());
		back.setBounds(30, 50, 270, 60);
		menu.add(back);
		back.addMouseListener(new MenuMouse());
		
		Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		tiles.setBounds(0, 0, 720, 91);
		tiles.setOpaque(true);
		tiles.setBackground(Color.GRAY);
		tiles.setBorder(solidBorder);
		
		tiles2.setOpaque(true);
		tiles2.setBackground(Color.GRAY);
		tiles2.setBorder(solidBorder);
		
		tiles3.setOpaque(true);
		tiles3.setBackground(Color.GRAY);
		tiles3.setBorder(solidBorder);
		
		paths.setOpaque(true);
		paths.setBackground(Color.GRAY);
		paths.setBorder(solidBorder);
		
		buildings.setOpaque(true);
		buildings.setBackground(Color.GRAY);
		buildings.setBorder(solidBorder);
		
		tools.setOpaque(true);
		tools.setBackground(Color.GRAY);
		tools.setBorder(solidBorder);
		
		extra.setOpaque(true);
		extra.setBackground(Color.GRAY);
		extra.setBorder(solidBorder);
		
		int y = 6;
		
		//Тайлы
		grass.setBounds(20, y, 48, 48);
		grass.setIcon(TilesImage.grass);
		grass.addMouseListener(m);
		grassFire.setBounds(80, y, 48, 48);
		grassFire.setIcon(TilesImage.grassFire);
		grassFire.addMouseListener(m);
		three.setBounds(140, y, 48, 48);
		three.setIcon(TilesImage.three);
		three.addMouseListener(m);
		water.setBounds(200, y, 48, 48);
		water.setIcon(TilesImage.water);
		water.addMouseListener(m);
		mount.setBounds(260, y, 48, 48);
		mount.setIcon(TilesImage.mount);
		mount.addMouseListener(m);
		brickWallP.setBounds(320, y, 48, 48);
		brickWallP.setIcon(TilesImage.brickWallP);
		brickWallP.addMouseListener(m);
		brickWall.setBounds(380, y, 48, 48);
		brickWall.setIcon(TilesImage.brickWall);
		brickWall.addMouseListener(m);
		three2.setBounds(440, y, 48, 48);
		three2.setIcon(TilesImage.three2);
		three2.addMouseListener(m);
		water1.setBounds(500, y, 48, 48);
		water1.setIcon(TilesImage.water1);
		water1.addMouseListener(m);
		flooring.setBounds(560, y, 48, 48);
		flooring.setIcon(TilesImage.flooring);
		flooring.addMouseListener(m);
		homeWall.setBounds(620, y, 48, 48);
		homeWall.setIcon(TilesImage.homeWall);
		homeWall.addMouseListener(m);
		homeWallRigth.setBounds(20, y, 48, 48);
		homeWallRigth.setIcon(TilesImage.homeWallRigth);
		homeWallRigth.addMouseListener(m);
		flooringRigth.setBounds(80, y, 48, 48);
		flooringRigth.setIcon(TilesImage.flooringRigth);
		flooringRigth.addMouseListener(m);
		homeWallLeft.setBounds(140, y, 48, 48);
		homeWallLeft.setIcon(TilesImage.homeWallLeft);
		homeWallLeft.addMouseListener(m);
		flooringLeft.setBounds(200, y, 48, 48);
		flooringLeft.setIcon(TilesImage.flooringLeft);
		flooringLeft.addMouseListener(m);
		voidT.setBounds(260, y, 48, 48);
		voidT.setIcon(TilesImage.voidI);
		voidT.addMouseListener(m);
		window.setBounds(320, y, 48, 48);
		window.setIcon(TilesImage.window);
		window.addMouseListener(m);
		grassDark.setBounds(380, y, 48, 48);
		grassDark.setIcon(TilesImage.grassDark);
		grassDark.addMouseListener(m);
		threeDark.setBounds(440, y, 48, 48);
		threeDark.setIcon(TilesImage.threeDark);
		threeDark.addMouseListener(m);
		threeDark2.setBounds(500, y, 48, 48);
		threeDark2.setIcon(TilesImage.threeDark2);
		threeDark2.addMouseListener(m);
		well.setBounds(560, y, 48, 48);
		well.setIcon(TilesImage.well);
		well.addMouseListener(m);
		
		//Дороги
		brickPath.setBounds(20, y, 48, 48);
		brickPath.setIcon(TilesImage.brickPath);
		brickPath.addMouseListener(m);
		brickPathG.setBounds(100, y, 48, 48);
		brickPathG.setIcon(TilesImage.brickPathG);
		brickPathG.addMouseListener(m);
		bridge.setBounds(180, y, 48, 48);
		bridge.setIcon(TilesImage.bridge);
		bridge.addMouseListener(m);
		path.setBounds(260, y, 48, 48);
		path.setIcon(TilesImage.path);
		path.addMouseListener(m);
		
		//Постройки
		home01.setBounds(20, y, 48, 48);
		home01.setIcon(TilesImage.home01);
		home01.addMouseListener(m);
		home02.setBounds(100, y, 48, 48);
		home02.setIcon(TilesImage.home02);
		home02.addMouseListener(m);
		home03.setBounds(180, y, 48, 48);
		home03.setIcon(TilesImage.home03);
		home03.addMouseListener(m);
		homeFire.setBounds(260, y, 48, 48);
		homeFire.setIcon(TilesImage.homeFire);
		homeFire.addMouseListener(m);
		
		//Инструменты
		empty.setBounds(20, y, 48, 48);
		empty.setIcon(delete);
		empty.addMouseListener(m);
		fill.setBounds(100, y, 50, 50);
		fill.setIcon(fillI);
		fill.addMouseListener(m);
		save.setBounds(180, y, 50, 50);
		save.setIcon(saveImage);
		save.addMouseListener(m);
		open.setBounds(240, y, 50, 50);
		open.setIcon(openI);
		open.addMouseListener(m);
		define.setBounds(320, y, 50, 50);
		define.setIcon(defineI);
		define.addMouseListener(m);
		
		//extra
		portal.setBounds(20, y, 50, 50);
		portal.setIcon(portalI);
		portal.addMouseListener(m);
		
		p.addMouseListener(m);
		p.addMouseMotionListener(m);
		
		tiles.add(grass);
		tiles.add(grassFire);
		tiles.add(three);
		tiles.add(water);
		tiles.add(mount);
		tiles.add(brickWallP);
		tiles.add(brickWall);
		tiles.add(three2);
		tiles.add(water1);
		tiles.add(flooring);
		tiles.add(homeWall);
		
		tiles2.add(homeWallRigth);
		tiles2.add(flooringRigth);
		tiles2.add(homeWallLeft);
		tiles2.add(flooringLeft);
		tiles2.add(voidT);
		tiles2.add(window);
		tiles2.add(grassDark);
		tiles2.add(threeDark);
		tiles2.add(threeDark2);
		tiles2.add(well);
		
		paths.add(brickPath);
		paths.add(brickPathG);
		paths.add(bridge);
		paths.add(path);
		
		buildings.add(home01);
		buildings.add(home02);
		buildings.add(home03);
		buildings.add(homeFire);
		
		tools.add(empty);
		tools.add(fill);
		tools.add(save);
		tools.add(open);
		tools.add(define);
		
		extra.add(portal);
		
		jtp = new JTabbedPane();
		jtp.addTab("Tiles", tiles);
		jtp.addTab("Tiles 2", tiles2);
		jtp.addTab("Tiles 3", tiles3);
		jtp.addTab("Paths", paths);
		jtp.addTab("Build", buildings);
		jtp.addTab("Tools", tools);
		jtp.addTab("Extra", extra);
		jtp.setBounds(0, 576, 720, 91);
		p.add(jtp);
	}

	private void massiv() {
		//Стартовое заполнение
		int x = 0;
		int y = 0;
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				mapFile[j][i] = 0;
				map[j][i] = new JLabel();
				map[j][i].setIcon(whiteOpaque);
				map[j][i].setBounds(x, y, 48, 48);
				p.add(map[j][i]);
				x += 48;
			}
			y += 48;
			x = 0;
		}
	}
	
	public static void click(int x, int y) {
		if (x <= 48) {
			xFill = 0;
		} else {
			xFill = x / 48;
		}
		
		if (y <= 48) {
			yFill = 0;
		} else {
			yFill = y / 48;
		}
		//System.out.println(xFill + " " + yFill);
		
		//System.out.println("x = " + xFill + " y = " + yFill);
		if (block == 1) {
			map[xFill][yFill].setIcon(TilesImage.path);
			mapFile[xFill][yFill] = 1;
			lastTool = TilesImage.path;
			lastToolN = 1;
		}
		if (block == 2) {
			map[xFill][yFill].setIcon(TilesImage.grass);
			mapFile[xFill][yFill] = 0;
			lastTool = TilesImage.grass;
			lastToolN = 0;
		}
		if (block == 4) {
			map[xFill][yFill].setIcon(TilesImage.three);
			mapFile[xFill][yFill] = 2;
		}
		if (block == 5) {
			map[xFill][yFill].setIcon(TilesImage.water);
			mapFile[xFill][yFill] = 3;
			lastTool = TilesImage.water;
			lastToolN = 3;
		}
		if (block == 6) {
			map[xFill][yFill].setIcon(TilesImage.mount);
			mapFile[xFill][yFill] = 4;
		}
		if (block == 7) {
			map[xFill][yFill].setIcon(TilesImage.home01);
			mapFile[xFill][yFill] = 5;
			lastTool = TilesImage.home01;
			lastToolN = 5;
		}
		if (block == 8) {
			map[xFill][yFill].setIcon(TilesImage.brickPath);
			mapFile[xFill][yFill] = 6;
			lastTool = TilesImage.brickPath;
			lastToolN = 6;
		}
		if (block == 9) {
			map[xFill][yFill].setIcon(TilesImage.brickWallP);
			mapFile[xFill][yFill] = 7;
			lastTool = TilesImage.brickWallP;
			lastToolN = 7;
		}
		if (block == 10) {
			map[xFill][yFill].setIcon(TilesImage.brickWall);
			mapFile[xFill][yFill] = 8;
			lastTool = TilesImage.brickWall;
			lastToolN = 8;
		}
		if (block == 11) {
			map[xFill][yFill].setIcon(TilesImage.brickPathG);
			mapFile[xFill][yFill] = 9;
			lastTool = TilesImage.brickPathG;
			lastToolN = 9;
		}
		if (block == 12) {
			map[xFill][yFill].setIcon(TilesImage.bridge);
			mapFile[xFill][yFill] = 10;
			lastTool = TilesImage.bridge;
			lastToolN = 10;
		}
		if (block == 13) {
			map[xFill][yFill].setIcon(TilesImage.three2);
			mapFile[xFill][yFill] = 11;
		}
		if (block == 14) {
			map[xFill][yFill].setIcon(TilesImage.water1);
			mapFile[xFill][yFill] = 12;
			lastTool = TilesImage.water1;
			lastToolN = 12;
		}
		if (block == 15) {
			map[xFill][yFill].setIcon(TilesImage.home02);
			mapFile[xFill][yFill] = 13;
		}
		if (block == 16) {
			map[xFill][yFill].setIcon(TilesImage.home03);
			mapFile[xFill][yFill] = 14;
		}
		if (block == 17) {
			//Горящий дом
			map[xFill][yFill].setIcon(TilesImage.homeFire);
			mapFile[xFill][yFill] = 15;
		}
		if (block == 18) {
			map[xFill][yFill].setIcon(TilesImage.flooring);
			mapFile[xFill][yFill] = 16;
			lastTool = TilesImage.flooring;
			lastToolN = 16;
		}
		if (block == 19) {
			map[xFill][yFill].setIcon(TilesImage.homeWall);
			mapFile[xFill][yFill] = 17;
			lastTool = TilesImage.homeWall;
			lastToolN = 17;
		}
		if (block == 20) {
			map[xFill][yFill].setIcon(TilesImage.homeWallRigth);
			mapFile[xFill][yFill] = 18;
			lastTool = TilesImage.homeWallRigth;
			lastToolN = 18;
		}
		if (block == 21) {
			map[xFill][yFill].setIcon(TilesImage.flooringRigth);
			mapFile[xFill][yFill] = 19;
			lastTool = TilesImage.flooringRigth;
			lastToolN = 19;
		}
		if (block == 22) {
			map[xFill][yFill].setIcon(TilesImage.homeWallLeft);
			mapFile[xFill][yFill] = 20;
			lastTool = TilesImage.homeWallLeft;
			lastToolN = 20;
		}
		if (block == 23) {
			map[xFill][yFill].setIcon(TilesImage.flooringLeft);
			mapFile[xFill][yFill] = 21;
			lastTool = TilesImage.flooringLeft;
			lastToolN = 21;
		}
		if (block == 24) {
			map[xFill][yFill].setIcon(TilesImage.voidI);
			mapFile[xFill][yFill] = 22;
			lastTool = TilesImage.voidI;
			lastToolN = 22;
		}
		if (block == 25) {
			map[xFill][yFill].setIcon(TilesImage.window);
			mapFile[xFill][yFill] = 23;
			lastTool = TilesImage.window;
			lastToolN = 23;
		}
		if (block == 26) {
			map[xFill][yFill].setIcon(TilesImage.grassDark);
			mapFile[xFill][yFill] = 24;
			lastTool = TilesImage.grassDark;
			lastToolN = 24;
		}
		if (block == 27) {
			map[xFill][yFill].setIcon(TilesImage.threeDark);
			mapFile[xFill][yFill] = 25;
			lastTool = TilesImage.threeDark;
			lastToolN = 25;
		}
		if (block == 28) {
			map[xFill][yFill].setIcon(TilesImage.threeDark2);
			mapFile[xFill][yFill] = 26;
			lastTool = TilesImage.threeDark2;
			lastToolN = 26;
		}
		if (block == 29) {
			map[xFill][yFill].setIcon(TilesImage.grassFire);
			mapFile[xFill][yFill] = 27;
			lastTool = TilesImage.grassFire;
			lastToolN = 27;
		}
		if (block == 30) {
			map[xFill][yFill].setIcon(TilesImage.well);
			mapFile[xFill][yFill] = 28;
			lastTool = TilesImage.well;
			lastToolN = 28;
		}
		
		
		if (block == 96) {
			for (int i = 0; i <= 11; i++) {
				for (int j = 0; j <= 14; j++) {
					map[j][i].setIcon(lastTool);
					mapFile[j][i] = lastToolN;
				}
			}
		}
		if (block == 97) {
			map[xFill][yFill].setIcon(whiteOpaque);
		}
		if (block == 98) {
			//map[xFill][yFill].setIcon(portalI);
			//mapFile[xFill][yFill] = 99;
			portalClick = true;
			if (warning == false) {
				JOptionPane.showMessageDialog(null, "На один уровень можно поставить не более 4 порталов");
				warning = true;
			}
			readFile();
			for (int i = 0; i <= portalConf.length - 1; i++) {
				//System.out.println(portalConf[i]);
			}
			if (length > 23) {
				JOptionPane.showMessageDialog(null, "У вас уже есть 4 портала");
				map[xFill][yFill].setIcon(whiteOpaque);
				mapFile[xFill][yFill] = 0;
			} else {
			portalFill();
			}
		}
		if (block == 99) {
			JOptionPane.showMessageDialog(null, xFill + " " + yFill);
		}
	}
	
	private static void readFile() {
		int count = 1;
		String aa = JOptionPane.showInputDialog("Введите номер уровеня к которому прикрепляете портал", "");
		levelNumber = Integer.parseInt(aa);
		portalNumber = new File("res/portals/" + levelNumber + ".txt");
		if (!portalNumber.exists()) {
			try {
				portalNumber.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			s = new Scanner(portalNumber);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNext()) {
			portalConf[count] = s.next();
			count++;
		}
		for (int i = 1; i <= portalConf.length - 1; i++) {
			if (portalConf[i] != null) {
				length++;
			}
		}
	}

	private static void portalFill() {
		//Заполняет файл с конфигурациями порталов
		if (portalClick == true) {
			String idPortalS = "" + idPortal;
			portalConf[length + 1] = idPortalS;
			String ab = JOptionPane.showInputDialog("Введите уровень в который ведет портал", "");
			portalConf[length + 2] = ab;
			String ac = JOptionPane.showInputDialog("Введите стартовую координату x", "");
			portalConf[length + 3] = ac;
			String ad = JOptionPane.showInputDialog("Введите стартовую координату y", "");
			portalConf[length + 4] = ad;
			String xFillM = "" + xFill;
			String yFillM = "" + yFill;
			portalConf[length + 5] = xFillM;
			portalConf[length + 6] = yFillM;
			try {
				portalW = new PrintWriter("res/portals/" + levelNumber + ".txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			for (int i = 1; i <= length+7; i++) {
				if (portalConf[i] != null) {
					portalW.println(portalConf[i]);
				}
			}
			portalClick = false;
			portalW.close();
		}
	}
	
	public static void saveLevel() {
		numberLevel = JOptionPane.showInputDialog("Введите номер уровня", "");
		int number;
		number = Integer.parseInt(numberLevel);
		try {
			pw = new PrintWriter("res/levels/" + number + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i <= 11; i++) {
			for (int j = 0; j <= 14; j++) {
				pw.print(mapFile[j][i] + " ");
			}
			pw.print("\n");
		}
		pw.close();
	}

	private static class Keypad implements KeyListener {
		static boolean x = false;
		@Override
		public void keyPressed(KeyEvent a) {
			int key = a.getExtendedKeyCode();
			if (key == KeyEvent.VK_ESCAPE) {
				if (x == false) {
					p.add(menu);
					x = true;
				} else {
					p.remove(menu);
					x = false;
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	
	private class MenuMouse implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {	
		}
		@Override
		public void mousePressed(MouseEvent a) {
			if (a.getComponent() == back) {
				dispose();
				Menu m = new Menu();
				m.setVisible(true);
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setSize(600, 561);
				m.setResizable(false);
				m.setLocationRelativeTo(null);
				p.remove(menu);
				Keypad.x = false;
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
}
