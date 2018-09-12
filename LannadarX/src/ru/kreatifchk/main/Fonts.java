package ru.kreatifchk.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {
	
	public static Font digitalThin, chemuRetro, harpseal;
	
	public Fonts() {
		InputStream is;
		try {
			is = Main.class.getResourceAsStream("/ru/kreatifchk/res/font/Digital_Thin.ttf");
			digitalThin = Font.createFont(Font.TRUETYPE_FONT, is);
			
			is = Main.class.getResourceAsStream("/ru/kreatifchk/res/font/ChemuRetro.ttf");
			chemuRetro = Font.createFont(Font.TRUETYPE_FONT, is);
			
			is = Main.class.getResourceAsStream("/ru/kreatifchk/res/font/Harpseal.otf");
			harpseal = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
