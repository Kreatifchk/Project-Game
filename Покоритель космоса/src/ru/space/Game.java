package ru.space;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import ru.space.Player.PlayerListener;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener {
	
	//static ImageIcon stone, grass, water, sand;
	
	static World[][] tile = new World[20][14]; //Всео 280
	static int[][] pole = new int[20][14]; //Текущий квадрат
	static int[][] poleDown = new int[20][14];
	static int[][] poleUp = new int[20][14];
	static int[][] poleLeft = new int[20][14];
	static int[][] poleRigth = new int[20][14];
	
	static JLabel fps = new JLabel();
 	
	static Player p = new Player();
	static Planet pl = new Planet();
	
	Timer t = new Timer(20, this);
	//Thread fp = new Thread(new Fps());
	
	static int numberP = 1;
	static int xSquare = 0, ySquare = 0; //На каком квадрате игрок
	
	static boolean inv = false; //Видимость FPS
	
	File planet = new File("res/planet" + numberP + "/");
	File config = new File("res/Config.dat");
	static File square;
	File configPlanet = new File("res/planet" + numberP + "/config.pl");
	Scanner s;
	
	PrintWriter pw;
	static FileWriter pw1;
	static FileReader fr;
	
	File f1;
	
	static Random r = new Random();
	
	public Game() {
		super("Game");
		setLayout(null);
		setFocusable(true);
		Planet.genPlanet(); //Генерирует тип планеты
		//init();
		
		tileFill();
		//Test rr = new Test();
		//rr.execute();
		generatePlayer();
		addKeyListener(new PlayerListener());
		addKeyListener(new Key());
		t.start();
	}
	
	//Генерация  игрока в начале игры
	private void generatePlayer() {
		p.setBounds(0, 0, 48, 48);
		p.setIcon(Player.pDown);
		Player.x = 9;
		Player.y = 6;
		tile[9][6].add(p);
	}
	
	//Движение игрока
	protected static void move(int direction) {
		int currentX = Player.getXP();
		int currentY = Player.getYP();
		
		if (direction == 1) {
			p.setIcon(Player.pDown);
			if (Player.getYP() < 13) {
				//Если игрок не у границы экрана
				if (tile[currentX][currentY+1].solid != true) {
					//Если след. тайл не твердый
					tile[currentX][currentY].remove(p); //Удалить игрока с текущего тайла
					currentY += 1;
					tile[currentX][currentY].add(p); //Создать игрока на следующем тайле
					p.setYP(currentY);
					if (Player.yS == 13) {
						Player.yS = 0;
						generateSquare(1);
					} else {
						Player.yS += 1;
					}
					Player.yA += 1;
				}
			} else {
				//Если у границы экрана
				if (Player.yS == 13) {
					Player.yS = 0;
					generateSquare(1);
				} else {
					Player.yS += 1;
				}
				moveRepaint(1); //движение в новый квадрат
				Player.yA += 1;
			}
		}
		
		if (direction == 2) {
			p.setIcon(Player.pUp);
			if (Player.getYP() > 0) {
				if (tile[currentX][currentY-1].solid != true) {
					tile[currentX][currentY].remove(p);
					currentY -= 1;
					tile[currentX][currentY].add(p);
					p.setYP(currentY);
					if (Player.yS == 0) {
						Player.yS = 13;
						generateSquare(2);
					} else {
						Player.yS -= 1;
					}
					Player.yA -= 1;
				}
			} else {
				if (Player.yS == 0) {
					Player.yS = 13;
					generateSquare(2);
				} else {
					Player.yS -= 1;
				}
				moveRepaint(2);
				Player.yA -= 1;
			}
		}
		if (direction == 3) {
			p.setIcon(Player.pLeft);
			if (Player.getXP() > 0) {
				if (tile[currentX-1][currentY].solid != true) {
					tile[currentX][currentY].remove(p);
					currentX -= 1;
					tile[currentX][currentY].add(p);
					p.setXP(currentX);
					if (Player.xS == 0) {
						Player.xS = 19;
						generateSquare(3);
					} else {
						Player.xS -= 1;
					}
					Player.xA -= 1;
				}
			} else {
				if (Player.xS == 0) {
					Player.xS = 19;
					generateSquare(3);
				} else {
					Player.xS -= 1;
				}
				moveRepaint(3);
				Player.xA -= 1;
			}
		}
		if (direction == 4) {
			p.setIcon(Player.pRight);
			if (Player.getXP() < 19) {
				if (tile[currentX+1][currentY].solid != true) {
					tile[currentX][currentY].remove(p);
					currentX += 1;
					tile[currentX][currentY].add(p);
					p.setXP(currentX);
					if (Player.xS == 19) {
						Player.xS = 0;
						generateSquare(4);
					} else {
						Player.xS += 1;
					}
					Player.xA += 1;
				}
			} else {
				if (Player.xS == 19) {
					Player.xS = 0;
					generateSquare(4);
				} else {
					Player.xS += 1;
				}
				moveRepaint(4);
				Player.xA += 1;
			}
		}
	}
	
	//Генерация нового квадрата
	protected static void generateSquare(int direction) {
		if (direction == 1) {
			//Вниз
			ySquare += 1;
		}
		else if (direction == 2) {
			//Вверх
			ySquare -= 1;
		}
		else if (direction == 3) {
			//Влево
			xSquare -= 1;
		}
		else if (direction == 4) {
			//Вправо
			xSquare += 1;
		}
		
		boolean write = false; //Надо ли записывать в файл
		try {
			square = new File("res/planet1/" + xSquare + "_" + ySquare + ".pl");
			if (square.exists() == true) {
				//Если файл уже существует не перезаписывать
				write = false;
				fr = new FileReader(square);
			} else {
				pw1 = new FileWriter(square, true);
				write = true;
			}
		} catch (IOException e) {
		}
		
		Planet.pole(direction);
		//Заполняет массив с полем (будущим)
		for (int i = 0; i <= tile[0].length - 1; i++) {
			for (int j = 0; j <= tile.length - 1; j++) {
				try {
					if (direction == 1) {
						if (write == true) {
							//poleDown[j][i] = Planet.pole(1);
							pw1.write(poleDown[j][i]);
							pw1.flush();
						} else {
							poleDown[j][i] = fr.read();
						}
					}
					if (direction == 2) {
						if (write == true) {
							//poleUp[j][i] = Planet.pole(2);
							pw1.write(poleUp[j][i]);
							pw1.flush();
						} else {
							poleUp[j][i] = fr.read();
						}
					}
					if (direction == 3) {
						if (write == true) {
							//poleLeft[j][i] = Planet.pole(3);
							pw1.write(poleUp[j][i]);
							pw1.flush();
						} else {
							poleLeft[j][i] = fr.read();
						}
					}
					if (direction == 4) {
						if (write == true) {
							//poleRigth[j][i] = Planet.pole(4);
							pw1.write(poleRigth[j][i]);
							pw1.flush();
						} else {
							poleRigth[j][i] = fr.read();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
				
		try {
			pw1.close();
		} catch (IOException e) {
		}
	}

	//Перерисовка поля при движении
	protected static void moveRepaint(int direction) {
		//Вниз
		if (direction == 1) {
			for (int i = 0; i <= 19; i++) {
				for (int j = 0; j <= 12; j++) {
					tile[i][j].setYTile(tile[i][j].getYtile()+1);
					tile[i][j].setIcon(tile[i][j+1].getIcon());
					tile[i][j].solid = tile[i][j+1].solid;
				}
			}
			for (int i = 0; i <= 19; i++) {
				tile[i][13].setIcon(null);
				pl.iconTile(poleDown[i][Player.yS], i, 13);
			}
		}
		//Вверх
		if (direction == 2) {
			for (int i = 19; i >= 0; i--) {
				for (int j = 13; j >= 1; j--) {
					tile[i][j].setYTile(tile[i][j].getYtile()-1);
					tile[i][j].setIcon(tile[i][j-1].getIcon());
					tile[i][j].solid = tile[i][j-1].solid;
				}
			}
			for (int i = 0; i <= 19; i++) {
				tile[i][0].setIcon(null);
				pl.iconTile(poleUp[i][Player.yS], i, 0);
			}
		}
		//Влево
		if (direction == 3) {
			for (int i = 19; i >= 1; i--) {
				for (int j = 13; j >= 0; j--) {
					tile[i][j].setXTile(tile[i][j].getXtile()-1);
					tile[i][j].setIcon(tile[i-1][j].getIcon());
					tile[i][j].solid = tile[i-1][j].solid;
				}
			}
			for (int i = 0; i <= 13; i++) {
				tile[0][i].setIcon(null);
				pl.iconTile(poleLeft[Player.xS][i], 0, i);
			}
		}
		//Вправо
		if (direction == 4) {
			for (int i = 0; i <= 18; i++) {
				for (int j = 0; j <= 13; j++) {
					tile[i][j].setXTile(tile[i][j].getXtile()+1);
					tile[i][j].setIcon(tile[i+1][j].getIcon());
					tile[i][j].solid = tile[i+1][j].solid;
				}
			}
			for (int i = 0; i <= 13; i++) {
				tile[19][i].setIcon(null);
				pl.iconTile(poleRigth[Player.xS][i], 19, i);
			}
		}
	}
	
	//Заполняет поле тайлами в начале
	private void tileFill() {
		//Test t = new Test();
		//t.execute();
		
		int x = 0;
		int y = 0;
		f1 = new File("res/planet1/0_0.pl");
		boolean write = false;
		if (f1.exists() == true) {
			write = true; //Если файл существует
			try {
				fr = new FileReader(configPlanet);
				Planet.typeP = fr.read();
				fr = new FileReader(f1);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//write = false; //УБРАТЬ!!!
		//Если файла планеты нету сгенерировать новую
		if (write != true) {
			Planet.pole(0);
		}
		
		for (int i = 0; i <= 13; i++) {
			for (int j = 0; j <= 19; j ++) {
				if (write == true) {
					try {
						pole[j][i] = fr.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					//pole[j][i] = Planet.pole();
				}
				tile[j][i] = new World(j, i, false); 
				tile[j][i].setBounds(x, y, 48, 48);
				pl.iconTile(pole[j][i], j, i);
				add(tile[j][i]);
				x += 48;
			}
			y += 48;
			x = 0;
		}
		
		/*if (write = true) {
			try {
				fr.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}*/
		
		//PrintWriter
		//Запись первой планеты
		//write = true; //УБРАТЬ!!!
		if (write != true) {
			for (int i = 0; i <= 13; i++) {
				for (int j = 0; j <= 19; j ++) {
					try {
						pw1 = new FileWriter("res/planet1/0_0.pl", true);
						pw1.write(tile[j][i].type);
						pw1.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				pw1 = new FileWriter(configPlanet);
				pw1.write(Planet.typeP);
				pw1.flush();
				pw1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public class Test extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			int x = 0, y = 0;
			for (int i = 0; i <= 13; i++) {
				for (int j = 0; j <= 19; j ++) {
					pole[j][i] = 2;
					tile[j][i] = new World(j, i, false); 
					tile[j][i].setBounds(x, y, 48, 48);
					pl.iconTile(pole[j][i], j, i);
					//add(tile[j][i]);
					add(dick[j]);
					Sleep.sleep(100);
					x += 48;
				}
				y += 48;
				x = 0;
			}
			return null;
		}
	}*/
	
	public class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			if (key == KeyEvent.VK_D) {
				tileRem();
			}
		}
	}
	
	protected void tileRem() {
		for (int i = 0; i < tile.length; i++) {
			for (int j = 0; j < tile[0].length; j++) {
				remove(tile[i][j]);
				tile[i][j] = null;
				pole[i][j] = 0;
			}
		}
		GeneratePlanet.countWater = 0;
		GeneratePlanet.countThree = 0;
		tileFill();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
}
