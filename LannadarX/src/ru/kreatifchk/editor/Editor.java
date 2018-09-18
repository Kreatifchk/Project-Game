package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

import javax.crypto.Cipher;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Img;
import ru.kreatifchk.tools.Resize;
import ru.kreatifchk.tools.Sleep;

public class Editor extends JFrame implements ActionListener, ChangeListener, MouseListener, MouseMotionListener, Runnable, Serializable {
	
	//Сделать настройку предпоказа тайлов
	//Сделать требование чтоб все тайлы были заполнены
	
	private static final long serialVersionUID = 3108L;
	
	JLabel topPanel, bottomPanel;
	static JLabel centerPanel;
	static JLayeredPane mainPane;

	Dialog dialog; //Диалоговое окно для создания локации
	JLabel transDialog; //Диалоговое окно для трансфера
	EditorButton createButton, openButton, closeButton, saveButton; //Кнопки в верхней панели
	TileButton tileForest, tileCity, fill, delete, transit, monster;
	PointEditor field[][]; //Поле - массив ячеек редактора которые заполняются редактором карты
	TileKit tk; //Окно с выбором тайла из набора
	
	static JLabel selectTile = new JLabel(); //Показывает выбранный тайл или инструмент
	
	JSlider widt, heig; //Слайдеры во всплывающем окне для выбора размера карты
	
	protected static boolean openDialog; //Открыты ли диалоговое ок
	private boolean fillMode; //Если нажали кнопку заливка
	boolean deleteMode; //Если нажали кнопку удаления тайлов
	boolean draw; //Включен ли предпоказ тайлов (не реализовано)
	
	protected enum Mode {standart, fill, delete, transit, monster}; //Вместо boolean, будет доработано позже
	static Mode currentMode = Mode.standart; //Текущий мод
	
	int buttonActive; //Какая кнопка мыши нажата
	
	protected static Player.Direction dirTransit = Player.Direction.down; //Выбранное направление перемещения
	protected static String transitLocation; //В какую локацию перемещение
	protected static int xTrans = 0, yTrans = 0; //На какое поле перемещается персонаж
	
	Timer t = new Timer(20, new Repainter());
	
	static int actTile; //Выбранный тайл
	
	Thread animated = new Thread(this);
	
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	public Editor() {
		setTitle("Lannadar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize((int)(960*Main.INC), (int)(672*Main.INC)+Main.insets.top);
		setSize(Main.WIDTH, Main.HEIGHT);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);	
		setIconImage(Menu.icon);
		arrangement();
		
		t.start();
		animated.setDaemon(true);
		animated.start(); //Включить поток анимирования тайлов
		
		//Локализация JFileChooser
		UIManager.put("FileChooser.fileNameHeaderText", "Имя");
		UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
		UIManager.put("FileChooser.homeFolderToolTipText", "Домой");
		UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
		UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
		UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
		UIManager.put("FileChooser.upFolderToolTipText", "Вверх");
		UIManager.put("FileChooser.newFolderToolTipText", "Создать папку");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Подробно");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
		UIManager.put("FileChooser.viewMenuLabelText", "Настроить вид");
		UIManager.put("FileChooser.refreshActionLabelText", "Обновить");
		UIManager.put("FileChooser.newFolderActionLabelText", "Новая папка");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлов");
		UIManager.put("FileChooser.lookInLabelText", "Смотреть в");
		UIManager.put("FileChooser.fileNameHeaderText", "Имя");
		UIManager.put("FileChooser.openButtonText", "Открыть");
		UIManager.put("FileChooser.saveButtonText", "Сохранить");
		UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
		UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
		UIManager.put("FileChooser.cancelButtonText", "Отмена");
		UIManager.put("FileChooser.cancelButtonText", "Отмена");
		UIManager.put("FileChooser.openDialogTitleText", "Открыть");
		UIManager.put("FileChooser.saveDialogTitleText", "Сохранить");
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
		
		delete = new TileButton(996);
		delete.setLocation((int)(140*Main.INC), 0);
		delete.addMouseListener(this);
		bottomPanel.add(delete);
		
		fill = new TileButton(997);
		fill.setLocation((int)(200*Main.INC), 0);
		fill.addMouseListener(this);
		bottomPanel.add(fill);
		
		transit = new TileButton(998);
		transit.setLocation((int)(260*Main.INC), 0);
		transit.addMouseListener(this);
		bottomPanel.add(transit);
		
		monster = new TileButton(999);
		monster.setLocation((int)(320*Main.INC), 0);
		monster.addMouseListener(this);
		bottomPanel.add(monster);
	}
	
	// Диалоговое окно для кнопки: создать локацию
	@SuppressWarnings("serial")
	private class Dialog extends JLabel {
		JButton create = new JButton("Создать");
		JButton cancel = new JButton("Отмена");
		JLabel widtValue = new JLabel("20");
		JLabel heigValue = new JLabel("12");
		Image bg = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/dialogBackground.png"));
		public Dialog() {
			setSize((int)(270*Main.INC), (int)(220*Main.INC));
			
			create.setBounds((int)(30*Main.INC), (int)(175*Main.INC), (int)(85*Main.INC), (int)(35*Main.INC));
			create.addActionListener(Editor.this);
			add(create);
			cancel.setBounds((int)(145*Main.INC), (int)(175*Main.INC), (int)(85*Main.INC), (int)(35*Main.INC));
			cancel.addActionListener((e) -> {mainPane.remove(this); openDialog = false;});
			add(cancel);
			
			widtValue.setSize((int)(18*Main.INC), (int)(18*Main.INC));
			Center.cnt(widtValue, this, (int)(8*Main.INC));
			add(widtValue);
			widt = new JSlider(20, 120, 20);
			widt.setBounds((int)(13*Main.INC), (int)(35*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
			widt.setMajorTickSpacing(10);
			widt.setMinorTickSpacing(5);
			widt.setPaintTicks(true);
			widt.setPaintLabels(true);
			widt.addChangeListener(Editor.this);
			widt.setUI(new LannadarSliderUI(widt));
			add(widt);
			
			/*LannadarSlider lnd = new LannadarSlider(20, 120, 12);
			lnd.setBounds((int)(13*Main.INC), (int)(105*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
			lnd.setMajorTickSpacing(10);
			lnd.setMinorTickSpacing(5);
			add(lnd);*/
			heigValue.setSize((int)(18*Main.INC), (int)(18*Main.INC));
			Center.cnt(heigValue, this, (int)(78*Main.INC));
			add(heigValue);
			heig = new JSlider(12, 120, 12);
			heig = new JSlider(13, 120, 13);
			heig.setBounds((int)(13*Main.INC), (int)(105*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
			heig.setMajorTickSpacing(10);
			heig.setMinorTickSpacing(5);
			heig.setPaintTicks(true);
			heig.setPaintLabels(true);
			heig.addChangeListener(Editor.this);
			heig.setUI(new LannadarSliderUI(heig));
			add(heig);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bg, 0, 0, null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		//Открытие всплывающего окна создания карты
		if (a.getSource() == createButton & openDialog == false) {
			openDialog = true;
			
			dialog = new Dialog();
			Center.cnt(dialog, mainPane);
			dialog.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			mainPane.add(dialog, new Integer(10));
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
			
			//Задаем вопрос про перезапись файла
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
		//Создание новой пустой карты в диалоговом окне
		if (dialog != null && a.getSource() == dialog.create) {
			//Перед созданием новой карты очистка старой
			field = null;
			centerPanel.removeAll();
			//Создание карты заданных размеров
			mainPane.remove(dialog);
			
			openDialog = false;
			int x = Integer.parseInt(dialog.widtValue.getText()), y = Integer.parseInt(dialog.heigValue.getText()); //Кол-во полей по x и по н
			field = new PointEditor[x][y];
			int xPoint = (int)(4*Main.INC), yPoint = 0;
			for (int i = 0; i < field[0].length; i++) {
				for (int j = 0; j < field.length; j++) {
					field[j][i] = new PointEditor(j, i);
					field[j][i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
					field[j][i].setBounds(xPoint, yPoint, (int)(48*Main.INC), (int)(48*Main.INC));
					field[j][i].setOpaque(true);
					field[j][i].setBackground(new Color(255, 255, 255, 240));
					centerPanel.add(field[j][i]);
					xPoint += (int)(48*Main.INC);
				}
				yPoint += (int)(48*Main.INC);
				xPoint = (int)(4*Main.INC);
			}
			
			repaint();
		}
	}
	
	//Открытие карты (сделано отдельно по причине открытия карты внешне
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
						System.out.println("null");
					}
				}
				x = 0;
				y += Tile.SIZE;
			}
			//Удаляем временный файл
			tempFile.deleteOnExit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
	
	/** Установка тайла в редакторе */
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
			if (deleteMode == false & currentMode == Mode.standart) {
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
			} else if (deleteMode == true) {
				//Режим удаления тайлов
				field[xClick][yClick].setIcon(null);
				field[xClick][yClick].number = -1;
			} else if (currentMode == Mode.transit) {
				//Установка перемещений между локациями
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
		}
	}

	//Лисенер для JSLider во всплывающем окне
	@Override
	public void stateChanged(ChangeEvent a) {
		if (a.getSource() == widt) {
			dialog.widtValue.setText(widt.getValue() + "");
		}
		if (a.getSource() == heig) {
			dialog.heigValue.setText(heig.getValue() + "");
		}
		repaint();
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
		if (a.getSource() == centerPanel & field != null & buttonActive == 1 & openDialog == false & currentMode == Mode.standart) {
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
		if (field != null & deleteMode == false & openDialog == false & draw == false & currentMode == Mode.standart) {
			//Если какой-то тайл менял иконку возвращаем ему истинную иконку
			for (int i = 0; i < field[0].length; i++) {
				for (int j = 0; j < field.length; j++) {
					if (field[j][i].izm == true) {
						field[j][i].setIcon(field[j][i].oldIcon);
						field[j][i].oldIcon = null;
						field[j][i].izm = false;
					}
				}
			}
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
		if (a.getSource() == centerPanel & field != null & buttonActive == 1 & openDialog == false) {
			//Принцип аналогичен рисованию в mouseDragged
			//Вычисляем клетку на которой курсор
			int rem = -(field[0][0].getX() % Tile.SIZE);
			int xClick = (-(field[0][0].getX() / Tile.SIZE)) + ((a.getX() + rem) / Tile.SIZE);
			rem = -(field[0][0].getY() % Tile.SIZE);
			int yClick = (-(field[0][0].getY() / Tile.SIZE)) + ((a.getY() + rem) / Tile.SIZE);
			
			setTile(xClick, yClick);
		}
		//Закрытие диаоговых окон на уровне 10
		if (a.getSource() == centerPanel & openDialog == true) {
			for (Component i: mainPane.getComponentsInLayer(10)) {
				mainPane.remove(i);
			}
			openDialog = false;
		}
		//Заливка
		if (a.getComponent() == centerPanel & fillMode == true) {
			for (int i = 0; i < field[0].length; i++) {
				for (int j = 0; j < field.length; j++) {
					field[j][i].setIcon(TilesList.tiles[actTile].icon);
					field[j][i].number = actTile;
				}
			}
			fillMode = false;
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
		if (a.getComponent() instanceof TileButton & openDialog == false) {
			TileButton comp = (TileButton) a.getComponent();
			comp.pressed = false;
			if (comp.number == 0) {
				tk = new TileKit(0);
				Center.cnt(tk, mainPane);
				mainPane.add(tk, new Integer(10));
				openDialog = true;
			}
			if (comp.number == 1) {
				tk = new TileKit(1);
				Center.cnt(tk, mainPane);
				mainPane.add(tk, new Integer(10));
				openDialog = true;
			}
			
			//Инструменты
			if (comp.number == 996) {
				if (deleteMode == false) {
					deleteMode = true;
					selectTile.setIcon(new ImageIcon(Resize.resizeA(comp.deleteI, (int)(43*Main.INC), (int)(43*Main.INC))));
				} else {
					deleteMode = false;
					selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
				}
			}
			//Заливка
			if (comp.number == 997) {
				fillMode = true;
			}
			//Установка перемещений
			if (comp.number == 998) {
				if (currentMode == Mode.standart & field != null) {
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
			if (comp.number == 999) {
				if (currentMode == Mode.standart) {
					MonsterDialog md = new MonsterDialog(comp);
					md.addMouseListener(this);
					mainPane.add(md, new Integer(10));
					openDialog = true;
				}
			}
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
							//System.out.println(j + " " + i);
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
