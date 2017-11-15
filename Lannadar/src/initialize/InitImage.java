package initialize;

import java.awt.Image;

import javax.swing.ImageIcon;

import base.Game;
import base.Player;
import base.Resize;

public class InitImage {
	
	public static Image bc, icon, startI;
	public static Image start2I, continuedI,
	editorI, aboutI, settingsI, exitI, logo;
	public static ImageIcon alpha, beta;
	
	public static Image frameQwestA, frameQwestD;
	
	public InitImage() {
		menuImage();
		panelImage();
		new TilesImage();
		new Player().init();
		new inventory.InitImage();
	}
	
	String file = "res/Image/";
	private void menuImage() {
		bc = new ImageIcon(Game.class.getResource(file + "menu/bc.png")).getImage();
		icon = new ImageIcon(Game.class.getResource(file + "icon.png")).getImage();
		startI = new ImageIcon(Game.class.getResource(file + "menu/start.png")).getImage();
		start2I = new ImageIcon(Game.class.getResource(file + "menu/start2.png")).getImage();
		continuedI = new ImageIcon(Game.class.getResource(file + "menu/continued.png")).getImage();
		editorI = new ImageIcon(Game.class.getResource(file + "menu/editor.png")).getImage();
		aboutI = new ImageIcon(Game.class.getResource(file + "menu/about.png")).getImage();
		settingsI = new ImageIcon(Game.class.getResource(file + "menu/settings.png")).getImage();
		exitI = new ImageIcon(Game.class.getResource(file + "menu/exit.png")).getImage();
		alpha = new ImageIcon(Game.class.getResource(file + "menu/alpha.png"));
		beta = new ImageIcon(Game.class.getResource(file + "menu/beta.png"));
		logo = new ImageIcon(Game.class.getResource(file + "menu/logo.png")).getImage();
	}
	
	private static void panelImage() {;
		frameQwestA = new ImageIcon(Game.class.getResource("res/others/frameQwest.png")).getImage();
		frameQwestD = new ImageIcon(Game.class.getResource("res/others/frameQwestA.png")).getImage();
		frameQwestA = Resize.resizeA(frameQwestA, 428, 49);
		frameQwestD = Resize.resizeA(frameQwestD, 428, 49);
	}
	
}
