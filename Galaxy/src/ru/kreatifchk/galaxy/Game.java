package ru.kreatifchk.galaxy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable, ActionListener, Serializable {
	
	//Сделать чтоб империи могли скидывать уровень бунта
	//Сделать определние богатства империи (+)
	//Сделать чтоб в некоторых случаях империи объъединялись (+)
	//Сделать чтоб у определнных империи было определнное количество денег (+)
	//Добавить счетчик доминантной империи (то есть накладывать санкции на тех кто долго доминирует)
	//Общее количество клеток - 504
	
	static Sector[][] sector = new Sector[28][18]; //37 21
	volatile Empery[] emp = new Empery[15];
	Boolean[] empAlive = new Boolean[emp.length]; //какие империи живы
	RiotCoor[] riotCoor = new RiotCoor[504];
	JLabel[] lid = new JLabel[3];
	
	int empCount = emp.length - 1; //Количество империй
	int speed = 250;
	static int x = sector.length - 1, y = sector[0].length-1; //Количество клеток 26 20
	
	int idEmp;
	String name; //Для вывода в спец. панели
	String idX, xX, yX, tX, ttX;
	String point; //Для вывода количества клеток
	String mon; //Для вывода денег
	String cov; //Для вывода союза
	
	boolean paused = false;
	boolean infA;
	boolean rashB = false, objB = false;
	
	ImageIcon spDown = new ImageIcon(getClass().getResource("res/SpeedDown.png"));
	ImageIcon pauseI = new ImageIcon(getClass().getResource("res/pause.png"));
	ImageIcon spUp = new ImageIcon(getClass().getResource("res/SpeedUp.png"));
	
	
	Border br = BorderFactory.createLineBorder(Color.GRAY, 2);
	Border brPanel = BorderFactory.createLineBorder(Color.BLACK, 3);
	
	JLabel panel = new JLabel();
	JLabel rightP = new JLabel();
	JLayeredPane osn = new JLayeredPane(); //Для определения местоположения щелчка
	JLabel inf = new Information(); //На нем будет информация о империи
	JLabel textL = new JLabel("Лидеры:"); //Просто слово - лидеры
	JButton speedDown = new JButton();
	JButton speedUp = new JButton();
	JButton pause = new JButton();
	JButton test = new JButton(); //Для разных целей
	JCheckBox rash = new JCheckBox("Расширенный режим");
	JCheckBox obj = new JCheckBox("Объединение империй");
	
	
	Mouse ml = new Mouse();
	
	
	Thread game = new Thread(this);
	Thread riot = new Thread(new Riot());
	Thread army = new Thread(new Army());
	Timer t = new Timer(20, this);
	
	Random r = new Random();
	
	public Game() {
		super("Galaxy");
		setLayout(null);
		
		table();
		
		t.start();
		game.start();
		riot.start();
		army.start();
		
		genEmpery();
		mapFill();
		lidersFill();
		mode();
		
		panel.setBounds(0, 540, 842, 80);
		panel.setOpaque(true);
		panel.setBackground(Color.GRAY);
		panel.setBorder(brPanel);
		
		rightP.setBounds(840, 0, 254, 620);
		rightP.setOpaque(true);
		rightP.setBackground(Color.LIGHT_GRAY);
		rightP.setBorder(brPanel);
		
		speedDown.setBounds(10, 10, 100, 60);
		speedDown.setIcon(spDown);
		speedDown.setBorderPainted(false);
		speedDown.setBackground(Color.GRAY);
		speedDown.addActionListener(new Listener());
		
		pause.setBounds(120, 9, 40, 60);
		pause.setIcon(pauseI);
		pause.setBorderPainted(false);
		pause.setBackground(Color.GRAY);
		pause.addActionListener(new Listener());
		
		speedUp.setBounds(170, 10, 100, 60);
		speedUp.setIcon(spUp);
		speedUp.setBorderPainted(false);
		speedUp.setBackground(Color.GRAY);
		speedUp.addActionListener(new Listener());
		
		test.setBounds(280, 10, 60, 60);
		test.addActionListener(new Listener());
		
		//osn.setBounds(0, 0, 840, 540);
		osn.setBounds(0, 0, 1100, 649);
		osn.addMouseListener(ml);
		
		//inf.setBounds(269, 89, 302, 361); //300, 104, 243, 334
		inf.setBounds(269, 89, 382, 431);
		//inf.setOpaque(true);
		//inf.setBackground(new Color(192, 192, 192));
		
		textL.setBounds(90, 10, 80, 20);
		Font ff = new Font("Times new Roman", Font.BOLD, 20);
		Font fx = new Font("Times new Roman", Font.BOLD, 18);
		textL.setFont(ff);
		textL.setForeground(Color.BLACK);
		
		lid[0].setBounds(5, 40, 300, 20);
		lid[0].setFont(fx);
		lid[0].setForeground(Color.BLACK);
		lid[1].setBounds(5, 70, 300, 20);
		lid[1].setFont(fx);
		lid[1].setForeground(Color.BLACK);
		lid[2].setBounds(5, 100, 300, 20);
		lid[2].setFont(fx);
		lid[2].setForeground(Color.BLACK);
		
		rash.setBounds(688, 10, 150, 15);
		rash.setBackground(Color.GRAY);
		rash.setForeground(Color.YELLOW);
		rash.setSelected(rashB);
		rash.addItemListener(new Listener());
		
		obj.setBounds(678, 30, 160, 15);
		obj.setBackground(Color.GRAY);
		obj.setForeground(Color.YELLOW);
		obj.setSelected(objB);
		obj.addItemListener(new Listener());
		
		add(panel);
		add(rightP);
		add(osn);
		rightP.add(textL);
		rightP.add(lid[0]);
		rightP.add(lid[1]);
		rightP.add(lid[2]);
		panel.add(speedDown);
		panel.add(pause);
		panel.add(speedUp);
		panel.add(test);
		panel.add(rash);
		panel.add(obj);
	}

	private void mode() {
		//Образец Сериализации
		//Сериализовать можно все: переменные, классы(точнее их объекты)
		try(ObjectInputStream os = new ObjectInputStream(new FileInputStream("person.dat"))) {
			try {
				rashB = (Boolean) os.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try(ObjectInputStream os = new ObjectInputStream(new FileInputStream("person1.dat"))) {
			try {
				objB = (Boolean) os.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Устанавливает клетки
	private void table() {
		int x = 0, y = 0;
		for (int i = 0; i <= Game.y; i++) {
			for (int j = 0; j <= Game.x; j++) {
				sector[j][i] = new Sector();
				sector[j][i].setBounds(x, y, 30, 30); //50 50
				sector[j][i].setOpaque(true);
				sector[j][i].setBackground(Color.WHITE);
				sector[j][i].setBorder(br);
				x += 30;
				osn.add(sector[j][i], 1);
			}
			x = 0;
			y += 30;
		}
	}

	//Генерирует рандомный цвет
	private int color() {
		int x;
		x = r.nextInt(255);
		return x;
	}
	
	//Генерирует империи - первую клетку
	private void genEmpery() {
		for (int i = 0; i <= empCount; i++) {
			int x = r.nextInt(Game.x+1), y = r.nextInt(Game.y+1);
			emp[i] = new Empery(color(), color(), color());
			emp[i].id = i+1;
			emp[i].alive = true;
			emp[i].name = Name.name();
			emp[i].money = 1000;
			
			sector[x][y].setBackground(emp[i].cl);
			sector[x][y].idControl = i+1;
			sector[x][y].army = 100;
		}
	}
	
	//Создает для каждой империи карту(массив) контроля
	private void mapFill() {
		for (int i = 0; i <= empCount; i++) {
			for (int ii = 0; ii <= Game.y; ii++) {
				for (int j = 0; j <= Game.x; j++) {
					if (sector[j][ii].idControl == emp[i].id) {
						emp[i].controlMap[j][ii] = 1;
					} else {
						emp[i].controlMap[j][ii] = 0;
					}
				}
			}
		}
	}

	//Только для избежанияя NullPointerException с Лидерами
	private void lidersFill() {
		lid[0] = new JLabel();
		lid[1] = new JLabel();
		lid[2] = new JLabel();
	}
	
	//Здесь будет происходить движений империй
	@Override
	public void run() {
		Sleep.sleep(500);
		while (true) {
			if (paused == false) {
				for (int i = 0; i <= empCount; i++) {
					for (int ii = 0; ii <= Game.y; ii++) {
						for (int j = 0; j <= Game.x; j++) {
							if (sector[j][ii].idControl == emp[i].id) {
								int direction = r.nextInt(4);
								
								if (direction == 0) {
									//Вверх
									if (ii - 1 >= 0) { 
										if (rashB == true) {
											if (attack(j, ii, 0) == true) {
												sector[j][ii-1].army = 0;
												sector[j][ii-1].money = 0;
												sector[j][ii-1].idControl = emp[i].id;
												sector[j][ii-1].setBackground(emp[i].cl);
											}
										} else {
											sector[j][ii-1].idControl = emp[i].id;
											sector[j][ii-1].setBackground(emp[i].cl);
										}
									} else {
										direction = 1;
									}
								}
								
								if (direction == 1) {
									//Вниз
									if (ii + 1 <= Game.y) {
										if (rashB == true) {
											if (attack(j, ii, 1) == true) {
												sector[j][ii+1].money = 0;
												sector[j][ii+1].army = 0;
												sector[j][ii+1].idControl = emp[i].id;
												sector[j][ii+1].setBackground(emp[i].cl);
											}
										} else {
											sector[j][ii+1].idControl = emp[i].id;
											sector[j][ii+1].setBackground(emp[i].cl);
										}
									} else {
										direction = 2;
									}
								}
								
								if (direction == 2) {
									//Влево
									if (j - 1 >= 0) {
										if (rashB == true) {
											if (attack(j, ii, 2) == true) {
												sector[j-1][ii].money = 0;
												sector[j-1][ii].army = 0;
												sector[j-1][ii].idControl = emp[i].id;
												sector[j-1][ii].setBackground(emp[i].cl);
											}
										} else {
											sector[j-1][ii].idControl = emp[i].id;
											sector[j-1][ii].setBackground(emp[i].cl);
										}
									} else {
										direction = 3;
									}
								}
								
								if (direction == 3) {
									//Вправо
									if (j + 1 <= Game.x) {
										if (rashB == true) {
											if (attack(j, ii, 3) == true) {
												sector[j+1][ii].money = 0;
												sector[j+1][ii].army = 0;
												sector[j+1][ii].idControl = emp[i].id;
												sector[j+1][ii].setBackground(emp[i].cl);
											}
										} else {
											sector[j+1][ii].idControl = emp[i].id;
											sector[j+1][ii].setBackground(emp[i].cl);
										}
									} else {
										direction = 0;
									}
								}
							}
						}
					}
					//Sleep.sleep(50);
				}
				alive();
				count();
				liders();
				if (objB == true) {
					joining();
				}
			}
			Sleep.sleep(speed);
		}
	}
	
	//Осуществляет атаку
	private boolean attack(int j, int i, int direction) {
		boolean x = false;
		if (sector[j][i].army <= 0) {
			x = false;
		} else {
			if (direction == 0) {
				if (sector[j][i].army >= sector[j][i-1].army) {
					sector[j][i].army -= sector[j][i-1].army;
				}
			} else if (direction == 1) {
				if (sector[j][i].army >= sector[j][i+1].army) {
					sector[j][i].army -= sector[j][i+1].army;
				} 
			} else if (direction == 2) {
				if (sector[j][i].army >= sector[j-1][i].army) {
					sector[j][i].army -= sector[j-1][i].army;
				}
			} else if (direction == 3) {
				if (sector[j][i].army >= sector[j+1][i].army) {
					sector[j][i].army -= sector[j+1][i].army;
				}
			}
			/*for (int k = 0; k <= sector[j][i].army; k++) {
				if (sector[j][i].army > 0) {
					int p = r.nextInt(2)+1;
					if (p == 1) {
						sector[j][i].army--;
					}
					
					if (direction == 0) {
						sector[j][i-1].army--;
					} else if (direction == 1) {
						sector[j][i+1].army--;
					} else if (direction == 2) {
						sector[j-1][i].army--;
					} else if (direction == 3) {
						sector[j+1][i].army--;
					}
				}
			}*/
			x = true;
		}
		return x;
	}
	
	//Организовывает слитие государств
	private void joining() {
		for (int i = 0; i <= empCount; i++) {
			for (int j = 0; j <= empCount; j++) {
				int c = emp[i].stars;
				if (emp[j].stars >= c * 2 & emp[j].stars < 450) {
					if (r.nextInt(2600) >= 2580) {
						for (int iq = 0; iq <= Game.y; iq++) {
							for (int jq = 0; jq <= Game.x; jq++) {
								if (sector[jq][iq].idControl == emp[i].id) {
									sector[jq][iq].idControl = emp[j].id;
									sector[jq][iq].setBackground(emp[j].cl);
								}
							}
						}
					}
				}
			}
		}
	}
	
	//Проверяет жива ли еще империя (есть ли клетки)
	private void alive() {
		boolean al;
		for (int q = 0; q <= empCount; q++) {
			al = false;
			for (int i = 0; i <= Game.y; i++) {
				for (int j = 0; j <= Game.x; j++) {
					if (sector[j][i].idControl == emp[q].id) {
						j = Game.x;
						i = Game.x;
						al = true;
					}
				}
			}
			if (al != true) {
				emp[q].alive = false;
				//emp[q].covenant = 100;
			}
		}
	}
	
	//Подсчитывает количество клеток у каждой империи и уведичивает бунты
	private void count() {
		for (int q = 0; q <= empCount; q++) {
			emp[q].stars = 0;
			for (int i = 0; i <= Game.y; i++) {
				for (int j = 0; j <= Game.x; j++) {
					if (sector[j][i].idControl == emp[q].id) {
						emp[q].stars++;
					}
				}
			}
			int bunt = 0;
			if (emp[q].stars >= 20 & emp[q].stars <= 40) {
				bunt = r.nextInt(3)+1;
			}
			else if (emp[q].stars >= 41 & emp[q].stars <= 60) {
				bunt = r.nextInt(4) + 3;
			}
			else if (emp[q].stars >= 61 & emp[q].stars <= 80) {
				bunt = r.nextInt(5) + 5;
			}
			else if (emp[q].stars >= 81  & emp[q].stars <= 100) {
				bunt = r.nextInt(6) + 7;
			}
			else if (emp[q].stars >= 101 & emp[q].stars <= 140) {
				bunt = r.nextInt(6) + 10;
			}
			else if (emp[q].stars >= 141) {
				bunt = r.nextInt(6) + 13;
			}
			emp[q].riot += bunt; //Поднимает уровень бунта
		}
	}
	
	//Ищет лидеров
	private void liders() {
		int x = 90;
		String xx = "";
		int[] lidN = new int[empCount+1];
		String[] lidNm = new String[empCount+1];
		for (int i = 0; i <= empCount; i++) {
			lidN[i] = emp[i].stars;
			lidNm[i] = emp[i].name;
		}
		
		for (int i = 0; i <= empCount + 10; i++) {
			for (int j = 0; j <= empCount; j++) {
				if (j < empCount && lidN[j] > lidN[j+1]) {
					x = lidN[j];
					lidN[j] = lidN[j+1];
					lidN[j+1] = x;
					
					xx = lidNm[j];
					lidNm[j] = lidNm[j+1];
					lidNm[j+1] = xx;
				}
			}
		}
		lid[0].setText(lidNm[lidNm.length-1] + " - " + lidN[lidN.length-1]);
		lid[1].setText(lidNm[lidNm.length-2] + " - " + lidN[lidN.length-2]);
		lid[2].setText(lidNm[lidNm.length-3] + " - " + lidN[lidN.length-3]);
	}
	
	//Меняет клетки на бунтующих
	private void riot(int j, int i) {
		for (int d = 0; d <= empCount; d++) {
			if (emp[d].alive == false) {
				int id = emp[d].id;
				//emp[d] = null;
				emp[d] = new Empery(color(), color(), color());
				emp[d].name = Name.name();
				emp[d].id = id;
				emp[d].alive = true;
				
				sector[j][i].idControl = id;
				sector[j+1][i].idControl = id;
				sector[j-1][i].idControl = id;
				sector[j][i+1].idControl = id;
				sector[j][i-1].idControl = id;
				sector[j+1][i+1].idControl = id;
				sector[j+1][i-1].idControl = id;
				sector[j-1][i+1].idControl = id;
				sector[j-1][i-1].idControl = id;
				
				sector[j+1][i].army += 10;
				sector[j-1][i].army += 10;
				sector[j][i-1].army += 10;
				sector[j][i+1].army += 10;
				sector[j-1][i-1].army += 10;
				sector[j+1][i-1].army += 10;
				sector[j-1][i+1].army += 10;
				sector[j+1][i+1].army += 10;
				
				sector[j][i].setBackground(emp[d].cl);
				sector[j+1][i].setBackground(emp[d].cl);
				sector[j][i+1].setBackground(emp[d].cl);
				sector[j-1][i].setBackground(emp[d].cl);
				sector[j][i-1].setBackground(emp[d].cl);
				sector[j+1][i+1].setBackground(emp[d].cl);
				sector[j-1][i-1].setBackground(emp[d].cl);
				sector[j+1][i-1].setBackground(emp[d].cl);
				sector[j-1][i+1].setBackground(emp[d].cl);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public class Listener implements ActionListener, ItemListener {
		@Override
		public void actionPerformed(ActionEvent a) {
			if (a.getSource() == speedDown) {
				speed += 100;
			}
			if (a.getSource() == speedUp) {
				speed -= 50;
			}
			if (a.getSource() == pause) {
				if (paused == false) {
					paused = true;
					/*for (int i = 0; i <= empCount; i++) {
						if (emp[i].alive == true) {
							System.out.println("Империя " + i + " жива.");
						} else {
							System.out.println("Империя " + i + " не жива.");
						}
					}*/
				} else {
					paused = false;
				}
			}
			if (a.getSource() == test) {
				for (int i = 0; i <= empCount; i++) {
					//System.out.println("Уровень бунта: " + emp[i].riot + "%");
					System.out.println("Количество звезд у империи " + i + " : " + emp[i].stars);
				}
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getItem() == rash) {
				try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
					if (rashB == false) {
						rashB = true;
					} else {
						rashB = false;
					}
					os.writeObject(rashB);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if (e.getItem() == obj) {
				try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("person1.dat"))) {
					if (objB == false) {
						objB = true;
					} else {
						objB = false;
					}
					os.writeObject(objB);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public class Mouse implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent a) {
			int x = a.getX(), y = a.getY();
			x = x / 30;
			y = y / 30;
			int id = sector[x][y].idControl;
			idEmp = id - 1;
			name = emp[id-1].name;
			point = "" + emp[id-1].stars;
			idX = "" + emp[id-1].id;
			xX = "" + x;
			yX = "" + y;
			
			tX = "" + sector[x][y].money;
			ttX = "" + sector[x][y].army;
			
			/*if (emp[id-1].covenant <= empCount) {
				cov = "" + emp[emp[id-1].covenant].name;
			}*/
			mon = "" + emp[id-1].money;
			if (infA == false) {
				//inf.setSize(300, 600);
				osn.add(inf, 1);
				infA = true;
				paused = true;
			} else {
				osn.remove(inf);
				infA = false;
				paused = false;
			}
		}
		@Override
		public void mouseEntered(MouseEvent a) {
		}
		@Override
		public void mouseExited(MouseEvent a) {
		}
		@Override
		public void mousePressed(MouseEvent a) {
		}
		@Override
		public void mouseReleased(MouseEvent a) {
		}
	}
	
	public class Riot implements Runnable {
		int number = 0, el = 0;
		@Override
		//Сделать разные формы (не только квадратные восствния)
		//Сделать чтоб могло быть несколько бунтов одновеменно
		public void run() {
			Sleep.sleep(100);
			while(true) {
				for (int z = 0; z <= empCount; z++) {
					number = 0;
					el = 0;
					if (emp[z].riot >= 100) {
						//Происходит бунт, рождаются империи
						for (int i = 1; i <= Game.y-1; i++) {
							for (int j = 1; j <= Game.x-1; j++) {
								if (sector[j][i].idControl == emp[z].id) {
									//Выбирает все удачные для бунта клетки
									int prov = 0;
									if (j + 1 <= Game.y && sector[j+1][i].idControl == emp[z].id) {
										prov++;
									}
									if (j - 1 >= 0 && sector[j-1][i].idControl == emp[z].id) {
										prov++;
									}
									if (i + 1 >= 0 && sector[j][i+1].idControl == emp[z].id) {
										prov++;
									}
									if (i - 1 >= 0 && sector[j][i-1].idControl == emp[z].id) {
										prov++;
									}
									if (prov >= 4) {
										riotCoor[number] = new RiotCoor(j,i);
										number++;
									}
								}
							}
						}
						//Подчитывает сколько удачных клеток
						for (int i = 0; i <= riotCoor.length - 1; i++) {
							if (riotCoor[i] != null) {
								el++;
							}
						}
						
						if (el != 0) {
							//Если удачные клетки есть
							int b = r.nextInt(el);
							riot(riotCoor[b].x, riotCoor[b].y);
							int pl = r.nextInt(10)+1;
							if (pl <= 3) {
								emp[z].riot = 0;
							} else {
								emp[z].riot = 50;
							}
						}
					}
				}
				//wealth();
				Sleep.sleep(20);
			}
		}
	}
	
	//Увеличивает деньги и армии
	public class Army implements Runnable {
		@Override
		public void run() {
			while (true) {
				if (rashB == true) {
					if (paused == false) {
						try {
							wealth();
							//Подсчитывает кол-во клеток находящихся в войне
							for (int u = 0; u <= empCount; u++) {
								emp[u].varC = 0;
								for (int i = 0; i <= Game.y; i++) {
									for (int j = 0; j <= Game.x; j++) {
										var(u, i, j);
									}
								}
								//System.out.println("Империя " + (u+1) + " воюющих клеток: " + emp[u].varC);
								int pr = 70;
								if (emp[u].stars >= 450) {
									pr = 40;
								}
								int moneyV = Interest.interest(emp[u].money, pr), moneyM = emp[u].money - moneyV;
								emp[u].moneyV = moneyV;
								emp[u].moneyM = moneyM;
								//System.out.println("Империя " + (u+1) + ". Денег: " + emp[u].money + ". На войну " + moneyV + ". На мир: " + moneyM + ". Военных терр: " + emp[u].varC + ". Мирных терр: " + (emp[u].stars - emp[u].varC));
							}
							

							for (int u = 0; u <= empCount; u++) {
								double mVX = (double) emp[u].moneyV; //Деньги на войну
								double mMX = (double) emp[u].moneyM; //Деньги на мир
								int mV = Interest.roundMin(mVX / emp[u].varC); //Сумма на кажду клетку в войне
								int mM = Interest.roundMin(mMX / (emp[u].stars - emp[u].varC));
								for (int i = 0; i <= Game.y; i++) {
									for (int j = 0; j <= Game.x; j++) {
										if (sector[j][i].idControl == emp[u].id) {
											if (sector[j][i].var == true) {
												//Если клетка в состаянии войны
												sector[j][i].money += mV;
												emp[u].money -= mV;
												if (sector[j][i].money >= 8) {
													sector[j][i].army += 1;
													sector[j][i].money -= 8;
												}
											} else {
												//Если клетка в мирном состаянии
												sector[j][i].money += mM;
												emp[u].money -= mM;
												if (sector[j][i].money >= 10) {
													sector[j][i].army += 1;
													sector[j][i].money -= 10;
												}
											}
										}
									}
								}
							}
						} catch (NullPointerException e) {
							//e.printStackTrace();
						}
					}
				}
				Sleep.sleep(speed);
			}
		}
	}
	
	//Подсчитывает кол-во клеток находящихся в войне
	private void var(int u, int i, int j) {
		if (sector[j][i].idControl == emp[u].id) {
			if (j+1 <= Game.x && sector[j+1][i].idControl != 0
				&& sector[j+1][i].idControl != emp[u].id) {
				//System.out.println("1: " + i + " " + j);
				emp[u].varC ++;
				sector[j][i].var = true;
			}
			else if (j-1 >= 0 && sector[j-1][i].idControl != 0
					 && sector[j-1][i].idControl != emp[u].id) {
				//System.out.println("2: " + i + " " + j);
				emp[u].varC ++;
				sector[j][i].var = true;
			}
			else if (i+1 <= Game.y && sector[j][i+1].idControl != 0 
					 && sector[j][i+1].idControl != emp[u].id) {
				//System.out.println("1: " + i + " " + j);
				emp[u].varC ++;
				sector[j][i].var = true;
			}
			else if (i-1 >= 0 && sector[j][i-1].idControl != 0 
					 && sector[j][i-1].idControl != emp[u].id) {
				//System.out.println("1: " + i + " " + j);
				emp[u].varC ++;
				sector[j][i].var = true;
			}
			else {
				sector[j][i].var = false;
			}
		}
	}
	
	//Увеличивает деньги империй
	private void wealth() {
		for (int i = 0; i <= empCount; i++) {
			emp[i].money += emp[i].stars;
		}
	}
	
	
	public class Information extends JLabel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 1, 382, 431);  //301, 360
			g2d.setColor(new Color(192, 192, 192));
			g2d.fillRect(1, 2, 381, 430); //300, 359
			g2d.setColor(Color.BLACK);
			Font f = new Font("Times new Roman", Font.PLAIN, 22);
			g2d.setFont(f);
			g2d.drawString("x: " + xX + " y:" + yX, 5, 20);
			g2d.drawString(name, 5, 60); //40
			g2d.drawString("Количество территорий: " + point, 5, 100);
			g2d.drawString("Деньги империи: " + mon, 5, 140);
			g2d.drawString("ID: " + idX, 5, 180);
			
			g2d.drawString("Деньги региона: " + tX, 5, 220);
			g2d.drawString("Армия региона: " + ttX, 5, 260);
			//g2d.drawString("Cоюз с: " + cov, 5, 120);
			Font ft = new Font("Times new Roman", Font.BOLD, 22);
			g2d.setFont(ft);
			g2d.drawString("Правящие партии", 100, 300);
			
			Font ft2 = new Font("Times new Roman", Font.PLAIN, 22);
			g2d.setFont(ft2);
			
			int x = 340;
			for (int i = 0; i < emp[idEmp].pt.length; i++) {
				g2d.drawString(emp[idEmp].pt[i].getName() + "   " + emp[idEmp].pt[i].getTrust() + " % доверия", 5, x);
				x += 40;
			}
		}
	}
}
