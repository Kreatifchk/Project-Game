package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.crypto.Cipher;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import ru.kreatifchk.game.Monster;
import ru.kreatifchk.game.Player;
import ru.kreatifchk.game.Tile;
import ru.kreatifchk.game.TilesList;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.main.Menu;
import ru.kreatifchk.tools.Aes256;
import ru.kreatifchk.tools.Img;
import ru.kreatifchk.tools.Resize;
import ru.kreatifchk.tools.Sleep;

public class Editor extends JFrame implements ActionListener, MouseListener, MouseMotionListener, Runnable, Serializable {
	
	//Сделать требование чтоб все тайлы были заполнены
	private static final long serialVersionUID = 3108L;
	
	JLabel topPanel, bottomPanel;
	static JLabel centerPanel;
	static JLayeredPane mainPane;

	CreateDialog dialog; //Диалоговое окно для создания локации
	JLabel transDialog; //Диалоговое окно для трансфера
	EditorButton createButton, openButton, closeButton, saveButton; //Кнопки в верхней панели
	TileButton tileForest, tileCity, fill, delete, transit, monster, drawB; //Конпки тайлов и инструментов снизу
	static PointEditor field[][]; //Поле - массив ячеек редактора которые заполняются редактором карты
	
	static JLabel selectTile = new JLabel(); //Показывает выбранный тайл или инструмент
	
	protected static boolean openDialog; //Открыты ли диалоговое ок
	private boolean draw = true; //Включен ли предпоказ тайлов
	
	protected enum Mode {standart, fill, delete, transit, monster}; //Вместо boolean, будет доработано позже
	static Mode currentMode = Mode.standart; //Текущий мод
	
	private int buttonActive; //Какая кнопка мыши нажата
	
	protected static Player.Direction dirTransit = Player.Direction.down; //Выбранное направление перемещения
	protected static String transitLocation; //В какую локацию перемещение
	protected static int xTrans = 0, yTrans = 0; //На какое поле перемещается персонаж
	
	Timer t = new Timer(20, new Repainter());
	
	static int actTile; //Выбранный тайл
	
	Thread animated = new Thread(this);
	
	public Editor() {
		setTitle("Lannadar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize((int)(960*Main.INC), (int)(672*Main.INC)+Main.insets.top);
		setSize(Main.WIDTH, Main.HEIGHT);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		//setLocationRelativeTo(null);
		setLocation(Menu.loc);
		setIconImage(Menu.icon);
		arrangement();
		
		t.start();
		animated.setDaemon(true);
		animated.start(); //Включить поток анимирования тайлов
	}
	
	public Editor setFileParametr(File f) {
		openMap(f);
		return this;
	}
	
	// Первичная компоновка
	private void arrangement() {
		mainPane = new JLayeredPane();
		mainPane.setBounds(0, 0, getWidth(), getHeight());
		add(mainPane);
		
		topPanel = new JLabel();
		topPanel.setBounds(0, 0, getWidth()-(int)(4*Main.INC), (int)(48*Main.INC));
		topPanel.setIcon(Img.ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/panel.png")));
		
		bottomPanel = new JLabel();
		bottomPanel.setBounds(0, (int)(624*Main.INC), getWidth()-(int)(4*Main.INC), (int)(48*Main.INC));
		bottomPanel.setIcon(Img.ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/panel.png")));
		
		centerPanel = new JLabel();
		centerPanel.setBounds(0, (int)(48*Main.INC), getWidth()-(int)(4*Main.INC), (int)(576*Main.INC));
		centerPanel.setIcon(Img.ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/editor/centerPanel.png")));
		centerPanel.addMouseMotionListener(this);
		centerPanel.addMouseListener(this);
		
		mainPane.add(topPanel, new Integer(1));
		mainPane.add(bottomPanel, new Integer(1));
		mainPane.add(centerPanel, new Integer(1));
		
		//Кнопка - создать карту
		createButton = new EditorButton(0, (int)(144*Main.INC), (int)(38*Main.INC));
		createButton.setLocation((int)(10*Main.INC), 7);
		createButton.addActionListener(this);
		createButton.addMouseListener(this);
		topPanel.add(createButton);
		
		//Кнопка - открыть карту
		openButton = new EditorButton(1, (int)(144*Main.INC), (int)(38*Main.INC));
		openButton.setLocation((int)(180*Main.INC), 7);
		openButton.addActionListener(this);
		openButton.addMouseListener(this);
		topPanel.add(openButton);
		
		//Кнопка - сохранить карту
		saveButton = new EditorButton(2, (int)(144*Main.INC), (int)(38*Main.INC));
		saveButton.setLocation(getWidth()-(int)(324*Main.INC), 7);
		saveButton.addActionListener(this);
		saveButton.addMouseListener(this);
		topPanel.add(saveButton);
		
		//Кнопка - закрыть карту
		closeButton = new EditorButton(3, (int)(144*Main.INC), (int)(38*Main.INC));
		closeButton.setLocation(getWidth()-(int)(154*Main.INC), 7);
		closeButton.addActionListener(this);
		closeButton.addMouseListener(this);
		topPanel.add(closeButton);
		
		//Значок выбранного тайла сверху
		selectTile.setSize((int)(46*Main.INC), (int)(46*Main.INC));
		selectTile.setLocation((getWidth() - selectTile.getWidth()) / 2, 0);
		selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
		selectTile.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		topPanel.add(selectTile);
		
		//Кнопки наборов тайлов и инструментов
		tileForest = new TileButton(0);
		tileForest.setLocation((int)(20*Main.INC), 0);
		tileForest.addMouseListener(this);
		bottomPanel.add(tileForest);
		
		tileCity = new TileButton(1);
		tileCity.setLocation((int)(80*Main.INC), 0);
		tileCity.addMouseListener(this);
		bottomPanel.add(tileCity);
		
		delete = new TileButton(995);
		delete.setLocation((int)(140*Main.INC), 0);
		delete.addMouseListener(this);
		bottomPanel.add(delete);
		
		fill = new TileButton(996);
		fill.setLocation((int)(200*Main.INC), 0);
		fill.addMouseListener(this);
		bottomPanel.add(fill);
		
		transit = new TileButton(997);
		transit.setLocation((int)(260*Main.INC), 0);
		transit.addMouseListener(this);
		bottomPanel.add(transit);
		
		monster = new TileButton(998);
		monster.setLocation((int)(320*Main.INC), 0);
		monster.addMouseListener(this);
		bottomPanel.add(monster);
		
		drawB = new TileButton(999);
		drawB.setLocation((int)(380*Main.INC), 0);
		drawB.addMouseListener(this);
		bottomPanel.add(drawB);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		//Открытие всплывающего окна создания карты
		if (a.getSource() == createButton & openDialog == false) {
			openDialog = true;
			new CreateDialog();
		}
		//Открытие диалогового окна открыть карту из файла
		if (a.getSource() == openButton & openDialog == false) {
			//Выбор файла для чтения
			File read = new File("data/maps/"); //Путь к директории карт
			JFileChooser jf = new JFileChooser(read.getAbsolutePath());
			jf.setFileFilter(new FileNameExtensionFilter("Lannadar files", "lnd"));
			jf.showOpenDialog(mainPane);
			
			read = new File(jf.getSelectedFile().getAbsolutePath());
			openMap(read);
		}
		//Открытие всплывающего окна сохранение карты
		if (a.getSource() == saveButton & openDialog == false & field != null) {
			//Позволяем выбрать папку сохранения карт и дать название файлу
			File write = new File("data/maps/"); //Путь к директории карт
			JFileChooser jf = new JFileChooser(write.getAbsolutePath());
			jf.showSaveDialog(mainPane);
			//Если файл уже имеет расширение lnd не добавлять его
			if (getExtension(jf.getSelectedFile()).equals("lnd")) {
				write = new File(jf.getSelectedFile() + "");
			} else {
				write = new File(jf.getSelectedFile() + ".lnd");
			}
			
			//Задаем вопрос про перезапись файла, если необходимо
			int rez = 0;
			if (write.exists()) {
				rez = JOptionPane.showConfirmDialog(mainPane, "Перезаписать файл?", "Потверждение", JOptionPane.YES_NO_OPTION);
			}
			
			//Если пользователь разрешил перезапись
			if (rez == 0) {
				File tempFile = null;
				FSTObjectOutput oos;
				try {
					//Создаем временный файл
					tempFile = File.createTempFile("serial", ".lnd");
					//Записываем данные в файл сериализацией то есть байтами
					oos = new FSTObjectOutput(new FileOutputStream(tempFile));
					oos.writeObject(field);
					oos.writeObject(Map.monsters);
					oos.writeObject("Miku");
					oos.close();
					//Читаем данные из этого файла и записываем их в массив байтов
					FileInputStream fis = new FileInputStream(tempFile);
					byte[] buffer = new byte[fis.available()];
					fis.read(buffer, 0, fis.available()); //Читать байты пока они доступны
					fis.close();
					
					//Зашифровывем массив байтов в другой массив байтов
					byte[] shifr = new Aes256().makeAes(buffer, Cipher.ENCRYPT_MODE);
					
					//Записываем зашифрованные байты в конечный файл
					write.createNewFile();
					FileOutputStream fw = new FileOutputStream(write);
					fw.write(shifr);
					fw.close();
					//Удаляем временный файл
					tempFile.deleteOnExit();
					
					JOptionPane.showMessageDialog(mainPane, "Сохранено!");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}  catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Ошибка");
					e.printStackTrace();
				}
			}
		}
		//Нажатие кнопки "в меню"
		if (a.getSource() == closeButton & openDialog == false) {
			//Если поля уже есть спросить еще раз
			int rez = -1;
			if (field != null) {
				rez = JOptionPane.showConfirmDialog(mainPane, "Любые несохраненные изменения будут потеряны",
						"Потверждение", JOptionPane.YES_NO_OPTION);
			}
			if (field == null || rez == 0) {
				field = null;
				mainPane.remove(centerPanel);
				centerPanel = null;
				mainPane = null;
				
				setVisible(false);
				dispose();
				new Menu();
			}
		}
	}
	
	//Открытие карты (сделано отдельно по причине открытия карты внешне
	@SuppressWarnings("unchecked")
	private void openMap(File read) {
		byte[] buffer = null;
		try {
			//Считываем байты из зашифрованного файла в массив байтов
			FileInputStream fis = new FileInputStream(read);
			buffer = new byte[fis.available()];
			fis.read(buffer, 0, fis.available());
			fis.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		//Расшифровываем и засовываем в массив байтов
		byte[] shifr = new Aes256().makeAes(buffer, Cipher.DECRYPT_MODE);
		
		File tempFile = null;
		
		try {
			//Создаем временный файл
			tempFile = File.createTempFile("serial1", ".lnd");
			//Записываем расшифрованные байты в файл
			FileOutputStream fw = new FileOutputStream(tempFile);
			fw.write(shifr);
			fw.close();
			//Превращаем байты в объекты
			FSTObjectInput ois = new FSTObjectInput(new FileInputStream(tempFile));
			PointEditor[][] p = (PointEditor[][]) ois.readObject();
			Map.monsters = (ArrayList<Monster>) ois.readObject();
			ois.close();
			
			//Сначало удалить старую карту если есть
			if (field != null) {
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						centerPanel.remove(field[j][i]);
					}
				}
				field = null;
			}
			
			//Потом создавать новую
			field = new PointEditor[p.length][p[0].length];
			int x = 0, y = 0;
			for (int i = 0; i < p[0].length; i++) {
				for (int j = 0; j < p.length; j++) {
					field[j][i] = new PointEditor(j, i);
					field[j][i].number = p[j][i].number;
					field[j][i].dirTrans = p[j][i].dirTrans;
					field[j][i].xTrans = p[j][i].xTrans;
					field[j][i].yTrans = p[j][i].yTrans;
					field[j][i].transition = p[j][i].transition;
					field[j][i].anim = p[j][i].anim;
					if (p[j][i].anim) {
						field[j][i].setIcon(new AnimatedIcon(TilesList.tiles[p[j][i].number].icon2));
					} else {
						field[j][i].setIcon(TilesList.tiles[p[j][i].number].getIcon());
					}
					field[j][i].setIcon(TilesList.tiles[p[j][i].number].getIcon());
					field[j][i].setBounds(x, y, Tile.SIZE, Tile.SIZE);
					field[j][i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
					centerPanel.add(field[j][i]);
					x += Tile.SIZE;
					
					int left = 1, right = 1, top = 1, bottom = 1;
					if (field[j][i].dirTrans != null) {
						if (field[j][i].dirTrans == Player.Direction.left) {
							left = 5;
						}
						if (field[j][i].dirTrans == Player.Direction.right) {
							right = 5;
						}
						if (field[j][i].dirTrans == Player.Direction.up) {
							top = 5;
						}
						if (field[j][i].dirTrans == Player.Direction.down) {
							bottom = 5;
						}
						field[j][i].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
					}
					if (field[j][i] == null) {
					}
				}
				x = 0;
				y += Tile.SIZE;
			}
			//Удаляем временный файл
			tempFile.deleteOnExit();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Получить расширение файла
	private String getExtension(File f) {
		String x = f.getName();
		int pos = 0;
		for (int i = 0; i < x.length(); i++) {
			if (i > 0 & x.charAt(i) == '.') {
				pos = i;
			}
		}
		if (pos == 0) {
			return "";
		} else {
			return x.substring(pos+1, x.length());
		}
	}
	
	/** Установка тайла в редакторе, а также других объектов на карту */
	private void setTile(int xClick, int yClick) {
		field[xClick][yClick].oldIcon = null;
		//Установка мультитайла
		if (TilesList.tiles[actTile].width != -1 | TilesList.tiles[actTile].height != -1 & currentMode == Mode.standart) {
			for (int i = 0; i < TilesList.tiles[actTile].width; i++) {
				for (int j = 0; j < TilesList.tiles[actTile].height; j++) {
					field[xClick+j][yClick+i].setIcon(Img.ImageIcon(Main.class.getResource(
							"/ru/kreatifchk/res/image/tiles/bigTiles/" + TilesList.tiles[actTile].nameFile + "" + j + "" + i + ".png")));
					field[xClick+j][yClick+i].number = actTile;
					field[xClick+j][yClick+i].izm = false;
				}
			}
		} else {
			//Или установка одного тайла
			if (currentMode == Mode.standart) {
				//Если тайл анимированный то записать в клетку для потока анимации и установить иконку
				if (TilesList.tiles[actTile].animate == true) {
					field[xClick][yClick].anim = true;
					field[xClick][yClick].setIcon(new AnimatedIcon(TilesList.tiles[actTile].icon2));
				} else {
					field[xClick][yClick].anim = false;
					field[xClick][yClick].setIcon(TilesList.tiles[actTile].icon);
				}
				field[xClick][yClick].number = actTile;
				field[xClick][yClick].izm = false;
			}
		}
		//Удаление тайлов
		if (currentMode == Mode.delete) {
			//Режим удаления тайлов
			field[xClick][yClick].setIcon(null);
			field[xClick][yClick].number = -1;
			field[xClick][yClick].izm = false;
		}
		//Установка перемещений между локациями
		if (currentMode == Mode.transit) {
			if (field[xClick][yClick].transition == null) {
			}
			if (field[xClick][yClick].transition.equals("")) {
				int top = 1, left = 1, bottom = 1, right = 1;
				if (dirTransit == Player.Direction.left) {
					left = 5;
				}
				if (dirTransit == Player.Direction.right) {
					right = 5;
				}
				if (dirTransit == Player.Direction.up) {
					top = 5;
				}
				if (dirTransit == Player.Direction.down) {
					bottom = 5;
				}
				field[xClick][yClick].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
				field[xClick][yClick].dirTrans = dirTransit;
				field[xClick][yClick].transition = transitLocation;
				field[xClick][yClick].xTrans = xTrans;
				field[xClick][yClick].yTrans = yTrans;
			} else {
				//Удаление перемещений
				field[xClick][yClick].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				field[xClick][yClick].dirTrans = null;
				field[xClick][yClick].transition = "";
				field[xClick][yClick].xTrans = -1;
				field[xClick][yClick].yTrans = -1;
			}
		}
		//Установка мобов
		if (currentMode == Mode.monster) {
			try {
				//Если на данных координатах уже есть монстр то удалить его
				Map.monsters.remove(Map.monsters.stream().
						filter((e) -> e.startX == xClick & e.startY == yClick).findFirst().get());
			} catch(NoSuchElementException e) {
				//Иначе - наборот поставить
				Monster mr = MonsterDialog.mr;
				Map.monsters.add((Monster) new Monster(mr.getName(), mr.getImageName(), mr.getHpMax(),
						mr.getMpMax(), mr.getLevel(), mr.getDanger()).setStart(xClick, yClick));
			}
		}
	}
	
	//Возвращает всем клеткам их истинные иконки
	private void returnIcon() {
		for (int i = 0; i < field[0].length; i++) {
			for (int j = 0; j < field.length; j++) {
				if (field[j][i].izm == true) {
					field[j][i].setIcon(field[j][i].oldIcon);
					field[j][i].oldIcon = null;
					field[j][i].izm = false;
				}
			}
		}
	}

	int x = 0, y = 0, dv; //dv - смещение поля в определенную сторону
	@Override
	public void mouseDragged(MouseEvent a) {
		//Перемещение поля (правой кнопкой мыши)
		if (buttonActive == 3 & field != null) {
			//Движение нас влево, поле вправо, мышь слева направо
			if (a.getX() > x & field[0][0].getX() < 0) {
				//Не дает выехать за край
				if (a.getX() - x + field[0][0].getX() > 0) {
					dv = field[0][0].getX();
				} else {
					dv = a.getX() - x;
				}
				
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						field[j][i].setLocation(dv + field[j][i].getX(), field[j][i].getY());
					}
				}
			}
			//Движение нас вверх, поле вниз, мышь сверху вниз
			if (a.getY() > y & field[0][0].getY() < 0) {
				//Не дает выехать за край
				if (a.getY() - y + field[0][0].getY() > 0) {
					dv = field[0][0].getY();
				} else {
					dv = a.getY() - y;
				}
				
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						field[j][i].setLocation(field[j][i].getX(), dv + field[j][i].getY());
					}
				}
			}
			//Движение нас вправо, поле влево, мышь справа налево
			if (a.getX() < x & field[field.length-1][0].getX() > centerPanel.getWidth() - (int)(48*Main.INC)) {
				//Не дает выехать за край
				if (a.getX() - x + field[field.length-1][0].getX() < centerPanel.getWidth() - (int)(48*Main.INC)) {
					dv = (field[field.length-1][0].getX() - (centerPanel.getWidth() - (int)(48*Main.INC)));
				} else {
					dv = a.getX() - x;
				}
				
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						field[j][i].setLocation(dv + field[j][i].getX(), field[j][i].getY());
					}
				}
			}
			//Движение нас вниз, поле вврх, мышь снизу вверх
			if (a.getY() < y & field[0][field[0].length-1].getY() > centerPanel.getHeight() - (int)(48*Main.INC)) {
				//Не дает выехать за край
				if (a.getY() - y + field[0][field[0].length-1].getY() < centerPanel.getHeight() - (int)(48*Main.INC)) {
					dv = (field[0][field[0].length-1].getY() - (centerPanel.getHeight() - (int)(48*Main.INC)));
				} else {
					dv = a.getY() - y;
				}
				
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						field[j][i].setLocation(field[j][i].getX(), dv + field[j][i].getY());
					}
				}
			}
			
			x = a.getX();
			y = a.getY();
		}
		
		//Рисование левой кнопкой мыши
		if (a.getSource() == centerPanel & field != null & buttonActive == 1 & !openDialog & currentMode == Mode.standart) {
			/*На какую клетку нажали
			 * Берем клетку 0,0 и получаем значене в клетках до той что стоит в левом верхнем углу,
			 * делаем значение положительным, и прибавляем к месту нажатия c учетом неполной клетки слева (и сверху если есть) 
			 * Аналогично с одночной установкой */
			int rem = -(field[0][0].getX() % Tile.SIZE);
			int xClick = (-(field[0][0].getX() / Tile.SIZE)) + ((a.getX() + rem) / Tile.SIZE);
			rem = -(field[0][0].getY() % Tile.SIZE);
			int yClick = (-(field[0][0].getY() / Tile.SIZE)) + ((a.getY() + rem) / Tile.SIZE);
			
			setTile(xClick, yClick);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent a) {
		//Показывает выбранный тайл на карте
		if (field != null & !openDialog & draw & currentMode == Mode.standart) {
			//Если какой-то тайл менял иконку возвращаем ему истинную иконку
			returnIcon();
			//Вычисляем клетку на которой курсор
			int rem = -(field[0][0].getX() % Tile.SIZE);
			int xClick = (-(field[0][0].getX() / Tile.SIZE)) + ((a.getX() + rem) / Tile.SIZE);
			rem = -(field[0][0].getY() % Tile.SIZE);
			int yClick = (-(field[0][0].getY() / Tile.SIZE)) + ((a.getY() + rem) / Tile.SIZE);
			//Устанавливаем "нашу иконку"
			if (TilesList.tiles[actTile].width != -1 | TilesList.tiles[actTile].height != -1) {
				//Для мультитайла
				for (int i = 0; i < TilesList.tiles[actTile].width; i++) {
					for (int j = 0; j < TilesList.tiles[actTile].height; j++) {
						//Запоминаем какая на ней иконка
						field[xClick+j][yClick+i].oldIcon = (ImageIcon) field[xClick+j][yClick+i].getIcon();
						field[xClick+j][yClick+i].setIcon(Img.ImageIcon(Main.class.getResource(
								"/ru/kreatifchk/res/image/tiles/bigTiles/" + TilesList.tiles[actTile].nameFile + "" + j + "" + i + ".png")));
						field[xClick+j][yClick+i].izm = true;
					}
				}
			} else {
				//Для одиночного
				//Запоминаем какая на ней иконка
				field[xClick][yClick].oldIcon = (ImageIcon) field[xClick][yClick].getIcon();
				field[xClick][yClick].setIcon(TilesList.tiles[actTile].icon);
				field[xClick][yClick].izm = true;
			}
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent a) {
		//Установка тайлов по одному, левой кнопкой мыши
		if (a.getSource() == centerPanel & field != null & buttonActive == 1 & !openDialog) {
			//Принцип аналогичен рисованию в mouseDragged
			//Вычисляем клетку на которой курсор
			int rem = -(field[0][0].getX() % Tile.SIZE);
			int xClick = (-(field[0][0].getX() / Tile.SIZE)) + ((a.getX() + rem) / Tile.SIZE);
			rem = -(field[0][0].getY() % Tile.SIZE);
			int yClick = (-(field[0][0].getY() / Tile.SIZE)) + ((a.getY() + rem) / Tile.SIZE);
			
			setTile(xClick, yClick);
		}
		//Закрытие диаоговых окон на уровне 10
		if (a.getSource() == centerPanel & openDialog) {
			for (Component i: mainPane.getComponentsInLayer(10)) {
				mainPane.remove(i);
			}
			openDialog = false;
		}
		//Заливка
		if (a.getComponent() == centerPanel & currentMode == Mode.fill) {
			for (int i = 0; i < field[0].length; i++) {
				for (int j = 0; j < field.length; j++) {
					field[j][i].setIcon(TilesList.tiles[actTile].icon);
					field[j][i].izm = false;
					field[j][i].number = actTile;
				}
			}
			currentMode = Mode.standart;
		}
	}
	@Override
	public void mouseEntered(MouseEvent a) {
		if (a.getComponent() instanceof EditorButton) {
			EditorButton eb = (EditorButton) a.getComponent();
			eb.selected = true;
		}
		if (a.getComponent() instanceof TileButton) {
			TileButton comp = (TileButton) a.getComponent();
			comp.selected = true;
		}
	}
	@Override
	public void mouseExited(MouseEvent a) {
		if (a.getComponent() instanceof EditorButton) {
			EditorButton eb = (EditorButton) a.getComponent();
			eb.selected = false;
		}
		if (a.getComponent() instanceof TileButton) {
			TileButton comp = (TileButton) a.getComponent();
			comp.selected = false;
		}
	}
	@Override
	public void mousePressed(MouseEvent a) {
		buttonActive = a.getButton();
		x = a.getX(); // Для скролла
		y = a.getY(); //Для скролла
		
		if (a.getComponent() instanceof EditorButton) {
			EditorButton eb = (EditorButton) a.getComponent();
			eb.pressed = true;
		}
		if (a.getComponent() instanceof TileButton) {
			TileButton comp = (TileButton) a.getComponent();
			comp.pressed = true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent a) {
		x = 0;
		y = 0;
		
		if (a.getComponent() instanceof EditorButton) {
			EditorButton eb = (EditorButton) a.getComponent();
			eb.pressed = false;
		}
		if (a.getComponent() instanceof TileButton & openDialog == false & field != null) {
			TileButton comp = (TileButton) a.getComponent();
			comp.pressed = false;
			returnIcon();
			//Наборы тайлов
			if (comp.number == 0) {
				mainPane.add(new TileKit(0), new Integer(10));
				openDialog = true;
			}
			if (comp.number == 1) {
				mainPane.add(new TileKit(1), new Integer(10));
				openDialog = true;
			}
			
			//Инструменты
			//Удаление
			if (comp.number == 995) {
				if (currentMode == Mode.standart) {
					currentMode = Mode.delete;
					selectTile.setIcon(new ImageIcon(Resize.resizeA(comp.deleteI, (int)(43*Main.INC), (int)(43*Main.INC))));
				} else if (currentMode == Mode.delete ){
					currentMode = Mode.standart;
					selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
				}
			}
			//Заливка
			if (comp.number == 996) {
				currentMode = Mode.fill;
			}
			//Установка перемещений
			if (comp.number == 997) {
				if (currentMode == Mode.standart) {
					//Первое нажатие на кнопку включает окно, если все успешно то и соответствующий режим
					transDialog = new TransitDialog(comp);
					mainPane.add(transDialog, new Integer(10));
					openDialog = true;
				} else if (currentMode == Mode.transit) {
					//Повторное не открывает окно, но отключает режим
					currentMode = Mode.standart;
					selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
				}
			}
			//Установка монстров
			if (comp.number == 998) {
				if (currentMode == Mode.standart) {
					MonsterDialog md = new MonsterDialog(comp);
					md.addMouseListener(this);
					mainPane.add(md, new Integer(10));
					openDialog = true;
				} else if (currentMode == Mode.monster) {
					currentMode = Mode.standart;
					selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
				}
			}
			//Включение или отключение предпоказа тайлов
			if (comp.number == 999) {
				draw = (draw == true) ? false: true; //Если выражение в скобках равно то присваивается false иначе true (тенарный оператор)
			}
		} else if (a.getComponent() instanceof TileButton) {
			//На кнопку нажали но условия не соответствуют, просто сменить вид конпки
			TileButton comp = (TileButton) a.getComponent();
			comp.pressed = false;
		}
	}
	
	private class Repainter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			Editor.this.repaint();
		}
	}

	AnimatedIcon ai;
	@Override
	public void run() {
		while(true) {
			try {
			if (field != null) {
				for (int i = 0; i < field[0].length; i++) {
					for (int j = 0; j < field.length; j++) {
						if (field[j][i].anim == true) {
							((AnimatedIcon) field[j][i].getIcon()).frameChange();
						}
					}
				}
			}
			} catch (Exception e) {}
			Sleep.sleep(100);
		}
	}
	
}
