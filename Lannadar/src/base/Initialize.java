package base;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import inventory.InventList;

public class Initialize {
	
	public static Font uph, smw;
	
	static Image bc, icon, startI, start2I, continuedI,
	editorI, aboutI, settingsI, exitI, logo; static ImageIcon alpha, beta;
	
	public Initialize() {
		initImage();
		initFont();
		initFunc();
	}
	
	private void initImage() {
		new TilesImage();
		new Player().init();
		menuImage();
		new inventory.InitImage();
	}
	
	String file = "res/Image/";
	private void menuImage() {
		bc = new ImageIcon(getClass().getResource(file + "menu/bc.png")).getImage();
		icon = new ImageIcon(getClass().getResource(file + "icon.png")).getImage();
		startI = new ImageIcon(getClass().getResource(file + "menu/start.png")).getImage();
		start2I = new ImageIcon(getClass().getResource(file + "menu/start2.png")).getImage();
		continuedI = new ImageIcon(getClass().getResource(file + "menu/continued.png")).getImage();
		editorI = new ImageIcon(getClass().getResource(file + "menu/editor.png")).getImage();
		aboutI = new ImageIcon(getClass().getResource(file + "menu/about.png")).getImage();
		settingsI = new ImageIcon(getClass().getResource(file + "menu/settings.png")).getImage();
		exitI = new ImageIcon(getClass().getResource(file + "menu/exit.png")).getImage();
		alpha = new ImageIcon(getClass().getResource(file + "menu/alpha.png"));
		beta = new ImageIcon(getClass().getResource(file + "menu/beta.png"));
		logo = new ImageIcon(getClass().getResource(file + "menu/logo.png")).getImage();
	}
	
	private void initFont() {
		try {
			InputStream s = getClass().getResourceAsStream("res/Font/UpheavalPro.ttf");
			InputStream s2 = getClass().getResourceAsStream("res/Font/SMW_Text_NC.ttf");
			uph = Font.createFont(Font.TRUETYPE_FONT, s).deriveFont(22F);
			smw = Font.createFont(Font.TRUETYPE_FONT, s2).deriveFont(22F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initFunc() {
		new InventList();
	}
	
}
