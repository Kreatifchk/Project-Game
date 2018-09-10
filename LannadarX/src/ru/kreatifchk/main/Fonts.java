package ru.kreatifchk.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {
	
	public static Font digitalThin;
	
	public Fonts() {
		InputStream is = Main.class.getResourceAsStream("/ru/kreatifchk/res/font/Digital_Thin.ttf");
		try {
			digitalThin = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
