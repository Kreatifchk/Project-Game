package strategy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener {
	
	static JLayeredPane jlp = new JLayeredPane();
	
	static JLabel downPanel = new JLabel();
	static JLabel miniMap = new JLabel();
	static CenterPanel downCenter;
	static RigthPanel downInf;
	
	//JLabel base = new JLabel();
	
	Timer t = new Timer(20, this);
	
	static BcTile[][] bcTiles = new BcTile[15][15]; //������ �����
	
	static ArrayList<Empery> emp = new ArrayList<Empery>();
	static ArrayList<Town> town = new ArrayList<Town>();
	
	static int turn = 1; //����� ����
	
	public Game() {
		super("���������");
		setLayout(null);
		jlp.setBounds(0, 0, 1000, 700);
		jlp.setLayout(null);
		jlp.addMouseListener(new Mouse());
		jlp.addMouseMotionListener(new Mouse());
		add(jlp);
		
		setFocusable(true);
		addKeyListener(new Key());
		
		@SuppressWarnings("unused")
		Images img = new Images();
		
		init();
		TownList.list();
		empery();
		
		down();
	}
	
	public class Key extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Menu.g.setLocation(Menu.g.getX() + 70, Menu.g.getY());
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Menu.g.setLocation(Menu.g.getX() - 70, Menu.g.getY());
			}
			if (e.getKeyCode() == KeyEvent.VK_0) {
				for (int i = 0; i < town.get(0).line.size(); i++) {
					System.out.println(town.get(0).line.get(i).lineNumber);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				System.exit(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				downCenter.locked();
				CenterPanel.focus = false;
				RigthPanel.name = null;
				downInf.inform.repaint();
			}
		}
	}
	
	private void down() {
		downPanel.setBounds(0, 512, 1000, 188);
		downPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
		jlp.add(downPanel, new Integer(10));
		
		miniMap.setBounds(0, 4, 200, 184);
		miniMap.setBackground(new Color(34, 70, 98));
		miniMap.setOpaque(true);
		downPanel.add(miniMap);
		
		downCenter = new CenterPanel();
		downCenter.setBounds(200, 4, 600, 184);
		downCenter.setBackground(new Color(150, 75, 0));
		downCenter.setOpaque(true);
		downPanel.add(downCenter);
		
		downInf = new RigthPanel();
		downInf.setBounds(800, 4, 200, 184);
		downInf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		downInf.setBackground(new Color(150, 75, 0));
		downInf.setOpaque(true);
		downPanel.add(downInf);
	}
	
	private void init() {
		int x = 128 * (-0), y = 128 * (-0);
		for (int i = 0; i < bcTiles.length; i++) {
			for (int j = 0; j < bcTiles[0].length; j++) {
				bcTiles[i][j] = new BcTile(i, j);
				bcTiles[i][j].setBounds(x, y, 128, 128);
				bcTiles[i][j].setIcon(Images.grass);
				//base.add(bcTiles[i][j]);
				jlp.add(bcTiles[i][j], new Integer(1));
				x += 128;
			}
			
			x = 128 * (-0);
			y += 128;
		}
	}
	
	private void empery() {
		emp.add(new Empery(0).setName("������ �������"));
		emp.add(new Empery(1).setName("������� �������"));
		emp.add(new Empery(2).setName("��������� �������"));
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
		jlp.repaint();
	}
	
	public class Mouse implements MouseListener, MouseMotionListener {
		boolean in, over;
		private void move(int direction) {
			new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					for (int i = 0; i <= 50; i++) {
						if (in == true) {
							in = false;
							break;
						}
						if (direction == 1) {
							//���� ��������� ������ ���� �� 32 ������� ����� (� ������ ������)
							for (int z = 0; z < bcTiles.length; z++) {
								for (int f = 0; f < bcTiles[0].length; f++) {
									if (bcTiles[14][bcTiles.length-1].getX() < (bcTiles[0].length - 1) * 128) {
										//��������� �� ��������� �� ����. ���� �� ������� (��� ����� ������ ������� ���� � �����)
										bcTiles[z][f].setLocation(bcTiles[z][f].getX() + 32, bcTiles[z][f].getY());
									} else {
										break;
									}
								}
							}
						} else if (direction == 2) {
							for (int z = 0; z < bcTiles.length; z++) {
								for (int f = 0; f < bcTiles[0].length; f++) {
									if (bcTiles[bcTiles.length-1][14].getY() < (bcTiles.length-1) * 128) {
										//1792
										bcTiles[z][f].setLocation(bcTiles[z][f].getX(), bcTiles[z][f].getY() + 32);
									} else {
										break;
									}
								}
							}
						} else if (direction == 3) {
							for (int z = 0; z < bcTiles.length; z++) {
								for (int f = 0; f < bcTiles[0].length; f++) {
									if (bcTiles[14][bcTiles.length-1].getX() > 872) {
										bcTiles[z][f].setLocation(bcTiles[z][f].getX() - 32, bcTiles[z][f].getY());
									} else {
										break;
									}
								}
							}
						} else if (direction == 4) {
							for (int z = 0; z < bcTiles.length; z++) {
								for (int f = 0; f < bcTiles[0].length; f++) {
									if (bcTiles[bcTiles.length-1][14].getY() > 384) {
										//-1440
										bcTiles[z][f].setLocation(bcTiles[z][f].getX(), bcTiles[z][f].getY() - 32);
									} else {
										break;
									}
								}
							}
						}
						Sleep.sleep(20);
					}
					over = false;
					return null;
				}
				
			}.execute();
		}
		
		//MouseListener
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if (over == true) {
				over = false;
				in = true;
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getComponent() == jlp) {
				over = true;
				if (e.getX() < 100 & e.getY() > 10 & e.getY() < 660) {
					//< 500
					move(1); //������ �����
				}
				if (e.getY() < 100 & e.getX() > 10 & e.getX() < 990) {
					//<350
					move(2); //������ �����
				}
				if (e.getX() > 950 & e.getY() > 10 & e.getY() < 660) {
					//>500
					move(3); //������ ������
				}
				if (e.getY() > 650 & e.getX() > 10 & e.getX() < 990) {
					//>350
					move(4); //������ ����
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		//MouseMotionListener
		@Override
		public void mouseDragged(MouseEvent e) {
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
	
}
