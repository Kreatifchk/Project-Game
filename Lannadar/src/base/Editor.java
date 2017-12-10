package base;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.border.Border;

import initialize.TilesImage;
import menu.Menu;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, AdjustmentListener {

	static ImageIcon whiteOpaque, delete, fillI, saveImage, portalI,
	openI, defineI, tilesI;
	static ImageIcon lastTool;
	
	Image icon = new ImageIcon(getClass().getResource("res/Image/icon.png")).getImage();
	
	static File portalNumber; //Файл с порталами и уровнями
	static PrintWriter pw, portalW;
	static Scanner s;
	EditorListener m = new EditorListener();
	static EditorOpen eo = new EditorOpen();
	
	static int block = 97; //Выбранный инструмент
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
	
	static JLabel[][] map = new JLabel[17][12]; //Массив для отрисовки
	static int[][] mapFile = new int[map.length][map[0].length];
	//Массив для сохранения данных
	
	JLayeredPane basicPane = new JLayeredPane();
	static JPanel menu = new JPanel();
	static JButton back = new JButton("Обратно в меню");

	JLabel main = new JLabel();
	
	static JButton fill = new JButton("");
	static JButton save = new JButton("");
	static JButton empty  = new JButton("");
	static JButton open = new JButton();
	static JButton define = new JButton();
	static JButton tilesB = new JButton();
	
	static JButton[] allTiles = new JButton[TileList.tiles.length];
	//Массив конопок - тайлов
	
	static JTabbedPane jtp;
	
	Timer t = new Timer(20, this);
	
	String pathF = "res/Image/Editor/";
	
	static JLabel tilesList;
	
	public Editor() {
		super("Lannadar");
		setLayout(null);
		setIconImage(icon);
		basicPane.setBounds(0, 0, 822, 695);//726
		add(basicPane);
		addKeyListener(new Keypad());
		setFocusable(true);

		new TilesImage();
		
		delete = new ImageIcon(getClass().getResource(pathF + "delete.png"));
		fillI  = new ImageIcon(getClass().getResource(pathF + "fill.png"));
		saveImage = new ImageIcon(getClass().getResource(pathF + "levelSave.png"));
		whiteOpaque = new ImageIcon(getClass().getResource(pathF + "whiteOpaque.png"));
		openI = new ImageIcon(getClass().getResource(pathF + "levelOpen.png"));
		defineI = new ImageIcon(getClass().getResource(pathF + "define.png"));
		tilesI = new ImageIcon(getClass().getResource(pathF + "tiles.png"));
		
		massiv();
		t.start();
		
		for (int i = 0; i < mapFile.length; i++) {
			for (int j = 0; j < mapFile[0].length; j++) {
				mapFile[i][j] = -1;
			}
		}
		
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
		main.setBounds(0, 0, 720, 91);
		main.setOpaque(true);
		main.setBackground(Color.gray);
		main.setBorder(solidBorder);
		
		int y = 6;
		
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
		open.setBounds(260, y, 50, 50);
		open.setIcon(openI);
		open.addMouseListener(m);
		define.setBounds(340, y, 50, 50);
		define.setIcon(defineI);
		define.addMouseListener(m);
		tilesB.setBounds(420, y, 48, 48);
		tilesB.setIcon(tilesI);
		tilesB.addMouseListener(m);
		
		basicPane.addMouseListener(m);
		basicPane.addMouseMotionListener(m);
		basicPane.addMouseWheelListener(m);
		
		main.add(empty);
		main.add(fill);
		main.add(save);
		main.add(open);
		main.add(define);
		main.add(tilesB);
		
		jtp = new JTabbedPane();
		jtp.addTab("Main", main);
		jtp.setBounds(0, 576, 816, 91); //720, 91
		basicPane.add(jtp, new Integer(1));
	}

	private void massiv() {
		//Стартовое заполнение
		int x = 0;
		int y = 0;
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				mapFile[j][i] = 0;
				map[j][i] = new JLabel();
				map[j][i].setIcon(whiteOpaque);
				map[j][i].setBounds(x, y, 48, 48);
				basicPane.add(map[j][i], new Integer(1));
				x += 48;
			}
			y += 48;
			x = 0;
		}
	}
	
	static JLabel inList;
	static JScrollBar scroll;
	protected static void tilesList() {
		tilesList = new JLabel();
		tilesList.setBounds((Menu.ed.getWidth() - 326)/2, (Menu.ed.getHeight() - 358)/2-40, 328, 358);
		tilesList.setBackground(new Color(100, 90, 110));
		tilesList.setOpaque(true);
		tilesList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		Menu.ed.basicPane.add(tilesList, new Integer(9));
		
		inList = new JLabel();
		inList.setBounds(0, 0, 300, 1074); //+358
		tilesList.add(inList);
		
		scroll = new JScrollBar();
		scroll.setUI(new QwestScrollUI());
		scroll.setMinimum(0);
		scroll.setMaximum(716); //+358
		scroll.setBounds(304, 10, 20, tilesList.getHeight() - 20);
		scroll.addAdjustmentListener(Menu.ed);
		tilesList.add(scroll);
		Menu.ed.revalidate();
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

		if (block >= 0 & block < allTiles.length) {
			map[xFill][yFill].setIcon(allTiles[block].getIcon());
			mapFile[xFill][yFill] = block;
		}	
		
		if (block == 96) {
			for (int i = 0; i < map[0].length; i++) {
				for (int j = 0; j < map.length; j++) {
					map[j][i].setIcon(lastTool);
					mapFile[j][i] = lastToolN;
				}
			}
		}
		if (block == 97) {
			map[xFill][yFill].setIcon(whiteOpaque);
			mapFile[xFill][yFill] = -1;
		}
		if (block == 99) {
			JOptionPane.showMessageDialog(null, xFill + " " + yFill);
		}
	}
	
	public static void saveLevel() {
		numberLevel = JOptionPane.showInputDialog("Введите номер уровня", "");
		int number = Integer.parseInt(numberLevel);
		try {
			pw = new PrintWriter("src/base/res/levels/" + number + ".txt");
			//pw = new PrintWriter("res/levels/" + number + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
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
					Menu.ed.basicPane.add(menu, new Integer(10));
					x = true;
				} else {
					Menu.ed.basicPane.remove(menu);
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
				//p.remove(menu);
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

	@Override
	public void adjustmentValueChanged(AdjustmentEvent a) {
		if (a.getSource() == scroll) {
			inList.setLocation(inList.getX(), inList.getX() - a.getValue());
		}
	}
	
}
