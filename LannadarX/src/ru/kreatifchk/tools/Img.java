package ru.kreatifchk.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import ru.kreatifchk.main.Main;

public class Img {
	
	/** Инициализация изображения подстраивающаяся под разрешение экрана */
	public static Image Image(URL location) {
		Image img = new ImageIcon(location).getImage();
		img = Resize.resizeA(img, (int)(img.getWidth(null) * Main.INC), (int)(img.getHeight(null) * Main.INC));
		return img;
	}
	
	/** Инициализация изображения с изменением размера в width по ширине height по высоте раз */
	public static Image Image(URL location, float width, float height) {
		Image img = new ImageIcon(location).getImage();
		img = Resize.resizeA(img, (int)(img.getWidth(null) * width), (int)(img.getHeight(null) * height));
		return img;
	}
	
	/** Инициализация иконки подстраивающаяся под разрешение экрана */
	public static ImageIcon ImageIcon(URL location) {
		Image im = new ImageIcon(location).getImage();
		ImageIcon img = Resize.resizeIcon(im, (int)(im.getWidth(null) * Main.INC), (int)(im.getHeight(null) * Main.INC));
		return img;
	}
	
	/** Инициализация иконки с изменением размера в width по ширине height по высоте раз */
	public static ImageIcon ImageIcon(URL location, float width, float height) {
		Image im = new ImageIcon(location).getImage();
		ImageIcon img = Resize.resizeIcon(im, (int)(im.getWidth(null) * width), (int)(im.getHeight(null) * height));
		return img;
	}
	
	/** Инициализация указанной иконки с изменением размера в width по ширине height по высоте раз */
	public static ImageIcon ImageIcon(ImageIcon ic, float width, float height) {
		Image im = ic.getImage();
		ImageIcon img = Resize.resizeIcon(im, (int)(im.getWidth(null) * width), (int)(im.getHeight(null) * height));
		return img;
	}
	
	/** Инициализация прозрачных изображений */
	public static Image imageTransparent(URL location, int trans) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(location);
		} catch (IOException e) {}
		
		int transparency = trans; //0-255, 0 is invisible, 255 is opaque
		int colorMask = 0x00FFFFFF; //AARRGGBB
		int alphaShift = 24;
		for(int y = 0; y < im.getHeight(); y++) {
			for(int x = 0; x < im.getWidth(); x++) {
				im.setRGB(x, y, (im.getRGB(x, y) & colorMask) | (transparency << alphaShift));
		    }
		}
		
		Image img = im.getScaledInstance((int)(im.getWidth()*Main.INC), (int)(im.getHeight()*Main.INC), java.awt.Image.SCALE_DEFAULT);
		return img;
	}
	
	/** Инициализация прозрачных изображений */
	public static ImageIcon iconTransparent(URL location, int trans) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(location);
		} catch (IOException e) {}
		
		int transparency = trans; //0-255, 0 is invisible, 255 is opaque
		int colorMask = 0x00FFFFFF; //AARRGGBB
		int alphaShift = 24;
		for(int y = 0; y < im.getHeight(); y++) {
			for(int x = 0; x < im.getWidth(); x++) {
				im.setRGB(x, y, (im.getRGB(x, y) & colorMask) | (transparency << alphaShift));
		    }
		}
		
		ImageIcon img = new ImageIcon(im.getScaledInstance((int)(im.getWidth()*Main.INC), (int)(im.getHeight()*Main.INC), java.awt.Image.SCALE_DEFAULT));
		return img;
	}
	
	/** Инициализация указанных прозрачных изображений (работает странно)*/
	/*public static ImageIcon iconTransparent(ImageIcon ic, int trans, boolean size) {
		BufferedImage im = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = im.createGraphics();
		g.drawImage(ic.getImage(), 0, 0, null);
		g.dispose();
		
		int transparency = trans; //0-255, 0 is invisible, 255 is opaque
		int colorMask = 0x00FFFFFF; //AARRGGBB
		int alphaShift = 24;
		for(int y = 0; y < im.getHeight(); y++) {
			for(int x = 0; x < im.getWidth(); x++) {
				im.setRGB(x, y, (im.getRGB(x, y) & colorMask) | (transparency << alphaShift));
		    }
		}
		
		//Изменять размер или нет
		ImageIcon img;
		if (size == true) {
			img = new ImageIcon(im.getScaledInstance((int)(im.getWidth()*Main.INC), (int)(im.getHeight()*Main.INC), java.awt.Image.SCALE_DEFAULT));
		} else {
			img = new ImageIcon(im.getScaledInstance(im.getWidth(), im.getHeight(), java.awt.Image.SCALE_DEFAULT));
		}
		return img;
	}*/
	
	/** Инициализация указанных прозрачных изображений (мой метод)*/
	public static ImageIcon iconTransparent(ImageIcon ic, int trans, boolean size) {
		Image im = ic.getImage();
		BufferedImage bf = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bf.createGraphics();
		g.drawImage(im, 0, 0, null);
		
		int x = 0, y = 0, rgb;
		Color cl;
		for (int i = 0; i < bf.getHeight(); i++) {
			for (int j = 0; j < bf.getWidth(); j++) {
				bf.getRGB(j, i);
				cl = new Color(bf.getRGB(j, i));
				rgb = new Color(cl.getRed(), cl.getGreen(), cl.getBlue(), trans).getRGB();
				bf.setRGB(x, y, rgb);
				x++;
			}
			x = 0;
			y++;
		}
		g.dispose();
		if (size == true) {
			return new ImageIcon(bf.getScaledInstance(bf.getWidth(), bf.getHeight(), BufferedImage.TYPE_INT_ARGB));
		} else {
			return new ImageIcon(bf.getScaledInstance(bf.getWidth(), bf.getHeight(), BufferedImage.TYPE_INT_ARGB));
		}
	}
	
}
