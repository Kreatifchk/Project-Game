package ru.kreatifchk.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.kreatifchk.editor.Editor;
import ru.kreatifchk.game.Game;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Img;

@SuppressWarnings("serial")
public class Menu extends JFrame implements MouseListener {
	
	Image background = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/backgroundMenu.png"));
	Image buttonI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/buttonMenu.png"));
	Image buttonEntered = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/buttonEntered.png"));
	Image buttonPressed = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/buttonPressed.png"));
	Image startI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/start.png"));
	Image continuedI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/continued.png"));
	Image editorI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/editor.png"));
	Image aboutI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/about.png"));
	Image settingsI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/settings.png"));
	Image exitI = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/menu/exit.png"));
	public static Image icon = new ImageIcon(Main.class.getResource("/ru/kreatifchk/res/image/icon.png")).getImage();
	
	MainPanel pan = new MainPanel();
	
	JButton start = new MenuButton(0);
	JButton continued = new MenuButton(1);
	JButton editor = new MenuButton(2);
	JButton about = new MenuButton(3);
	JButton settings = new MenuButton(4);
	JButton exit = new MenuButton(5);
	
	JLabel am = new JLabel();
	
	public Menu() {
		setTitle("Lannadar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.WIDTH, Main.HEIGHT);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setIconImage(icon);
		
		pan.setBounds(0, 0, getWidth(), getHeight());
		pan.setOpaque(true);
		pan.setBackground(Color.blue);
		pan.setLayout(null);
		add(pan);
		
		start.setSize(getHeight()/3, getHeight()/10);
		continued.setSize(getHeight()/3, getHeight()/10);
		editor.setSize(getHeight()/3, getHeight()/10);
		about.setSize(getHeight()/3, getHeight()/10);
		settings.setSize(getHeight()/3, getHeight()/10);
		exit.setSize(getHeight()/3, getHeight()/10);
		start.setBorderPainted(false);
		continued.setBorderPainted(false);
		editor.setBorderPainted(false);
		about.setBorderPainted(false);
		settings.setBorderPainted(false);
		exit.setBorderPainted(false);
		start.setOpaque(false);
		continued.setOpaque(false);
		editor.setOpaque(false);
		about.setOpaque(false);
		settings.setOpaque(false);
		exit.setOpaque(false);
		Center.cnt(start, this, (int)(35*Main.INC));
		Center.cnt(continued, this, (int)(142*Main.INC));
		Center.cnt(editor, this, (int)(249*Main.INC));
		Center.cnt(about, this, (int)(356*Main.INC));
		Center.cnt(settings, this, (int)(463*Main.INC));
		Center.cnt(exit, this, (int)(570*Main.INC));
		pan.add(start);
		pan.add(continued);
		pan.add(editor);
		pan.add(about);
		pan.add(settings);
		pan.add(exit);
	}
	
	private class MainPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(background, 0, 0, null);
		}
	}
	
	private class MenuButton extends JButton {
		int number;
		boolean entered = false, pressed = false;
		public MenuButton(int number) {
			this.number = number;
			addMouseListener(Menu.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if (pressed == true) {
				g2d.drawImage(buttonPressed, 0, 0, null);
			}
			else if (entered == true) {
				g2d.drawImage(buttonEntered, 0, 0, null);
			} else {
				g2d.drawImage(buttonI, 0, 0, null);
			}
			
			if (number == 0) {
				g2d.drawImage(startI, 0, 0, null);
			}
			if (number == 1) {
				g2d.drawImage(continuedI, 0, 0, null);
			}
			if (number == 2) {
				g2d.drawImage(editorI, 0, 0, null);
			}
			if (number == 3) {
				g2d.drawImage(aboutI, 0, 0, null);
			}
			if (number == 4) {
				g2d.drawImage(settingsI, 0, 0, null);
			}
			if (number == 5) {
				g2d.drawImage(exitI, 0, 0, null);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent a) {
		MenuButton mb = (MenuButton) a.getComponent();
		mb.entered = true;
	}
	@Override
	public void mouseExited(MouseEvent a) {
		MenuButton mb = (MenuButton) a.getComponent();
		mb.entered = false;
	}
	@Override
	public void mousePressed(MouseEvent a) {
		MenuButton mb = (MenuButton) a.getComponent();
		mb.pressed = true;
	}
	@Override
	public void mouseReleased(MouseEvent a) {
		MenuButton mb = (MenuButton) a.getComponent();
		mb.pressed = false;
		
		if (mb.number == 0) {
			setVisible(false);
			new Game(false);
			dispose();
		}
		if (mb.number == 2) {
			new Editor();
			setVisible(false);
			dispose();
		}
		if (mb.number == 5) {
			System.exit(0);
		}
	}
	
}
