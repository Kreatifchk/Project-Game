package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.util.NoSuchElementException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import base.Editor;
import base.Game;
import base.Main;
import initialize.InitImage;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

@SuppressWarnings("serial")
public class Menu extends JFrame {
	
	Image bc = InitImage.bc;
	Image icon = InitImage.icon;
	Image startI = InitImage.startI;
	Image start2I = InitImage.start2I;
	Image continuedI = InitImage.continuedI;
	Image editorI = InitImage.editorI;
	Image aboutI = InitImage.aboutI;
	Image settingsI = InitImage.settingsI;
	Image exitI = InitImage.exitI;
	ImageIcon alphaI = InitImage.alpha;
	ImageIcon betaI = InitImage.beta;
	static Image logoI = InitImage.logo;
	
	SButton start = new SButton();
	CButton continued = new CButton();
	EButton editor = new EButton();
	JButton history = new JButton("История версий");
	AButton about = new AButton();
	StButton settings = new StButton();
	ExButton exit = new ExButton();
	
	MenuListener ml = new MenuListener();
	ButtonListener bl = new ButtonListener();
	
	public static BasicPlayer player = new BasicPlayer();
	
	final static JLabel VERSION = new JLabel("Версия: 0.5.5");
	static JLabel logo = new LogoLabel();
	//JLabel beta = new BetaLabel();
	//JLabel alpha = new AlphaLabel();
	JLabel alpha = new JLabel();
	
	//static JFrame g;
	public static Game g;
	public static Editor ed;
	
	static boolean restartMusic = false;
	
	public Menu() {
		super("Lannadar");
		
		if (restartMusic == false) {
			music();
			restartMusic = true;
		}
		
		setLayout(null);
		setIconImage(icon);
		JPanel jp = new MenuPanel();
		jp.setLayout(null);
		jp.setBounds(0, 0, 600, 541);
		Font font = new Font("Verdana", Font.BOLD, 20);
		start.setBounds(215, 105, 155, 35); //220, 110, 150 30
		start.setOpaque(false);
		start.setBorderPainted(false);
		continued.setBounds(220, 170, 150, 30); //220, 170
		continued.setOpaque(false);
		continued.setBorderPainted(false);
		editor.setBounds(220, 230, 150, 30); //220, 230
		editor.setOpaque(false);
		editor.setBorderPainted(false);
		history.setBounds(220, 310, 150, 30); //220, 310
		about.setBounds(220, 370, 150, 30); //220, 370
		about.setOpaque(false);
		about.setBorderPainted(false);
		settings.setBounds(220, 430, 150, 30); //220, 430
		settings.setOpaque(false);
		settings.setBorderPainted(false);
		exit.setBounds(220, 490, 150, 30); //220, 490
		exit.setOpaque(false);
		exit.setBorderPainted(false);

		//start.addActionListener(ml);
		start.addMouseListener(bl);
		continued.addActionListener(ml);
		editor.addActionListener(ml);
		history.addActionListener(ml);
		about.addActionListener(ml);
		settings.addActionListener(ml);
		exit.addActionListener(ml);
		
		//Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		logo.setBounds(110, 10, 370, 90);
		//logo.setBorder(border);
		//beta.setBounds(410, 65, 150, 90);
		alpha.setBounds(410, 65, 150, 90);
		alpha.setIcon(alphaI);
		
		VERSION.setBounds(415, 490, 200, 30);
		VERSION.setFont(font);
		VERSION.setForeground(Color.CYAN);
		add(jp);
		jp.add(start);
		jp.add(continued);
		jp.add(editor);
		if (Main.history == true) {
			jp.add(history);
		}
		jp.add(about);
		jp.add(settings);
		jp.add(exit);
		jp.add(VERSION);
		jp.add(logo);
		//jp.add(beta);
		jp.add(alpha);
	}
	
	private class MenuPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(bc, 0, 0, null);
		}
	}
	
	public class SButton extends JButton {
		Image active = startI;
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(active, 0, 0, null);
		}
	}
	
	public class CButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(continuedI, 0, 0, null);
		}
	}
	
	public class EButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(editorI, 0, 0, null);
		}
	}
	
	public class AButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(aboutI, 0, 0, null);
		}
	}
	
	public class StButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(settingsI, 0, 0, null);
		}
	}
	
	public class ExButton extends JButton {
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(exitI, 0, 0, null);
		}
	}
	
	public class ButtonListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == start) {
				start.active = start2I;
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getSource() == start) {
				start.active = startI;
				
				g = new Game(false, false);
				
				dispose();
				try {
					player.stop();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
				//Game g = new Game();
				g.setVisible(true);
				g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				g.setSize(822, 704); //726, 701
				g.setResizable(false);
				g.setLocationRelativeTo(null);
			}
		}
	}
	
	public class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getSource());
			if (e.getSource() == start) {
				g = new Game(false, false);
				
				dispose();
				try {
					player.stop();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
				//Game g = new Game();
				g.setVisible(true);
				g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				g.setSize(822, 701);//726, 701
				g.setResizable(false);
				g.setLocationRelativeTo(null);
			}
			if (e.getSource() == continued) {
				try {
					player.stop();
					g = new Game(false, true);
					dispose();
					g.setVisible(true);
					g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					g.setSize(822, 704);//726, 701
					g.setResizable(false);
					g.setLocationRelativeTo(null);
				} catch (NoSuchElementException ex) {
					JOptionPane.showMessageDialog(null, "Не найдены сохранения");
				}  catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource() == editor) {
				dispose();
				try {
					player.stop();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
				ed = new Editor();
				ed.setVisible(true);
				ed.setSize(822, 695); //726, 695(675)
				ed.setDefaultCloseOperation(EXIT_ON_CLOSE);
				ed.setResizable(false);
				ed.setLocationRelativeTo(null);
			}
			if (e.getSource() == history) {
				dispose();
				History hs = new History();
				hs.setVisible(true);
				hs.setSize(600, 541); //600 541
				hs.setDefaultCloseOperation(EXIT_ON_CLOSE);
				hs.setLocationRelativeTo(null);
				hs.setResizable(false);
			}
			if (e.getSource() == about) {
				dispose();
				About ab = new About();
				ab.setVisible(true);
				ab.setSize(600, 541); //600 541
				ab.setDefaultCloseOperation(EXIT_ON_CLOSE);
				ab.setLocationRelativeTo(null);
				ab.setResizable(false);
			}
			if (e.getSource() == settings) {
				dispose();
				Settings st = new Settings();
				st.setVisible(true);
				st.setSize(600, 541);
				st.setDefaultCloseOperation(EXIT_ON_CLOSE);
				st.setLocationRelativeTo(null);
				st.setResizable(false);
			}
			if (e.getSource() == exit) {
				System.exit(0);
			}
		}
	}
	
	private void music() {
		try {
			player.open(new BufferedInputStream(Game.class.getResourceAsStream("res/music/Antarctica.mp3")));
			player.play();
			player.setGain(0.30);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	private static class LogoLabel extends JLabel{
		public void paintComponent(Graphics g) {
			//logoI = new ImageIcon(getClass().getResource("res/logo.png")).getImage();
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(logoI, 0, 0, null);
		}
	}
	
}
